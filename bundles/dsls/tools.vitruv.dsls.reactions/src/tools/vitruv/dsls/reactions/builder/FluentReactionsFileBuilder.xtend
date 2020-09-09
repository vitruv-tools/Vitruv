package tools.vitruv.dsls.reactions.builder

import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtend.lib.annotations.Accessors
import tools.vitruv.dsls.mirbase.mirBase.MetamodelImport
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsFile
import tools.vitruv.dsls.reactions.reactionsLanguage.ReactionsLanguageFactory

import static com.google.common.base.Preconditions.*

class FluentReactionsFileBuilder extends FluentReactionElementBuilder {

	@Accessors(PUBLIC_GETTER)
	val ReactionsFile reactionsFile = ReactionsLanguageFactory.eINSTANCE.createReactionsFile

	@Accessors(PUBLIC_GETTER)
	var String fileName

	package new(String fileName, FluentBuilderContext context) {
		super(context)
		this.fileName = fileName
	}

	override protected attachmentPreparation() {
		super.attachmentPreparation()
		checkState(reactionsFile.reactionsSegments.size > 0,
			'''No reactions segments were added to this reactions file («fileName»)!''')
	}

	def package start() {
		readyToBeAttached = true
		this
	}

	def attachTo(Resource resource) {
		triggerBeforeAttached(reactionsFile, resource)
		val resourceContentLength = resource.contents.size
		resource.contents += reactionsFile

		// this call has the (desired!) side effect to create the jvm types!
		val newContents = resource.getContents()
		// very rough check
		checkState(newContents.size > resourceContentLength + 1,
			'''Jvm type creation for failed for the reactions file «fileName»!''')
		triggerAfterJvmTypeCreation()
	}

	def importMetamodel(MetamodelImport mmImport) {
		childBuilders += new MetamodelImportBuilder(mmImport, context)
	}

	def operator_add(FluentReactionsSegmentBuilder reactionsSegmentBuilder) {
		checkNotYetAttached()
		reactionsFile.reactionsSegments += reactionsSegmentBuilder.segment
		childBuilders += reactionsSegmentBuilder
		this
	}

	override toString() {
		'''reactions file builder for “«fileName»”'''
	}
}

class MetamodelImportBuilder extends FluentReactionElementBuilder {
	MetamodelImport mmImport

	new(MetamodelImport mmImport, FluentBuilderContext context) {
		super(context)
		this.mmImport = mmImport
		readyToBeAttached = true
	}

	override protected attachmentPreparation() {
		super.attachmentPreparation()
		metamodelImport(mmImport.package, mmImport.name)
	}
}
