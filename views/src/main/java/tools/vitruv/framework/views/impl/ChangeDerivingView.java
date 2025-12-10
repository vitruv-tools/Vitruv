package tools.vitruv.framework.views.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.common.util.URIUtil;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.framework.views.ChangeableViewSource;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

/**
 * A view that derives changes based on the changed state of its
 * resources and allows to propagate them
 * back to the underlying models using the {@link #commitChanges} method.
 */
public class ChangeDerivingView implements ModifiableView, CommittableView {
    private final BasicView view;
    private final StateBasedChangeResolutionStrategy changeResolutionStrategy;
    private ResourceSet originalStateViewResourceSet;
    private Map<Resource, Resource> originalStateResourceMapping;

    /**
     * Creates a new instance with the given underlying view and change resolution
     * 
     * @param view
     * @param changeResolutionStrategy
     */
    public ChangeDerivingView(BasicView view, StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        checkArgument(view != null, "view must not be null");
        checkState(!view.isModified(), "view must not be modified");
        checkState(!view.isOutdated(), "view must not be outdated");
        checkArgument(changeResolutionStrategy != null, "change resolution strategy must not be null");
        this.view = view;
        this.changeResolutionStrategy = changeResolutionStrategy;
        setupReferenceState();
    }

    @Override
    public void update() {
        closeOriginalState();
        view.update();
        setupReferenceState();
    }

    private void setupReferenceState() {
        originalStateViewResourceSet = new ResourceSetImpl();
        ResourceCopier.copyViewResources(view.getViewResourceSet().getResources(), originalStateViewResourceSet);
        originalStateResourceMapping = new HashMap<>();
        view.getViewResourceSet().getResources().forEach(resource -> {
            Resource originalResource = originalStateViewResourceSet.getResources().stream()
                    .filter(res -> res.getURI().equals(resource.getURI()))
                    .findFirst()
                    .orElse(null);
            originalStateResourceMapping.put(resource, originalResource);
        });
    }

    @Override
    public void commitChanges() {
        view.checkNotClosed();
        List<VitruviusChange<HierarchicalId>> changes = new ArrayList<>();
        Set<Resource> allResources = new HashSet<>(originalStateResourceMapping.keySet());
        allResources.addAll(view.getViewResourceSet().getResources()); // consider newly added resources

        for (Resource changedResource : allResources) {
            if (!URIUtil.isPathmap(changedResource.getURI())) {
                Resource referenceResource = originalStateResourceMapping.get(changedResource);
                VitruviusChange<HierarchicalId> change = generateChange(changedResource, referenceResource);
                if (change.containsConcreteChange()) {
                    // only add changes that can be propagated, i.e., that are not empty
                    // (see https://github.com/vitruv-tools/Vitruv/issues/717)
                    changes.add(change);
                }
            }
        }

        VitruviusChange<HierarchicalId> change = VitruviusChangeFactory.getInstance().createCompositeChange(changes);
        view.getViewType().commitViewChanges(this, change);
        view.setViewChanged(false);
    }

    @Override
    public void close() throws Exception {
        if (!isClosed()) {
            closeOriginalState();
        }
        view.close();
    }

    private VitruviusChange<HierarchicalId> generateChange(Resource newState, Resource referenceState) {
        if (referenceState == null) {
            return changeResolutionStrategy.getChangeSequenceForCreated(newState);
        } else if (newState == null) {
            return changeResolutionStrategy.getChangeSequenceForDeleted(referenceState);
        } else {
            return changeResolutionStrategy.getChangeSequenceBetween(newState, referenceState);
        }
    }

    private void closeOriginalState() {
        originalStateViewResourceSet.getResources().forEach(resource -> {
            try {
                resource.unload();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        originalStateViewResourceSet.getResources().clear();
    }

    @Override
    public ChangeRecordingView withChangeRecordingTrait() {
        ChangeRecordingView newView = view.withChangeRecordingTrait();
        closeOriginalState();
        return newView;
    }

    @Override
    public ChangeDerivingView withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        ChangeDerivingView newView = view.withChangeDerivingTrait(changeResolutionStrategy);
        closeOriginalState();
        return newView;
    }

    // Delegation methods from BasicView
    @Override
    public List<org.eclipse.emf.ecore.EObject> getRootObjects() {
        return view.getRootObjects();
    }

    @Override
    public boolean isModified() {
        return view.isModified();
    }

    @Override
    public boolean isOutdated() {
        return view.isOutdated();
    }

    @Override
    public void modifyContents(java.util.function.Consumer<ResourceSet> modificationFunction) {
        view.modifyContents(modificationFunction);
    }

    @Override
    public void registerRoot(org.eclipse.emf.ecore.EObject object, org.eclipse.emf.common.util.URI persistAt) {
        view.registerRoot(object, persistAt);
    }

    @Override
    public void moveRoot(org.eclipse.emf.ecore.EObject object, org.eclipse.emf.common.util.URI newLocation) {
        view.moveRoot(object, newLocation);
    }

    @Override
    public boolean isClosed() {
        return view.isClosed();
    }

    @Override
    public ViewSelection getSelection() {
        return view.getSelection();
    }

    @Override
    public ViewType<? extends ViewSelector> getViewType() {
        return view.getViewType();
    }

    @Override
    public ChangeableViewSource getViewSource() {
        return view.getViewSource();
    }
}