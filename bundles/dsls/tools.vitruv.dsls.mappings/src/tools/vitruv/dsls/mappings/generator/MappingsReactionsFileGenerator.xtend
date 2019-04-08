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

class MappingsReactionsFileGenerator extends AbstractMappingsSegmentGenerator {
	@Accessors(PROTECTED_GETTER)
	val boolean left2right
	@Accessors(PROTECTED_GETTER)
	val FluentReactionsLanguageBuilder create
	
	@Inject
	Provider<IReactionsGenerator> reactionsGeneratorProvider
	
	var ReactionGeneratorContext context;
	
	val MappingsFile mappingsFile;
	
	new(String basePackage, MappingsSegment segment, boolean left2right, FluentReactionsLanguageBuilder create, MappingsFile file) {
		super(basePackage, segment)
		this.left2right = left2right
		this.create = create
		this.mappingsFile = file
	}
	
	protected def String getDirectedSegmentName() '''«segment.name»«directionSuffix»'''
	
	private def String getDirectionSuffix() '''«IF left2right »_L2R« ELSE »_R2L« ENDIF»'''
	
	protected def getFromParameters(Mapping mapping){
		if(left2right) mapping.leftParameters else mapping.rightParameters
	}
	
	protected def getToParameters(Mapping mapping){
		if(left2right) mapping.rightParameters else mapping.leftParameters
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
		val segment = create.reactionsSegment(directedSegmentName)
			.inReactionToChangesIn(fromDomain.domain)
			.executeActionsIn(toDomain.domain)
		reactionsFile += segment	
	    context= new ReactionGeneratorContext(reactionsFile, segment);		
	}
	
	def generateFile(IFileSystemAccess2 fsa){
		//val generator = reactionsGeneratorProvider.get()
		val generator = new ExternalReactionsGenerator
		generator.useResourceSet(mappingsFile.eResource.resourceSet)
		generator.addReactionsFile(context.file)
		generator.generate(fsa)
		generator.writeReactions(fsa)
	}
}