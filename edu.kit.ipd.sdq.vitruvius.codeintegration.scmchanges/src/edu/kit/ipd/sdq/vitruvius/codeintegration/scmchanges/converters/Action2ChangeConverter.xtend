package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.converters

import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.IAction2ChangeConverter
import com.github.gumtreediff.actions.model.Action
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.visitors.Action2ChangeConvertingActionVisitor

class Action2ChangeConverter implements IAction2ChangeConverter {
	
	override convertToChanges(Iterable<Action> actions) {
		val visitor = new Action2ChangeConvertingActionVisitor
		actions.forEach[it.accept(visitor)]
		return visitor.changes
	}
	
}