package tools.vitruv.framework.domains.ui.monitorededitor

import tools.vitruv.framework.vsum.VirtualModel
import org.eclipse.xtend.lib.annotations.Accessors

abstract class AbstractMonitoredEditor implements MonitoredEditor {
	@Accessors(PROTECTED_GETTER)
	val VirtualModel virtualModel
	@Accessors(PUBLIC_GETTER, PUBLIC_SETTER)
	var int automaticPropagationAfterMilliseconds

	/** 
	 * Sets the time after which change propagation is executed automatically if no further change
	 * occurs. Setting the value to -1 disables automatic propagation.
	 * @param millisecondsAfterLastChange the time after which change propagation is
	 * performed if no further change occurs
	 */
	override void setAutomaticPropagationAfterMilliseconds(int millisecondsAfterLastChange) {
		this.automaticPropagationAfterMilliseconds = millisecondsAfterLastChange
	}

	new(VirtualModel virtualModel) {
		this.virtualModel = virtualModel
		this.automaticPropagationAfterMilliseconds = -1
	}

}
