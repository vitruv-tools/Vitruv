package tools.vitruv.variability.vave.tests;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.Match;
import org.eclipse.emf.compare.diff.DefaultDiffEngine;
import org.eclipse.emf.compare.diff.DiffBuilder;
import org.eclipse.emf.compare.diff.FeatureFilter;
import org.eclipse.emf.compare.diff.IDiffEngine;
import org.eclipse.emf.compare.diff.IDiffProcessor;
import org.eclipse.emf.compare.match.DefaultComparisonFactory;
import org.eclipse.emf.compare.match.DefaultEqualityHelperFactory;
import org.eclipse.emf.compare.match.DefaultMatchEngine;
import org.eclipse.emf.compare.match.IComparisonFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.eobject.IdentifierEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.rcp.EMFCompareRCPPlugin;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.EqualityHelper;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import com.google.common.cache.CacheBuilder;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.variability.vave.tests.umlcomp.UMLMatchEngineFactory;

/**
 * Computes the metrics used for the evaluation with ArgoUML. This involves a comparison of all generated variants with / without consistency preservation with the ground-truth variants by using EMFCompare.
 */
//@Disabled
public class MetricsComputationTest {

	@Test
	public void computeMetricsForEval() throws IOException {
		Path groundTruthLocation = Paths.get("C:\\FZI\\git\\argouml-spl-revisions-variants");
		Path resultsLocation = Paths.get("C:\\FZI\\vave-resource-location");

		{ // compute differences between ground truth uml models at revision R and R-1 per product variant.
			FileWriter fw = new FileWriter("C:\\FZI\\eval-results\\res1.csv");

			List<Map<String, Integer>> revisionMap = new ArrayList<>();

			for (int rev = 1; rev <= 9; rev++) {
				Path previousRevisionLocation = groundTruthLocation.resolve("R" + (rev - 1) + "_variants");
				Path revisionLocation = groundTruthLocation.resolve("R" + rev + "_variants");

				Collection<Path> variantLocations = Files.list(revisionLocation).collect(Collectors.toList());

				Map<String, Integer> differencesPerVariant = new HashMap<>();

				for (Path variantLocation : variantLocations) {
					int numDiffs = this.compareModelsOfArgoUMLVariants(variantLocation.resolve("model.uml"), previousRevisionLocation.resolve(variantLocation.getFileName()).resolve("model.uml"));

					differencesPerVariant.put(variantLocation.getFileName().toString(), numDiffs);
				}

				revisionMap.add(differencesPerVariant);
			}
			// write results to csv file (product id; system revision; differences).
			for (int rev = 1; rev <= 9; rev++) {
				for (Map.Entry<String, Integer> entry : revisionMap.get(rev - 1).entrySet()) {
					fw.write(entry.getKey() + ";" + rev + ";" + entry.getValue());
				}
			}

			fw.close();
		}

		{ // compute differences between ground truth uml models at revisions R and computed uml models at revision R per product variant.
			FileWriter fw = new FileWriter("C:\\FZI\\eval-results\\res2.csv");

			List<Map<String, Integer>> revisionMap = new ArrayList<>();

			for (int rev = 0; rev <= 9; rev++) {
				Path revisionLocation = groundTruthLocation.resolve("R" + rev + "_variants");

				Collection<Path> variantLocations = Files.list(revisionLocation).collect(Collectors.toList());

				Map<String, Integer> differencesPerVariant = new HashMap<>();

				for (Path variantLocation : variantLocations) {
					int numDiffs = this.compareModelsOfArgoUMLVariants(variantLocation.resolve("model.uml"), resultsLocation.resolve("R" + rev + "-eval-variants").resolve(variantLocation.getFileName()).resolve("umloutput/umloutput.uml"));

					differencesPerVariant.put(variantLocation.getFileName().toString(), numDiffs);
				}

				revisionMap.add(differencesPerVariant);
			}
			// write results to csv file (product id; system revision; differences).
			for (int rev = 0; rev <= 9; rev++) {
				for (Map.Entry<String, Integer> entry : revisionMap.get(rev).entrySet()) {
					fw.write(entry.getKey() + ";" + rev + ";" + entry.getValue());
				}
			}

			fw.close();
		}

		// aggregate over revisions and total? or use excel or R for that?
	}

