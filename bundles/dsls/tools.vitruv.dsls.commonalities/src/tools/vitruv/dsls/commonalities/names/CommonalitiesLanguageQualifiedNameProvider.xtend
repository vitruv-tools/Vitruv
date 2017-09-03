package tools.vitruv.dsls.commonalities.names

import com.google.inject.Singleton
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName
import tools.vitruv.dsls.commonalities.language.elements.ClassLike
import tools.vitruv.dsls.commonalities.language.elements.MemberLike
import tools.vitruv.dsls.commonalities.language.elements.PackageLike

@Singleton
class CommonalitiesLanguageQualifiedNameProvider extends IQualifiedNameProvider.AbstractImpl {
	
	override getFullyQualifiedName(EObject object) {
		val segments = getFullyQualifiedNameSegments(object)
		if (segments === null) return null
		QualifiedName.create(segments)
	}
	
	def private dispatch List<String> getFullyQualifiedNameSegments(MemberLike member) {
		segmentList(getFullyQualifiedNameSegments(member.classLikeContainer), member.name)
	}
	
	def private dispatch List<String> getFullyQualifiedNameSegments(ClassLike classl) {
		segmentList(getFullyQualifiedNameSegments(classl.packageLikeContainer), classl.name)
	}
	
	def private dispatch List<String> getFullyQualifiedNameSegments(PackageLike packagel) {
		segmentList(packagel.name)
	}
	
	def private dispatch List<String> getFullyQualifiedNameSegments(EObject object) {
		return null
	}
	
	def private dispatch List<String> getFullyQualifiedNameSegments(Void nill) {
		return null
	}
	
	def private segmentList(List<String> existing, String element) {
		if (element === null) return null
		existing.add(element)
		return existing
	}
	
	def private segmentList(String element) {
		if (element === null) return null
		val result = new ArrayList<String>(3)
		result.add(element)
		return result
	}
	
}