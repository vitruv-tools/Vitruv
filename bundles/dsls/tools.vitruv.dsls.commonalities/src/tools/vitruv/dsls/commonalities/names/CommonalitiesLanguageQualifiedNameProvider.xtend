package tools.vitruv.dsls.commonalities.names

import com.google.inject.Singleton
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import tools.vitruv.dsls.commonalities.language.elements.ClassLike
import tools.vitruv.dsls.commonalities.language.elements.MemberLike
import tools.vitruv.dsls.commonalities.language.elements.PackageLike

@Singleton
class CommonalitiesLanguageQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {
	
	override getFullyQualifiedName(EObject object) {
		QualifiedName.create(getFullyQualifiedNameSegments(object))
	}
	
	def private dispatch Iterable<String> getFullyQualifiedNameSegments(MemberLike member) {
		getFullyQualifiedNameSegments(member.classLikeContainer) + #[member.name]
	}
	
	def private dispatch Iterable<String> getFullyQualifiedNameSegments(ClassLike classl) {
		getFullyQualifiedNameSegments(classl.packageLikeContainer) + #[classl.name]
	}
	
	def private dispatch Iterable<String> getFullyQualifiedNameSegments(PackageLike packagel) {
		#[packagel.name]
	}
	
	def private dispatch Iterable<String> getFullyQualifiedNameSegments(EObject object) {
		return #[]
	}
	
}