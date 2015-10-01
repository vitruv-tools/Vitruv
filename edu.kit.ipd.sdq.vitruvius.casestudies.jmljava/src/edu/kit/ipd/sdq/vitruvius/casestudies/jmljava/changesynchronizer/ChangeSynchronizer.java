package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.changesynchronizer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.ModelProvidingDirtyMarker;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.vitruvius.changesynchronizer.internal.VitruviusChangeSynchronizer;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause;

/**
 * Singleton implementation of the ChangeSynchronizer. Listeners can register to be informed about
 * the synchronization status (e.g. to stop listening for changes).
 */
public final class ChangeSynchronizer implements ChangeSynchronizing, SynchronisationAbortedListener {

    private static final Logger LOGGER = Logger.getLogger(ChangeSynchronizing.class);

    private final VitruviusChangeSynchronizer vitruviusSynchronizer = new VitruviusChangeSynchronizer();
    private final Set<SynchronisationListener> listeners = new HashSet<SynchronisationListener>();
    private boolean doNotAcceptNewChanges = false;

    /**
     * Registers a listener for the synchronization status.
     *
     * @param listener
     *            The listener to register.
     */
    public synchronized void register(final SynchronisationListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Unregisters a listener for the synchronization status.
     *
     * @param listener
     *            The listener to unregister.
     */
    public synchronized void unregister(final SynchronisationListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public List<List<Change>> synchronizeChanges(final List<Change> changes) {
        if (this.doNotAcceptNewChanges) {
            return null;
        }
        synchronized (this.vitruviusSynchronizer) {
            this.notifyListenersSynchronisationStarted();
            final List<List<Change>> ret = this.vitruviusSynchronizer.synchronizeChanges(changes);
            this.notifyListenersSynchronisationFinished();
            return ret;
        }
    }

    @Override
    public void synchronizeChange(final Change change) {
        if (this.doNotAcceptNewChanges) {
            return;
        }
        synchronized (this.vitruviusSynchronizer) {
            this.notifyListenersSynchronisationStarted();
            this.vitruviusSynchronizer.synchronizeChange(change);
            this.notifyListenersSynchronisationFinished();
        }
    }

    @Override
    public void synchronisationAborted(final EMFModelChange abortedChange) {
        this.notifyListenersSynchronisationAborted(abortedChange);
    }

    @Override
    public void synchronisationAborted(final TransformationAbortCause cause) {
        this.notifyListenersSynchronisationAborted(cause);
    }

    /**
     * Informs all listeners about the start of a synchronization.
     */
    private void notifyListenersSynchronisationStarted() {
        this.notifyListeners(new ListenerCaller<SynchronisationListener>() {
            @Override
            public void notifyListener(final SynchronisationListener listener) {
                listener.syncStarted();
            }
        });
    }

    /**
     * Informs all listeners about the end of a synchronization.
     */
    private void notifyListenersSynchronisationFinished() {
        this.notifyListeners(new ListenerCaller<SynchronisationListener>() {
            @Override
            public void notifyListener(final SynchronisationListener listener) {
                listener.syncFinished();
            }
        });
    }

    /**
     * Informs all listeners that the synchronisation has been aborted.
     *
     * @param abortedChange
     *            The unprocessed change because of the aborted transformation.
     */
    private void notifyListenersSynchronisationAborted(final EMFModelChange abortedChange) {
        this.notifyListeners(new ListenerCaller<SynchronisationListener>() {
            @Override
            public void notifyListener(final SynchronisationListener listener) {
                listener.syncAborted(abortedChange);
            }
        });
    }

    /**
     * Informs all listeners that the synchronisation has been aborted.
     *
     * @param cause
     *            The cause for the abortion.
     */
    private void notifyListenersSynchronisationAborted(final TransformationAbortCause cause) {
        this.notifyListeners(new ListenerCaller<SynchronisationListener>() {
            @Override
            public void notifyListener(final SynchronisationListener listener) {
                listener.syncAborted(cause);
            }
        });
    }

    /**
     * Informs the given listener by calling one or more methods of it.
     *
     * @author Stephan Seifermann
     *
     * @param <T>
     *            The type of the listener
     */
    private interface ListenerCaller<T> {

        /**
         * Informs the given listener.
         *
         * @param listener
         *            The listener to be informed.
         */
        void notifyListener(T listener);
    }

    /**
     * Notifies all listeners by passing the listener to the notify method of the given parameter.
     *
     * @param notifiable
     *            The notifier, which informs the listener.
     */
    private void notifyListeners(final ListenerCaller<SynchronisationListener> notifiable) {
        for (final SynchronisationListener listener : this.listeners) {
            try {
                notifiable.notifyListener(listener);
            } catch (final Exception e) {
                LOGGER.warn("Exception thrown by " + listener + " after notifying it.", e);
            }
        }
    }

    public ModelProvidingDirtyMarker getModelProvidingDirtyMarker() {
        return this.vitruviusSynchronizer.getModelProvidingDirtyMarker();
    }

    public void doNotAcceptNewChanges() {
        this.doNotAcceptNewChanges = true;
    }

}
