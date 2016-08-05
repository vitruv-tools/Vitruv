package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URIHaving
import java.util.List
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProcessableChange

interface VitruviusChange extends ProcessableChange, URIHaving {
	public def List<EChange> getEChanges();
	public def ChangeDescription getOriginalChangeDescription();
}