package tools.vitruv.framework.tests.versioning

import tools.vitruv.framework.versioning.VersioningFactory
import org.eclipse.emf.ecore.resource.Resource
import tools.vitruv.framework.change.echange.resolve.StagingArea
import org.eclipse.emf.ecore.resource.ResourceSet
import tools.vitruv.framework.change.echange.TypeInferringAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringCompoundEChangeFactory
import org.eclipse.emf.common.util.URI
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingAtomicEChangeFactory
import tools.vitruv.framework.change.echange.TypeInferringUnresolvingCompoundEChangeFactory
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import java.io.IOException
import allElementTypes.AllElementTypesFactory

class RootTest {
	
 	protected var allElementTypes.Root rootObject = null
 	protected var Resource resource = null
 	protected var StagingArea stagingArea = null
 	protected var ResourceSet resourceSet = null
 	
 	protected var TypeInferringAtomicEChangeFactory atomicFactory = null
 	protected var TypeInferringCompoundEChangeFactory compoundFactory = null
 	
 	protected URI fileUri = null
 	protected URI stagingResourceName = null
 	
 	protected static val String METAMODEL = "allelementtypes"
 	protected static val String MODEL_FILE_NAME = "model"
 	
 	protected static val String STAGING_AREA_EXTENSION = "staging"
 	protected static val String STAGING_AREA_FILE_NAME = "stagingArea"
 	
 	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder()

	def void test() throws IOException {
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
 		
 		// Factories for creating changes
 		atomicFactory = TypeInferringUnresolvingAtomicEChangeFactory.instance
 		compoundFactory = TypeInferringUnresolvingCompoundEChangeFactory.instance
		val root = VersioningFactory.eINSTANCE.createRoot
		val repo = root.createRepository
		val authorA = root.createAuthor("Author", "NAme")
		authorA.switchToRepository(repo)
		authorA.createBranch("feature/test")
		
	}
}