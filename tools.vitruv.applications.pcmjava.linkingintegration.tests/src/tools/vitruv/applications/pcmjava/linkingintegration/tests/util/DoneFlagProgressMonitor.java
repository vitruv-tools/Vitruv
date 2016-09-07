package tools.vitruv.applications.pcmjava.linkingintegration.tests.util;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.NullProgressMonitor;

/**
     * Thread-safe simple progress monitor for knowing when a job is done.
     *
     */
    public class DoneFlagProgressMonitor extends NullProgressMonitor {

        private final AtomicBoolean isDone = new AtomicBoolean(false);

        @Override
        public void done() {
            this.isDone.set(true);
        }

        public boolean isDone() {
            return this.isDone.get();
        }

    }