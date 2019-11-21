package tools.vitruv.extensions.dslsruntime.mappings.updates

import java.util.function.Function
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.xbase.lib.Functions.Function0

class MappingRoutineUpdateHelper {

	static def updateFromSources(MappingUpdateTarget target, MappingUpdateSource... sources) {
		sources.forEach[forceSoureCondition]
		val updateSource = sources.findFirst[isCurrentUpdateSource(target)]
		if (updateSource !== null) {
			// there was actually an update 
			val interchangeableValue = updateSource.interchangeableValue
			// 1) update the target
			target.updateValue(interchangeableValue)
			// 2) update the other sources that were not the actual update source
			sources.filter[it !== updateSource].forEach [
				it.updateTargetValue(interchangeableValue)
			]
		}
	}

	static def eObjectTarget(EObject object, String featureName) {
		val feature = object.findFeature(featureName)
		new MappingUpdateTarget() {
			override currentValue() {
				object.eGet(feature)
			}

			override updateValue(Object value) {
				object.eSet(feature, value)
			}
		}
	}

	static def simpleEObjectSource(EObject object, String featureName) {
		val feature = object.findFeature(featureName)
		simpleObjectSource(object.eGet(feature), [object.eSet(feature, it)])
	}

	static def simpleEObjectSource(EObject object, EStructuralFeature feature) {
		simpleObjectSource(object.eGet(feature), [object.eSet(feature, it)])
	}

	static def simpleObjectSource(Object value, ValueSetter valueSetter) {
		return new MappingUpdateSource {

			override updateValue(Object interchangeableValue) {
				valueSetter.set(interchangeableValue)
			}

			override currentValue() {
				value
			}

			override transfromation() {
				new SimpleMappingUpdateTransformation
			}
		}
	}

	static def eObjectSource(EObject object, String featureName,
		Function<Object, Object> toInterchangableValueTransformation,
		Function<Object, Object> toTargetValueTransformation) {
		eObjectSource(object, featureName, new MappingUpdateTransformation() {
			override transformToInterchangeableValue() {
				toInterchangableValueTransformation
			}

			override transformToTargetValue() {
				toTargetValueTransformation
			}
		})
	}

	static def eObjectSource(EObject object, String featureName, MappingUpdateTransformation transformation) {
		val feature = object.findFeature(featureName)
		return new MappingUpdateSource {
			override updateValue(Object value) {
				object.eSet(feature, value)
			}

			override currentValue() {
				object.eGet(feature)
			}

			override transfromation() {
				transformation
			}
		}
	}

	static def objectSource(Function0<Object> valueGetter, ValueSetter valueSetter,
		Function<Object, Object> toInterchangableValueTransformation,
		Function<Object, Object> toTargetValueTransformation) {
		objectSource(valueGetter, valueSetter, new MappingUpdateTransformation() {
			override transformToInterchangeableValue() {
				toInterchangableValueTransformation
			}

			override transformToTargetValue() {
				toTargetValueTransformation
			}
		})
	}

	static def objectSource(Function0<Object> valueGetter, ValueSetter valueSetter,
		MappingUpdateTransformation transformation) {
		return new MappingUpdateSource {
			override updateValue(Object value) {
				valueSetter.set(value)
			}

			override currentValue() {
				valueGetter.apply
			}

			override transfromation() {
				transformation
			}
		}
	}

	private static def forceSoureCondition(MappingUpdateSource parameter) {
		val currentValue = parameter.currentValue
		// check if current value is not the same as a full transformation 
		val interchangeableValue = parameter.transfromation.transformToInterchangeableValue.apply(currentValue)
		val targetValue = parameter.transfromation.transformToTargetValue.apply(interchangeableValue)
		if (targetValue != currentValue) {
			// update current value to transformation value because it conforms to the conditions
			parameter.updateValue(targetValue)
		}
	}

	private static def findFeature(EObject object, String featureName) {
		object.eClass.EAllStructuralFeatures.findFirst[it.name == featureName]
	}

	private static def isCurrentUpdateSource(MappingUpdateSource source, MappingUpdateTarget target) {
		val currentValue = target.currentValue
		if (currentValue === null) {
			// inital update, just take the first one
			return true
		}
		currentValue != source.interchangeableValue
	}

	private static def getInterchangeableValue(MappingUpdateSource parameter) {
		parameter.transfromation.transformToInterchangeableValue.apply(parameter.currentValue)
	}

	private static def void updateTargetValue(MappingUpdateSource parameter, Object interchangeableValue) {
		parameter.updateValue(parameter.transfromation.transformToTargetValue.apply(interchangeableValue))
	}

	static interface ValueSetter {
		def void set(Object value)
	}

}
