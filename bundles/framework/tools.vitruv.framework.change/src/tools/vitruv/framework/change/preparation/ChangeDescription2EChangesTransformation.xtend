package tools.vitruv.framework.change.preparation

import java.util.List

import tools.vitruv.framework.change.echange.EChange
import org.eclipse.emf.ecore.change.ChangeDescription

interface ChangeDescription2EChangesTransformation {
	def List<EChange> transform(ChangeDescription changeDescription)
}
