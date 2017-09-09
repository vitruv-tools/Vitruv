package tools.vitruv.framework.change.echange

import tools.vitruv.framework.change.echange.resolve.EChangeResolver
import tools.vitruv.framework.change.uuid.UuidResolver
import tools.vitruv.framework.change.echange.util.ApplyEChangeSwitch
import com.google.common.base.Objects

class EChangeResolverAndApplicator {
	static def EChange resolveBefore(EChange eChange, UuidResolver uuidResolver) {
		return EChangeResolver.resolveCopy(eChange, uuidResolver, true, true);
	}

	static def EChange resolveAfter(EChange eChange, UuidResolver uuidResolver) {
		return EChangeResolver.resolveCopy(eChange, uuidResolver, false, true);
	}
	
	static def EChange resolveBeforeAndApplyForward(EChange eChange, UuidResolver uuidResolver) {
		val EChange resolvedChange = resolveBefore(eChange, uuidResolver);
		if (((!Objects.equal(resolvedChange, null)) && resolvedChange.applyForward())) {
			return resolvedChange;
		}
		else {
			return null;
		}
	}

	static def EChange resolveAfterAndApplyBackward(EChange eChange, UuidResolver uuidResolver) {
		val EChange resolvedChange = resolveAfter(eChange, uuidResolver);
		if (((!Objects.equal(resolvedChange, null)) && resolvedChange.applyBackward())) {
			return resolvedChange;
		}
		else {
			return null;
		}
	}

	static def boolean applyForward(EChange eChange) {
		return ApplyEChangeSwitch.applyEChange(eChange, true);
	}

	static def boolean applyBackward(EChange eChange) {
		return ApplyEChangeSwitch.applyEChange(eChange, false);
	}
}