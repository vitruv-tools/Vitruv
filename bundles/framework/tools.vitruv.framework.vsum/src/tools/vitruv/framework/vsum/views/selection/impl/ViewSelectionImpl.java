package tools.vitruv.framework.vsum.views.selection.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;

import com.google.common.collect.ImmutableList;

import tools.vitruv.framework.vsum.views.ModifiableViewSelection;
import tools.vitruv.framework.vsum.views.ViewSelector;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Preconditions.checkNotNull;

public class ViewSelectionImpl<S extends ViewSelector> implements ModifiableViewSelection {
	// TODO Discuss whether we want to guarantee some ordering in the elements. This
	// was not document yet, but maybe we want to have it
	final Map<EObject, Boolean> elementsSelection = new HashMap<>();

	public ViewSelectionImpl(Collection<EObject> selectableElements) {
		selectableElements.forEach(object -> this.elementsSelection
				.put(checkNotNull(object, "element to select must not be null"), false));
	}

	public ViewSelectionImpl(ModifiableViewSelection sourceViewSelection) {
		this(ImmutableList.copyOf(sourceViewSelection.getSelectableElements()));
		sourceViewSelection.getSelectedElements().forEach(element -> setSelected(element, true));
	}

	private void checkIsSelectable(EObject eObject) {
		checkState(elementsSelection.keySet().contains(eObject),
				"given object %s must be contained in the selector elements", eObject);
	}

	@Override
	public boolean isSelected(EObject eObject) {
		checkIsSelectable(eObject);
		return elementsSelection.get(eObject);
	}

	@Override
	public Iterable<EObject> getSelectedElements() {
		return elementsSelection.entrySet().stream().filter(entry -> entry.getValue()).map(entry -> entry.getKey())
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<EObject> getSelectableElements() {
		return elementsSelection.keySet();
	}

	@Override
	public void setSelected(EObject eObject, boolean selected) {
		checkIsSelectable(eObject);
		elementsSelection.put(eObject, selected);
	}

}