	public int compareModelsOfArgoUMLVariants(Path model1Location, Path model2Location) {
		final MatchEngineFactoryImpl matchEngineFactory = new UMLMatchEngineFactory(new EqualityHelper(EqualityHelper.createDefaultCache(CacheBuilder.newBuilder())));
		matchEngineFactory.setRanking(20);

		IMatchEngine.Factory.Registry registry = EMFCompareRCPPlugin.getDefault().getMatchEngineFactoryRegistry();
		registry.add(matchEngineFactory);

		IDiffProcessor diffProcessor = new DiffBuilder();
		IDiffEngine diffEngine = new DefaultDiffEngine(diffProcessor) {
			@Override
			protected FeatureFilter createFeatureFilter() {
				return new FeatureFilter() {
					@Override
					protected boolean isIgnoredReference(Match match, EReference reference) {
						return super.isIgnoredReference(match, reference);
					}

					@Override
					public boolean isIgnoredAttribute(EAttribute attribute) {
						return super.isIgnoredAttribute(attribute);
					}

					@Override
					public boolean checkForOrderingChanges(EStructuralFeature feature) {
						return super.checkForOrderingChanges(feature);
					}
				};
			}
		};

		EMFCompare comparator = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).setDiffEngine(diffEngine).build();

		// ------------------------------------

		ResourceSet resourceSet1 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resource1 = resourceSet1.getResource(URI.createFileURI(model1Location.toString()), true);
		System.out.println("LOADED FIRST RESOURCE");

		// HERE START THE FIXES

