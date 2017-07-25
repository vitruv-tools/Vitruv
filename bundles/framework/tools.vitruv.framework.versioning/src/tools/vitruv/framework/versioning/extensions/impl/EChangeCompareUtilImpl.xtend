package tools.vitruv.framework.versioning.extensions.impl

import java.util.Set
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.echange.compound.impl.CreateAndInsertNonRootImpl
import tools.vitruv.framework.change.echange.compound.impl.CreateAndReplaceNonRootImpl
import tools.vitruv.framework.change.echange.feature.attribute.impl.ReplaceSingleValuedEAttributeImpl
import tools.vitruv.framework.versioning.extensions.EChangeCompareUtil
import org.eclipse.emf.common.util.URI

class EChangeCompareUtilImpl implements EChangeCompareUtil {
	static val Set<Pair<String, String>> rootToRootMap = newHashSet

	static def EChangeCompareUtil init() {
		new EChangeCompareUtilImpl
	}

	private new() {
	}

	override addPair(Pair<String, String> pair) {
		rootToRootMap += pair
	}

	override isEChangeEqual(EChange e1, EChange e2) {
		compareEchange(e1, e2)
	}

	override containerIsRootAndMapped(String containerString, InternalEObject affectedContainer2) {
		val affectedContainerPlatformString2 = affectedContainer2.eProxyURI.comparableString
		rootToRootMap.filter [
			val toDirection = containerString.contains(key) && affectedContainerPlatformString2.contains(value)
			val fromDirection = containerString.contains(value) && affectedContainerPlatformString2.contains(key)
			return toDirection || fromDirection
		].map [
			val x = if (containerString.contains(key)) it else new Pair(value, key)
			return x
		].map [
			if (!affectedContainerPlatformString2.contains(value))
				throw new IllegalStateException('''«affectedContainerPlatformString2» is not lying under root«value»''')
			val s = affectedContainerPlatformString2.replace(value, key)
			val x = containerString == s
			return x
		].fold(false, [current, next|(current || next)])
	}

	override getComparableString(URI uri) {
		uri.toString
	}

	private dispatch def boolean compareEchange(EChange e1, EChange e2) {
		false
	}

	private dispatch def boolean compareEchange(ReplaceSingleValuedEAttributeImpl<?, ?> e1,
		ReplaceSingleValuedEAttributeImpl<?, ?> e2) {
		val affectedObjectIsEqual = EcoreUtil::equals(e1.affectedEObject, e2.affectedEObject)
		val affectedFeatureIsEqual = EcoreUtil::equals(e1.affectedFeature, e2.affectedFeature)
		val newValueIsEqual = e1.newValue == e2.newValue
		val affectedContainer1 = e1.affectedEObject as InternalEObject
		val affectedContainerPlatformString1 = affectedContainer1.eProxyURI.comparableString
		val containerIsRootAndMapped = containerIsRootAndMapped(affectedContainerPlatformString1,
			e2.affectedEObject as InternalEObject)
		return (affectedObjectIsEqual || containerIsRootAndMapped) && affectedFeatureIsEqual && newValueIsEqual
	}

	private dispatch def boolean compareEchange(CreateAndReplaceNonRootImpl<?, ?> e1,
		CreateAndReplaceNonRootImpl<?, ?> e2) {
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
		CreateAndInsertNonRootImpl<?, ?> e1,
		CreateAndInsertNonRootImpl<?, ?> e2
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

}
