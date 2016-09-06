package edu.kit.ipd.sdq.vitruvius.applications.jmljava.changesynchronizer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.ModelProvidingDirtyMarker;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.vitruvius.changesynchronizer.internal.VitruviusChangeSynchronizer;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.GeneralChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.ChangeSynchronizing;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.modelsynchronization.TransformationAbortCause;

/**
 * Singleton implementation of the ChangeSynchronizer. Listeners can register to be informed about
 * the synchronization status (e.g. to stop listening for changes).
 */
public final class ChangeSynchronizer implements ChangeSynchronizing, SynchronisationAbortedListener {

    private static final Logger LOGGER = Logger.getLogger(ChangeSynchronizing.class);

    private final VitruviusChangeSynchronizer vitruviusSynchronizer = new VitruviusChangeSynchronizer();
    private final Set<JmlSynchronizationListener> listeners = new HashSet<JmlSynchronizationListener>();
    private boolean doNotAcceptNewChanges = false;

    /**
     * Registers a listener for the synchronization status.
     *
     * @param listener
     *            The listener to register.
     */
    public synchronized void register(final JmlSynchronizationListener listener) {
        this.listeners.add(listener);
    }

    /**
     * Unregisters a listener for the synchronization status.
     *
     * @param listener
     *            The listener to unregister.
     */
    public synchronized void unregister(final JmlSynchronizationListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public List<List<VitruviusChange>> synchronizeChange(final VitruviusChange change) {
        if (this.doNotAcceptNewChanges) {
            return null;
        }
        synchronized (this.vitruviusSynchronizer) {
            this.notifyListenersSynchronisationStarted();
            final List<List<VitruviusChange>> ret = this.vitruviusSynchronizer.synchronizeChange(change);
            this.notifyListenersSynchronisationFinished();
            return ret;
        }
    }

    @Override
    public void synchronisationAborted(final GeneralChange abortedChange) {
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
        this.notifyListeners(new ListenerCaller<JmlSynchronizationListener>() {
            @Override
            public void notifyListener(final JmlSynchronizationListener listener) {
                listener.syncStarted();
            }
        });
    }

    /**
     * Informs all listeners about the end of a synchronization.
     */
    private void notifyListenersSynchronisationFinished() {
        this.notifyListeners(new ListenerCaller<JmlSynchronizationListener>() {
            @Override
            public void notifyListener(final JmlSynchronizationListener listener) {
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
    private void notifyListenersSynchronisationAborted(final GeneralChange abortedChange) {
        this.notifyListeners(new ListenerCaller<JmlSynchronizationListener>() {
            @Override
            public void notifyListener(final JmlSynchronizationListener listener) {
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
        this.notifyListeners(new ListenerCaller<JmlSynchronizationListener>() {
            @Override
            public void notifyListener(final JmlSynchronizationListener listener) {
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
    private void notifyListeners(final ListenerCaller<JmlSynchronizationListener> notifiable) {
        for (final JmlSynchronizationListener listener : this.listeners) {
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
