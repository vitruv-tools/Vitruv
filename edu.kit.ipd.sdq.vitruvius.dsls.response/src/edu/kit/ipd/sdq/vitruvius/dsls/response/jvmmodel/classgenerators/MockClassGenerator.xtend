package edu.kit.ipd.sdq.vitruvius.dsls.response.jvmmodel.classgenerators

import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.emf.ecore.EObject
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.EChangeHelper.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Trigger
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import org.eclipse.xtext.common.types.JvmVisibility

/** 
 * This is a temporary class that will be removed if the new change metamodel with TUID updates inside the monitors 
 * is available. 
 * Its purpose is to mock a class so that the TUID calculation works correctly for objects that were deletes
 * and thus lie inside a ChangeDescription instead of their old container now.  
 */
class MockClassGenerator extends ClassGenerator {
	private val Trigger trigger;
	
	new(Trigger responseTrigger, TypesBuilderExtensionProvider typesBuilderExtensionProvider) {
		super(typesBuilderExtensionProvider);
		this.trigger = responseTrigger;
	}
	
	public override generateClass() {
		return generateMockType(trigger);
	}
	
	
	private def dispatch JvmGenericType generateMockType(Trigger responseTrigger) {
		// In general we do not need a mock type
		return null;
	}
	
	private def dispatch JvmGenericType generateMockType(AtomicFeatureChange elementChange) {
		val change = elementChange.generateEChangeInstanceClass();
		var JvmGenericType result;
		if (change.methods.exists[it.name == "setOldValue"]) {
			val affectedElementClass = elementChange.changedFeature.feature.EType;
			if (EObject.isAssignableFrom(affectedElementClass.instanceClass)) {
				result = affectedElementClass.toClass(affectedElementClass.instanceClass.name + "ContainerMock") [
					superTypes += typeRef(affectedElementClass.instanceClass);
					members += toConstructor() [
						parameters += toParameter("containedObject", typeRef(affectedElementClass.instanceClass));
						parameters += toParameter("containerObject", typeRef(EObject));
						body = '''
							super();
							this.containedObject = containedObject;
							this.containerObject = containerObject;
						'''
					]
					members += toField("containedObject", typeRef(affectedElementClass.instanceClass));
					members += toField("containerObject", typeRef(EObject));
					members += affectedElementClass.instanceClass.methods.map[method |
						toMethod(affectedElementClass, method.name, typeRef(method.returnType)) [
							visibility = JvmVisibility.PUBLIC;
							exceptions += method.exceptionTypes.map[typeRef()];
							parameters += method.parameters.map[affectedElementClass.toParameter(name, typeRef(type))];
						if (simpleName.equals("eContainer")) {
									body = '''
									return containerObject;
								'''
							} else if (simpleName.equals("eResource")) {
								body = '''
									return containerObject.eResource();
								'''
							} else {
								body = '''
									«IF !method.returnType.equals(Void.TYPE)»return «ENDIF»containedObject.«simpleName»(«FOR param : parameters SEPARATOR ', '»«param.name»«ENDFOR»);
								'''
							}
						]
					]
				]	
			}
		}
		return result;
	}
	
}