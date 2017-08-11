package tools.vitruv.framework.tuid

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.tuid.impl.TuidManagerImpl

interface TuidManager {
	static TuidManager instance = TuidManagerImpl::init

	static def TuidManager getInstance() {
		// This class is a singleton for now. We will probably change that decision later
		return instance
	}

	def void addTuidCalculator(TuidCalculator calculator)

	def void addTuidUpdateListener(TuidUpdateListener updateListener)

	def void flushRegisteredObjectsUnderModification()

	def void notifyListenerAfterTuidUpdate(Tuid newTuid)

	def void notifyListenerBeforeTuidUpdate(Tuid oldTuid)

	def void registerObjectUnderModification(EObject objectUnderModification)

	def void reinitialize()

	def void removeTuidCalculator(TuidCalculator calculator)

	def void removeTuidUpdateListener(TuidUpdateListener updateListener)

	def void updateTuid(EObject oldObject, EObject newObject)

	def void updateTuid(Tuid oldTuid, EObject newObject)

	def void updateTuidsOfRegisteredObjects()
}
