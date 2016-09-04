package edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.ejbtransformations.java2pcm

import org.emftext.language.java.classifiers.ConcreteClassifier
import org.palladiosimulator.pcm.repository.BasicComponent
import org.palladiosimulator.pcm.repository.OperationInterface
import edu.kit.ipd.sdq.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransformingProviding
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.tests.util.JaMoPP2PCMTransformationTest
import edu.kit.ipd.sdq.vitruvius.applications.pcmjava.ejbtransformations.java2pcm.change2commandtransforming.Change2CommandTransformingEJBJavaToPCM

/**
 * class that contains special methods for EJB testing
 */
abstract class EJBJaMoPP2PCMTransformationTest extends JaMoPP2PCMTransformationTest {
	
	public static val String STATELESS_ANNOTATION_NAME = "Stateless"
	public static val String REMOTE_ANNOTATION_NAME = "Remote"
	public static val String EJB_FIELD_ANNOTATION_NAME = "EJB"
	
	protected static val String TEST_CLASS_NAME = "TestEJBClass"
	protected static val String TEST_INTERFACE_NAME = "TestEJBInterface"
	protected static val String TEST_FIELD_NAME = "testEJBfield"
	
	new() {
		super([ | 
			AbstractChange2CommandTransformingProviding.createChange2CommandTransformingProviding(
				#[new Change2CommandTransformingEJBJavaToPCM()])
		]);
	}
	
	def protected createEJBClass(String className) {
		val ConcreteClassifier classifier = super.createClassInPackage(this.mainPackage, className) as ConcreteClassifier 
		val BasicComponent correspondingBasicComponent = this.addAnnotationToClassifier(classifier, STATELESS_ANNOTATION_NAME, BasicComponent, className)
		correspondingBasicComponent
	}
	
	def protected createEJBInterface(String interfaceName) {
		val ConcreteClassifier classifier = super.createJaMoPPInterfaceInPackage(mainPackage.name, interfaceName) as ConcreteClassifier 
		val OperationInterface correspondingOpInterface = this.addAnnotationToClassifier(classifier, REMOTE_ANNOTATION_NAME, 
				OperationInterface, interfaceName)
		correspondingOpInterface
	}
	
	def protected createPackageEJBClassAndInterface(){
		createPackageAndEJBInterface()
		return this.createEJBClass(TEST_CLASS_NAME) 
	}
	
	def protected createPackageAndEJBInterface() {
		super.addRepoContractsAndDatatypesPackage()
		this.createEJBInterface(TEST_INTERFACE_NAME)
	}
	
}