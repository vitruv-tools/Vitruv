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

	@Test
	public void createSpecificArgoUMLVariantJava() throws IOException {
		Path targetLocation = Paths.get("C:\\FZI\\git\\argouml-spl-revisions-variants");
		Path splLocations = Paths.get("C:\\FZI\\git\\argouml-spl-revisions");

		for (int rev = 0; rev <= 0; rev++) {
			Path splLocation = splLocations.resolve("R" + rev);

			// all features
			String[] allConfig = optional.clone();
			this.createVariant(allConfig, splLocation, targetLocation);
		}
	}

	/**
	 * Creates a subset of variants for the first nine revisions of ArgoUML-SPL.
	 * 
	 * @throws IOException
	 */
	@Test
	public void createArgoUMLVariantsJavaForInternalization() throws IOException {
		Path targetLocation = Paths.get("C:\\FZI\\git\\argouml-spl-revisions-variants");
		Path splLocations = Paths.get("C:\\FZI\\git\\argouml-spl-revisions");

		for (int rev = 0; rev <= 9; rev++) {
			Path splLocation = splLocations.resolve("R" + rev);

			// only core
			String[] emptyConfig = new String[] {};
			this.createVariant(emptyConfig, splLocation, targetLocation);

			// single features
			for (int i = 0; i < optional.length; i++) {
				String[] config = new String[] { optional[i] };
				this.createVariant(config, splLocation, targetLocation);
			}

			// pair-wise feature interactions
			for (int i = 0; i < optional.length; i++) {
				for (int j = i + 1; j < optional.length; j++) {
					String[] config = { optional[i], optional[j] };
					this.createVariant(config, splLocation, targetLocation);
				}
			}

			// selected three-wise feature interactions
			String[] threeWiseConfig = new String[] { LOGGING, COLLABORATIONDIAGRAM, SEQUENCEDIAGRAM };
			this.createVariant(threeWiseConfig, splLocation, targetLocation);

			// all features
			String[] allConfig = optional.clone();
			this.createVariant(allConfig, splLocation, targetLocation);
		}
	}

	/**
	 * Adds mandatory features and creates variant.
	 * 
	 * @param currentArray
	 * @param splLocation
	 * @param targetLocation
	 * @throws IOException
	 */
	private void createVariant(String[] currentArray, Path splLocation, Path targetLocation) throws IOException {
		List<String> current = new ArrayList<>(Arrays.asList(currentArray));

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

	}

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
		Path targetLocation = Paths.get("C:\\FZI\\git\\argouml-spl-revisions-variants");

		for (int rev = 0; rev <= 9; rev++) {
			Path revisionLocation = targetLocation.resolve("R" + rev + "_variants");

			Collection<Path> variantLocations = Files.list(revisionLocation).collect(Collectors.toList());

			for (Path variantLocation : variantLocations) {
				// create uml diagram of variant
				List<String> fullyQualifiedNames = new ArrayList<>();
				List<Path> javaFiles = new ArrayList<>();

				Path[] sourceFolders = new Path[] { variantLocation.resolve("src\\argouml-core-model\\src"), variantLocation.resolve("src\\argouml-core-model-euml\\src"), variantLocation.resolve("src\\argouml-core-model-mdr\\src"), variantLocation.resolve("src\\argouml-app\\src"), variantLocation.resolve("src\\argouml-core-diagrams-sequence2\\src") };
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

//			// HERE START THE FIXES
//
//			org.eclipse.uml2.uml.Package argoPackage = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get())
//					.getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();

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
	}

	/**
	 * This test creates all variants of a given ArgoUML-SPL revision.
	 */
	@Test
	public void generateAllArgoUMLVariants() throws IOException {
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
