package tools.vitruv.domains.java.util

import org.emftext.language.java.containers.CompilationUnit

class JavaPersistenceHelper {
	public static def String getJavaProjectSrc() {
		return "src/";
	}
	
	public static def String getPackageInfoClassName() {
		"package-info"
	} 
	
	public static def String buildJavaFilePath(String fileName, Iterable<String> namespaces) {
		return '''src/«FOR namespace : namespaces SEPARATOR "/" AFTER "/"»«namespace»«ENDFOR»«fileName».java''';
	}
	
	public static def String buildJavaFilePath(CompilationUnit compilationUnit) {
		return '''src/«FOR namespace : compilationUnit.namespaces SEPARATOR "/" AFTER "/"»«namespace»«ENDFOR»«compilationUnit.name».java''';
	}
	
	public static def String buildJavaFilePath(org.emftext.language.java.containers.Package javaPackage) {
		return '''src/«FOR namespace : javaPackage.namespaces SEPARATOR "/" AFTER "/"»«namespace»«ENDFOR»«javaPackage.name»/«packageInfoClassName».java''';
	}
}
