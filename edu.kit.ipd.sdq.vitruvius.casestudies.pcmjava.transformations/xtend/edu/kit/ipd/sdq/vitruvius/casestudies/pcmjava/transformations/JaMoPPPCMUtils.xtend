package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations

import edu.kit.ipd.sdq.vitruvius.framework.code.jamopp.JaMoPPParser
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI
import java.io.ByteArrayInputStream
import java.util.Set
import org.emftext.language.java.classifiers.Classifier
import org.emftext.language.java.containers.CompilationUnit
import org.emftext.language.java.containers.Package
import org.emftext.language.java.containers.JavaRoot

class JaMoPPPCMUtils {
	private new() {
	}

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

	def static CompilationUnit createCompilationUnit(String name, String content) {
		return createJavaRoot(name, content) as CompilationUnit
	}
	
	def static Package createPackage(String namespace){
		val String content = '''package «namespace»;'''
		return createJavaRoot("package-info", content) as Package
	}
	
	def static JavaRoot createJavaRoot(String name, String content){
		val JaMoPPParser jaMoPPParser = new JaMoPPParser
		val inStream = new ByteArrayInputStream(content.bytes)
		val javaRoot = jaMoPPParser.parseCompilationUnitFromInputStream(VURI.getInstance(name + ".java").EMFUri, inStream)
		javaRoot.name = name + ".java"
		return javaRoot
	}

}
