package tools.vitruv.framework.vsum.views.selection;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.vsum.views.ModifiableViewSelection;
import tools.vitruv.framework.vsum.views.ViewSelector;

public class ElementViewSelection<S extends ViewSelector> extends AbstractViewSelection<S> {

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
