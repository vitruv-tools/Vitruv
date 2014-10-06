package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import de.uka.ipd.sdq.pcm.repository.Repository
import edu.kit.ipd.sdq.vitruvius.framework.code.jamopp.JaMoPPParser
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.UserInteractionType
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.UserInteracting
import java.io.ByteArrayInputStream
import java.util.ArrayList
import java.util.List
import java.util.Set
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.JavaRoot
import org.emftext.language.java.containers.Package
import org.emftext.language.java.types.Boolean
import org.emftext.language.java.types.Byte
import org.emftext.language.java.types.Char
import org.emftext.language.java.types.ClassifierReference
import org.emftext.language.java.types.Double
import org.emftext.language.java.types.Float
import org.emftext.language.java.types.Int
import org.emftext.language.java.types.Long
import org.emftext.language.java.types.NamespaceClassifierReference
import org.emftext.language.java.types.Short
import org.emftext.language.java.types.Void
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants
import org.apache.log4j.Logger

class JaMoPPPCMUtils {
	private new() {
	}

	private static Logger logger = Logger.getLogger(JaMoPPPCMUtils.simpleName)

	def static Package getContainingPackageFromCorrespondenceInstance(Classifier classifier,
		CorrespondenceInstance correspondenceInstance) {
		var namespace = classifier.containingCompilationUnit.namespacesAsString
		if (namespace.endsWith("$")) {
			namespace = namespace.substring(0, namespace.length - 1)
		}
		if (!namespace.endsWith(".")) {
			namespace = namespace + "."
		}
		val finalNamespace = namespace
		var Set<Package> packagesWithCorrespondences = correspondenceInstance.
			getAllEObjectsInCorrespondencesWithType(Package)
		val packagesWithNamespace = packagesWithCorrespondences.filter[pack|
			finalNamespace.equals(pack.namespacesAsString)]
		if (null != packagesWithNamespace && 0 < packagesWithNamespace.size &&
			null != packagesWithNamespace.iterator.next) {
			return packagesWithNamespace.iterator.next
		}
		return null;
	}

	def public static Package findCorrespondingPackageByName(String name, CorrespondenceInstance correspondenceInstance,
		Repository repo) {
		val packages = correspondenceInstance.getCorrespondingEObjectsByType(repo, Package)
		for (package : packages) {
			if (package.name.equalsIgnoreCase(name)) {
				return package
			}
		}
		return null
	}

	def static CompilationUnit createCompilationUnit(String name, String content) {
		return createJavaRoot(name, content) as CompilationUnit
	}

	def static Package createPackage(String namespace) {
		val String content = '''package «namespace»;'''
		return createJavaRoot("package-info", content) as Package
	}

	def static JavaRoot createJavaRoot(String name, String content) {
		val JaMoPPParser jaMoPPParser = new JaMoPPParser
		val inStream = new ByteArrayInputStream(content.bytes)
		val javaRoot = jaMoPPParser.parseCompilationUnitFromInputStream(VURI.getInstance(name + ".java").EMFUri,
			inStream)
		javaRoot.name = name + ".java"
		return javaRoot
	}

	def public static createJaMoPPMethod(String content) {
		try{ 
		val String cuContent = "class Dummy{" + content + "}"
		val String name = "vitruvius.meta/src/dummy.java"; 
		val cu = createJavaRoot(name, cuContent) as CompilationUnit
		return cu.classifiers.get(0).methods.get(0)
		}catch(Throwable t){
			logger.warn("Exception during createJaMoPPMethod with content " + content + " Exception: " + t)
			return null;
		}
		
	}

	def dispatch static getNameFromJaMoPPType(ClassifierReference reference) {
		return reference.target.name
	}

	def dispatch static getNameFromJaMoPPType(NamespaceClassifierReference reference) {
		val classRef = reference.pureClassifierReference
		return classRef.target.name

	// is currently not possible: see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=404817
	//return getNameFromJaMoPPType(classRef)
	}

	def dispatch static getNameFromJaMoPPType(Boolean reference) {
		return "boolean"
	}

	def dispatch static getNameFromJaMoPPType(Byte reference) {
		return "byte"
	}

	def dispatch static getNameFromJaMoPPType(Char reference) {
		return "char"
	}

	def dispatch static getNameFromJaMoPPType(Double reference) {
		return "double"
	}

	def dispatch static getNameFromJaMoPPType(Float reference) {
		return "float"
	}

	def dispatch static getNameFromJaMoPPType(Int reference) {
		return "int"
	}

	def dispatch static getNameFromJaMoPPType(Long reference) {
		return "long"
	}

	def dispatch static getNameFromJaMoPPType(Short reference) {
		return "short"
	}

	def dispatch static getNameFromJaMoPPType(Void reference) {
		return "void"
	}

	def static askUserForPackage(CorrespondenceInstance correspondenceInstance, Repository repository,
		UserInteracting userInteractiong, String message) {
		val packages = correspondenceInstance.getAllCorrespondingEObjects(repository).filter(typeof(Package))
		val List<String> options = new ArrayList<String>
		for (pack : packages) {
			options.add(pack.name)
		}
		val int selection = userInteractiong.selectFromMessage(UserInteractionType.MODAL, message, options)
		return packages.findFirst[pack|pack.name.equals(options.get(selection))]
	}

}
