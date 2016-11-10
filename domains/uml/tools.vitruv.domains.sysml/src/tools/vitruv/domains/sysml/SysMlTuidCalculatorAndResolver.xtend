package tools.vitruv.domains.sysml

import tools.vitruv.framework.tuid.AttributeTUIDCalculatorAndResolver
import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrus.sysml14.blocks.Block
import org.eclipse.uml2.uml.UMLPackage
import org.eclipse.papyrus.sysml14.blocks.ValueType

package class SysMlTuidCalculatorAndResolver extends AttributeTUIDCalculatorAndResolver {
	
	new() {
		super(UMLPackage.eNS_PREFIX, UMLPackage.Literals.NAMED_ELEMENT__NAME.name)
	}
	
	override protected calculateIndividualTUIDDelegator(EObject obj) throws IllegalArgumentException {
		super.calculateIndividualTUIDDelegator(obj.stereotypedObject);
	}
	
	def protected dispatch EObject getStereotypedObject(EObject object) throws IllegalArgumentException {
		return object;
	}
	
	def protected dispatch EObject getStereotypedObject(Block block) throws IllegalArgumentException {
		return block.base_Class;
	}
	
	def protected dispatch EObject getStereotypedObject(ValueType valueType) throws IllegalArgumentException {
		return valueType.base_DataType;
	}
	
}