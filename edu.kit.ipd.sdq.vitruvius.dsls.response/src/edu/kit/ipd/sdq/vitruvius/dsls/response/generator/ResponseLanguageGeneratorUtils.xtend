package edu.kit.ipd.sdq.vitruvius.dsls.response.generator

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair
import edu.kit.ipd.sdq.vitruvius.dsls.response.helper.JavaGeneratorHelper.ImportHelper

final class ResponseLanguageGeneratorUtils {
	private static val FSA_SEPARATOR = "/";
	private static val XTEND_FILE_EXTENSION = ".xtend"; 
	
	static def String getPackageName(Pair<VURI, VURI> modelPair) '''
		responses«modelPair.metamodelPairName»'''
	
	static def String getMetamodelPairName(Pair<VURI, VURI> modelPair) '''
		«modelPair.first.fileExtension»To«modelPair.second.fileExtension»'''
	
	static def String getExecutorName(Pair<VURI, VURI> modelPair) '''
		Response«modelPair.metamodelPairName»Executor'''
	
	static def String getExecutorFilePath(Pair<VURI, VURI> modelPair) '''
		«modelPair.packageName»«FSA_SEPARATOR»«modelPair.executorName»«XTEND_FILE_EXTENSION»'''
		
	static def String getChange2CommandTransformingName(Pair<VURI, VURI> modelPair) '''
		Response«modelPair.metamodelPairName»Change2CommandTransforming'''
		
	static def String getChange2CommandTransformingFilePath(Pair<VURI, VURI> modelPair) '''
		«modelPair.packageName»«FSA_SEPARATOR»«modelPair.change2CommandTransformingName»«XTEND_FILE_EXTENSION»'''
	
	static def String getResponseFilePath(Pair<VURI, VURI> modelPair, String responseName) '''
		«modelPair.packageName»«FSA_SEPARATOR»«responseName»«XTEND_FILE_EXTENSION»'''
	
	static def generateClass(String packageName, ImportHelper importHelper, CharSequence classImplementation) '''
		package «packageName»;
		
		«importHelper.generateImportCode»
		
		«classImplementation»
		'''

}