package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjavaejb

import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm.JaMoPP2PCMTransformationTest
import org.emftext.language.java.classifiers.ConcreteClassifier
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.CompilationUnitManipulatorHelper

/**
 * class that contains special methods for EJB testing
 */
abstract class EJBJaMoPP2PCMTransformationTest extends JaMoPP2PCMTransformationTest {
	
	private static val String STATELESS_ANNOTATION_NAME = "Stateless"
	private static val String REMOTE_ANNOTATION_NAME = "Remote"
	protected static val String EJB_FIELD_ANNOTATION_NAME = "EJB"
	
	protected static val String TEST_CLASS_NAME = "TestEJBClass"
	protected static val String TEST_INTERFACE_NAME = "TestEJBInterface"
	protected static val String TEST_FIELD_NAME = "testEJBfield"
	
	def protected createEJBClass(String className) {
		val ConcreteClassifier classifier = super.createClassInPackage(this.mainPackage, className) as ConcreteClassifier 
		val BasicComponent correspondingBasicComponent = this.addAnnotationToClassifier(classifier, STATELESS_ANNOTATION_NAME, BasicComponent, className)
		correspondingBasicComponent
	}
	
	def protected createEJBInterface(String interfaceName) {
		val ConcreteClassifier classifier = super.createInterfaceInPackage(mainPackage.name, interfaceName) as ConcreteClassifier 
		val OperationInterface correspondingOpInterface = this.addAnnotationToClassifier(classifier, REMOTE_ANNOTATION_NAME, 
				OperationInterface, interfaceName)
		correspondingOpInterface
	}
	
	def createPackageEJBClassAndInterface(){
		createPackageAndEJBInterface()
		return this.createEJBClass(TEST_CLASS_NAME) 
	}
	
	def createPackageAndEJBInterface() {
		super.addRepoContractsAndDatatypesPackage()
		this.createEJBInterface(TEST_INTERFACE_NAME)
	}
	

	
}