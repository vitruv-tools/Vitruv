package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.URIHaving
import org.eclipse.emf.ecore.change.ChangeDescription
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.RecordedChange

interface EMFModelChange extends RecordedChange, URIHaving {
    def ChangeDescription getChangeDescription();
}
