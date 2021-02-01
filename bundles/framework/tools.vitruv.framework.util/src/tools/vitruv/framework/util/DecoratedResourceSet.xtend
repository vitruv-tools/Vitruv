package tools.vitruv.framework.util

import org.eclipse.emf.ecore.resource.ResourceSet

interface DecoratedResourceSet {
	def ResourceSet getOriginal()
	
	static def ResourceSet getOriginal(ResourceSet source) {
		if (source instanceof DecoratedResourceSet) {
			getOriginal(source.original)
		} else source
	}
}