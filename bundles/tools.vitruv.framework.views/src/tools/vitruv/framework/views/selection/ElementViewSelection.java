package tools.vitruv.framework.views.selection;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.views.ModifiableViewSelection;

public class ElementViewSelection extends AbstractViewSelection {

	public ElementViewSelection(Collection<EObject> selectableElements) {
		super(selectableElements);
	}

	public ElementViewSelection(ModifiableViewSelection sourceViewSelection) {
		super(sourceViewSelection);
	}

	@Override
	public boolean isViewObjectSelected(EObject eObject) {
		return isSelected(eObject);
	}

}
