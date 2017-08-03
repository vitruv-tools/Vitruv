package tools.vitruv.framework.versioning.extensions.impl

import java.util.Set
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.change.echange.compound.CreateAndInsertRoot
import tools.vitruv.framework.change.echange.compound.CreateAndReplaceNonRoot
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.compound.CreateAndInsertNonRoot

class EChangeCompareUtilImpl implements EChangeCompareUtil {
	static val Set<Pair<String, String>> rootToRootMap = newHashSet
	static val Set<Pair<String, String>> nameToNameMap = newHashSet

	static def EChangeCompareUtil init() {
		new EChangeCompareUtilImpl
	}

	private static def getNameExtracted(String pathWithName) {
		val segments = pathWithName.split("/")
		val file = segments.last
		val nameSegments = file.split("\\.")
		val nameWithoutSuffix = nameSegments.get(0)
		return nameWithoutSuffix
	}

	private static def isUriMapped(String uriString1, String uriString2) {
		isMapped(rootToRootMap, uriString1, uriString2)
	}

	private static def isAttributeMapped(String uriString1, String uriString2) {
		isMapped(nameToNameMap, uriString1, uriString2)
	}

	private static def isMapped(Set<Pair<String, String>> setOfPairs, String string, String string2) {
		return setOfPairs.filter [
			val toDirection = string.contains(key) && string2.contains(value)
			val fromDirection = string.contains(value) && string2.contains(key)
			return toDirection || fromDirection
		].map [
			val returnValue = if (string.contains(key)) it else new Pair(value, key)
			return returnValue
		].map [
			if (!string2.contains(value))
				throw new IllegalStateException('''«string2» is not lying under root«value»''')
			val s = string2.replace(value, key)
			val returnValue = string == s
			// FIXME PS Sometimes URI has '/0' at the end
			val containerStringWithZero = '''«string»0'''
			val y = containerStringWithZero == s
			return returnValue || y
		].fold(false, [current, next|(current || next)])
	}

	private new() {
	}

	override addPair(Pair<String, String> pair) {
		rootToRootMap += pair
		nameToNameMap += pair.key.nameExtracted -> pair.value.nameExtracted
	}

	override isEChangeEqual(EChange e1, EChange e2) {
		compareEchange(e1, e2)
	}

	override containerIsRootAndMapped(String containerString, InternalEObject affectedContainer2) {
		val affectedContainerPlatformString2 = affectedContainer2.eProxyURI.comparableString
		return isUriMapped(containerString, affectedContainerPlatformString2)
	}

	override getComparableString(URI uri) {
		uri.toString
	}

	private dispatch def boolean compareEchange(EChange e1, EChange e2) {
		false
	}

	private dispatch def boolean compareEchange(
		ReplaceSingleValuedEAttribute<?, ?> e1,
		ReplaceSingleValuedEAttribute<?, ?> e2
	) {
		val affectedObjectIsEqual = EcoreUtil::equals(e1.affectedEObject, e2.affectedEObject)
		val affectedFeatureIsEqual = EcoreUtil::equals(e1.affectedFeature, e2.affectedFeature)
		val newValue1 = e1.newValue
		val newValue2 = e2.newValue
		val newValueIsEqual = newValue1 == newValue2
		var newValueMapped = false
		if (newValue1 instanceof String && newValue2 instanceof String) {
			newValueMapped = isAttributeMapped(newValue1 as String, newValue2 as String)
		}
		val affectedContainer1 = e1.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.comparableString
		val containerIsRootAndMapped = containerIsRootAndMapped(affectedContainerPlatformString1,
			e2.affectedEObject as InternalEObject)
		val returnValue = (affectedObjectIsEqual || containerIsRootAndMapped) && affectedFeatureIsEqual &&
			(newValueIsEqual || newValueMapped)
		return returnValue
	}

	private dispatch def boolean compareEchange(
		CreateAndReplaceNonRoot<?, ?> e1,
		CreateAndReplaceNonRoot<?, ?> e2
	) {
		val createdObjectIsEqual = EcoreUtil::equals(e1.createChange.affectedEObject, e2.createChange.affectedEObject)
		val containerIsEqual = EcoreUtil::equals(e1.insertChange.affectedEObject, e2.insertChange.affectedEObject)
		val affectedContainer1 = e1.insertChange.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.comparableString
		var containerIsRootAndMapped = containerIsRootAndMapped(affectedContainerPlatformString1,
			e2.insertChange.affectedEObject as InternalEObject)
		val newValueIsEqual = EcoreUtil::equals(e1.insertChange.newValue, e2.insertChange.newValue)
		return createdObjectIsEqual && (containerIsEqual || containerIsRootAndMapped) && newValueIsEqual
	}

	private dispatch def boolean compareEchange(
		CreateAndInsertNonRoot<?, ?> e1,
		CreateAndInsertNonRoot<?, ?> e2
	) {
		val createdObjectIsEqual = EcoreUtil::equals(e1.createChange.affectedEObject, e2.createChange.affectedEObject)
		val containerIsEqual = EcoreUtil::equals(e1.insertChange.affectedEObject, e2.insertChange.affectedEObject)
		val affectedContainer1 = e1.insertChange.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.comparableString
		var containerIsRootAndMapped = containerIsRootAndMapped(affectedContainerPlatformString1,
			e2.insertChange.affectedEObject as InternalEObject)
		val newValueIsEqual = EcoreUtil::equals(e1.insertChange.newValue, e2.insertChange.newValue)
		val indexEqual = e1.insertChange.index === e2.insertChange.index
		val returnValue = createdObjectIsEqual && (containerIsEqual || containerIsRootAndMapped) && newValueIsEqual &&
			indexEqual
		return returnValue
	}

	private dispatch def boolean compareEchange(
		CreateAndInsertRoot<?> e1,
		CreateAndInsertRoot<?> e2
	) {
		val createdObjectIsEqual = EcoreUtil::equals(e1.createChange.affectedEObject, e2.createChange.affectedEObject)
		val uri1 = e1.insertChange.uri
		val uri2 = e2.insertChange.uri
		val uriEqual = uri1 == uri2
		val uriMappedEqual = isUriMapped(uri1, uri2)
		val newValueIsEqual = EcoreUtil::equals(e1.insertChange.newValue, e2.insertChange.newValue)
		val indexEqual = e1.insertChange.index === e2.insertChange.index
		val returnValue = createdObjectIsEqual && (uriEqual || uriMappedEqual) && newValueIsEqual && indexEqual
		return returnValue
	}

}
