package edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.InsertRootEObject
import org.eclipse.emf.ecore.EObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RootFactory
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.root.RemoveRootEObject
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.TUID
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EAttribute
import java.util.HashMap
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.InsertEAttributeValue
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.feature.attribute.AttributeFactory

final class TypeInferringChangeFactory {
	val Metamodel metamodel
	
	new(Metamodel metamodel) {
		this.metamodel = metamodel
	}
	
	def <T extends EObject> InsertRootEObject<T> createInsertRootChange(T newValue, boolean isCreate) {
		val c = RootFactory.eINSTANCE.createInsertRootEObject
		c.newValue = newValue
		c.isCreate = isCreate
		return c
	}
	
	def <T extends EObject> RemoveRootEObject createRemoveRootChange(T oldObject, boolean isDelete) {
		val c = RootFactory.eINSTANCE.createRemoveRootEObject
		val oldTUID = getTUID(oldObject)
		c.oldTUID = oldTUID
		val feature2OldValueMap = new HashMap<EStructuralFeature,Object>();
		for (feature : oldObject.eClass.EAllStructuralFeatures) {
			val oldValue = getOldValueForMap(oldObject, feature)
			feature2OldValueMap.put(feature,oldValue)
		}
		c.isDelete = isDelete
		return c
	}
	
	private def TUID getTUID(EObject o) {
		return TUID::getInstance(metamodel.calculateTUIDFromEObject(o))
	}
	
	private def dispatch Object getOldValueForMap(EObject o, EAttribute attribute) {
		return o.eGet(attribute)
	}
	
	private def dispatch Object getOldValueForMap(EObject o, EReference reference) {
		val referencedEObject = o.eGet(reference) as EObject
		return getTUID(referencedEObject)
	}
	
	def <A extends EObject, T extends Object> InsertEAttributeValue<A,T> createInsertAttributeChange() {
		val c = AttributeFactory.eINSTANCE.createInsertEAttributeValue()
//		c.
		return c
	}
}