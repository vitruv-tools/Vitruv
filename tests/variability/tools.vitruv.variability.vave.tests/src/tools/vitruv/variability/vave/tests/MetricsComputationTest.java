package tools.vitruv.variability.vave.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import org.eclipse.emf.compare.match.IEqualityHelperFactory;
import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.eobject.IEObjectMatcher;
import org.eclipse.emf.compare.match.eobject.IdentifierEObjectMatcher;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.rcp.EMFCompareRCPPlugin;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.EqualityHelper;
import org.eclipse.emf.compare.utils.UseIdentifiers;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.common.base.Function;
import com.google.common.cache.LoadingCache;

import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;

/**
 * Computes the metrics used for the evaluation with ArgoUML. This involves a comparison of all generated variants with / without consistency preservation with the ground-truth variants by using EMFCompare.
 */
@Disabled
public class MetricsComputationTest {

	@Test
	public void compareModels() {

		// !!!custom identifier

//		Set<String> parameterNames1 = new HashSet<>();
//		Set<String> parameterNames2 = new HashSet<>();
//		Map<Resource, Set<String>> parameterNamesMap = new HashMap<>();
		Map<EObject, Set<String>> parameterNamesMap = new HashMap<>();
		Function<EObject, String> idFunction = new Function<EObject, String>() {
			public String apply(EObject input) {
				String id = null;

//				if (input == null)
//					return null;
//
//				if (input instanceof Model) {
//					id = ((Model) input).getName();
//					if (id == null)
//						System.out.println("1ERROR!!!");
//				} else if (input instanceof org.eclipse.uml2.uml.Package) {
//					id = ((org.eclipse.uml2.uml.Package) input).getQualifiedName();
//					if (id == null)
//						System.out.println("2ERROR!!!");
//				} else if (input instanceof org.eclipse.uml2.uml.Class) {
//					id = ((org.eclipse.uml2.uml.Class) input).getQualifiedName();
//					if (id == null)
//						System.out.println("3ERROR!!!");
//				} else if (input instanceof org.eclipse.uml2.uml.Interface) {
//					id = ((org.eclipse.uml2.uml.Interface) input).getQualifiedName();
//					if (id == null)
//						System.out.println("4ERROR!!!");
//				} else if (input instanceof org.eclipse.uml2.uml.Property) {
//					id = ((org.eclipse.uml2.uml.Property) input).getQualifiedName();
//					if (id == null)
//						System.out.println("5ERROR!!!");
//				} else if (input instanceof org.eclipse.uml2.uml.Operation) {
////					// TODO: also consider paramters
////					id = ((org.eclipse.uml2.uml.Operation) input).getQualifiedName();
//					org.eclipse.uml2.uml.Operation operation = (org.eclipse.uml2.uml.Operation) input;
//					id = operation.getQualifiedName() + "(" + operation.getOwnedParameters().stream().filter(p -> p.getType() != null && p.getDirection() == ParameterDirectionKind.IN_LITERAL).map(p -> p.getType().getName().toString()).collect(Collectors.joining(",")) + ")";
//				} else if (input instanceof org.eclipse.uml2.uml.Parameter) {
//					org.eclipse.uml2.uml.Parameter parameter = ((org.eclipse.uml2.uml.Parameter) input);
//					if (parameter.getQualifiedName() != null) {
//						Set<String> parameterNames = parameterNamesMap.get(parameter.eContainer());
//						if (parameterNames == null) {
//							parameterNames = new HashSet<>();
//							parameterNamesMap.put(parameter.eContainer(), parameterNames);
//						}
//						if (!parameterNames.contains(parameter.getQualifiedName())) {
//							id = parameter.getQualifiedName();
////					if (id == null)
////						System.out.println("6ERROR!!!");
//							parameterNames.add(parameter.getQualifiedName());
//						} else {
//							int i = 0;
//							while (parameterNames.contains(parameter.getQualifiedName() + "-" + i)) {
//								i++;
//							}
//							parameterNames.add(parameter.getQualifiedName() + "-" + i);
//							// id = parameter.getQualifiedName() + "-" + i + " - " + (parameter.getType() != null ? parameter.getType().getName() : "NULL") + " - " + parameter.getDirection();
//							id = parameter.getQualifiedName() + "-" + i;
//						}
//					}
//				}
//
//				if (id != null) {
//					id = id + " - " + input.eClass().getName();
//				}
////				id = new Random().nextLong() + "";
//
//				System.out.println("ID: " + id);
//				if (id == null)
//					System.out.println("INPUT: " + input);

				// a null return here tells the match engine to fall back to the other matchers
				return id;
			}
		};
		// Using this matcher as fall back, EMF Compare will still search for XMI IDs on EObjects for which we had no custom id function.
		IEObjectMatcher fallBackMatcher = DefaultMatchEngine.createDefaultEObjectMatcher(UseIdentifiers.NEVER);
		IEObjectMatcher customIDMatcher = new IdentifierEObjectMatcher(fallBackMatcher, idFunction);

		// !!! custom equality helper

		IEqualityHelperFactory helperFactory = new DefaultEqualityHelperFactory() {
			@Override
			public org.eclipse.emf.compare.utils.IEqualityHelper createEqualityHelper() {
				final LoadingCache<EObject, URI> cache = EqualityHelper.createDefaultCache(getCacheBuilder());
				return new EqualityHelper(cache) {
					@Override
					public boolean matchingValues(Object object1, Object object2) {
//						System.out.println("MATCHING: " + object1 + " / " + object2);
//						if (object1 instanceof Model && object2 instanceof Model) {
//							return Objects.equals(((Model) object1).getQualifiedName(), ((Model) object2).getQualifiedName());
//						} else if (object1 instanceof org.eclipse.uml2.uml.Package && object2 instanceof org.eclipse.uml2.uml.Package) {
//							return Objects.equals(((org.eclipse.uml2.uml.Package) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Package) object2).getQualifiedName());
//						} else if (object1 instanceof org.eclipse.uml2.uml.Class && object2 instanceof org.eclipse.uml2.uml.Class) {
//							return Objects.equals(((org.eclipse.uml2.uml.Class) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Class) object2).getQualifiedName());
//						} else if (object1 instanceof org.eclipse.uml2.uml.Interface && object2 instanceof org.eclipse.uml2.uml.Interface) {
//							return Objects.equals(((org.eclipse.uml2.uml.Interface) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Interface) object2).getQualifiedName());
//						} else if (object1 instanceof org.eclipse.uml2.uml.Property && object2 instanceof org.eclipse.uml2.uml.Property) {
//							return Objects.equals(((org.eclipse.uml2.uml.Property) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Property) object2).getQualifiedName());
//						} else if (object1 instanceof org.eclipse.uml2.uml.Operation && object2 instanceof org.eclipse.uml2.uml.Operation) {
//							return Objects.equals(((org.eclipse.uml2.uml.Operation) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Operation) object2).getQualifiedName());
//						} else if (object1 instanceof org.eclipse.uml2.uml.Parameter && object2 instanceof org.eclipse.uml2.uml.Parameter) {
//							return Objects.equals(((org.eclipse.uml2.uml.Parameter) object1).getQualifiedName(), ((org.eclipse.uml2.uml.Parameter) object2).getQualifiedName());
//						}
						return super.matchingValues(object1, object2);
					}
				};
			}
		};

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
		Resource resource1 = resourceSet1.getResource(URI.createFileURI("C:\\FZI\\model-tests\\papyrus-models\\model_original.uml"), true);
		System.out.println("LOADED FIRST RESOURCE");

		ResourceSet resourceSet2 = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
		Resource resource2 = resourceSet2.getResource(URI.createFileURI("C:\\FZI\\model-tests\\vitruvius-models\\umloutput_original.uml"), true);
//		Resource resource2 = resourceSet2.getResource(URI.createFileURI("C:\\FZI\\git\\argouml-workaround\\model2.uml"), true);
		System.out.println("LOADED SECOND RESOURCE");

		org.eclipse.uml2.uml.Package argoPackage1 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource1.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get()).getPackagedElements()
				.stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();

		org.eclipse.uml2.uml.Package argoPackage2 = (org.eclipse.uml2.uml.Package) ((org.eclipse.uml2.uml.Package) ((Model) resource2.getContents().stream().filter(v -> (v instanceof Model)).findFirst().get()).getPackagedElements().stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("org")).findFirst().get()).getPackagedElements()
				.stream().filter(v -> (v instanceof org.eclipse.uml2.uml.Package) && ((org.eclipse.uml2.uml.Package) v).getName().equals("argouml")).findFirst().get();

		System.out.println("PACKAGE1: " + argoPackage1);
		System.out.println("PACKAGE2: " + argoPackage2);

		long timeStart = System.currentTimeMillis();

		IComparisonScope scope = new DefaultComparisonScope(argoPackage1, argoPackage2, null);
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
