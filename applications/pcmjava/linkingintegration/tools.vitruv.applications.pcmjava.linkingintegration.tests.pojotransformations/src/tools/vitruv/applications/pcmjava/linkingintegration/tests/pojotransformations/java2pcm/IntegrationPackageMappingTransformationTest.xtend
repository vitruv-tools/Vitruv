package tools.vitruv.applications.pcmjava.linkingintegration.tests.pojotransformations.java2pcm

import org.junit.Test
import org.palladiosimulator.pcm.repository.BasicComponent
import java.util.ArrayList
import tools.vitruv.applications.pcmjava.linkingintegration.tests.CodeIntegrationTestCBSNamespace

class IntegrationPackageMappingTransformationTest extends Java2PCMPackageIntegrationMappingTransformationTest{
	
	@Test
	def public void testAddPackageInIntegratedArea(){
		val String packagename = CodeIntegrationTestCBSNamespace.PACKAGE_NAME_OF_DISPLAY_COMPONENT + "." +NAME_OF_INTEGRATED_PACKAGE
		val packages = packagename.split("\\.")
		testAddPackage(NAME_OF_INTEGRATED_PACKAGE, packages)
			
	}
	
	@Test
	def public void testAddPackageInNonIntegratedArea(){
		testAddPackage(NAME_OF_NOT_INTEGRATED_PACKAGE)
	}	
	
	def private void testAddPackage(String name, String... namespace){
		val namespaceList = new ArrayList<String>()
		namespaceList.addAll(namespace)
		namespaceList.add(name)
		this.testUserInteractor.addNextSelections(0);
		val BasicComponent bc = super.createSecondPackage(BasicComponent, namespaceList);
		
		assertRepositoryAndPCMName(bc.repository__RepositoryComponent, bc, name);
		assertNoUserInteractingMessage
		
	} 
	
}