		org.eclipse.uml2.uml.Package model1 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource1.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()));

		org.eclipse.uml2.uml.Package argoPackage1 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource1.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get()).getPackagedElements()
				.stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();

		// delete all opaque behaviors
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof OpaqueBehavior || o instanceof OpaqueExpression) { // || o instanceof org.eclipse.uml2.uml.LiteralSpecification) {
					toDelete.add(o);
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
//		// delete all contents of parameters
//		{
//			Collection<EObject> toDelete = new ArrayList<>();
//			TreeIterator<EObject> it = resource1.getAllContents();
//			while (it.hasNext()) {
//				EObject o = it.next();
//				if (o instanceof Parameter) {
//					for (EObject child : o.eContents())
//						toDelete.add(child);
//				}
//			}
//			for (EObject o : toDelete)
//				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
//		}
		// delete all classes that are empty and have an interface with same name in same package
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof Class && o.eContents().isEmpty() && ((Class) o).eContainer().eContents().stream().filter(v -> v instanceof Interface && ((Interface) v).getName().equals(((Class) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
					toDelete.add(o);
					System.out.println("CLEANUP2: " + ((Class) o).getQualifiedName());
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
		// delete all empty classes (or better: all "external" types) in argouml package (they are either external and placed in the wrong package or are actually interfaces and created a second time as empty classes.
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = argoPackage1.eAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				// TODO: check for external!
				if (o instanceof Class && ((Class) o).getAppliedStereotype("external") != null) {
					toDelete.add(o);
					System.out.println("CLEANUP6: " + ((Class) o).getQualifiedName() + " - " + o);
				}
				if (o instanceof Class && o.eContents().isEmpty()) { // && ((Class)o).getAppliedStereotype("External") == null) {
					toDelete.add(o);
					System.out.println("CLEANUP4: " + ((Class) o).getQualifiedName() + " - " + o);
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
		// delete all parameters without type where another parameter with the same name exists
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				// if (o instanceof Parameter && ((Parameter) o).getType() == null && ((Parameter) o).getOperation().getOwnedParameters().stream().filter(v -> v != o && ((Parameter) v).getName() != null && ((Parameter) v).getName().equals(((Parameter) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
				if (o instanceof Parameter && ((Parameter) o).getOperation().getOwnedParameters().stream().filter(v -> v != o && ((Parameter) v).getName() != null && ((Parameter) v).getName().equals(((Parameter) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
					toDelete.add(o);
					System.out.println("CLEANUP3: " + ((Parameter) o).getQualifiedName());
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
		// make all interface operations public and abstract
		{
			TreeIterator<EObject> it = resource1.getAllContents(); // argoPackage.eAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof Operation && o.eContainer() instanceof Interface) {
					((Operation) o).setIsAbstract(true);
					// ((Operation) o).setVisibility(VisibilityKind.PUBLIC_LITERAL);
					((Operation) o).setVisibility(null);
				}
			}
		}
		// delete empty packages
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof Package && ((org.eclipse.uml2.uml.Package) o).eContents().isEmpty()) {
					toDelete.add(o);
					System.out.println("CLEANUP7: " + ((Parameter) o).getQualifiedName());
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}

		ResourceSet resourceSet2 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resource2 = resourceSet2.getResource(URI.createFileURI(model2Location.toString()), true);
		System.out.println("LOADED SECOND RESOURCE");

		org.eclipse.uml2.uml.Package model2 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource2.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()));

		org.eclipse.uml2.uml.Package argoPackage2 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource2.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get()).getPackagedElements()
				.stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();

		// ---------------------

		long timeStart = System.currentTimeMillis();

		IComparisonScope scope = new DefaultComparisonScope(model1, model2, null);
		Comparison comparison = comparator.compare(scope);
		List<Diff> differences = comparison.getDifferences();
		List<Match> matches = comparison.getMatches();

		long timeDiff = System.currentTimeMillis() - timeStart;
		System.out.println("TOTAL TIME: " + timeDiff);

		System.out.println("DIFFERENCES: " + differences.size());
		System.out.println("MATCHES: " + matches.size());

		System.out.println("DONE");

		return differences.size();
	}

	@Test
	public void compareModels() throws IOException {

		Map<Operation, String> operationToIdMap = new HashMap<>();
		Map<Resource, Set<String>> idsMap = new HashMap<>();

		Function<EObject, String> idFunction = new Function<EObject, String>() {
			public String apply(EObject input) {
				String id = null;

				if (input == null)
					return null;

				if (input instanceof OpaqueBehavior) {
					System.out.println("DAMN!!!");
				} else if (input instanceof Model) {
					id = ((Model) input).getName();
					if (id == null)
						System.out.println("1ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Package) {
					id = ((org.eclipse.uml2.uml.Package) input).getQualifiedName();
					if (id == null)
						System.out.println("2ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Class) {
					id = ((org.eclipse.uml2.uml.Class) input).getQualifiedName();
					if (id == null)
						System.out.println("3ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Interface) {
					id = ((org.eclipse.uml2.uml.Interface) input).getQualifiedName();
					if (id == null)
						System.out.println("4ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Property) {
					id = ((org.eclipse.uml2.uml.Property) input).getQualifiedName();
					if (id == null)
						System.out.println("5ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.InterfaceRealization) {
					id = ((org.eclipse.uml2.uml.InterfaceRealization) input).getImplementingClassifier().getQualifiedName() + ":::" + ((org.eclipse.uml2.uml.InterfaceRealization) input).getContract().getQualifiedName();
					if (id == null)
						System.out.println("6ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Generalization) {
					Generalization gen = (org.eclipse.uml2.uml.Generalization) input;
					if (gen.getSpecific() != null && gen.getGeneral() != null)
						id = gen.getSpecific().getQualifiedName() + ":::" + gen.getGeneral().getQualifiedName();
					if (id == null)
						System.out.println("7ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Operation) {
					org.eclipse.uml2.uml.Operation operation = (org.eclipse.uml2.uml.Operation) input;
					id = operation.getQualifiedName() + "(" + operation.getOwnedParameters().stream().filter(p -> p.getType() != null && p.getDirection() == ParameterDirectionKind.IN_LITERAL).map(p -> p.getType().getName().toString().toLowerCase()).collect(Collectors.joining(",")) + ")";

					operationToIdMap.put(operation, id);
				} else if (input instanceof org.eclipse.uml2.uml.Parameter) {
					org.eclipse.uml2.uml.Parameter parameter = ((org.eclipse.uml2.uml.Parameter) input);

					String opid = operationToIdMap.get(parameter.getOperation());

					if (parameter.getName() == null || parameter.getName().isEmpty()) {
						id = opid + " - " + "RETURN PARAMETER";
					} else {
						id = opid + " - " + parameter.getName();
					}
				}

				// TODO: make (primitive) types comparable!

				String suffix = input.eClass().getName();
				if (input instanceof org.eclipse.uml2.uml.Class || input instanceof org.eclipse.uml2.uml.Interface) {
//					suffix = "Type";
				}
				if (id != null) {
					id = id + " - " + suffix;
				}

				if (id != null) {
					Set<String> ids = idsMap.get(input.eResource());
					if (ids == null) {
						ids = new HashSet<>();
						idsMap.put(input.eResource(), ids);
					}
					if (ids.contains(id)) {
						int i = 0;
						while (ids.contains(id + "-" + i))
							i++;
						ids.add(id + "-" + i);
						id = id + "-" + i;
						System.out.println("ERROR: " + id);
					}
//					if (ids.contains(id))
//						throw new RuntimeException("ERROR: " + id);
					ids.add(id);
				}

				System.out.println("ID: " + id);
				if (id == null)
					System.out.println("INPUT: " + input);

				// a null return here tells the match engine to fall back to the other matchers
				return id;
			}
		};
		// Using this matcher as fall back, EMF Compare will still search for XMI IDs on EObjects for which we had no custom id function.
		IEObjectMatcher fallBackMatcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.WHEN_AVAILABLE);
		IEObjectMatcher customIDMatcher = new IdentifierEObjectMatcher(fallBackMatcher, idFunction);

//		// !!! custom equality helper
//
//		IEqualityHelperFactory helperFactory = new DefaultEqualityHelperFactory() {
//			@Override
//			public org.eclipse.emf.compare.utils.IEqualityHelper createEqualityHelper() {
//				final LoadingCache<EObject, URI> cache = EqualityHelper.createDefaultCache(getCacheBuilder());
//				return new EqualityHelper(cache) {
//					@Override
//					public boolean matchingValues(Object object1, Object object2) {
////						System.out.println("MATCHING: " + object1 + " / " + object2);
////						if (object1 instanceof Model && object2 instanceof Model) {
////							return Objects.equals(((Model) object1).getQualifiedName(), ((Model) object2).getQualifiedName());
////						} else if (object1 instanceof org.eclipse.uml2.uml.Package && object2 instanceof org.eclipse.uml2.uml.Package) {
////							return Objects.equals(((org.eclipse.uml2.uml.Package) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Package) object2).getQualifiedName());
////						} else if (object1 instanceof org.eclipse.uml2.uml.Class && object2 instanceof org.eclipse.uml2.uml.Class) {
////							return Objects.equals(((org.eclipse.uml2.uml.Class) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Class) object2).getQualifiedName());
////						} else if (object1 instanceof org.eclipse.uml2.uml.Interface && object2 instanceof org.eclipse.uml2.uml.Interface) {
////							return Objects.equals(((org.eclipse.uml2.uml.Interface) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Interface) object2).getQualifiedName());
////						} else if (object1 instanceof org.eclipse.uml2.uml.Property && object2 instanceof org.eclipse.uml2.uml.Property) {
////							return Objects.equals(((org.eclipse.uml2.uml.Property) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Property) object2).getQualifiedName());
////						} else if (object1 instanceof org.eclipse.uml2.uml.Operation && object2 instanceof org.eclipse.uml2.uml.Operation) {
////							return Objects.equals(((org.eclipse.uml2.uml.Operation) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Operation) object2).getQualifiedName());
////						} else if (object1 instanceof org.eclipse.uml2.uml.Parameter && object2 instanceof org.eclipse.uml2.uml.Parameter) {
////							return Objects.equals(((org.eclipse.uml2.uml.Parameter) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Parameter) object2).getQualifiedName());
////						}
//						return super.matchingValues(object1, object2);
//					}
//				};
//			}
//		};
//
//		IComparisonFactory comparisonFactory = new DefaultComparisonFactory(helperFactory);
		IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());

		// IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
		// for OSGi (IDE, RCP) usage
		IMatchEngine.Factory.Registry registry = EMFCompareRCPPlugin.getDefault().getMatchEngineFactoryRegistry();
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(customIDMatcher, comparisonFactory);
		matchEngineFactory.setRanking(20); // default engine ranking is 10, must be higher to override.
		registry.add(matchEngineFactory);

		IDiffProcessor diffProcessor = new DiffBuilder();
		IDiffEngine diffEngine = new DefaultDiffEngine(diffProcessor) {
			@Override
			protected FeatureFilter createFeatureFilter() {
				return new FeatureFilter() {
					@Override
					protected boolean isIgnoredReference(Match match, EReference reference) {
//						return reference == EcorePackage.Literals.ENAMED_ELEMENT__NAME || super.isIgnoredReference(match, reference);
//						return !(reference == UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT
//								|| reference == UMLPackage.Literals.NAMESPACE__OWNED_MEMBER
//								|| reference == UMLPackage.Literals.CLASS__OWNED_OPERATION || reference == UMLPackage.Literals.CLASS__NESTED_CLASSIFIER
//								|| reference == UMLPackage.Literals.CLASSIFIER__ATTRIBUTE || reference == UMLPackage.Literals.CLASSIFIER__GENERALIZATION 
//								|| reference == UMLPackage.Literals.BEHAVIORED_CLASSIFIER__INTERFACE_REALIZATION || reference == UMLPackage.Literals.BEHAVIOR__OWNED_PARAMETER
////								|| reference == UMLPackage.Literals.OPERATION__TYPE 
//								|| reference == UMLPackage.Literals.TYPED_ELEMENT__TYPE
//								);
						return !(reference == UMLPackage.Literals.NAMESPACE__OWNED_MEMBER // contained packages, operations, attributes, etc.
								|| reference == UMLPackage.Literals.CLASSIFIER__GENERALIZATION || reference == UMLPackage.Literals.BEHAVIORED_CLASSIFIER__INTERFACE_REALIZATION || reference == UMLPackage.Literals.TYPED_ELEMENT__TYPE);

//						return true;
					}

					@Override
					public boolean isIgnoredAttribute(EAttribute attribute) {
						return !(attribute == UMLPackage.Literals.NAMED_ELEMENT__VISIBILITY || attribute == UMLPackage.Literals.NAMED_ELEMENT__NAME);
					}

					@Override
					public boolean checkForOrderingChanges(EStructuralFeature feature) {
						return false;
					}
				};
			}
		};

		EMFCompare comparator = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).setDiffEngine(diffEngine).build();

		ResourceSet resourceSet1 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resource1 = resourceSet1.getResource(URI.createFileURI("C:\\FZI\\model-tests\\papyrus-models\\model-full-v2.uml"), true);
		System.out.println("LOADED FIRST RESOURCE");

		// HERE START THE FIXES

		org.eclipse.uml2.uml.Package model1 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource1.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()));

		org.eclipse.uml2.uml.Package argoPackage1 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource1.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get()).getPackagedElements()
				.stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();

		// delete all opaque behaviors
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof OpaqueBehavior || o instanceof OpaqueExpression) { // || o instanceof org.eclipse.uml2.uml.LiteralSpecification) {
					toDelete.add(o);
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
//		// delete all contents of parameters
//		{
//			Collection<EObject> toDelete = new ArrayList<>();
//			TreeIterator<EObject> it = resource1.getAllContents();
//			while (it.hasNext()) {
//				EObject o = it.next();
//				if (o instanceof Parameter) {
//					for (EObject child : o.eContents())
//						toDelete.add(child);
//				}
//			}
//			for (EObject o : toDelete)
//				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
//		}
		// delete all classes that are empty and have an interface with same name in same package
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof Class && o.eContents().isEmpty() && ((Class) o).eContainer().eContents().stream().filter(v -> v instanceof Interface && ((Interface) v).getName().equals(((Class) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
					toDelete.add(o);
					System.out.println("CLEANUP2: " + ((Class) o).getQualifiedName());
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
		// delete all empty classes (or better: all "external" types) in argouml package (they are either external and placed in the wrong package or are actually interfaces and created a second time as empty classes.
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = argoPackage1.eAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				// TODO: check for external!
				if (o instanceof Class && ((Class) o).getAppliedStereotype("external") != null) {
					toDelete.add(o);
					System.out.println("CLEANUP6: " + ((Class) o).getQualifiedName() + " - " + o);
				}
				if (o instanceof Class && o.eContents().isEmpty()) { // && ((Class)o).getAppliedStereotype("External") == null) {
					toDelete.add(o);
					System.out.println("CLEANUP4: " + ((Class) o).getQualifiedName() + " - " + o);
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
		// delete all parameters without type where another parameter with the same name exists
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				// if (o instanceof Parameter && ((Parameter) o).getType() == null && ((Parameter) o).getOperation().getOwnedParameters().stream().filter(v -> v != o && ((Parameter) v).getName() != null && ((Parameter) v).getName().equals(((Parameter) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
				if (o instanceof Parameter && ((Parameter) o).getOperation().getOwnedParameters().stream().filter(v -> v != o && ((Parameter) v).getName() != null && ((Parameter) v).getName().equals(((Parameter) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
					toDelete.add(o);
					System.out.println("CLEANUP3: " + ((Parameter) o).getQualifiedName());
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
		// make all interface operations public and abstract
		{
			TreeIterator<EObject> it = resource1.getAllContents(); // argoPackage.eAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof Operation && o.eContainer() instanceof Interface) {
					((Operation) o).setIsAbstract(true);
					// ((Operation) o).setVisibility(VisibilityKind.PUBLIC_LITERAL);
					((Operation) o).setVisibility(null);
				}
			}
		}
		// delete empty packages
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				if (o instanceof Package && ((org.eclipse.uml2.uml.Package) o).eContents().isEmpty()) {
					toDelete.add(o);
					System.out.println("CLEANUP7: " + ((Parameter) o).getQualifiedName());
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}

		ResourceSet resourceSet2 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resource2 = resourceSet2.getResource(URI.createFileURI("C:\\FZI\\model-tests\\vitruvius-models\\umloutput-full-v2.uml"), true);
//		Resource resource2 = resourceSet2.getResource(URI.createFileURI("C:\\FZI\\git\\argouml-workaround\\model2.uml"), true);
		System.out.println("LOADED SECOND RESOURCE");

		org.eclipse.uml2.uml.Package model2 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource2.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()));

//		org.eclipse.uml2.uml.Package argoPackage1 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource1.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get()).getPackagedElements()
//				.stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();
//
//		org.eclipse.uml2.uml.Package argoPackage2 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource2.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get()).getPackagedElements()
//				.stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();
//
//		System.out.println("PACKAGE1: " + argoPackage1);
//		System.out.println("PACKAGE2: " + argoPackage2);

		ResourceSet tempResourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource tempResource = tempResourceSet.createResource(URI.createFileURI("C:\\FZI\\model-tests\\dummy-model.uml"));
		Model modelRoot = UMLFactory.eINSTANCE.createModel();
		org.eclipse.uml2.uml.Package tempPackage = UMLFactory.eINSTANCE.createPackage();
		modelRoot.getPackagedElements().add(tempPackage);
		tempResource.getContents().add(modelRoot);
		int maxDifferences1 = 0;
		int maxDifferences2 = 0;
		operationToIdMap.clear();
		idsMap.clear();
		{
			IComparisonScope tempScope = new DefaultComparisonScope(modelRoot, model2, null);
			Comparison comparison = comparator.compare(tempScope);
			List<Diff> differences = comparison.getDifferences();
			List<Match> matches = comparison.getMatches();
			System.out.println("1DIFFERENCES: " + differences.size());
			System.out.println("1MATCHES: " + matches.size());
			maxDifferences1 = differences.size();
		}
		operationToIdMap.clear();
		idsMap.clear();
		{
			IComparisonScope tempScope = new DefaultComparisonScope(model1, modelRoot, null);
			Comparison comparison = comparator.compare(tempScope);
			List<Diff> differences = comparison.getDifferences();
			List<Match> matches = comparison.getMatches();
			System.out.println("2DIFFERENCES: " + differences.size());
			System.out.println("2MATCHES: " + matches.size());
			maxDifferences2 = differences.size();
		}
		operationToIdMap.clear();
		idsMap.clear();

		long timeStart = System.currentTimeMillis();

		IComparisonScope scope = new DefaultComparisonScope(model1, model2, null);
		Comparison comparison = comparator.compare(scope);
		List<Diff> differences = comparison.getDifferences();
		List<Match> matches = comparison.getMatches();

		long timeDiff = System.currentTimeMillis() - timeStart;
		System.out.println("TOTAL TIME: " + timeDiff);

		System.out.println("MAX DIFFERENCES: " + (maxDifferences1 + maxDifferences2));
		System.out.println("DIFFERENCES: " + differences.size());
		System.out.println("MATCHES: " + matches.size());

		System.out.println("TEST");
	}

	@Test
	public void compareModelWithSelfTest() throws IOException {

		Map<Operation, String> operationToIdMap = new HashMap<>();
		Map<Resource, Set<String>> idsMap = new HashMap<>();

		Function<EObject, String> idFunction = new Function<EObject, String>() {
			public String apply(EObject input) {
				String id = null;

				if (input == null)
					return null;

				if (input instanceof OpaqueBehavior) {
					System.out.println("DAMN!!!");
				} else if (input instanceof Model) {
					id = ((Model) input).getName();
					if (id == null)
						System.out.println("1ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Package) {
					id = ((org.eclipse.uml2.uml.Package) input).getQualifiedName();
					if (id == null)
						System.out.println("2ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Class) {
					id = ((org.eclipse.uml2.uml.Class) input).getQualifiedName();
					if (id == null)
						System.out.println("3ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Interface) {
					id = ((org.eclipse.uml2.uml.Interface) input).getQualifiedName();
					if (id == null)
						System.out.println("4ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Property) {
					id = ((org.eclipse.uml2.uml.Property) input).getQualifiedName();
					if (id == null)
						System.out.println("5ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.InterfaceRealization) {
					id = ((org.eclipse.uml2.uml.InterfaceRealization) input).getImplementingClassifier().getQualifiedName() + ":::" + ((org.eclipse.uml2.uml.InterfaceRealization) input).getContract();
					if (id == null)
						System.out.println("6ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Generalization) {
					Generalization gen = (org.eclipse.uml2.uml.Generalization) input;
					if (gen.getSpecific() != null && gen.getGeneral() != null)
						id = gen.getSpecific().getQualifiedName() + ":::" + gen.getGeneral().getQualifiedName();
					if (id == null)
						System.out.println("7ERROR!!!");
				} else if (input instanceof org.eclipse.uml2.uml.Operation) {
					org.eclipse.uml2.uml.Operation operation = (org.eclipse.uml2.uml.Operation) input;
					id = operation.getQualifiedName() + "(" + operation.getOwnedParameters().stream().filter(p -> p.getType() != null && p.getDirection() == ParameterDirectionKind.IN_LITERAL).map(p -> p.getType().getName().toString().toLowerCase()).collect(Collectors.joining(",")) + ")";

//					Set<String> operationIds = operationsMap.get(operation.eResource());
//					if (operationIds == null) {
//						operationIds = new HashSet<>();
//						operationsMap.put(operation.eResource(), operationIds);
//					}
//					if (!operationIds.contains(id)) {
//						operationIds.add(id);
//					} else {
//						System.out.println("DUPLICATE: " + id);
//						int i = 0;
//						while (operationIds.contains(id + "-" + i)) {
//							i++;
//						}
//						operationIds.add(id + "-" + i);
//						id = id + "-" + i;
//					}
					operationToIdMap.put(operation, id);

//					System.out.println("INPUT: " + id);

//					id = null;
				} else if (input instanceof org.eclipse.uml2.uml.Parameter) {
					org.eclipse.uml2.uml.Parameter parameter = ((org.eclipse.uml2.uml.Parameter) input);

					org.eclipse.uml2.uml.Operation operation = parameter.getOperation();
					String opid = operationToIdMap.get(operation);

					if (parameter.getName() == null || parameter.getName().isEmpty()) {
						id = opid + " - " + "RETURN PARAMETER";
					} else {
						id = opid + " - " + parameter.getName();
					}
				}

				// TODO: make (primitive) types comparable!

				String suffix = input.eClass().getName();
				if (input instanceof org.eclipse.uml2.uml.Class || input instanceof org.eclipse.uml2.uml.Interface) {
//					suffix = "Type";
				}
				if (id != null) {
					id = id + " - " + suffix;
				}

				if (id != null) {
					Set<String> ids = idsMap.get(input.eResource());
					if (ids == null) {
						ids = new HashSet<>();
						idsMap.put(input.eResource(), ids);
					}
//					if (ids.contains(id))
//						throw new RuntimeException("ERROR: " + id);
					ids.add(id);
				}

//				System.out.println("ID: " + id);
//				if (id == null)
//					System.out.println("INPUT: " + input);

				// a null return here tells the match engine to fall back to the other matchers
				return id;
			}
		};
		// Using this matcher as fall back, EMF Compare will still search for XMI IDs on EObjects for which we had no custom id function.
		IEObjectMatcher fallBackMatcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.WHEN_AVAILABLE);
		IEObjectMatcher customIDMatcher = new IdentifierEObjectMatcher(fallBackMatcher, idFunction);

		IComparisonFactory comparisonFactory = new DefaultComparisonFactory(new DefaultEqualityHelperFactory());

		// IMatchEngine.Factory.Registry registry = MatchEngineFactoryRegistryImpl.createStandaloneInstance();
		// for OSGi (IDE, RCP) usage
		IMatchEngine.Factory.Registry registry = EMFCompareRCPPlugin.getDefault().getMatchEngineFactoryRegistry();
		final MatchEngineFactoryImpl matchEngineFactory = new MatchEngineFactoryImpl(customIDMatcher, comparisonFactory);
		matchEngineFactory.setRanking(20); // default engine ranking is 10, must be higher to override.
		registry.add(matchEngineFactory);

		IDiffProcessor diffProcessor = new DiffBuilder();
		IDiffEngine diffEngine = new DefaultDiffEngine(diffProcessor) {
			@Override
			protected FeatureFilter createFeatureFilter() {
				return new FeatureFilter() {
//					@Override
//					protected boolean isIgnoredReference(Match match, EReference reference) {
//						return reference == EcorePackage.Literals.ENAMED_ELEMENT__NAME ||
//								super.isIgnoredReference(match, reference);
//					}

					@Override
					public boolean checkForOrderingChanges(EStructuralFeature feature) {
						return false;
					}
				};
			}
		};

		EMFCompare comparator = EMFCompare.builder().setMatchEngineFactoryRegistry(registry).setDiffEngine(diffEngine).build();

		ResourceSet resourceSet1 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resource1 = resourceSet1.getResource(URI.createFileURI("C:\\FZI\\model-tests\\papyrus-models\\model-full-v2.uml"), true);
		System.out.println("LOADED FIRST RESOURCE");

		// HERE START THE FIXES

//		org.eclipse.uml2.uml.Package model1 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource1.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()));
//
//		org.eclipse.uml2.uml.Package argoPackage1 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource1.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get()).getPackagedElements()
//				.stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();

//		// delete all opaque behaviors
//		{
//			Collection<EObject> toDelete = new ArrayList<>();
//			TreeIterator<EObject> it = resource1.getAllContents();
//			while (it.hasNext()) {
//				EObject o = it.next();
//				if (o instanceof OpaqueBehavior || o instanceof OpaqueExpression) { // || o instanceof org.eclipse.uml2.uml.LiteralSpecification) {
//					toDelete.add(o);
//				}
//			}
//			for (EObject o : toDelete)
//				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
//		}
//		// delete all classes that are empty and have an interface with same name in same package
//		{
//			Collection<EObject> toDelete = new ArrayList<>();
//			TreeIterator<EObject> it = resource1.getAllContents();
//			while (it.hasNext()) {
//				EObject o = it.next();
//				if (o instanceof Class && o.eContents().isEmpty() && ((Class) o).eContainer().eContents().stream().filter(v -> v instanceof Interface && ((Interface) v).getName().equals(((Class) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
//					toDelete.add(o);
//					System.out.println("CLEANUP2: " + ((Class) o).getQualifiedName());
//				}
//			}
//			for (EObject o : toDelete)
//				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
//		}
//		// delete all empty classes (or better: all "external" types) in argouml package (they are either external and placed in the wrong package or are actually interfaces and created a second time as empty classes.
//		{
//			Collection<EObject> toDelete = new ArrayList<>();
//			TreeIterator<EObject> it = argoPackage1.eAllContents();
//			while (it.hasNext()) {
//				EObject o = it.next();
//				// TODO: check for external!
//				if (o instanceof Class && ((Class) o).getAppliedStereotype("external") != null) {
//					toDelete.add(o);
//					System.out.println("CLEANUP6: " + ((Class) o).getQualifiedName() + " - " + o);
//				}
//				if (o instanceof Class && o.eContents().isEmpty()) { // && ((Class)o).getAppliedStereotype("External") == null) {
//					toDelete.add(o);
//					System.out.println("CLEANUP4: " + ((Class) o).getQualifiedName() + " - " + o);
//				}
//			}
//			for (EObject o : toDelete)
//				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
//		}
		// delete all parameters without type where another parameter with the same name exists
		{
			Collection<EObject> toDelete = new ArrayList<>();
			TreeIterator<EObject> it = resource1.getAllContents();
			while (it.hasNext()) {
				EObject o = it.next();
				// if (o instanceof Parameter && ((Parameter) o).getType() == null && ((Parameter) o).getOperation().getOwnedParameters().stream().filter(v -> v != o && ((Parameter) v).getName() != null && ((Parameter) v).getName().equals(((Parameter) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
				if (o instanceof Parameter && ((Parameter) o).getOperation().getOwnedParameters().stream().filter(v -> v != o && ((Parameter) v).getName() != null && ((Parameter) v).getName().equals(((Parameter) o).getName())).findAny().isPresent()) { // && ((Class)o).getAppliedStereotype("External") == null) {
					toDelete.add(o);
					System.out.println("CLEANUP3: " + ((Parameter) o).getQualifiedName());
				}
			}
			for (EObject o : toDelete)
				org.eclipse.emf.ecore.util.EcoreUtil.remove(o);
		}
//		// make all interface operations public and abstract
//		{
//			TreeIterator<EObject> it = resource1.getAllContents(); // argoPackage.eAllContents();
//			while (it.hasNext()) {
//				EObject o = it.next();
//				if (o instanceof Operation && o.eContainer() instanceof Interface) {
//					((Operation) o).setIsAbstract(true);
//					// ((Operation) o).setVisibility(VisibilityKind.PUBLIC_LITERAL);
//					((Operation) o).setVisibility(null);
//				}
//			}
//		}

		long timeStart = System.currentTimeMillis();

		IComparisonScope scope = new DefaultComparisonScope(resource1, resource1, null);
		Comparison comparison = comparator.compare(scope);
		List<Diff> differences = comparison.getDifferences();
		List<Match> matches = comparison.getMatches();

		long timeDiff = System.currentTimeMillis() - timeStart;
		System.out.println("TOTAL TIME: " + timeDiff);

		System.out.println("DIFFERENCES: " + differences.size());
		System.out.println("MATCHES: " + matches.size());

		System.out.println("TEST");
	}

}
