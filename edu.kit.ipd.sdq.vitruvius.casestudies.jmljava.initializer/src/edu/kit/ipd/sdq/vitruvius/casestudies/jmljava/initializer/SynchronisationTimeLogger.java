package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.initializer;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.google.common.base.Stopwatch;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.SynchronisationListener;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.user.TransformationAbortCause;

public final class SynchronisationTimeLogger implements SynchronisationListener {

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
	public void syncAborted(EMFModelChange abortedChange) {
	}

	@Override
	public void syncAborted(TransformationAbortCause cause) {
	}
	
	public static SynchronisationTimeLogger getInstance() {
		return INSTANCE;
	}

}
