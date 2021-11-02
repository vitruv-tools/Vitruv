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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.variability.vave.tests.generator.JavaPpGenerator;
import tools.vitruv.variability.vave.tests.generator.UMLFromJavaGenerator;

/**
 * Use JavaPP and Papyrus to generate source code variants and corresponding UML diagrams.
 */
@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
//@Disabled
public class GeneratorTest {

	/**
	 * Uses Vitruvius to create a UML model for every ArgoUML variant.
	 */
	@Test
	public void addVitruvUMLModelToAllArgoUMLVariants() {
		// TODO
	}

	/**
	 * This test uses the Papyrus Java Reverse functionality to add a UML model to every ArgoUML variant in a given folder.
	 */
	@Test
	public void addPapyrusUMLModelToAllArgoUMLVariants() throws IOException {
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
//			Path[] sourceFolders = new Path[] { variantLocation.resolve("src\\argouml-core-model\\src"), variantLocation.resolve("src\\argouml-core-model-euml\\src"), variantLocation.resolve("src\\argouml-core-model-mdr\\build\\java"), variantLocation.resolve("src\\argouml-core-model-mdr\\src") };
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
			modelRoot.setName("umloutput");
			resource.getContents().add(modelRoot);

			UMLFromJavaGenerator.Parameters parameters;
			parameters = new UMLFromJavaGenerator.Parameters();
			parameters.setSearchPaths(Arrays.asList(new String[] {}));
			parameters.setUmlRootPackage(modelRoot);
			parameters.setPackageName("");
			parameters.setCreationPaths(Arrays.asList(new String[] { "java.*", "blubb.*", ".", "org.*", "blubb.*", ".", "javax.*", "blubb.*", ".", "tudresden.*", "blubb.*", "." }));
			parameters.setQualifiedNamesInProjects(fullyQualifiedNames);

			UMLFromJavaGenerator visitor = new UMLFromJavaGenerator(parameters);
			for (Path javaFile : javaFiles) {
				visitor.processJavaFile(javaFile);
			}

			org.eclipse.uml2.uml.Package argoPackage = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get())
					.getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();

//			// HERE START THE FIXES
//
//			// delete all classes that are empty and have an interface with same name in same package
//			{
//				Collection<EObject> toDelete = new ArrayList<>();
//				TreeIterator<EObject> it = resource.getAllContents();
//				while (it.hasNext()) {
//					EObject o = it.next();
//					if (o instanceof Class && o.eContents().isEmpty() && ((Class) o).eContainer().eContents().stream().filter(v -> v instanceof Interface && ((Interface) v).getName().equals(((Class) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
//						toDelete.add(o);
//						System.out.println("CLEANUP2!!!");
//					}
//				}
//				for (EObject o : toDelete)
//					org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
//			}
//			// delete all empty classes in argouml package (they are either external and placed in the wrong package or are actually interfaces and created a second time as empty classes.
//			{
//				Collection<EObject> toDelete = new ArrayList<>();
//				TreeIterator<EObject> it = argoPackage.eAllContents();
//				while (it.hasNext()) {
//					EObject o = it.next();
//					if (o instanceof Class && o.eContents().isEmpty()) { // && ((Class)o).getAppliedStereotype("External") == null) {
//						toDelete.add(o);
//						System.out.println("CLEANUP4!!!");
//					}
//				}
//				for (EObject o : toDelete)
//					org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
//			}
//			// delete all parameters without type where another parameter with the same name exists
//			{
//				Collection<EObject> toDelete = new ArrayList<>();
//				TreeIterator<EObject> it = resource.getAllContents();
//				while (it.hasNext()) {
//					EObject o = it.next();
//					if (o instanceof Parameter && ((Parameter) o).getType() == null && ((Parameter) o).getOperation().getOwnedParameters().stream().filter(v -> v != o && ((Parameter) v).getName().equals(((Parameter) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
//						toDelete.add(o);
//						System.out.println("CLEANUP3!!!");
//					}
//				}
//				for (EObject o : toDelete)
//					org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
//			}
//			// make all interface operations public and abstract
//			{
//				TreeIterator<EObject> it = resource.getAllContents(); // argoPackage.eAllContents();
//				while (it.hasNext()) {
//					EObject o = it.next();
//					if (o instanceof Operation && o.eContainer() instanceof Interface) {
//						((Operation) o).setIsAbstract(true);
//						// ((Operation) o).setVisibility(VisibilityKind.PUBLIC_LITERAL);
//						((Operation) o).setVisibility(null);
//						System.out.println("CLEANUP1!!!");
//					}
//				}
//			}

			resource.save(null);
		}
	}

	/**
	 * This test creates all variants of a given ArgoUML-SPL revision.
	 */
	@Test
	public void generateAllArgoUMLVariants() throws IOException {
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

		// Path splLocation = Paths.get("C:\\FZI\\git\\argouml-spl-revisions\\R1");
		Path targetLocation = Paths.get("C:\\FZI\\git\\argouml-spl-revisions-variants");
		Path splLocations = Paths.get("C:\\FZI\\git\\argouml-spl-revisions");

		for (int i = 0; i < 26; i++) {
			Path splLocation = splLocations.resolve("R" + i);

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

				System.out.println("BEGIN: " + variantName);

				current.addAll(Arrays.asList(mandatory));

				Path variantsLocation = targetLocation.resolve(splLocation.getFileName().toString() + "_variants");
				Path variantLocation = variantsLocation.resolve("V" + (variantName.isEmpty() ? "" : "-") + variantName);

				Path splSourceFolder = splLocation.resolve("src");

				Files.createDirectories(variantLocation);

				JavaPpGenerator generator = new JavaPpGenerator(splSourceFolder.toFile());
				generator.setFeatures(current.toArray(new String[current.size()]));
				generator.generateFiles(variantLocation.toFile());

				System.out.println("END: " + variantName);

				binarycounter++;
			}
		}
	}

}
