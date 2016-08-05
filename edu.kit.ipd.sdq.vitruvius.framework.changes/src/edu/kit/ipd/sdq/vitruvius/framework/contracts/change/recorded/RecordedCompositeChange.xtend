package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.recorded

import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.RecordedChange

interface RecordedCompositeChange extends CompositeChange<RecordedChange>, RecordedChange {
	
}