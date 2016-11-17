package tools.vitruv.domains.sysml.tuid

import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrus.sysml14.blocks.Block
import org.eclipse.papyrus.sysml14.blocks.ValueType

final class SysMlToUmlResolver {
	private static SysMlToUmlResolver instance;
	
	private new() {}
	
	public static def getInstance() {
		if (instance === null) {
			instance = new SysMlToUmlResolver();
		}
		return instance;
	}
	
	def dispatch EObject getStereotypedObject(EObject object) throws IllegalArgumentException {
		return object;
	}
	
	def dispatch EObject getStereotypedObject(Block block) throws IllegalArgumentException {
		return block.base_Class;
	}
	
	def dispatch EObject getStereotypedObject(ValueType valueType) throws IllegalArgumentException {
		return valueType.base_DataType;
	}
}
