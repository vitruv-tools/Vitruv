package tools.vitruv.framework.views.selection;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.views.ModifiableViewSelection;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractViewSelection implements ModifiableViewSelection {
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
		return Collections.unmodifiableSet(elementsSelection.keySet());
	}

	@Override
	public void setSelected(EObject eObject, boolean selected) {
		checkIsSelectable(eObject);
		elementsSelection.put(eObject, selected);
	}

}
