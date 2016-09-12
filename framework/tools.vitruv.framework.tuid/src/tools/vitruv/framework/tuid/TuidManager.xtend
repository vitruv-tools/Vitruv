package tools.vitruv.framework.tuid

import java.util.List
import java.util.ArrayList
import java.util.Map
import org.eclipse.emf.ecore.EObject
import java.util.HashMap
import tools.vitruv.framework.util.XtendAssertHelper

final class TuidManager {
	private static val instance = new TuidManager();
	private val List<TuidCalculator> tuidCalculator;
	private val List<TuidUpdateListener> tuidUpdateListener;
	private val Map<EObject, TUID> tuidUpdateCache = new HashMap<EObject, TUID>();
	
	private new() {
		this.tuidCalculator = new ArrayList<TuidCalculator>();
		this.tuidUpdateListener = new ArrayList<TuidUpdateListener>();
	}
	
	public static def TuidManager getInstance() {
		// This class is a singleton for now. We will probably change that decision later 
		return instance;
	}
	
	public def void addTuidUpdateListener(TuidUpdateListener updateListener) {
		if (updateListener != null) {
			tuidUpdateListener += updateListener;
		}
	}
	
	public def removeTuidUpdateListener(TuidUpdateListener updateListener) {
		tuidUpdateListener.remove(updateListener);
	}
	
	public def void addTuidCalculator(TuidCalculator calculator) {
		if (calculator != null) {
			tuidCalculator += calculator;
		}
	}
	
	public def removeTuidCalculator(TuidCalculator calculator) {
		tuidCalculator.remove(calculator);
	}
	
	public def reinitialize() {
		tuidUpdateListener.clear();
		tuidCalculator.clear();
		flushRegisteredObjectsUnderModification();
		TUID.reinitialize();
	}
	
	def private TuidCalculator getTuidCalculator(EObject object) {
		var TuidCalculator resultCalculator = null;
		for (potentialCalculator : tuidCalculator) {
			if (potentialCalculator.canCalculateTuid(object)) {
				if (resultCalculator != null) {
					throw new IllegalStateException("There are two TUID calculators registered that can handle the EObject: " + object + ", which are " + resultCalculator + " and " + potentialCalculator);
				}
				resultCalculator = potentialCalculator;
			}
		}
		return resultCalculator;
	}
	
	def private boolean hasTuidCalculator(EObject object) {
		return object.tuidCalculator != null;
	}
	
	def private TUID calculateTuid(EObject object) {
		val tuidCalculator = object.tuidCalculator;
		if (tuidCalculator != null) {
			return tuidCalculator.calculateTuid(object);
		} else {
			throw new IllegalArgumentException("No TUID calculator registered for EObject: " + object);
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
	
	def public updateTuid(TUID oldTuid, EObject newObject) {
		if (newObject.hasTuidCalculator) {
			oldTuid.updateTuid(newObject.calculateTuid);
		}
	}
	
	package def notifyListenerBeforeTuidUpdate(TUID oldTuid) {
		for (listener : tuidUpdateListener) {
			listener.performPreAction(oldTuid);
		}
	}
	
	package def notifyListenerAfterTuidUpdate(TUID newTuid) {
		for (listener : tuidUpdateListener) {
			listener.performPostAction(newTuid);
		}
	}
}