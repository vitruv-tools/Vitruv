package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.visitors

import com.github.gumtreediff.actions.ActionVisitor
import com.github.gumtreediff.actions.model.Delete
import com.github.gumtreediff.actions.model.Insert
import com.github.gumtreediff.actions.model.Move
import com.github.gumtreediff.actions.model.Update
import java.util.HashSet
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange
import java.util.Set
import org.apache.log4j.Logger

class Action2ChangeConvertingActionVisitor implements ActionVisitor {
	
	private final static Logger logger = Logger::getLogger(Action2ChangeConvertingActionVisitor)
	
	private final Set<EChange> changes = new HashSet;
	
	override visit(Delete delete) {
		logger.warn("TODO: Delete Action 2 Change conversion not yet implemented")
	}
	
	override visit(Insert insert) {
		logger.warn("TODO: Insert Action 2 Change conversion not yet implemented")
		if (insert.node.leaf) {
			logger.warn('''Insert Action:
			
			«insert» ''')
		}
	}
	
	override visit(Move move) {
		logger.warn("Move: Move Action 2 Change conversion not yet implemented")
	}
	
	override visit(Update update) {
		logger.warn("TODO: Update Action 2 Change conversion not yet implemented")
	}
	
	def getChanges() {
		return changes;
	}
}