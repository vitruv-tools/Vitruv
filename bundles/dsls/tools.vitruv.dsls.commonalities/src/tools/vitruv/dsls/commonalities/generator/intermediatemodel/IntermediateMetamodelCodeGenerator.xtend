package tools.vitruv.dsls.commonalities.generator.intermediatemodel

import edu.kit.ipd.sdq.activextendannotations.Lazy
import java.util.Collections
import org.apache.log4j.Logger
import org.eclipse.emf.codegen.ecore.generator.Generator
import org.eclipse.emf.codegen.ecore.generator.GeneratorAdapterFactory
import org.eclipse.emf.codegen.ecore.genmodel.GenJDKLevel
import org.eclipse.emf.codegen.ecore.genmodel.GenModel
import org.eclipse.emf.codegen.ecore.genmodel.GenModelFactory
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory
import org.eclipse.emf.common.util.BasicMonitor
import org.eclipse.emf.common.util.Diagnostic
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.plugin.EcorePlugin
import tools.vitruv.dsls.commonalities.generator.SubGenerator
import tools.vitruv.extensions.dslruntime.commonalities.intermediatemodelbase.IntermediateModelBasePackage

import static extension tools.vitruv.dsls.commonalities.generator.intermediatemodel.IntermediateModelConstants.*
import static java.lang.System.lineSeparator
import java.util.List
import tools.vitruv.dsls.commonalities.generator.util.guice.GenerationScoped
import tools.vitruv.dsls.commonalities.generator.GenerationContext
import javax.inject.Inject
import static org.eclipse.emf.common.util.URI.*
import edu.kit.ipd.sdq.activextendannotations.CloseResource
import static com.google.common.base.Preconditions.checkArgument
import static com.google.common.base.Preconditions.checkState
import org.eclipse.core.runtime.Platform

@GenerationScoped
class IntermediateMetamodelCodeGenerator implements SubGenerator {
	static val Logger log = Logger.getLogger(IntermediateMetamodelCodeGenerator)
	static val GENERATED_CODE_COMPLIANCE_LEVEL = GenJDKLevel.JDK80_LITERAL
	static val GENERATED_CODE_FOLDER = "."
	static val INTERMEDIATEMODELBASE_GENMODEL_URI = EcorePlugin.getEPackageNsURIToGenModelLocationMap(true)
		.get(IntermediateModelBasePackage.eINSTANCE.nsURI)
		// the GenModel may be found in the local workspace, but it cannot be loaded from there.
		// The runtime plugin should also be on the classpath, so we just transform the URI into
		// a platform plugin uri if it is a platform resource uri.
		.ensurePlatformPlugin()
	@Lazy val GenModel intermediateModelBaseGenModel = resourceSet
		.getResource(INTERMEDIATEMODELBASE_GENMODEL_URI, true)
		.contents.head as GenModel

	@Inject extension GenerationContext

	override generate() {
		if (!isNewResourceSet) return
		
		val fsaTargetUri = fsa.getURI(GENERATED_CODE_FOLDER)
		val target = if (fsaTargetUri.isPlatformResource) {
			new PlatformResourceTarget(fsaTargetUri)
		} else if(fsaTargetUri.isFile && !Platform.isRunning) {
			new GloballyRegisteredFileTarget(fsaTargetUri)
		} else {
			throw new IllegalStateException('''Unsupported URI type: ‹«fsaTargetUri»›''')
		}

		generateInto(target)
	}
	
	private def void generateInto(@CloseResource ECoreCodeGenerationTarget target) {
		for (generatedConcept : generatedConcepts) {
			log.debug('''Generating code for the intermediate metamodel of concept '«generatedConcept»'.''')
			val generatedPackage = generatedConcept.intermediateMetamodelPackage
			val generatedGenModel = generateGenModel(generatedPackage, generatedConcept, target)
			generateModelCode(generatedGenModel)
		}
	}

