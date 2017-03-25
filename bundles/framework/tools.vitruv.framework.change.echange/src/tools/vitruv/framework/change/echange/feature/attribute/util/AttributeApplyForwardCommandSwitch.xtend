package tools.vitruv.framework.change.echange.feature.attribute.util

import java.util.Collections
import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.command.SetCommand
import tools.vitruv.framework.change.echange.feature.attribute.InsertEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.RemoveEAttributeValue
import tools.vitruv.framework.change.echange.feature.attribute.ReplaceSingleValuedEAttribute
import tools.vitruv.framework.change.echange.util.EChangeUtil
import tools.vitruv.framework.change.echange.util.RemoveAtCommand

/**
 * Switch to create commands for all EChange classes of the attribute package.
 * The commands applies the EChanges forward.
 */
public class AttributeApplyForwardCommandSwitch extends AttributeSwitch<List<Command>> {
	private static AttributeApplyForwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def AttributeApplyForwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new AttributeApplyForwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a {@link InsertEAttributeValue} change forward.
	 * @param object The change which commands should be created.
	 */
	 override public <A extends EObject, T extends Object> List<Command> caseInsertEAttributeValue(InsertEAttributeValue<A, T> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return Collections.singletonList(AddCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue, object.index))
	}	

	/**
	 * Create commands to apply a {@link RemoveEAttributeValue} change forward.
	 * @param object The change which commands should be created.
	 */
	override public <A extends EObject, T extends Object> List<Command> caseRemoveEAttributeValue(RemoveEAttributeValue<A, T> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return Collections.singletonList(RemoveAtCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.oldValue, object.index))
	}
	
	/**
	 * Create commands to apply a {@link ReplaceSingleValuedEAttribute} change forward.
	 * @param object The change which commands should be created.
	 */
	override public <A extends EObject, T extends Object> List<Command> caseReplaceSingleValuedEAttribute(ReplaceSingleValuedEAttribute<A, T> object) {
		val editingDomain = EChangeUtil.getEditingDomain(object.affectedEObject)
		return Collections.singletonList(SetCommand.create(editingDomain, object.affectedEObject, object.affectedFeature, object.newValue))
	}	
}