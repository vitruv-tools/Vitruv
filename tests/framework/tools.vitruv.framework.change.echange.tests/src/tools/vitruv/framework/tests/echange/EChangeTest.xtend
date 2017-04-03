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
import tools.vitruv.framework.change.echange.resolve.StagingArea
import org.eclipse.emf.ecore.EObject

/**
 * Default class for testing EChange changes.
 * Prepares two temporary model instances of the allelementtypes metamodel which 
 * can be modified by the EChange tests. The model is stored in one temporary file.
 */
 public abstract class EChangeTest {

 	protected var Root rootObject = null
 	protected var Resource resource = null
 	protected var StagingArea stagingArea = null
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
 		
 		// Create model
 		resourceSet = new ResourceSetImpl()
 		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(METAMODEL, new XMIResourceFactoryImpl())
 		resource = resourceSet.createResource(fileUri)
 		
 		rootObject = AllElementTypesFactory.eINSTANCE.createRoot()
 		resource.getContents().add(rootObject)
 		
 		resource.save(null)
 		
 		// Create staging area for resource set 1
 		stagingArea = StagingArea.getStagingArea(resourceSet)
 		
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
		stagingArea.clear
 	}
 	
	/**
	 * Prepares the staging area and the object which is in progress
	 * for a test. Inserts a new element to the staging area which will 
	 * be used in the test.
	 * @param object The EObject which will be inserted in the staging area. 
	 * 		Clears and sets the 0th element.
	 */
	protected def void prepareStagingArea(EObject object) {
		if (object != null) {
			stagingArea.add(object)			
		}
	}
 }