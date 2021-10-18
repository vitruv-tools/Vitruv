package tools.vitruv.variability.vave.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProjectManager;

/**
 * Use preprocessor and papyrus to generate source code variants with uml diagrams.
 * 
 */
@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
@Disabled
public class GeneratorTest {

	@Test
	public void generateTest() throws IOException {
		List<String> fullyQualifiedNames = new ArrayList<>();
		List<Path> javaFiles = new ArrayList<>();

		Path[] sourceFolders = new Path[] { Paths.get("C:\\FZI\\workspace-papyrus\\jp\\src") };
//		Path[] sourceFolders = new Path[] { Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model\\src"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-euml\\src"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-mdr\\build\\java"), Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-core-model-mdr\\src"),
//				Paths.get("C:\\FZI\\git\\argouml-workaround\\src\\argouml-app\\src")
//				// , Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-umlpropertypanels\\src"),
//				// , Paths.get("C:\\FZI\\argouml\\argouml\\src\\argouml-core-diagrams-sequence2\\src")
//		};
		for (Path sourceFolder : sourceFolders) {
			Files.walk(sourceFolder).forEach(f -> {
				if (Files.isRegularFile(f) && f.getFileName().toString().endsWith(".java") && !f.getFileName().toString().equals("package-info.java")) {
					javaFiles.add(f);
					System.out.println("ADDED JAVA FILE: " + f);
					String fullyQualifiedName = sourceFolder.relativize(f).toString().replace(java.io.File.separator, ".");
					fullyQualifiedName = fullyQualifiedName.substring(0, fullyQualifiedName.length() - 5);
					fullyQualifiedNames.add(fullyQualifiedName);
					System.out.println("ADDED FULLY QUALIFIED NAME: " + fullyQualifiedName);
				}
			});
		}

		ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resource = resourceSet.createResource(URI.createFileURI(Paths.get("C:\\FZI\\models-output\\model.uml").toString()));

		Model modelRoot = UMLFactory.eINSTANCE.createModel();
		resource.getContents().add(modelRoot);

		UMLFromJavaGenerator.Parameters parameters;
		parameters = new UMLFromJavaGenerator.Parameters();
		parameters.setSearchPaths(Arrays.asList(new String[] { "*" }));
		parameters.setUmlRootPackage(modelRoot);
		parameters.setPackageName("");
		parameters.setCreationPaths(Arrays.asList(new String[] { "pmain.*", "blubb.*", "pmain" }));
		parameters.setQualifiedNamesInProjects(fullyQualifiedNames);

		UMLFromJavaGenerator visitor = new UMLFromJavaGenerator(parameters);

		for (Path javaFile : javaFiles) {
			visitor.processJavaFile(javaFile);
		}

		resource.save(null);
		
//		// Get qualified names of CUs in projects
//		QualifiedNamesFromIJavaElementCollector collector = new QualifiedNamesFromIJavaElementCollector(false, true, false);
//		List<String> qualifiedNamesInProjects = collector.getQualifiedNamesFromSelection(recordedSelection);
//		parameters.setQualifiedNamesInProjects(qualifiedNamesInProjects);
//
//		ReverseSelectedNodeVisitor visitor = new ReverseSelectedNodeVisitor(parameters);
//		ProjectExplorerNodeWalker reverseWalker = new ProjectExplorerNodeWalker(visitor);
////		ProjectExplorerNodeWalkerWithIProgress reverseWalker = new ProjectExplorerNodeWalkerWithIProgress(visitor);
//		reverseWalker.visit(recordedSelection.toList());

	}

}
