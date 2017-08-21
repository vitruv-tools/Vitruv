package tools.vitruv.extensions.dslruntime.commonalities

import edu.kit.ipd.sdq.activextendannotations.Utility
import org.eclipse.emf.ecore.EObject
import static tools.vitruv.extensions.dslruntime.commonalities.CommonalitiesConstants.*
import org.eclipse.emf.common.util.EList
import java.util.concurrent.atomic.AtomicInteger
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.EcoreFactory
import java.util.concurrent.atomic.AtomicLong

@Utility
class IntermediateModelUtil {
		
	static val stagingUuidCounter = new AtomicInteger
	
	// it’s very unlikely that two instances of this class will be created at
	// the same time, which is the only case in which this counter would
	// produce the same number twice  
	static val intermediateModelNameCounter = new AtomicLong(System.currentTimeMillis)
	
	def static addIntermediateModelNonRoot(EObject root, EObject newChild) {
		if (root.eClass.name != INTERMEDIATE_MODEL_ROOT_CLASS) {
			throw new IllegalArgumentException('''The EObject “«root»” provided as root is not an intermediate model root class!''')
		}
		checkIsNonRoot(newChild, 'new child')
		val containment = root.eClass.EAllContainments.findFirst[name == INTERMEDIATE_MODEL_ROOT_CLASS_CONTAINER_NAME]
		if (containment === null) {
			throw new IllegalStateException('''The intermediate model root “«root»” does not have the containment called “«INTERMEDIATE_MODEL_ROOT_CLASS_CONTAINER_NAME»”!''')
		}
		val idCounterFeature = root.eClass.EAllStructuralFeatures.findFirst [name == INTERMEDIATE_MODEL_ROOT_CLASS_ID_COUNTER]
		if (idCounterFeature === null) {
			throw new IllegalStateException('''The intermediate model root “«root»” does not have the id counter attribute called “«INTERMEDIATE_MODEL_ROOT_CLASS_ID_COUNTER»!”''')
		}
		var int oldCounter
		synchronized (root) {
			oldCounter = root.eGet(idCounterFeature) as Integer
			root.eSet(idCounterFeature, oldCounter + 1)
		}
		newChild.assignId(oldCounter)
		(root.eGet(containment) as EList<EObject>) += newChild
	}
	
	def private static checkIsNonRoot(EObject object, String providedName) {
		if (object.eClass.ESuperTypes.findFirst[name == INTERMEDIATE_MODEL_NONROOT_CLASS] === null) {
			throw new IllegalArgumentException('''The EObject “«object»” provided as «providedName» is not an intermediate model non root class!''')
		}
	}
	
	def static String computeNewIntermediateModelName(String fileExtension) {
		'''intermediate/«intermediateModelNameCounter.getAndIncrement()».«fileExtension»'''
	}
	
	def static assignStagingId(EObject nonRoot) {
		nonRoot.assignId(stagingUuidCounter.getAndIncrement())
	}
	
	def private static assignId(EObject nonRoot, int id) {
		checkIsNonRoot(nonRoot, 'non root')
		val uuidFeature = nonRoot.eClass.EAllStructuralFeatures.findFirst [name == INTERMEDIATE_MODEL_ID_ATTRIBUTE]
		if (uuidFeature === null) {
			throw new IllegalStateException('''The intermediate model non-root “«nonRoot»” does not have the uuid feature “«INTERMEDIATE_MODEL_ID_ATTRIBUTE»!”''')
		}
		nonRoot.eSet(uuidFeature, EcoreFactory.eINSTANCE.createFromString(EcorePackage.eINSTANCE.EString, String.valueOf(id)))
	}
}
