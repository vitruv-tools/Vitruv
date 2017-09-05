package tools.vitruv.dsls.commonalities.ui.contentassist

import com.google.inject.Inject
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.ui.editor.contentassist.PrefixMatcher

import static extension tools.vitruv.dsls.commonalities.names.EPackageURINameResolver.getPackageName

class MetapackagePrefixMatcher extends PrefixMatcher {

	String name
	@Inject PrefixMatcher defaultMatcher

	override isCandidateMatchingPrefix(String name, String prefix) {
		defaultMatcher.isCandidateMatchingPrefix(this.name, prefix)
	}
	
	def setEPackage(EPackage ePackage) {
		name = ePackage.packageName
	}

}
