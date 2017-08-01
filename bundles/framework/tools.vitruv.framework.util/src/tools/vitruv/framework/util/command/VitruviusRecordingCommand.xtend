package tools.vitruv.framework.util.command

import java.util.Collections

import org.apache.log4j.Logger

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.transaction.RecordingCommand
import org.eclipse.emf.transaction.Transaction
import org.eclipse.emf.transaction.TransactionChangeDescription
import org.eclipse.emf.transaction.TransactionalEditingDomain

import tools.vitruv.framework.util.bridges.JavaBridge

abstract class VitruviusRecordingCommand extends RecordingCommand implements Command {
	protected static val logger = Logger::getLogger(VitruviusRecordingCommand.simpleName)
	protected ChangePropagationResult transformationResult
	RuntimeException runtimeException

	new() {
		super(null)
		this.runtimeException = null
	}

	def void setTransactionDomain(TransactionalEditingDomain domain) {
		JavaBridge.setFieldInClass(RecordingCommand, "domain", this, domain)
	}

	override protected void preExecute() {
		runtimeException = null
		super.preExecute
	}

	def void setRuntimeException(RuntimeException e) {
		runtimeException = e
	}

	def void rethrowRuntimeExceptionIfExisting() {
		if (runtimeException !== null)
			throw (runtimeException)
	}

	def protected void storeAndRethrowException(Throwable e) {
		var RuntimeException r
		if (e instanceof RuntimeException) {
			r = e
		} else {
			// soften
			r = new RuntimeException(e)
		}
		setRuntimeException(r)
		// just log and rethrow
		throw (r)
	}

	override getAffectedObjects() {
		val Transaction transaction = JavaBridge::getFieldFromClass(RecordingCommand, "transaction", this)
		if (transaction === null) {
			// TODO DW what to do, if transaction is null? when is this the case?
			return Collections::EMPTY_SET
		}
		val TransactionChangeDescription changeDescription = transaction.changeDescription
		val affectedEObjects = EMFChangeBridge::getAffectedObjects(changeDescription)
		return affectedEObjects
	}
}
