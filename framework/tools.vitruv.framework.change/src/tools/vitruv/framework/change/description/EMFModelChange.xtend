package tools.vitruv.framework.change.description

import org.eclipse.emf.ecore.change.ChangeDescription

interface EMFModelChange extends CompositeTransactionalChange {
    def ChangeDescription getChangeDescription();
}
