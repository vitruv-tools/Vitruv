package tools.vitruv.framework.tests

import java.util.ArrayList
import java.util.Arrays
import java.util.Collection
import java.util.Random
import java.util.concurrent.ConcurrentLinkedQueue
import org.apache.commons.lang.StringUtils
import org.apache.log4j.Logger
import org.eclipse.emf.common.util.URI
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.userinteraction.UserInteractionType

/** 
 * The {@link TestUserInteractor} can be used in tests to simulate UserInteracting. It has a queue
 * of next selections. If the queue is empty the {@link TestUserInteractor} decides randomly the
 * next selection. It also allows to simulate the thinking time for a user.
 */
class TestUserInteractor implements UserInteracting {
	static final Logger logger = Logger::getLogger(typeof(TestUserInteractor))
	final ConcurrentLinkedQueue<Integer> concurrentIntLinkedQueue
	final ConcurrentLinkedQueue<String> concurrentStringLinkedQueue
	final ConcurrentLinkedQueue<URI> concurrentURILinkedQueue
	final Random random
	final int minWaittime
	final int maxWaittime
	final int waitTimeRange
	Collection<String> messageLog

	new(int minWaittime, int maxWaittime) {
		if (minWaittime > maxWaittime) {
			throw new RuntimeException(
				'''Configure min and max waittime properly: Min«minWaittime» Max: «maxWaittime»'''.toString)
		}
		this.minWaittime = minWaittime
		this.maxWaittime = maxWaittime
		this.waitTimeRange = maxWaittime - minWaittime
		this.concurrentIntLinkedQueue = new ConcurrentLinkedQueue<Integer>
		this.concurrentStringLinkedQueue = new ConcurrentLinkedQueue<String>
		this.concurrentURILinkedQueue = new ConcurrentLinkedQueue<URI>
		this.random = new Random
		this.messageLog = new ArrayList<String>
	}

	new() {
		this(-1, -1)
	}

	def void addNextSelections(Integer... nextSelections) {
		this.concurrentIntLinkedQueue.clear
		this.concurrentIntLinkedQueue.addAll(Arrays::asList(nextSelections))
	}

	def void addNextSelections(String... nextSelections) {
		this.concurrentStringLinkedQueue.clear
		this.concurrentStringLinkedQueue.addAll(Arrays::asList(nextSelections))
	}

	def void addNextSelections(URI... nextSelections) {
		this.concurrentURILinkedQueue.clear
		this.concurrentURILinkedQueue.addAll(Arrays::asList(nextSelections))
	}

	override void showMessage(UserInteractionType type, String message) {
		logger.info('''showMessage: «message» Type: «type»'''.toString)
		this.messageLog.add(message)
	}

	override int selectFromMessage(UserInteractionType type, String message, String... selectionDescriptions) {
		logger.info(
			'''selectFromMessage: «message» Type: «type» Choices: «StringUtils::join(selectionDescriptions, ", ")»'''.
				toString)
				return this.selectFromMessage(selectionDescriptions.length)
			}

			def private int selectFromMessage(int maxLength) {
				this.simulateUserThinktime
				var int currentSelection
				if (!this.concurrentIntLinkedQueue.isEmpty) {
					currentSelection = this.concurrentIntLinkedQueue.poll
					if (currentSelection >= maxLength) {
						logger.warn(
							"currentSelection>maxLength - could lead to array out of bounds exception later on.")
					}
				} else {
					throw new IllegalStateException("No user interaction integer selection specified")
				}
				logger.info('''«typeof(TestUserInteractor).simpleName» selected «currentSelection»'''.toString)
				return currentSelection
			}

			def private void simulateUserThinktime() {
				if (-1 < this.maxWaittime) {
					val int currentWaittime = this.random.nextInt(this.waitTimeRange + 1) + this.minWaittime
					try {
						Thread::sleep(currentWaittime)
					} catch (InterruptedException e) {
						logger.trace('''User think time simulation thread interrupted: «e»'''.toString, e)
					}

				}
			}

			override String getTextInput(String msg) {
				this.simulateUserThinktime
				var String text = ""
				if (!this.concurrentStringLinkedQueue.isEmpty) {
					text = this.concurrentStringLinkedQueue.poll
				} else {
					throw new IllegalStateException("No user interaction integer selection specified")
				}
				logger.info('''«typeof(TestUserInteractor).simpleName» selecteded «text»'''.toString)
				return text
			}

			override URI selectURI(String message) {
				if (this.concurrentURILinkedQueue.isEmpty) {
					throw new IllegalStateException(
						'''No URI found in «typeof(TestUserInteractor).simpleName» for message «message»'''.
							toString)
						}
						val URI result = this.concurrentURILinkedQueue.poll
						logger.info(
							'''«typeof(TestUserInteractor).simpleName» selected «result.toString»'''.toString)
						return result
					}

					def boolean isResourceQueueEmpty() {
						return this.concurrentURILinkedQueue.isEmpty
					}

					def Collection<String> getMessageLog() {
						return this.messageLog
					}
				}
				