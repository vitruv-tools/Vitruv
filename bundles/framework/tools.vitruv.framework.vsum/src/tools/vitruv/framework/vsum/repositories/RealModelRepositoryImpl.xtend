package tools.vitruv.framework.vsum.repositories

import java.util.Set
import org.eclipse.emf.ecore.EObject
import java.util.HashSet

class RealModelRepositoryImpl {
	val Set<EObject> rootElements;
	
	new() {
		rootElements = new HashSet<EObject>();
	}
	
	public def void addRootElement(EObject rootElement) {
		this.rootElements += rootElement;
	}
	
}