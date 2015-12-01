package edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers

import java.util.Map
import java.util.function.Function
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension edu.kit.ipd.sdq.vitruvius.dsls.mapping.helpers.StringHelper.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import org.eclipse.emf.ecore.EClassifier

class JavaGeneratorHelper2 {
	public static def void generateJavaFile(IFileSystemAccess fsa, CharSequence fqn, Function<GeneratorHelper, CharSequence> generatorFunction) {
		fsa.generateFile(fqn.toString.classNameToJavaPath, JavaGeneratorHelper2.generate(fqn.toString.classNameToPackageName, generatorFunction))
	}
	
	public static def CharSequence generate(String packageName,
		Function<GeneratorHelper, CharSequence> generatorFunction) {
		(new JavaGeneratorHelper2(packageName, generatorFunction)).generate
	}

	private val String packageName
	private val Function<GeneratorHelper, CharSequence> generatorFunction

	private new(String packageName, Function<GeneratorHelper, CharSequence> generatorFunction) {
		this.packageName = packageName
		this.generatorFunction = generatorFunction
	}
	
	private static final int IMPORT = 0;

	private def generate() {
		val generatorHelper = new GeneratorHelper
		val content = generatorFunction.apply(generatorHelper)

		'''
			«IF packageName != null»
			package «packageName»;
			«ENDIF»
			
			«generatorHelper.generate(IMPORT)»
			
			«content»
		'''
	}

	public static class GeneratorHelper {
		private val Map<Integer, IGeneratorHelper> generators = newHashMap;
		
		public def generate(int key) {
			return generators.get(key)?.generate
		}
		
		public def register() {
			
		}
	}
	
	public static interface IGeneratorHelper {
		def CharSequence generate();
		def CharSequence get(Object object);
	}
	
	public static class ImportHelper implements IGeneratorHelper {
		private val Map<String, String> imports = newHashMap

		override generate() '''
			«FOR i : imports.values»
				import «i»;
			«ENDFOR»
		'''
		
		public def dispatch get(Object object) {
			throw new IllegalArgumentException("Unhandled type: " + object.toString)
		}
		
		public def dispatch get(Class<?> javaClass) {
			return typeRef(javaClass)
		}
		
		public def dispatch get(EClassifier eClassifier) {
			return typeRef(eClassifier)
		}
		
		public def dispatch get(CharSequence fullyQualifiedJVMName) {
			return typeRef(fullyQualifiedJVMName)
		}
		
		
		
		public def typeRef(Class<?> javaClass) {
			typeRef(javaClass.name)
		}

		public def typeRef(EClassifier eClassifier) {
			typeRef(eClassifier.instanceTypeName)
		}

		public def typeRef(CharSequence fullyQualifiedJVMName) {
			val fullyQualifiedJVMNameString = fullyQualifiedJVMName.toString
			if (fullyQualifiedJVMNameString.isSimpleName)
				return fullyQualifiedJVMNameString
				
			val simpleName = fullyQualifiedJVMNameString.lastFragmentOrComplete(".")

			if (!imports.containsKey(simpleName)) {
				imports.put(simpleName, fullyQualifiedJVMNameString)
			} else if (!imports.get(simpleName).equals(fullyQualifiedJVMNameString)) {
				return fullyQualifiedJVMNameString
			}
				
			return simpleName
		}
	}
}