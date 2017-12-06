package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import org.eclipse.xtend.lib.annotations.Accessors

class MappingsReactionsFileGenerator extends AbstractMappingsSegmentGenerator {
	@Accessors(PROTECTED_GETTER)
	val boolean left2right
	@Accessors(PROTECTED_GETTER)
	val FluentReactionsLanguageBuilder create
	
	new(String basePackage, MappingsSegment segment, boolean left2right, FluentReactionsLanguageBuilder create) {
		super(basePackage, segment)
		this.left2right = left2right
		this.create = create
	}
	
	protected def String getDirectedSegmentName() '''«segment.name»«directionSuffix»'''
	
	private def String getDirectionSuffix() '''«IF left2right »_L2R« ELSE »_R2L« ENDIF»'''
	
	protected def getFromDomain() {
		if (left2right) segment.leftDomain else segment.rightDomain
	}
	
	protected def getToDomain() {
		if (left2right) segment.rightDomain else segment.leftDomain
	}
	
	def createAndInitializeReactionsFile() {
		val reactionsFile = create.reactionsFile(directedSegmentName)
		//		FIXME MK add static extension imports for ALL mappings
		//		import static tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.AdRootXReRootMapping.*
		//		import static tools.vitruv.dsls.mappings.tests.addressesXrecipients.mappings.AddressXRecipientLocationCityMapping.*
		reactionsFile += create.reactionsSegment(directedSegmentName)
			.inReactionToChangesIn(fromDomain.domain)
			.executeActionsIn(toDomain.domain)
	}
}