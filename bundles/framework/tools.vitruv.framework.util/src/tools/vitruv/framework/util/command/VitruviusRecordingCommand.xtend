package tools.vitruv.framework.util.command

import java.util.Collection
import java.util.Collections
import org.apache.log4j.Logger
import org.eclipse.emf.common.command.Command
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.transaction.RecordingCommand
import org.eclipse.emf.transaction.Transaction
import org.eclipse.emf.transaction.TransactionChangeDescription
import org.eclipse.emf.transaction.TransactionalEditingDomain
import tools.vitruv.framework.util.bridges.JavaBridge

abstract class VitruviusRecordingCommand extends RecordingCommand implements Command {
	protected static final Logger logger = Logger.getLogger(VitruviusRecordingCommand.getSimpleName())
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
		this.runtimeException = null
		super.preExecute()
	}

	def void setRuntimeException(RuntimeException e) {
		this.runtimeException = e
	}

	def void rethrowRuntimeExceptionIfExisting() {
		if (this.runtimeException !== null) {
			throw (this.runtimeException)
		}
	}

	def protected void storeAndRethrowException(Throwable e) {
		var RuntimeException r
		if (e instanceof RuntimeException) {
			r = e as RuntimeException
		} else {
			// soften
			r = new RuntimeException(e)
		}
		setRuntimeException(r)
		// just log and rethrow
		throw (r)
	}

	override Collection<?> getAffectedObjects() {
		var Transaction transaction = JavaBridge.getFieldFromClass(RecordingCommand, "transaction", this)
		if (transaction === null) {
			// TODO DW what to do, if transaction is null? when is this the case?
			return Collections.EMPTY_SET
		}
		val TransactionChangeDescription changeDescription = transaction.getChangeDescription()
		val Collection<EObject> affectedEObjects = EMFChangeBridge.getAffectedObjects(changeDescription)
		return affectedEObjects
	}
}
