package tools.vitruv.framework.change.description

/**
 * A {@link CompositeTransactionalChange} defines a set of {@link TransactionalChange}s, which have to be performed
 * together. They were recorded together and have to propagated to models completely or not at all.
 */
interface CompositeTransactionalChange extends TransactionalChange, CompositeChange<TransactionalChange> {
	
}