package tools.vitruv.dsls.commonalities.scoping

import java.util.stream.Collectors
import java.util.stream.Stream
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtend.lib.annotations.Delegate
import org.eclipse.xtext.scoping.IScope

import static org.eclipse.xtext.scoping.Scopes.*

package class MetapackageScope implements IScope {
	@Delegate val IScope classesScope

	new(EPackage ePackage) {
		classesScope = scopeFor(ePackage.allClassifiers)
	}

	def private EClassifier[] getAllClassifiers(EPackage ePackage) {
		ePackage.streamClassifiers.collect(Collectors.toList)
	}

	def private Stream<EClassifier> streamClassifiers(EPackage ePackage) {
		return Stream.concat(ePackage.EClassifiers.stream, ePackage.ESubpackages.stream.flatMap[streamClassifiers])
	}
}
