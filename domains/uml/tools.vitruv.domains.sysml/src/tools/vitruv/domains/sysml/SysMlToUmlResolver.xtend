package tools.vitruv.domains.sysml

import org.eclipse.emf.ecore.EObject
import org.eclipse.papyrus.sysml14.blocks.Block
import org.eclipse.papyrus.sysml14.blocks.ValueType

package final class SysMlToUmlResolver {
	private static SysMlToUmlResolver instance;
	
	private new() {}
	
	public static def getInstance() {
		if (instance === null) {
			instance = new SysMlToUmlResolver();
		}
		return instance;
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