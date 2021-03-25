package tools.vitruv.framework.uuid

import org.eclipse.emf.ecore.EObject

class EmptyUuidResolver implements UuidResolver {
	package new() {}
	
	override hasUuid(EObject object) {
		return false;
	}
		
	override hasEObject(String uuid) {
		return false;
	}
	
	override getUuid(EObject object) {
		throw new IllegalStateException("This resolver is empty. Use hasUuid to check that before!");
	}
	
	override getEObject(String uuid) {
		throw new IllegalStateException("This resolver is empty.");
	}
	
	override registerEObject(String uuid, EObject eObject) {
		throw new UnsupportedOperationException("This resolver is empty.");
	}
	
	override getResourceSet() {
		throw new UnsupportedOperationException("This resolver is empty.");
	}
	
}