	private def generateGenModel(EPackage generatedPackage, String conceptName, ECoreCodeGenerationTarget target) {
		GenModelFactory.eINSTANCE.createGenModel() => [
			complianceLevel = GENERATED_CODE_COMPLIANCE_LEVEL
			modelDirectory = target.uri.toPlatformString(true)
			canGenerate = true
			modelName = conceptName
			usedGenPackages += intermediateModelBaseGenModel.genPackages
			initialize(Collections.singleton(generatedPackage))
			genPackages.head => [
				prefix = conceptName.intermediateMetamodelClassesPrefix
				basePackage = intermediateMetamodelPackagePrefix
				adapterFactory = false
			]
		]
	}

	private def generateModelCode(GenModel generatedGenModel) {
		GeneratorAdapterFactory.Descriptor.Registry.INSTANCE.addDescriptor(GenModelPackage.eNS_URI,
			GenModelGeneratorAdapterFactory.DESCRIPTOR)

		val generator = new Generator() => [
			input = generatedGenModel
		]
		val result = generator.generate(generatedGenModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, new BasicMonitor)
		if (result.severity != Diagnostic.OK) {
			throw new IllegalStateException("Generating the intermediate model failed:" + lineSeparator + result.explanation)
		}
	}

	private def getExplanation(Diagnostic diagnostic) {
		diagnostic.notOkayRootDiagnostics.map[message].toSet.join(lineSeparator) [ "  • " + it ]
	}

	private def Iterable<Diagnostic> getNotOkayRootDiagnostics(Diagnostic diagnostic) {
		if (diagnostic.severity == Diagnostic.OK) {
			emptyList
		} else if (diagnostic.children.isEmpty) {
			List.of(diagnostic)
		} else {
			diagnostic.children.flatMap[notOkayRootDiagnostics]
		}
	}

	private static def ensurePlatformPlugin(URI uri) {
		if (uri.isPlatformResource) {
			uri.replacePrefix(URI.createPlatformResourceURI("/", false), URI.createPlatformPluginURI("/", false))
		} else if (!uri.isPlatformPlugin) {
			throw new IllegalStateException('''Unsupported URI type, we need a platform plugin URI: ‹«uri»›''')
		} else {
			uri
		}
	}

	private static interface ECoreCodeGenerationTarget extends AutoCloseable {
		def URI getUri()
	}
	
	// In the easiest case, we generate into a platform resource target. This can be directly
	// resolved by the ECore generator.
	private static class PlatformResourceTarget implements ECoreCodeGenerationTarget {
		val URI uri
		
		private new(URI targetUri) {
			this.uri = targetUri
		}	
		
		override getUri() {
			uri
		}
		
		override close() {
			// nothing to do
		}
	}	
	
	// The entries in the GenPackage *have* to be workspace-relative. In absence of a workspace,
	// we need to register our target in the global EcorePlugin platform resource map by hand so
	// that it will be resolved by the ECore generator.
	private static class GloballyRegisteredFileTarget implements ECoreCodeGenerationTarget {
		static val PLATFORM_RESOURCE_ID = GloballyRegisteredFileTarget.name
		
		private new(URI targetUri) {
			checkArgument(targetUri.isFile, '''«targetUri» must be a file URI!''')
			val platformUri = createPlatformResourceURI(PLATFORM_RESOURCE_ID, true)
			checkState(!EcorePlugin.platformResourceMap.containsKey(PLATFORM_RESOURCE_ID), '''The global URI map already contains a mapping for ‹«platformUri»›!''')
			EcorePlugin.platformResourceMap.put(PLATFORM_RESOURCE_ID, targetUri)
		}	
		
		override getUri() {
			createPlatformResourceURI(PLATFORM_RESOURCE_ID, true)
		}
		
		override close() {
			checkState(EcorePlugin.platformResourceMap.remove(PLATFORM_RESOURCE_ID) !== null, '''Failed to unregister ‹«PLATFORM_RESOURCE_ID»›!''')
		}
	}
}
