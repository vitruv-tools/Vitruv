package tools.vitruv.framework.tuid

import java.util.Map
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import tools.vitruv.framework.util.XtendAssertHelper
import org.apache.log4j.Logger
import java.util.Set

final class TuidManager {
	static val logger = Logger.getLogger(TuidManager);
	static val instance = new TuidManager();
	val Set<TuidCalculator> tuidCalculator;
	val Set<TuidUpdateListener> tuidUpdateListener;
	val Map<EObject, Tuid> tuidUpdateCache = new HashMap<EObject, Tuid>();
	
	private new() {
		this.tuidCalculator = newHashSet();
		this.tuidUpdateListener = newHashSet();
	}
	
	static def TuidManager getInstance() {
		// This class is a singleton for now. We will probably change that decision later 
		return instance;
	}
	
	def void addTuidUpdateListener(TuidUpdateListener updateListener) {
		if (updateListener !== null) {
			tuidUpdateListener += updateListener;
		}
	}
	
	def removeTuidUpdateListener(TuidUpdateListener updateListener) {
		tuidUpdateListener.remove(updateListener);
	}
	
	def void addTuidCalculator(TuidCalculator calculator) {
		if (calculator !== null) {
			tuidCalculator += calculator;
		}
	}
	
	def removeTuidCalculator(TuidCalculator calculator) {
		tuidCalculator.remove(calculator);
	}
	
	def reinitialize() {
		flushRegisteredObjectsUnderModification();
		Tuid.reinitialize();
	}
	
	def private TuidCalculator getTuidCalculator(EObject object) {
		var TuidCalculator resultCalculator = null;
		for (potentialCalculator : tuidCalculator) {
			if (potentialCalculator.canCalculateTuid(object)) {
				if (resultCalculator !== null) {
					throw new IllegalStateException("There are two Tuid calculators registered that can handle the EObject: " + object + ", which are " + resultCalculator + " and " + potentialCalculator);
				}
				resultCalculator = potentialCalculator;
			}
		}
		return resultCalculator;
	}
	
	def private boolean hasTuidCalculator(EObject object) {
		return object.tuidCalculator !== null;
	}
	
	def private Tuid calculateTuid(EObject object) {
		val tuidCalculator = object.tuidCalculator;
		if (tuidCalculator !== null) {
			return tuidCalculator.calculateTuid(object);
		} else {
			throw new IllegalArgumentException("No Tuid calculator registered for EObject: " + object);
		}
	}
	
	def registerObjectUnderModification(EObject objectUnderModification) {
		if (objectUnderModification.hasTuidCalculator && !tuidUpdateCache.containsKey(objectUnderModification)) {
			tuidUpdateCache.put(objectUnderModification, objectUnderModification.calculateTuid);
		}
	}
	
	def flushRegisteredObjectsUnderModification() {
		tuidUpdateCache.clear();
	}
	
	def updateTuidsOfRegisteredObjects() {
		for (object : tuidUpdateCache.keySet) {
			val oldTuid = tuidUpdateCache.get(object);
			if (hasTuidCalculator(object)) {
				val newTuid = object.calculateTuid
				val oldTuidString = oldTuid.toString
				oldTuid.updateTuid(newTuid);
				logger.debug("Changed Tuid from " + oldTuidString + " to " + newTuid);
				XtendAssertHelper.assertTrue(oldTuid.equals(newTuid));
			}
		}
	}
	
	def updateTuid(EObject oldObject, EObject newObject) {
		if (oldObject.hasTuidCalculator && newObject.hasTuidCalculator) {
			val oldTuid = oldObject.calculateTuid;
			val newTuid = newObject.calculateTuid;
			oldTuid.updateTuid(newTuid);
		}
	}
	
	def updateTuid(Tuid oldTuid, EObject newObject) {
		if (newObject.hasTuidCalculator) {
			oldTuid.updateTuid(newObject.calculateTuid);
		}
	}
	
	package def notifyListenerBeforeTuidUpdate(Tuid oldTuid) {
		for (listener : tuidUpdateListener) {
			listener.performPreAction(oldTuid);
		}
	}
	
	package def notifyListenerAfterTuidUpdate(Tuid newTuid) {
		for (listener : tuidUpdateListener) {
			listener.performPostAction(newTuid);
		}
	}
}