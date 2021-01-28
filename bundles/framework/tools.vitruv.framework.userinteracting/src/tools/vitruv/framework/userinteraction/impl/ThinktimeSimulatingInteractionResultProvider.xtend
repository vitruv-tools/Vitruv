package tools.vitruv.framework.userinteraction.impl

import org.apache.log4j.Logger
import java.util.Random
import tools.vitruv.framework.userinteraction.InteractionResultProvider
import org.eclipse.xtend.lib.annotations.Delegate

/**
 * A predefined result provider that also simulates a think time on each request.
 * 
 * @author Heiko Klare
 */
class ThinktimeSimulatingInteractionResultProvider implements InteractionResultProvider {
	static val Logger logger = Logger.getLogger(ThinktimeSimulatingInteractionResultProvider)
	val Random random = new Random()
	val int minWaittime
	val int maxWaittime
	val int waitTimeRange
	val  InteractionResultProvider delegate

	/**
	 * @param minWaittime - the minimum time to wait in milliseconds 
	 * @param maxWaittime - the maximum time to wait in milliseconds
	 */
	new(InteractionResultProvider delegate, int minWaittime, int maxWaittime) {
		if (minWaittime > maxWaittime) {
			throw new RuntimeException(
				"Configure min and max waittime properly: Min" + minWaittime + " Max: " + maxWaittime)
		}
		this.delegate = delegate
		this.minWaittime = minWaittime
		this.maxWaittime = maxWaittime
		this.waitTimeRange = maxWaittime - minWaittime
	}

	private def void simulateUserThinktime() {
		if (-1 < this.maxWaittime) {
			val currentWaittime = this.random.nextInt(this.waitTimeRange + 1) + this.minWaittime
			try {
				Thread.sleep(currentWaittime)
			} catch (InterruptedException e) {
				logger.trace("User think time simulation thread interrupted: " + e, e)
			}
		}
	}
	
	@Delegate
	private def InteractionResultProvider waitAndAccessDelegate() {
		simulateUserThinktime()
		delegate
	}
}
