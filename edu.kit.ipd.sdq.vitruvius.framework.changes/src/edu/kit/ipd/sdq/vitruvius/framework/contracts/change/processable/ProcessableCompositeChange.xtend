package edu.kit.ipd.sdq.vitruvius.framework.contracts.change.processable

import edu.kit.ipd.sdq.vitruvius.framework.contracts.change.CompositeChange
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ProcessableChange

interface ProcessableCompositeChange extends CompositeChange<ProcessableChange>, ProcessableChange {
	
}