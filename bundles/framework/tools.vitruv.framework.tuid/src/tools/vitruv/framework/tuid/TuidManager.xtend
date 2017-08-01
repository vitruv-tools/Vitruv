package tools.vitruv.framework.tuid

import java.util.Map
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import tools.vitruv.framework.util.XtendAssertHelper
import org.apache.log4j.Logger
import java.util.Set

final class TuidManager {
	private static val logger = Logger.getLogger(TuidManager);
	private static val instance = new TuidManager();
	private val Set<TuidCalculator> tuidCalculator;
	private val Set<TuidUpdateListener> tuidUpdateListener;
	private val Map<EObject, Tuid> tuidUpdateCache = new HashMap<EObject, Tuid>();
	
	private new() {
		this.tuidCalculator = newHashSet();
		this.tuidUpdateListener = newHashSet();
	}
	
	public static def TuidManager getInstance() {
		// This class is a singleton for now. We will probably change that decision later 
		return instance;
	}
	
	public def void addTuidUpdateListener(TuidUpdateListener updateListener) {
		if (updateListener !== null) {
			tuidUpdateListener += updateListener;
		}
	}
	
	public def removeTuidUpdateListener(TuidUpdateListener updateListener) {
		tuidUpdateListener.remove(updateListener);
	}
	
	public def void addTuidCalculator(TuidCalculator calculator) {
		if (calculator !== null) {
			tuidCalculator += calculator;
		}
	}
	
	public def removeTuidCalculator(TuidCalculator calculator) {
		tuidCalculator.remove(calculator);
	}
	
	public def reinitialize() {
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
	
	def public registerObjectUnderModification(EObject objectUnderModification) {
		if (objectUnderModification.hasTuidCalculator) {
			tuidUpdateCache.put(objectUnderModification, objectUnderModification.calculateTuid);
		}
	}
	
	def public flushRegisteredObjectsUnderModification() {
		tuidUpdateCache.clear();
	}
	
	public def updateTuidsOfRegisteredObjects() {
		for (object : tuidUpdateCache.keySet) {
			val oldTuid = tuidUpdateCache.get(object);
			if (hasTuidCalculator(object)) {
				val newTuid = object.calculateTuid
				oldTuid.updateTuid(newTuid);
				logger.debug("Changed Tuid from " + oldTuid + " to " + newTuid);
				XtendAssertHelper.assertTrue(oldTuid.equals(newTuid));
			}
		}
	}
	
	def public updateTuid(EObject oldObject, EObject newObject) {
		if (oldObject.hasTuidCalculator && newObject.hasTuidCalculator) {
			val oldTuid = oldObject.calculateTuid;
			val newTuid = newObject.calculateTuid;
			oldTuid.updateTuid(newTuid);
		}
	}
	
	def public updateTuid(Tuid oldTuid, EObject newObject) {
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