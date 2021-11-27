package tools.vitruv.framework.change.echange.command

import java.util.Collections
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.domain.EditingDomain

/**
 * TODO: this needs to be implemented such that the index is not used. Instead, a list of predecessor objects (or their IDs) must be used for determining the insert position.
 */
class AddOrderCommand extends AddCommand {

	new(EditingDomain domain, EObject owner, EStructuralFeature feature, Object value) {
		super(domain, owner, feature, value)
	}

	new(EditingDomain domain, EList<?> ownerList, Object value) {
		super(domain, ownerList, Collections.singleton(value));
	}

}
