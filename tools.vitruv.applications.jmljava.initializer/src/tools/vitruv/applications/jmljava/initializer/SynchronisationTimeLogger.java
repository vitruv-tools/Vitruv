package tools.vitruv.applications.jmljava.initializer;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Stopwatch;

import tools.vitruv.applications.jmljava.changesynchronizer.JmlSynchronizationListener;
import tools.vitruv.framework.change.description.GeneralChange;
import tools.vitruv.framework.modelsynchronization.SynchronisationListener;
import tools.vitruv.framework.modelsynchronization.TransformationAbortCause;

public final class SynchronisationTimeLogger implements JmlSynchronizationListener {

	private static final Logger LOGGER = Logger.getLogger(SynchronisationTimeLogger.class);
	private static final SynchronisationTimeLogger INSTANCE = new SynchronisationTimeLogger();
	private final Stopwatch timer = Stopwatch.createUnstarted();
	
	private SynchronisationTimeLogger() {
		
	}
	
	@Override
	public void syncStarted() {
		timer.reset();
		timer.start();
	}

	@Override
	public void syncFinished() {
		timer.stop();
		LOGGER.info(String.format("Synchronisation duration: %.3f s", timer.elapsed(TimeUnit.MILLISECONDS) / 1000.0));
		timer.reset();
	}

	@Override
	public void syncAborted(GeneralChange abortedChange) {
	}

	@Override
	public void syncAborted(TransformationAbortCause cause) {
	}
	
	public static SynchronisationTimeLogger getInstance() {
		return INSTANCE;
	}

}
