package tools.vitruv.framework.change.echange.feature.util

import java.util.List
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import tools.vitruv.framework.change.echange.feature.FeatureEChange
import tools.vitruv.framework.change.echange.feature.UpdateMultiValuedFeatureEChange
import tools.vitruv.framework.change.echange.feature.attribute.util.AttributeApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.list.util.ListApplyForwardCommandSwitch
import tools.vitruv.framework.change.echange.feature.reference.util.ReferenceApplyForwardCommandSwitch

/**
 * Switch to create commands for all EChange classes of the feature package.
 * The commands applies the EChanges forward.
 */
public class FeatureApplyForwardCommandSwitch extends FeatureSwitch<List<Command>> {
	private static FeatureApplyForwardCommandSwitch instance;
	
	private new() {}
	
	/**
	 * Gets the singleton of the switch.
	 * @return The singleton instance.
	 */
	public static def FeatureApplyForwardCommandSwitch getInstance() {
		if (instance == null) {
			instance = new FeatureApplyForwardCommandSwitch();
		}
		return instance;
	}
	
	/**
	 * Create commands to apply a {@link FeatureEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	override public <A extends EObject, F extends EStructuralFeature> List<Command> caseFeatureEChange(FeatureEChange<A, F> object) {
		var result = AttributeApplyForwardCommandSwitch.instance.doSwitch(object)
		if (result == null) {
			result = ReferenceApplyForwardCommandSwitch.instance.doSwitch(object)
		}
		return result
	}
	
	/**
	 * Create commands to apply a {@link UpdateMultiValuedFeatureEChange} change forward.
	 * @param object The change which commands should be created.
	 */
	override public <A extends EObject, F extends EStructuralFeature> List<Command> caseUpdateMultiValuedFeatureEChange(UpdateMultiValuedFeatureEChange<A, F> object) {
		var result = ListApplyForwardCommandSwitch.instance.doSwitch(object)
		return result	
	}
}