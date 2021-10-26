package tools.vitruv.variability.vave.tests.generator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.designer.languages.java.reverse.javaparser.JavaParser;
import org.eclipse.papyrus.designer.languages.java.reverse.javaparser.ParseException;
import org.eclipse.papyrus.designer.languages.java.reverse.javaparser.ast.CompilationUnit;
import org.eclipse.papyrus.designer.languages.java.reverse.umlparser.ClassifierCatalog;
import org.eclipse.papyrus.designer.languages.java.reverse.umlparser.CompilationUnitAnalyser;
import org.eclipse.papyrus.designer.languages.java.reverse.umlparser.UmlUtils;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;

/**
 * Generates a UML class model from Java source code using Papyrus.
 */
public class UMLFromJavaGenerator {

	protected CompilationUnitAnalyser javaAnalyser;

	protected ClassifierCatalog classifierCatalog;

	protected List<String> reversedElementQualifiedNames;

	protected List<NamedElement> reversedNamedElement;

	public UMLFromJavaGenerator(Package rootPackage, String generatedPackageName, List<String> searchPaths, List<String> creationPaths, List<String> qualifiedNamesInProjects) {
		javaAnalyser = new CompilationUnitAnalyser(rootPackage, generatedPackageName, searchPaths, creationPaths, qualifiedNamesInProjects);

		reversedElementQualifiedNames = new ArrayList<String>();
		reversedNamedElement = new ArrayList<NamedElement>();
		classifierCatalog = javaAnalyser.getClassifierCatalog();

		if (javaAnalyser != null) {
			UmlUtils.applyCodeGenerationProfilesToModel(javaAnalyser.getRootModel());
		}
	}

	public UMLFromJavaGenerator(Parameters parameters) {
		this(parameters.getUmlRootPackage(), parameters.getPackageName(), parameters.getSearchPaths(), parameters.getCreationPaths(), parameters.getQualifiedNamesInProjects());
	}

	public List<String> getReversedElementQualifiedNames() {
		return reversedElementQualifiedNames;
	}

	public List<NamedElement> getReversedNamedElement() {
		return reversedNamedElement;
	}

	public void addReversedName(String name) {
		reversedElementQualifiedNames.add(name);

		NamedElement namedElement = classifierCatalog.getClassifier(name);
		if (namedElement != null) {
			reversedNamedElement.add(namedElement);
		}
	}

	public void processJavaFile(Path file) {
		System.out.println("try to reverse file (" + file + ")");
		try {
			CompilationUnit cu = JavaParser.parse(file.toFile());
			javaAnalyser.processCompilationUnit(cu);
			// Add the name to the reversed list
			// String reversedName = cu.getPackage().getPackageName() + cu.getTypes().get(0).toString();
			String reversedName = cu.getPackage().getPackageName() + "." + file.getFileName().toString().substring(0, file.getFileName().toString().length() - 5);
			addReversedName(reversedName);
			System.out.println("REVERSED: " + reversedName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static public class Parameters {
		protected List<String> searchPaths;

		protected List<String> creationPaths;

		protected Package umlRootPackage;

		protected String packageName;

		protected List<String> qualifiedNamesInProjects;

		public List<String> getSearchPaths() {
			return searchPaths;
		}

		public void setSearchPaths(List<String> searchPaths) {
			this.searchPaths = searchPaths;
		}

		public List<String> getCreationPaths() {
			return creationPaths;
		}

		public void setCreationPaths(List<String> creationPaths) {
			this.creationPaths = creationPaths;
		}

		public Package getUmlRootPackage() {
			return umlRootPackage;
		}

		public void setUmlRootPackage(Package umlModel) {
			this.umlRootPackage = umlModel;
		}

		public String getPackageName() {
			return packageName;
		}

		public void setPackageName(String packageName) {
			this.packageName = packageName;
		}

		public List<String> getQualifiedNamesInProjects() {
			return qualifiedNamesInProjects;
		}

		public void setQualifiedNamesInProjects(List<String> qualifiedNamesInProjects) {
			this.qualifiedNamesInProjects = qualifiedNamesInProjects;
		}
	}

}
