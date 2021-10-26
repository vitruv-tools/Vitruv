package tools.vitruv.variability.vave.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
import tools.vitruv.variability.vave.tests.generator.JavaPpGenerator;
import tools.vitruv.variability.vave.tests.generator.UMLFromJavaGenerator;

/**
 * Use preprocessor and papyrus to generate source code variants with uml diagrams.
 * 
 */
@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
@Disabled
public class GeneratorTest {

	@Test
	public void addUMLModelToAllArgoUMLVariants() throws IOException {
//		Path variantsLocation = Paths.get("");
//		Collection<Path> variantLocations = Files.list(variantsLocation).collect(Collectors.toList());

		Collection<Path> variantLocations = new ArrayList<>();
		variantLocations.add(Paths.get("C:\\FZI\\git\\argouml-workaround"));

		for (Path variantLocation : variantLocations) {
			// create uml diagram of variant
			List<String> fullyQualifiedNames = new ArrayList<>();
			List<Path> javaFiles = new ArrayList<>();

			Path[] sourceFolders = new Path[] { variantLocation.resolve("src\\argouml-core-model\\src"), variantLocation.resolve("src\\argouml-core-model-euml\\src"), variantLocation.resolve("src\\argouml-core-model-mdr\\build\\java"), variantLocation.resolve("src\\argouml-core-model-mdr\\src"), variantLocation.resolve("src\\argouml-app\\src"),
					variantLocation.resolve("src\\argouml-core-diagrams-sequence2\\src") };
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
			Resource resource = resourceSet.createResource(URI.createFileURI(variantLocation.resolve("model.uml").toString()));

			Model modelRoot = UMLFactory.eINSTANCE.createModel();
			resource.getContents().add(modelRoot);

			UMLFromJavaGenerator.Parameters parameters;
			parameters = new UMLFromJavaGenerator.Parameters();
			parameters.setSearchPaths(Arrays.asList(new String[] { "*" }));
			parameters.setUmlRootPackage(modelRoot);
			parameters.setPackageName("");
			parameters.setCreationPaths(Arrays.asList(new String[] {}));
			parameters.setQualifiedNamesInProjects(fullyQualifiedNames);

			UMLFromJavaGenerator visitor = new UMLFromJavaGenerator(parameters);
			for (Path javaFile : javaFiles) {
				visitor.processJavaFile(javaFile);
			}

			resource.save(null);
		}
	}

	@Test
	public void generateAllArgoUMLVariants() throws IOException {
		Path splLocation = Paths.get("C:\\FZI\\git\\argouml-spl-revisions\\R1");

		String COGNITIVE = "COGNITIVE";
		String LOGGING = "LOGGING";
		String ACTIVITYDIAGRAM = "ACTIVITYDIAGRAM";
		String STATEDIAGRAM = "STATEDIAGRAM";
		String SEQUENCEDIAGRAM = "SEQUENCEDIAGRAM";
		String USECASEDIAGRAM = "USECASEDIAGRAM";
		String COLLABORATIONDIAGRAM = "COLLABORATIONDIAGRAM";
		String DEPLOYMENTDIAGRAM = "DEPLOYMENTDIAGRAM";
		String Diagrams = "Diagrams";
		String Class = "Class";
		String ArgoUML = "ArgoUML";
		String Core = "Core";

		String[] mandatory = new String[] { "java.home", "user.home", "user.dir", Diagrams, Class, ArgoUML };

		String[] optional = new String[] { COGNITIVE, LOGGING, ACTIVITYDIAGRAM, COLLABORATIONDIAGRAM, DEPLOYMENTDIAGRAM, SEQUENCEDIAGRAM, STATEDIAGRAM, USECASEDIAGRAM };

		long binarycounter = 0;
		long max = (int) Math.pow(2, optional.length);

		while (binarycounter < max) {
			ArrayList<String> current = new ArrayList<String>();
			for (int j = 0; j < optional.length; j++) {
				// if bit on position j is 1
				if (((binarycounter >> j) & 1) == 1) {
					current.add(optional[j]);
				}
			}

			String variantName = current.stream().map(s -> s.substring(0, 4)).collect(Collectors.joining("-"));

			current.addAll(Arrays.asList(mandatory));

			Path variantsLocation = splLocation.getParent().resolve(splLocation.getFileName().toString() + "_variants");
			Path variantLocation = variantsLocation.resolve("V" + (variantName.isEmpty() ? "" : "-") + variantName);

			Path splSourceFolder = splLocation.resolve("src");

			Files.createDirectories(variantLocation);

			JavaPpGenerator generator = new JavaPpGenerator(splSourceFolder.toFile());
			generator.setFeatures(current.toArray(new String[current.size()]));
			generator.generateFiles(variantLocation.toFile());

			binarycounter++;
		}
	}

	@Test
	public void generateUMLTest() throws IOException {
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
	}

}
