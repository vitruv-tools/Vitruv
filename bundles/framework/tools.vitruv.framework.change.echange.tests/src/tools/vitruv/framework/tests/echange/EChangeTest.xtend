package tools.vitruv.framework.tests.echange

import allElementTypes.AllElementTypesFactory
import allElementTypes.Identified
import allElementTypes.Root
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import tools.vitruv.framework.change.echange.util.StagingArea

/**
 * Default class for testing EChange changes.
 * Prepares two temporary model instances of the allelementtypes metamodel which 
 * can be modified by the EChange tests. The model is stored in one temporary file.
 */
 public abstract class EChangeTest {

 	protected var Root rootObject = null
 	protected var Resource resource = null
 	protected var Resource stagingArea = null
 	protected var ResourceSet resourceSet = null
 	
 	protected var TypeInferringAtomicEChangeFactory atomicFactory = null
 	protected var TypeInferringCompoundEChangeFactory compoundFactory = null
 	
 	protected URI fileUri = null
 	protected URI stagingResourceName = null
 	
 	protected static val METAMODEL = "allelementtypes"
 	protected static val MODEL_FILE_NAME = "model"
 	
 	protected static val STAGING_AREA_EXTENSION = "staging"
 	protected static val STAGING_AREA_FILE_NAME = "stagingArea"
 	
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
		stagingResourceName = URI.createFileURI(StagingArea.DEFAULT_RESOURCE_NAME)
 		
 		// Create model
 		resourceSet = new ResourceSetImpl()
 		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(METAMODEL, new XMIResourceFactoryImpl())
 		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(StagingArea.DEFAULT_RESOURCE_EXTENSION, new XMIResourceFactoryImpl())
 		resource = resourceSet.createResource(fileUri)
 		
 		rootObject = AllElementTypesFactory.eINSTANCE.createRoot()
 		resource.getContents().add(rootObject)
 		
 		resource.save(null)
 		
 		// Create staging area for resource set 1
 		stagingArea = resourceSet.createResource(stagingResourceName)
 		
 		// Factorys for creating changes
 		atomicFactory = TypeInferringUnresolvingAtomicEChangeFactory.instance
 		compoundFactory = TypeInferringUnresolvingCompoundEChangeFactory.instance
 	}
 	
 	/**
 	 * Clears the staging areas of the resource sets
 	 * and the object in progress.
 	 */
 	@After
 	def void afterTest() {
		stagingArea.contents.clear
 	}
 	
 	/**
 	 * Tests whether a unresolved change and a resolved change are the same class.
 	 */
 	def protected static void assertDifferentChangeSameClass(EChange unresolvedChange, EChange resolvedChange)	 {
 		Assert.assertFalse(unresolvedChange.isResolved)
 		Assert.assertTrue(resolvedChange.isResolved)
 		Assert.assertFalse(unresolvedChange == resolvedChange)
 		Assert.assertEquals(unresolvedChange.getClass, resolvedChange.getClass)
 	}
 	 	
 	/**
 	 * Tests whether two objects are not the same object, but a copy with the same id.
 	 */
 	def protected static void assertIsCopy(Identified object1,  Identified object2) {
 		Assert.assertTrue(object1 != object2)
 		Assert.assertEquals(object1.id, object2.id)
 	}
 	
 	/**
 	 * Tests whether two objects are the same object or copies of each other.
 	 */
 	def protected static void assertEqualsOrCopy(Identified object1, Identified object2) {
		if (object1 != object2) {
			object1.assertIsCopy(object2)
		}
	}
 }