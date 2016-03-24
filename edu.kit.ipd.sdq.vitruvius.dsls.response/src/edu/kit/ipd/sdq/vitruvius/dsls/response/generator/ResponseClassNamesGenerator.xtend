package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import org.eclipse.emf.ecore.resource.Resource
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import org.eclipse.emf.common.util.URI
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Response
import static extension edu.kit.ipd.sdq.vitruvius.dsls.response.helper.ResponseLanguageHelper.*;
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Trigger
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicRootObjectChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.AtomicFeatureChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ArbitraryModelElementChange
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.XtendImportHelper
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.Effect
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ExplicitEffect
import edu.kit.ipd.sdq.vitruvius.dsls.response.responseLanguage.ImplicitEffect

final class ResponseClassNamesGenerator {
	private static String BASIC_PACKAGE = "mir";
	private static val FSA_SEPARATOR = "/";
	private static val XTEND_FILE_EXTENSION = ".java";
	private static val RESPONSES_PACKAGE = "responses";
	private static String EFFECTS_PACKAGE = "effects";
	private static String EFFECTS_FACADE_CLASS_NAME = "EffectsFacade";
	
	private new() {}
	
	static def generateClass(String packageName, XtendImportHelper importHelper, CharSequence classImplementation) '''
		package «packageName»;
		
		«importHelper.generateImportCode»
		
		«classImplementation»
		'''

	public static def String getFilePath(String qualifiedClassName) '''
		«qualifiedClassName.replace('.', FSA_SEPARATOR)»«XTEND_FILE_EXTENSION»'''
		
	public static def String getBasicMirPackageQualifiedName() '''
		«BASIC_PACKAGE»'''
		
	public static def String getBasicResponsesPackageQualifiedName() '''
		«basicMirPackageQualifiedName».«RESPONSES_PACKAGE»'''
	
	public static def String getBasicEffectsPackageQualifiedName() '''
		«basicMirPackageQualifiedName».«EFFECTS_PACKAGE»'''	
	
	private static def String getMetamodelIdentifier(URI uri) {
		if (uri.lastSegment.nullOrEmpty) {
			return uri.toString.split("\\.").last.toFirstUpper;
		} else {
			return uri.lastSegment.replace(".", "_").toFirstUpper;
		}
	}
	
	private static def String getPackageNameForResource(Resource resource) {
		return resource.URI.lastSegment.split("\\.").get(0);
	}
	
	private static def String getMetamodelIdentifier(VURI uri) {
		return uri.EMFUri.metamodelIdentifier;
	}
	
	private static def String getMetamodelPairName(Pair<VURI, VURI> modelPair) '''
		«modelPair.first.metamodelIdentifier»To«modelPair.second.metamodelIdentifier»'''
	
	private static def String getPackageName(Pair<VURI, VURI> modelPair) '''
		responses«modelPair.metamodelPairName»'''
	
	public static abstract class ClassNameGenerator {
		public def String getQualifiedName() '''
			«packageName».«simpleName»'''
			
		public def String getSimpleName();
		public def String getPackageName();
	}
	
	public static class Change2CommandTransformingProvidingClassNameGenerator extends ClassNameGenerator {
		public override String getSimpleName() '''
			ResponseChange2CommandTransformingProviding'''
				
		public override String getPackageName() '''
			«basicResponsesPackageQualifiedName»'''
	}
	
	public static class Change2CommandTransformingClassNameGenerator extends ClassNameGenerator {
		private val String metamodelPairName;
		
		public new(Pair<VURI, VURI> modelPair) {
			this.metamodelPairName = modelPair.metamodelPairName;
		}
		
		public override getSimpleName() '''
			Change2CommandTransforming«metamodelPairName»'''
		
		public override getPackageName() '''
			«basicResponsesPackageQualifiedName»'''	
	}	
	
	public static class ExecutorClassNameGenerator extends ClassNameGenerator {
		private val String metamodelPairName;
		private val String metamodelPairPackageName;
		private val String resourcePackageName;
		
		public new(Resource responseResource, Pair<VURI, VURI> modelPair) {
			this.metamodelPairName = modelPair.metamodelPairName;
			this.metamodelPairPackageName = modelPair.packageName;
			this.resourcePackageName = responseResource.packageNameForResource;
		}
		
		public override getSimpleName() '''
			Executor«metamodelPairName»'''
	
		public override getPackageName() '''
			«basicResponsesPackageQualifiedName».«resourcePackageName».«metamodelPairPackageName»'''		
	}
	
	public static class ResponseClassNameGenerator extends ClassNameGenerator {
		private val Response response;
		public new(Response response) {
			this.response = response;
		}
		
		public override String getSimpleName() '''
			«response.name»Response'''
		
		public override String getPackageName() '''
			«basicResponsesPackageQualifiedName».«response.eResource.packageNameForResource».«response.sourceTargetPair.packageName»'''		
	}
	
	public static class EffectClassNameGenerator extends ClassNameGenerator {
		private val Effect effect;
		public new(Effect effect) {
			this.effect = effect;
		}
		
		public override String getSimpleName() '''
			«effect.name»Effect'''
		
		private static def dispatch getName(ExplicitEffect effect) {
			return effect.name
		}
		
		private static def dispatch getName(ImplicitEffect effect) {
			return effect.containingResponse.name
		}
		
		public override String getPackageName() '''
			«basicEffectsPackageQualifiedName».«effect.eResource.getPackageNameForResource»'''		
	}
	
	public static class EffectsFacadeClassNameGenerator extends ClassNameGenerator {
		private val Effect identifyingEffect;
		public new(Effect identifyingEffect) {
			this.identifyingEffect = identifyingEffect;
		}
		
		public override String getSimpleName() '''
			«EFFECTS_FACADE_CLASS_NAME»'''
		
		public override String getPackageName() '''
			«basicEffectsPackageQualifiedName».«identifyingEffect.eResource.getPackageNameForResource»'''		
	}
}