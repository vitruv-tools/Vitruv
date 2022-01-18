package tools.vitruv.framework.vsum.views.selection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.ImmutableList;

import tools.vitruv.framework.vsum.views.ModifiableViewSelection;
import tools.vitruv.framework.vsum.views.ViewSelector;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractViewSelection<S extends ViewSelector> implements ModifiableViewSelection {
	// TODO Discuss whether we want to guarantee some ordering in the elements. This
	// was not document yet, but maybe we want to have it
	final Map<EObject, Boolean> elementsSelection = new HashMap<>();

	public AbstractViewSelection(Collection<EObject> selectableElements) {
		selectableElements.forEach(object -> this.elementsSelection
				.put(checkNotNull(object, "element to select must not be null"), false));
	}

	public AbstractViewSelection(ModifiableViewSelection sourceViewSelection) {
		this(sourceViewSelection.getSelectableElements());
		for (EObject selectableElement : sourceViewSelection.getSelectableElements()) {
			setSelected(selectableElement, sourceViewSelection.isSelected(selectableElement));
		}
	}

	private void checkIsSelectable(EObject eObject) {
		checkState(isSelectable(eObject), "given object %s must be contained in the selector elements", eObject);
	}

	@Override
	public boolean isSelected(EObject eObject) {
		return elementsSelection.getOrDefault(eObject, false);
	}

	@Override
	public boolean isSelectable(EObject eObject) {
		return elementsSelection.keySet().contains(eObject);
	}

	@Override
	public Collection<EObject> getSelectableElements() {
		return ImmutableList.copyOf(elementsSelection.keySet());
	}

	@Override
	public void setSelected(EObject eObject, boolean selected) {
		checkIsSelectable(eObject);
		elementsSelection.put(eObject, selected);
	}

}
