package tools.vitruv.framework.tuid

import java.util.Map
import java.util.Set
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.util.XtendAssertHelper

final class TuidManager {
	static extension Logger = Logger::getLogger(TuidManager)
	static val instance = new TuidManager
	val Map<EObject, Tuid> tuidUpdateCache
	val Set<TuidCalculator> tuidCalculator
	val Set<TuidUpdateListener> tuidUpdateListener

	private new() {
		this.tuidCalculator = newHashSet
		this.tuidUpdateCache = newHashMap
		this.tuidUpdateListener = newHashSet
	}

	static def TuidManager getInstance() {
		// This class is a singleton for now. We will probably change that decision later
		return instance
	}

	def void addTuidUpdateListener(TuidUpdateListener updateListener) {
		if (updateListener !== null)
			tuidUpdateListener += updateListener
	}

	def removeTuidUpdateListener(TuidUpdateListener updateListener) {
		tuidUpdateListener -= updateListener
	}

	def void addTuidCalculator(TuidCalculator calculator) {
		if (calculator !== null)
			tuidCalculator += calculator
	}

	def removeTuidCalculator(TuidCalculator calculator) {
		tuidCalculator -= calculator
	}

	def reinitialize() {
		flushRegisteredObjectsUnderModification
		Tuid::reinitialize
	}

	private def TuidCalculator getTuidCalculator(EObject object) {
		var TuidCalculator resultCalculator = null
		for (potentialCalculator : tuidCalculator) {
			if (potentialCalculator.canCalculateTuid(object)) {
				if (resultCalculator !== null) {
					throw new IllegalStateException(
						"There are two Tuid calculators registered that can handle the EObject: " + object +
							", which are " + resultCalculator + " and " + potentialCalculator)
				}
				resultCalculator = potentialCalculator
			}
		}
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

	def registerObjectUnderModification(EObject objectUnderModification) {
		if (objectUnderModification.hasTuidCalculator)
			tuidUpdateCache.put(objectUnderModification, objectUnderModification.calculateTuid)
	}

	def flushRegisteredObjectsUnderModification() {
		tuidUpdateCache.clear
	}

	def updateTuidsOfRegisteredObjects() {
		tuidUpdateCache.entrySet.filter[hasTuidCalculator(key)].forEach [
			val newTuid = key.calculateTuid
			value.updateTuid(newTuid)
			debug('''Changed Tuid from «value» to «newTuid»''')
			XtendAssertHelper.assertTrue(value == newTuid)
		]
	}

	def updateTuid(EObject oldObject, EObject newObject) {
		if (oldObject.hasTuidCalculator && newObject.hasTuidCalculator) {
			val oldTuid = oldObject.calculateTuid
			val newTuid = newObject.calculateTuid
			oldTuid.updateTuid(newTuid)
		}
	}

	def updateTuid(Tuid oldTuid, EObject newObject) {
		if (newObject.hasTuidCalculator)
			oldTuid.updateTuid(newObject.calculateTuid)
	}

	package def notifyListenerBeforeTuidUpdate(Tuid oldTuid) {
		tuidUpdateListener.forEach[performPreAction(oldTuid)]
	}

	package def notifyListenerAfterTuidUpdate(Tuid newTuid) {
		tuidUpdateListener.forEach[performPostAction(newTuid)]
	}
}
