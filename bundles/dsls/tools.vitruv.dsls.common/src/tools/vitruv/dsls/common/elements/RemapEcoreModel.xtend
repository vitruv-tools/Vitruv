package tools.vitruv.dsls.common.elements

import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.emf.ecore.EPackage

class RemapEcoreModel {
	@Accessors
	var String from

	def setTo(String uri) {
		EPackage.Registry.INSTANCE.put(uri, EPackage.Registry.INSTANCE.get(from))
	}
}
