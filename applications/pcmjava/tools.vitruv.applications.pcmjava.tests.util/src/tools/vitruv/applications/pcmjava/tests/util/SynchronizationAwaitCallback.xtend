package tools.vitruv.applications.pcmjava.tests.util

interface SynchronizationAwaitCallback {
	def void waitForSynchronization(int numberOfExpectedSynchronizationCalls);
}