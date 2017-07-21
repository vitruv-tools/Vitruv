package tools.vitruv.framework.vsum.repositories

import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.description.VitruviusChange
import java.util.List

interface ModelRepositoryInterface {

	def List<VitruviusChange> getLastResolvedChanges()

	def List<VitruviusChange> getLastUnresolvedChanges()

	def List<VitruviusChange> endRecording()

	def boolean unresolveChanges()

	def void addRootElement(EObject rootElement)

	def void cleanupRootElements()

	def void cleanupRootElementsWithoutResource()

	def void startRecording()
}
