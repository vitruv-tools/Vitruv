package tools.vitruv.framework.change.description

import tools.vitruv.framework.change.description.VitruviusChange

/**
 * A {@link TransactionalChange} defines one or more {@link VitruviusChange}s, which have to be performed
 * together. They were recorded together and have to propagated to models completely or not at all.
 */
interface TransactionalChange extends VitruviusChange {
	
}