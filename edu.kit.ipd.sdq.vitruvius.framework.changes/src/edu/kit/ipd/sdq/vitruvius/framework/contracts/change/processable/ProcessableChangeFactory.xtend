package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.impl.VitruviusChangeImpl
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable.impl.ProcessableCompositeChangeImpl

class ProcessableChangeFactory {
	private static ProcessableChangeFactory instance;
	
	private new() {}
	
	public static def ProcessableChangeFactory getInstance() {
		if (instance == null) {
			instance = new ProcessableChangeFactory();
		}
		return instance;
	}
	
	public def VitruviusChange createVitruviusChange(ChangeDescription changeDescription, List<EChange> eChanges, VURI vuri) {
		return new VitruviusChangeImpl(changeDescription, eChanges, vuri);
	}
	
	public def ProcessableCompositeChange createProcessableCompositeChange() {
		return new ProcessableCompositeChangeImpl();
	}
}