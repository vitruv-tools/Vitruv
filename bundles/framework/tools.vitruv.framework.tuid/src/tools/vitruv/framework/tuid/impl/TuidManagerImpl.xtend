package tools.vitruv.framework.tuid.impl

import java.util.Map
import java.util.Set

import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject

import tools.vitruv.framework.tuid.Tuid
import tools.vitruv.framework.tuid.TuidCalculator
import tools.vitruv.framework.tuid.TuidManager
import tools.vitruv.framework.tuid.TuidUpdateListener
import tools.vitruv.framework.util.XtendAssertHelper

class TuidManagerImpl implements TuidManager {
	static extension Logger = Logger::getLogger(TuidManagerImpl)

	static def TuidManager init() {
		new TuidManagerImpl
	}

	val Map<EObject, Tuid> tuidUpdateCache
	val Set<TuidCalculator> tuidCalculator
	val Set<TuidUpdateListener> tuidUpdateListener

	private new() {
		tuidCalculator = newHashSet
		tuidUpdateCache = newHashMap
		tuidUpdateListener = newHashSet
	}

	override addTuidUpdateListener(TuidUpdateListener updateListener) {
		if (updateListener !== null)
			tuidUpdateListener += updateListener
	}

	override removeTuidUpdateListener(TuidUpdateListener updateListener) {
		tuidUpdateListener -= updateListener
	}

	override addTuidCalculator(TuidCalculator calculator) {
		if (calculator !== null)
			tuidCalculator += calculator
	}

	override removeTuidCalculator(TuidCalculator calculator) {
		tuidCalculator -= calculator
	}

	override reinitialize() {
		flushRegisteredObjectsUnderModification
		Tuid::reinitialize
	}

	private def TuidCalculator getTuidCalculator(EObject object) {
		val potentialCalculators = tuidCalculator.filter[canCalculateTuid(object)]
		if (potentialCalculators.length > 1)
			throw new IllegalStateException(
				'''
				There are two Tuid calculators registered that can handle the EObject: «object», which are
				«FOR potentialCalculator : potentialCalculators SEPARATOR ", "» «potentialCalculator» «ENDFOR»
				potentialCalculator
			''')
		val resultCalculator = potentialCalculators.get(0)
		return resultCalculator
	}

	private def boolean hasTuidCalculator(EObject object) {
		return object.tuidCalculator !== null
	}

	private def Tuid calculateTuid(EObject object) {
		val tuidCalculator = object.tuidCalculator
		if (tuidCalculator === null)
			throw new IllegalArgumentException("No Tuid calculator registered for EObject: " + object)
		return tuidCalculator.calculateTuid(object)
	}

	override registerObjectUnderModification(EObject objectUnderModification) {
		if (objectUnderModification.hasTuidCalculator)
			tuidUpdateCache.put(objectUnderModification, objectUnderModification.calculateTuid)
	}

	override flushRegisteredObjectsUnderModification() {
		tuidUpdateCache.clear
	}

	override updateTuidsOfRegisteredObjects() {
		tuidUpdateCache.entrySet.filter[hasTuidCalculator(key)].forEach [
			val newTuid = key.calculateTuid
			value.updateTuid(newTuid)
			debug('''Changed Tuid from «value» to «newTuid»''')
			XtendAssertHelper::assertTrue(value == newTuid)
		]
	}

	override updateTuid(EObject oldObject, EObject newObject) {
		if (oldObject.hasTuidCalculator && newObject.hasTuidCalculator) {
			val oldTuid = oldObject.calculateTuid
			val newTuid = newObject.calculateTuid
			oldTuid.updateTuid(newTuid)
		}
	}

	override updateTuid(Tuid oldTuid, EObject newObject) {
		if (newObject.hasTuidCalculator)
			oldTuid.updateTuid(newObject.calculateTuid)
	}

	override notifyListenerBeforeTuidUpdate(Tuid oldTuid) {
		tuidUpdateListener.forEach [
			performPreAction(oldTuid)
		]
	}

	override notifyListenerAfterTuidUpdate(Tuid newTuid) {
		tuidUpdateListener.forEach [
			performPostAction(newTuid)
		]
	}
}
