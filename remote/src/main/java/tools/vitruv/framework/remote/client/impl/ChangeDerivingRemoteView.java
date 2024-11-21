package tools.vitruv.framework.remote.client.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceCopier;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.change.atomic.hid.HierarchicalId;
import tools.vitruv.change.composite.description.VitruviusChange;
import tools.vitruv.change.composite.description.VitruviusChangeFactory;
import tools.vitruv.framework.views.CommittableView;
import tools.vitruv.framework.views.ViewSelection;
import tools.vitruv.framework.views.ViewSelector;
import tools.vitruv.framework.views.ViewType;
import tools.vitruv.framework.views.changederivation.StateBasedChangeResolutionStrategy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * A {@link RemoteView} that derives changes based on the changed state of its resources and allows to propagate them
 * back to the Vitruvius server using the {@link #commitChanges} method.
 */
public class ChangeDerivingRemoteView implements CommittableView {
    private final RemoteView base;
    private final StateBasedChangeResolutionStrategy resolutionStrategy;

    private Map<Resource, Resource> originalResourceMapping;

    ChangeDerivingRemoteView(RemoteView base, StateBasedChangeResolutionStrategy resolutionStrategy) {
        checkArgument(base != null, "base must not be null");
        checkState(!base.isModified(), "view must not be modified");
        checkState(!base.isOutdated(), "view must not be outdated");
        checkArgument(resolutionStrategy != null, "resolution strategy must not be null");
        this.base = base;
        this.resolutionStrategy = resolutionStrategy;

        initializeResourceMapping(base.viewSource);
    }

    private void initializeResourceMapping(ResourceSet source) {
        originalResourceMapping = ResourceCopier.copyViewResources(source.getResources(), 
        		ResourceSetUtil.withGlobalFactories(new ResourceSetImpl()));
    }

    @Override
    public void close() {
        base.close();
    }

    @Override
    public Collection<EObject> getRootObjects() {
        return base.getRootObjects();
    }

    @Override
    public boolean isModified() {
        return base.isModified();
    }

    @Override
    public boolean isOutdated() {
        return base.isOutdated();
    }

    @Override
    public void update() {
        base.update();
        initializeResourceMapping(base.viewSource);
    }

    @Override
    public boolean isClosed() {
        return base.isClosed();
    }

    @Override
    public void registerRoot(EObject object, URI persistAt) {
        base.registerRoot(object, persistAt);
    }

    @Override
    public void moveRoot(EObject object, URI newLocation) {
        base.moveRoot(object, newLocation);
    }

    @Override
    public ViewSelection getSelection() {
        return base.getSelection();
    }

    @Override
    public ViewType<? extends ViewSelector> getViewType() {
        return base.getViewType();
    }

    @Override
    public CommittableView withChangeRecordingTrait() {
        return base.withChangeRecordingTrait();
    }

    @Override
    public CommittableView withChangeDerivingTrait(StateBasedChangeResolutionStrategy changeResolutionStrategy) {
        return base.withChangeDerivingTrait(changeResolutionStrategy);
    }

    /**
     * Commits the changes made to the view and its containing elements.
     *
     * @throws IllegalStateException if called on a closed view
     * @see #isClosed()
     * @see #commitChangesAndUpdate()
     */
    @Override
    public void commitChanges() {
        base.checkNotClosed();
        var allChanges = new LinkedList<VitruviusChange<HierarchicalId>>();
        base.viewSource.getResources().forEach(it -> {
            var changes = findChanges(originalResourceMapping.get(it), it);
            if (changes.getEChanges().size() > 0) {
                allChanges.add(changes);
            }
        });
        base.remoteConnection.propagateChanges(base.uuid, VitruviusChangeFactory.getInstance().createCompositeChange(allChanges));
        base.modified = false;
    }

    private VitruviusChange<HierarchicalId> findChanges(Resource oldState, Resource newState) {
        if (oldState == null) {
            return resolutionStrategy.getChangeSequenceForCreated(newState);
        } else if (newState == null) {
            return resolutionStrategy.getChangeSequenceForDeleted(oldState);
        } else {
            return resolutionStrategy.getChangeSequenceBetween(newState, oldState);
        }
    }
}
