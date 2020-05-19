package tools.vitruv.extensions.dslruntime.commonalities.operators

import com.google.common.base.Preconditions
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

class AttributeOperand {

	private val EObject object
	private val EStructuralFeature feature

	new(EObject object, EStructuralFeature feature) {
		Preconditions.checkNotNull(object, "Object is null!")
		Preconditions.checkNotNull(feature, "Feature is null!")
		this.object = object
		this.feature = feature
	}

	public def getObject() {
		return object
	}

	public def getFeature() {
		return feature
	}
}
