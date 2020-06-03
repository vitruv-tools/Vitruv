package tools.vitruv.dsls.commonalities.names

import com.google.inject.Singleton
import java.util.ArrayList
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.xbase.scoping.XbaseQualifiedNameProvider
import tools.vitruv.dsls.commonalities.language.elements.ClassLike
import tools.vitruv.dsls.commonalities.language.elements.MemberLike
import tools.vitruv.dsls.commonalities.language.elements.PackageLike

import static tools.vitruv.dsls.commonalities.names.QualifiedNameHelper.*

@Singleton
class CommonalitiesLanguageQualifiedNameProvider extends XbaseQualifiedNameProvider {

	override getFullyQualifiedName(EObject object) {
		if (object instanceof JvmIdentifiableElement) {
			return super.getFullyQualifiedName(object)
		}
		val segments = getFullyQualifiedNameSegments(object)
		if (segments !== null) {
			return QualifiedName.create(segments)
		}
		return null
	}

	private def dispatch List<String> getFullyQualifiedNameSegments(MemberLike member) {
		segmentList(getFullyQualifiedNameSegments(member.classLikeContainer), member.name)
	}

	private def dispatch List<String> getFullyQualifiedNameSegments(ClassLike classl) {
		segmentList(getFullyQualifiedNameSegments(classl.packageLikeContainer), classl.name)
	}

	private def dispatch List<String> getFullyQualifiedNameSegments(PackageLike packagel) {
		segmentList(packagel.name)
	}

	private def dispatch List<String> getFullyQualifiedNameSegments(EObject object) {
		return null
	}

	private def dispatch List<String> getFullyQualifiedNameSegments(Void nill) {
		return null
	}

	private def segmentList(List<String> existing, String element) {
		if (element === null) return null
		existing.add(element)
		return existing
	}

	private def segmentList(String packageLikeName) {
		if (packageLikeName === null) return null
		val result = new ArrayList<String>(4)
		result.add(packageLikeName)
		result.add(DOMAIN_METACLASS_SEPARATOR_SEGMENT)
		return result
	}
}
