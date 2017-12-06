package tools.vitruv.dsls.common.helper

import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility class JavaFileGenerator {
	static val JAVA_FILE_EXTENSION = ".java"
	public static val FSA_SEPARATOR = "/"
	
	static def generateClass(CharSequence classImplementation, String packageName, JavaImportHelper importHelper) '''
		package «packageName»;
		
		«importHelper.generateImportCode»
		
		«classImplementation»
		'''

	static def String getJavaFilePath(String qualifiedClassName) {
		qualifiedClassName.replace('.', FSA_SEPARATOR) + JAVA_FILE_EXTENSION
	}
	
	static def String getJavaFilePath(ClassNameGenerator className) {
		className.qualifiedName.javaFilePath
	}
		
	
}