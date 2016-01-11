package edu.kit.ipd.sdq.vitruvius.dsls.response.helper

import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ModelChangeEvent
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.UnsetEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateEAttribute
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateEReference
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.EObjectChange

final class EChangeHelper {
	
	static def String getGenericTypeParameterOfChange(ModelChangeEvent event, ImportHelper ih) {
		val changeClass = event.change.instanceClass;
		 if (UpdateEReference.isAssignableFrom(changeClass)
			|| UpdateEAttribute.isAssignableFrom(changeClass)
			|| UnsetEReference.isAssignableFrom(changeClass)
			|| UnsetEAttribute.isAssignableFrom(changeClass)) {
			return ih.typeRef(event.feature.feature.EType)
		} else if (EObjectChange.isAssignableFrom(changeClass)) {
			return ih.typeRef(event.feature.element);
		} else if (EFeatureChange.isAssignableFrom(changeClass)) {
			return ih.typeRef(event.feature.feature.class)
		} 
		return null;
	}
	
}