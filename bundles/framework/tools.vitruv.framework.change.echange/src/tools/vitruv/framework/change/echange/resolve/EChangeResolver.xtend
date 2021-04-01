package tools.vitruv.framework.change.echange.resolve

import tools.vitruv.framework.uuid.UuidResolver
import org.eclipse.emf.ecore.util.EcoreUtil
import tools.vitruv.framework.change.echange.EChange
import static com.google.common.base.Preconditions.checkArgument
import edu.kit.ipd.sdq.activextendannotations.Utility
import tools.vitruv.framework.change.echange.command.ApplyEChangeSwitch

/**
 * Utility class for applying and resolving a given EChange.
 */
@Utility
class EChangeResolver {
	static def EChange resolveBefore(EChange eChange, UuidResolver uuidResolver) {
		return resolveCopy(eChange, uuidResolver)
	}

	static def void applyForward(EChange eChange) {
		ApplyEChangeSwitch.applyEChange(eChange, true)
	}

	static def void applyBackward(EChange eChange) {
		ApplyEChangeSwitch.applyEChange(eChange, false)
	}

	/**
	 * Creates a copy of the change and resolves it using the given {@link UuidResolver}.
	 * 	 * 
	 * @param change					The {@link EChange} which shall be resolved.
	 * @param uuidResolver 				The {@link UuidResolver} to resolve {@link EObject}s from
	 * @param resolveBefore				{@code true} if the model is in the state before the change,
	 * 									{@code false} if the model is in the state after.
	 * @return 							Returns a resolved copy of the change. If the copy could not be resolved 
	 * 									an {@link IllegalStateException} is thrown
	 * @throws IllegalArgumentException The change is already resolved.
	 * @throws IllegalStateException 	The change cannot be resolved.
	 */
	def private static EChange resolveCopy(EChange change, UuidResolver uuidResolver) {
		checkArgument(!change.isResolved, "change must not be resolved when trying to resolve")
		var EChange copy = EcoreUtil.copy(change)
		new AtomicEChangeResolver(uuidResolver).resolve(copy)
		return copy
	}
	
}
