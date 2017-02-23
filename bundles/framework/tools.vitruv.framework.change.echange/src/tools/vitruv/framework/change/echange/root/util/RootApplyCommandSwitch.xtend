package tools.vitruv.framework.change.echange.root.util

import org.eclipse.emf.common.command.Command
import org.eclipse.emf.edit.command.AddCommand
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain
import org.eclipse.emf.edit.provider.ComposedAdapterFactory
import tools.vitruv.framework.change.echange.root.InsertRootEObject
import tools.vitruv.framework.change.echange.root.RemoveRootEObject
import tools.vitruv.framework.util.command.RemoveAtCommand

public class RootApplyCommandSwitch extends RootSwitch<Command> {
	override public Command caseInsertRootEObject(InsertRootEObject object) {
		val editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), null);
		return new AddCommand(editingDomain, object.resource.getContents, object.newValue, object.index)
	}
	override public Command caseRemoveRootEObject(RemoveRootEObject object) {
		val editingDomain = new AdapterFactoryEditingDomain(new ComposedAdapterFactory(), null);
		return RemoveAtCommand.create(editingDomain, object.resource.getContents, object.oldValue, object.index)
	}
}