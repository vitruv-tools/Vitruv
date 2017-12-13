package tools.vitruv.framework.tests.echange

import allElementTypes.AllElementTypesFactory
import allElementTypes.Root
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolverImpl
import tools.vitruv.framework.change.uuid.UuidGeneratorAndResolver
import java.util.List
import tools.vitruv.framework.change.echange.EChange
import org.junit.Assert
import tools.vitruv.framework.change.uuid.UuidResolver
import static extension tools.vitruv.framework.change.echange.resolve.EChangeResolverAndApplicator.*

/**
 * Default class for testing EChange changes.
 * Prepares two temporary model instances of the allelementtypes metamodel which 
 * can be modified by the EChange tests. The model is stored in one temporary file.
 */
 public abstract class EChangeTest {

 	protected var Root rootObject = null
 	protected var Resource resource = null
 	protected var ResourceSet resourceSet = null
 	protected var UuidGeneratorAndResolver uuidGeneratorAndResolver = null
 	
 	protected var TypeInferringAtomicEChangeFactory atomicFactory = null
 	protected var TypeInferringCompoundEChangeFactory compoundFactory = null
 	
 	protected URI fileUri = null
 	
 	protected static val METAMODEL = "allelementtypes"
 	protected static val MODEL_FILE_NAME = "model"
 	
 	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder()
	
	/**
	 * Sets up a new model and the two model instances before every test.
	 * The model is stored in a temporary file with filename {@link MODEL_FILE_NAME} 
	 * with extension {@link METAMODEL}. The folder is accessible by the attribute {@link testFolder}.
	 * The two model instances are stored in the two {@link ResourceSet} attributes {@link resourceSet1} and
	 * {@link resourceSet2}.
	 */
 	@Before
 	def void beforeTest() {
 		// Setup Files
 		var modelFile = testFolder.newFile(MODEL_FILE_NAME + "." + METAMODEL)
		fileUri = URI.createFileURI(modelFile.getAbsolutePath())
 		
 		// Create model
 		resourceSet = new ResourceSetImpl()
 		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(METAMODEL, new XMIResourceFactoryImpl())
 		resource = resourceSet.createResource(fileUri)
 		
 		rootObject = AllElementTypesFactory.eINSTANCE.createRoot()
 		resource.getContents().add(rootObject)
 		
 		resource.save(null)
 		
 		// Factorys for creating changes
 		this.uuidGeneratorAndResolver = new UuidGeneratorAndResolverImpl(resourceSet)
 		atomicFactory = new TypeInferringUnresolvingAtomicEChangeFactory(uuidGeneratorAndResolver);
 		compoundFactory = new TypeInferringUnresolvingCompoundEChangeFactory(uuidGeneratorAndResolver);
 	}
 	
 	/**
 	 * Clears the staging areas of the resource sets
 	 * and the object in progress.
 	 */
 	@After
 	def void afterTest() {
 	}
 	
 	/**
	 * Assert that change is not resolved.
	 */
	def protected static void assertIsNotResolved(List<? extends EChange> changes) {
		for (change : changes) {
			Assert.assertFalse(change.isResolved);
			for (involvedObject : change.involvedEObjects) {
				Assert.assertNull(involvedObject);	
			}
		}
	}
	
	/**
	 * Assert that change is resolved.
	 */
	def protected static void assertIsResolved(List<? extends EChange> changes) {
		for (change : changes) {
			Assert.assertTrue(change.isResolved);
			for (involvedObject : change.involvedEObjects) {
				Assert.assertNotNull(involvedObject);	
			}
		}
	}
	
	static def protected List<EChange> resolveBefore(List<? extends EChange> changes, UuidResolver uuidResolver) {
		val result = newArrayList
		for (change : changes) {
			result += change.resolveBefore(uuidResolver);
		}
		return result;
	}
	
	static def protected List<EChange> resolveAfter(List<? extends EChange> changes, UuidResolver uuidResolver) {
		val result = newArrayList
		for (change : changes.reverseView) {
			result.add(0, change.resolveAfter(uuidResolver));
		}
		return result;
	} 
	
	static def protected boolean applyBackward(List<EChange> changes) {
		assertIsResolved(changes);
		var result = true;
		for (change : changes.reverseView) {
			result = result && change.applyBackward
		}
		return result;
	}
	
	static def protected boolean applyForward(List<EChange> changes) {
		assertIsResolved(changes);
		var result = true;
		for (change : changes) {
			result = result && change.applyForward
		}
		return result;
	}
 }