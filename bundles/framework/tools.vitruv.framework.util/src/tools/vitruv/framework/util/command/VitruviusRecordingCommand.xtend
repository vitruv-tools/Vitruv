package tools.vitruv.framework.util.command

import java.util.Collections

import org.apache.log4j.Logger

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.transaction.RecordingCommand
import org.eclipse.emf.transaction.Transaction
import org.eclipse.emf.transaction.TransactionalEditingDomain

import tools.vitruv.framework.util.bridges.JavaBridge
import org.eclipse.xtend.lib.annotations.Accessors

abstract class VitruviusRecordingCommand extends RecordingCommand implements Command {
	protected static extension Logger = Logger::getLogger(VitruviusRecordingCommand.simpleName)
	protected ChangePropagationResult transformationResult
	@Accessors(PUBLIC_SETTER)
	RuntimeException runtimeException

	new() {
		super(null)
		this.runtimeException = null
	}

	def void setTransactionDomain(TransactionalEditingDomain domain) {
		JavaBridge::setFieldInClass(RecordingCommand, "domain", this, domain)
	}

	override protected void preExecute() {
		runtimeException = null
		super.preExecute
	}

	def void rethrowRuntimeExceptionIfExisting() {
		if (runtimeException !== null)
			throw runtimeException
	}

	def protected void storeAndRethrowException(Throwable e) {
		// soften
		val r = if (e instanceof RuntimeException) e else new RuntimeException(e)
		runtimeException = r
		// just log and rethrow
		throw (r)
	}

	override getAffectedObjects() {
		val transaction = JavaBridge::getFieldFromClass(RecordingCommand, "transaction", this) as Transaction
		if (transaction === null) {
			// TODO DW what to do, if transaction is null? when is this the case?
			return Collections::EMPTY_SET
		}
		val affectedEObjects = EMFChangeBridge::getAffectedObjects(transaction.changeDescription)
		return affectedEObjects
	}
}
