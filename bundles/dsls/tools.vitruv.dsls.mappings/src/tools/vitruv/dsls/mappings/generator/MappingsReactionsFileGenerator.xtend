package tools.vitruv.dsls.mappings.generator

import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsSegment
import tools.vitruv.dsls.reactions.builder.FluentReactionsLanguageBuilder
import org.eclipse.xtend.lib.annotations.Accessors
import com.google.common.base.Supplier
import tools.vitruv.dsls.reactions.api.generator.IReactionsGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.MappingsFile
import org.eclipse.xtext.generator.IFileSystemAccess2
import com.google.inject.Inject
import com.google.inject.Provider
import tools.vitruv.dsls.reactions.generator.ExternalReactionsGenerator
import tools.vitruv.dsls.mappings.mappingsLanguage.Mapping
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet

class MappingsReactionsFileGenerator extends AbstractMappingsSegmentGenerator {
	@Accessors(PROTECTED_GETTER)
	val boolean left2right
	@Accessors(PROTECTED_GETTER)
	val FluentReactionsLanguageBuilder create
	
	
	var ReactionGeneratorContext context;
	var IReactionsGenerator generator;
	val MappingsFile mappingsFile;
	
	new(String basePackage, MappingsSegment segment, boolean left2right, IReactionsGenerator reactionsGenerator, FluentReactionsLanguageBuilder create, MappingsFile file) {
		super(basePackage, segment)
		this.left2right = left2right
		this.create = create
		this.mappingsFile = file
		this.generator = reactionsGenerator;
	}
	
	protected def String getDirectedSegmentName() '''«segment.name»«directionSuffix»'''
	
	private def String getDirectionSuffix() '''«IF left2right »_L2R« ELSE »_R2L« ENDIF»'''
	
	protected def getFromParameters(Mapping mapping){
		if(left2right) mapping.leftParameters else mapping.rightParameters
	}
	
	protected def getToParameters(Mapping mapping){
		if(left2right) mapping.rightParameters else mapping.leftParameters
	}
	
	protected def getFromTriggers(Mapping mapping){
		if(left2right) mapping.leftReactionTriggers else mapping.righReactionTriggers
	}
	
	protected def getToTriggers(Mapping mapping){
		if(left2right) mapping.righReactionTriggers else mapping.leftReactionTriggers
	}
	
	protected def getFromConditions(Mapping mapping){
		if(left2right) mapping.leftConditions else mapping.rightConditions
	}
	
	protected def getToConditions(Mapping mapping){
		if(left2right) mapping.rightConditions else mapping.leftConditions
	}
	
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
		val segmentBuilder = create.reactionsSegment(directedSegmentName+"Segment")
			.inReactionToChangesIn(fromDomain.domain)
			.executeActionsIn(toDomain.domain)
		reactionsFile += segmentBuilder	
	    context= new ReactionGeneratorContext(reactionsFile, segmentBuilder, segment, mappingsFile, create);		
	}

}