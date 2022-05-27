package tools.vitruv.variability.vave.consistency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.eobject.CreateEObject;
import tools.vitruv.framework.change.echange.eobject.DeleteEObject;
import tools.vitruv.framework.change.echange.eobject.EObjectAddedEChange;
import tools.vitruv.framework.change.echange.eobject.EObjectExistenceEChange;
import tools.vitruv.framework.change.echange.eobject.EObjectSubtractedEChange;
import tools.vitruv.framework.change.echange.feature.FeatureEChange;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.FeatureModel;
import tools.vitruv.variability.vave.util.ExpressionPrinter;
import tools.vitruv.variability.vave.util.ExpressionSimplifier;
import tools.vitruv.variability.vave.util.ExpressionToCNFConverter;
import tools.vitruv.variability.vave.util.ExpressionToSATConverter;
import tools.vitruv.variability.vave.util.OptionsCollector;
import vavemodel.BinaryExpression;
import vavemodel.CrossTreeConstraint;
import vavemodel.DeltaModule;
import vavemodel.Disjunction;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.GroupType;
import vavemodel.Mapping;
import vavemodel.Not;
import vavemodel.Option;
import vavemodel.SystemRevision;
import vavemodel.Term;
import vavemodel.TreeConstraint;
import vavemodel.UnaryExpression;
import vavemodel.Variable;
import vavemodel.VavemodelFactory;
import vavemodel.util.VavemodelSwitch;

public class DependencyLifting implements ConsistencyRule {

	@Override
	public void internalizeChangesPost(VirtualVaVeModel vave, SystemRevision newSysRev) {
		this.liftingDependenciesBetweenFeatures(vave, newSysRev);
	}
	
	public FeatureModel liftingDependenciesBetweenFeatures(VirtualVaVeModel vave, SystemRevision sysrev) {

		// After every internalizeChanges, retrieve dependencies between deltas of vave model
		// If dependencies have been detected, find mapped features of deltas
		// check if features already depend on problem space (via implication or exclusion)
		// otherwise, add implication of depending features

		// start by translating the feature model at given system revision to sat
		FeatureModel fm = vave.externalizeDomain(sysrev);

		// select relevant mappings for sysrev
		List<Mapping> selectedMappings = new ArrayList<>();
		for (Mapping mapping : vave.getSystem().getMapping()) {
			OptionsCollector oc = new OptionsCollector();
			Collection<Option> options = oc.doSwitch(mapping.getExpression());
			if (options.contains(sysrev)) {
				selectedMappings.add(mapping);
				System.out.println("SELECTED MAPPING: " + mapping);
			}
		}

		// assign integers to every feature that is enabled by system revision
		Collection<FeatureOption> enabledFeatureOptions = sysrev.getEnablesoptions();
		int currentInt = 0;
		Map<Option, Integer> optionToIntMap = new HashMap<>();
		optionToIntMap.put(sysrev, ++currentInt);
		for (FeatureOption fo : enabledFeatureOptions) {
			if (fo instanceof Feature) {
				optionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE " + fo);
			}
		}
		for (FeatureOption fo : enabledFeatureOptions) {
			if (fo instanceof FeatureRevision) {
				Feature feature = (Feature) ((FeatureRevision) fo).eContainer();
				if (optionToIntMap.get(feature) == null) {
					optionToIntMap.put(feature, ++currentInt);
					System.out.println("ASSIGN VALUE " + optionToIntMap.get(feature) + " TO FEATURE " + fo);
				}
				optionToIntMap.put(fo, optionToIntMap.get(feature));
				System.out.println("ASSIGN VALUE " + optionToIntMap.get(fo) + " TO FEATURE REVISION " + fo);
			}
		}
		Map<FeatureOption, Integer> fmOptionToIntMap = new HashMap<>();
		for (Entry<Option, Integer> entry : optionToIntMap.entrySet()) {
			if (entry.getKey() instanceof Feature) {
				Feature fmFeature = fm.getFeatureOptions().stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(((Feature) entry.getKey()).getName())).findAny().get();
				fmOptionToIntMap.put(fmFeature, entry.getValue());
			} else if (entry.getKey() instanceof FeatureRevision) {
				FeatureRevision fmFeatureRevision = fm.getFeatureOptions().stream().filter(fo -> fo instanceof FeatureRevision).map(fr -> (FeatureRevision) fr).filter(fr -> ((Feature) fr.eContainer()).getName().equals(((Feature) ((FeatureRevision) entry.getKey()).eContainer()).getName()) && fr.getRevisionID() == ((FeatureRevision) entry.getKey()).getRevisionID()).findAny().get();
				fmOptionToIntMap.put(fmFeatureRevision, entry.getValue());
			}
		}

		// compute clauses for mappings
		Map<Mapping, Collection<int[]>> mappingToPosClauses = new HashMap<>();
		Map<Mapping, Collection<int[]>> mappingToNegClauses = new HashMap<>();
		for (Mapping mapping : selectedMappings) {
			// convert positive expression to CNF
			{
				// convert expression to CNF
//				ExpressionToCNFConverter ec = new ExpressionToCNFConverter();
//				Expression<? extends Option> cnfExpr = ec.convert(mapping.getExpression());
				// convert CNF to SAT int vector
				ExpressionToSATConverter e2sc = new ExpressionToSATConverter();
				e2sc.setIntsForOptions(optionToIntMap);
				Collection<int[]> clauses = e2sc.convertExpr2Sat(mapping.getExpression());
				mappingToPosClauses.put(mapping, clauses);

				System.out.println("CLAUSES FOR MAPPING " + mapping);
				for (int[] clause : clauses) {
					String modelString = "";
					for (int val : clause)
						modelString += val + ", ";
					System.out.println("MAPPING CLAUSE: " + modelString);
				}
			}
			// convert negative expression to CNF
			{
				// copy expression by creating CNF (we are actually only interested in the copy, not in the cnf conversion)
				ExpressionToCNFConverter ec = new ExpressionToCNFConverter();
				Term<Option> cnfCopyExpr = ec.convert(mapping.getExpression());
				// negate CNF
				Not<Option> not = VavemodelFactory.eINSTANCE.createNot();
				not.setTerm(cnfCopyExpr);
//				// convert expression to CNF
//				ExpressionToCNFConverter ec2 = new ExpressionToCNFConverter();
//				Expression<? extends Option> cnfExpr = ec2.convert(not);
				// convert expression to CNF SAT int vector
				ExpressionToSATConverter e2sc = new ExpressionToSATConverter();
				e2sc.setIntsForOptions(optionToIntMap);
				Collection<int[]> clauses = e2sc.convertExpr2Sat(not);
				mappingToNegClauses.put(mapping, clauses);

				System.out.println("CLAUSES FOR NEG MAPPING " + mapping);
				for (int[] clause : clauses) {
					String modelString = "";
					for (int val : clause)
						modelString += val + ", ";
					System.out.println("NEG MAPPING CLAUSE: " + modelString);
				}
			}
		}

		// compute dependencies between mappings based on their fragments
		Map<String, Set<Mapping>> objectIdInsertedByMappings = new LinkedHashMap<>();
		Map<String, Set<Mapping>> objectIdRemovedByMappings = new LinkedHashMap<>();
		Map<String, Set<Mapping>> objectIdUsedByMappings = new LinkedHashMap<>(); // all use-changes require insert changes of the same id and exclude remove changes

		// traverse every mapping, delta module, change, and check to which there are dependencies
		// example: this change in mapping M1 adds statement to method, other change in other mapping M2 adds method. then M1 requires M2.
		for (Mapping mapping : selectedMappings) {
			for (DeltaModule deltaModule : mapping.getDeltamodule()) {
				for (EChange change : deltaModule.getChange()) {
					// for every change, check its type, affected value, new value, old value, etc.
					if (change instanceof EObjectExistenceEChange<?>) {
						// creates or deletes an affectedEObject
						if (change instanceof CreateEObject) {
							Set<Mapping> changeList = objectIdInsertedByMappings.get(((CreateEObject<?>) change).getAffectedEObjectID());
							if (changeList == null) {
								changeList = new HashSet<>();
								objectIdInsertedByMappings.put(((CreateEObject<?>) change).getAffectedEObjectID(), changeList);
							}
							changeList.add(mapping);
						} else if (change instanceof DeleteEObject) {
							Set<Mapping> changeList = objectIdRemovedByMappings.get(((DeleteEObject<?>) change).getAffectedEObjectID());
							if (changeList == null) {
								changeList = new HashSet<>();
								objectIdRemovedByMappings.put(((DeleteEObject<?>) change).getAffectedEObjectID(), changeList);
							}
							changeList.add(mapping);
						}
					} else if (change instanceof FeatureEChange<?, ?>) {
						// changes reference or attribute of affected eobject
						Set<Mapping> changeList = objectIdUsedByMappings.get(((FeatureEChange<?, ?>) change).getAffectedEObjectID());
						if (changeList == null) {
							changeList = new HashSet<>();
							objectIdUsedByMappings.put(((FeatureEChange<?, ?>) change).getAffectedEObjectID(), changeList);
						}
						changeList.add(mapping);
					}
					if (change instanceof EObjectSubtractedEChange<?>) {
						// removes eobject from resource or reference
						Set<Mapping> changeList = objectIdUsedByMappings.get(((EObjectSubtractedEChange<?>) change).getOldValueID());
						if (changeList == null) {
							changeList = new HashSet<>();
							objectIdUsedByMappings.put(((EObjectSubtractedEChange<?>) change).getOldValueID(), changeList);
						}
						changeList.add(mapping);
					}
					if (change instanceof EObjectAddedEChange<?>) {
						// inserts eobject into resource or reference
						Set<Mapping> changeList = objectIdUsedByMappings.get(((EObjectAddedEChange<?>) change).getNewValueID());
						if (changeList == null) {
							changeList = new HashSet<>();
							objectIdUsedByMappings.put(((EObjectAddedEChange<?>) change).getNewValueID(), changeList);
						}
						changeList.add(mapping);
					}

				}
			}
		}

		System.out.println("INSERTIONS: " + objectIdInsertedByMappings.size());
		System.out.println("REMOVALS: " + objectIdRemovedByMappings.size());
		System.out.println("USES: " + objectIdUsedByMappings.size());

		System.out.println("INSERTIONS LIST: " + objectIdInsertedByMappings);
		System.out.println("REMOVALS LIST: " + objectIdRemovedByMappings);
		System.out.println("USES LIST: " + objectIdUsedByMappings);

		Map<Mapping, Set<Mapping>> requires = new LinkedHashMap<>();
		Map<Mapping, Set<Mapping>> excludes = new LinkedHashMap<>();

		for (Entry<String, Set<Mapping>> entry : objectIdUsedByMappings.entrySet()) {
			for (Mapping mapping : entry.getValue()) {
				Set<Mapping> requiresMappings = requires.get(mapping);
				if (requiresMappings == null) {
					requiresMappings = new HashSet<>();
					requires.put(mapping, requiresMappings);
				}
				Set<Mapping> excludesMappings = excludes.get(mapping);
				if (excludesMappings == null) {
					excludesMappings = new HashSet<>();
					excludes.put(mapping, excludesMappings);
				}
				Set<Mapping> createMappings = objectIdInsertedByMappings.get(entry.getKey()); // set of mappings that create the object
				if (createMappings != null)
					requiresMappings.addAll(createMappings); // the current mapping requires all mappings that create the used eobject
				Set<Mapping> deleteMappings = objectIdRemovedByMappings.get(entry.getKey()); // set of mappings that delete the object
				if (deleteMappings != null)
					excludesMappings.addAll(deleteMappings); // the current mapping excludes all mappings that delete the used eobject
			}
		}

		System.out.println("NUMBER OF REQUIRES DEPENDENCIES: " + requires);
		System.out.println("NUMBER OF EXCLUDES DEPENDENCIES: " + excludes);

		boolean repairHappened = false;

		// order requires dependencies such that the mappings with the fewest dependencies are processed first.
		List<Entry<Mapping, Set<Mapping>>> orderedRequires = requires.entrySet().stream().sorted((e1, e2) -> {
			int numE1Deps = e1.getValue().size();
			if (e1.getValue().contains(e1.getKey()))
				numE1Deps--;
			int numE2Deps = e2.getValue().size();
			if (e2.getValue().contains(e2.getKey()))
				numE2Deps--;
			if (numE1Deps < numE2Deps)
				return -1;
			else if (numE1Deps > numE2Deps)
				return 1;
			else
				return 0;
		}).collect(Collectors.toList());

		// for every dependency, create sat solver instance, add respective clauses, and check if it is satisfiable
		// for (Entry<Mapping, Set<Mapping>> entry : requires.entrySet()) {
		for (Entry<Mapping, Set<Mapping>> entry : orderedRequires) {
			// check if feature model has no root and if current mapping has only a single feature and requires no other mapping but itself
			OptionsCollector oc = new OptionsCollector();
			List<Option> requiringFeatures = oc.doSwitch(entry.getKey().getExpression()).stream().filter(o -> o instanceof FeatureOption).collect(Collectors.toList());
			if (fm.getRootFeature() == null && requiringFeatures.size() == 1 && (entry.getValue().isEmpty() || entry.getValue().size() == 1 && entry.getValue().contains(entry.getKey()))) {
				Feature requiringFeature = null;
				if (requiringFeatures.get(0) instanceof FeatureRevision)
					requiringFeature = (Feature) requiringFeatures.get(0).eContainer();
				else
					requiringFeature = (Feature) requiringFeatures.get(0);
				final Feature finalRequiringFeature = requiringFeature;
				final Feature fmRequiringFeature = fm.getFeatureOptions().stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(finalRequiringFeature.getName())).findAny().get();
				// add it as root feature
				fm.setRootFeature(fmRequiringFeature);

				repairHappened = true;
			}
			// order the required mappings such that dependencies to the mappings with most dependencies are processed first (this is a heuristic)
			List<Mapping> orderedRequired = entry.getValue().stream().sorted((e1, e2) -> {
				int numE1Deps = requires.get(e1).size();
				if (requires.get(e1).contains(e1))
					numE1Deps--;
				int numE2Deps = requires.get(e2).size();
				if (requires.get(e2).contains(e2))
					numE2Deps--;
				if (numE1Deps < numE2Deps)
					return 1;
				else if (numE1Deps > numE2Deps)
					return -1;
				else
					return 0;
			}).collect(Collectors.toList());
			// for every mapping that is required, we check if its presence is guaranteed
			for (Mapping requiredMapping : orderedRequired) {
				// create new solver instance
				ISolver solver = SolverFactory.newDefault();
				try {
					// add feature model constraints to solver
					for (int[] clause : computeFMClauses(fm, fmOptionToIntMap)) {
						solver.addClause(new VecInt(clause));
					}
					// add requiring mapping positively
					for (int[] clause : mappingToPosClauses.get(entry.getKey())) {
						solver.addClause(new VecInt(clause));
					}
					// add required mapping negatively
					for (int[] clause : mappingToNegClauses.get(requiredMapping)) {
						solver.addClause(new VecInt(clause));
					}
					// check for satisfiability
					if (solver.isSatisfiable()) {
						System.out.println("VIOLATION! MAPPING " + entry.getKey() + " requires MAPPING " + requiredMapping + "!!!");
						// externalize domain at sysrev to obtain a feature model, add missing constraints to feature model, return feature model
						// use option collector to check how many features exist in expression of requiring and required mapping.
//						// we start with the requiring mapping
//						OptionsCollector oc = new OptionsCollector();
//						List<Option> requiringFeatures = oc.doSwitch(entry.getKey().getExpression()).stream().filter(o -> o instanceof FeatureOption).collect(Collectors.toList());
						// next we do the required mapping
						OptionsCollector oc2 = new OptionsCollector();
						List<Option> requiredFeatures = oc2.doSwitch(requiredMapping.getExpression()).stream().filter(o -> o instanceof FeatureOption).collect(Collectors.toList());
						// if its only a single feature each and the requiring feature does not have a parent, add it as child of the required feature
						boolean addedTC = false;
						if (requiringFeatures.size() == 1 && requiredFeatures.size() == 1) {
							Feature requiringFeature = null;
							if (requiringFeatures.get(0) instanceof FeatureRevision)
								requiringFeature = (Feature) requiringFeatures.get(0).eContainer();
							else
								requiringFeature = (Feature) requiringFeatures.get(0);
							Feature requiredFeature = null;
							if (requiredFeatures.get(0) instanceof FeatureRevision)
								requiredFeature = (Feature) requiredFeatures.get(0).eContainer();
							else
								requiredFeature = (Feature) requiredFeatures.get(0);
							// if requiring feature does not have a parent, add it as optional child to required feature
							final Feature finalRequiringFeature = requiringFeature;
							final Feature finalRequiredFeature = requiredFeature;
							final Feature fmRequiringFeature = fm.getFeatureOptions().stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(finalRequiringFeature.getName())).findAny().get();
							final Feature fmRequiredFeature = fm.getFeatureOptions().stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(finalRequiredFeature.getName())).findAny().get();

							Feature parent = getParent(fm, fmRequiredFeature);
							// check if required feature is direct optional child of requiring feature
							if (parent == fmRequiringFeature) {
								Optional<TreeConstraint> of = fm.getTreeConstraints().stream().filter(tc -> tc.getFeature().contains(fmRequiredFeature)).findAny();
								if (of.isPresent()) {
									TreeConstraint tc = of.get();
									if (tc.getType() == GroupType.ORNONE && tc.getFeature().size() == 1) {
										tc.setType(GroupType.XOR);
										addedTC = true;
										repairHappened = true;
									}
								}
							}

							// check if it already has a parent
							if (!addedTC && fm.getTreeConstraints().stream().filter(tc -> tc.getFeature().contains(fmRequiringFeature)).findAny().isEmpty()) {
								// check if required feature is indirect child of requiring feature (to ensure there is no cycle in the fm)
								while (parent != null && parent != fmRequiredFeature) {
									parent = getParent(fm, parent);
								}
								if (parent == null) {
									TreeConstraint tc = VavemodelFactory.eINSTANCE.createTreeConstraint();
									tc.setType(GroupType.ORNONE);
									tc.getFeature().add(fmRequiringFeature);
									fmRequiredFeature.getTreeconstraint().add(tc);
									fm.getTreeConstraints().add(tc);
									addedTC = true;
									repairHappened = true;
									System.out.println("ADDED TC: " + fmRequiringFeature + " as child of " + fmRequiredFeature);
									System.out.println("ADDED TC: " + entry.getKey() + " as child of " + requiredMapping);
								}
							}
						}
						if (!addedTC) {
							// add CTC
							CrossTreeConstraint ctc = VavemodelFactory.eINSTANCE.createCrossTreeConstraint();
							fm.getCrossTreeConstraints().add(ctc);
							Disjunction<FeatureOption> disjunction = VavemodelFactory.eINSTANCE.createDisjunction();
							Not<FeatureOption> not = VavemodelFactory.eINSTANCE.createNot();

							VavemodelSwitch<Term<FeatureOption>> systemRevisionRemover = new VavemodelSwitch<Term<FeatureOption>>() {
								public <T extends Option> vavemodel.Term<FeatureOption> caseBinaryExpression(vavemodel.BinaryExpression<T> object) {
									object.getTerm().set(0, (Term<T>) doSwitch(object.getTerm().get(0)));
									object.getTerm().set(1, (Term<T>) doSwitch(object.getTerm().get(1)));
									return (BinaryExpression<FeatureOption>) object;
								};

								public <T extends Option> vavemodel.Term<FeatureOption> caseUnaryExpression(vavemodel.UnaryExpression<T> object) {
									object.setTerm((Term<T>) doSwitch(object.getTerm()));
									return (UnaryExpression<FeatureOption>) object;
								};

								public <T extends Option> vavemodel.Term<FeatureOption> caseVariable(vavemodel.Variable<T> object) {
									if (object.getOption() instanceof SystemRevision) {
										if (object.getOption() == sysrev) {
											return VavemodelFactory.eINSTANCE.createTrue();
										} else {
											return VavemodelFactory.eINSTANCE.createFalse();
										}
									} else {
										if (object.getOption() instanceof Feature) {
											Feature fmFeature = fm.getFeatureOptions().stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(((Feature) object.getOption()).getName())).findAny().get();
											object.setOption((T) fmFeature);
										} else if (object.getOption() instanceof FeatureRevision) {
											Optional<FeatureRevision> fmFeatureRevisionOpt = fm.getFeatureOptions().stream().filter(fo -> fo instanceof FeatureRevision).map(fr -> (FeatureRevision) fr)
													.filter(fr -> ((Feature) fr.eContainer()).getName().equals(((Feature) ((FeatureRevision) object.getOption()).eContainer()).getName()) && fr.getRevisionID() >= ((FeatureRevision) object.getOption()).getRevisionID()).findAny();
											if (fmFeatureRevisionOpt.isEmpty())
												System.out.println("ERROR");
											FeatureRevision fmFeatureRevision = fmFeatureRevisionOpt.get();
											object.setOption((T) fmFeatureRevision);
										}
										return (Variable<FeatureOption>) object;
									}
								};
							};

							Term<FeatureOption> leftTerm = new ExpressionSimplifier<FeatureOption>().doSwitch(systemRevisionRemover.doSwitch(new ExpressionSimplifier<Option>().doSwitch(entry.getKey().getExpression())));
							Term<FeatureOption> rightTerm = new ExpressionSimplifier<FeatureOption>().doSwitch(systemRevisionRemover.doSwitch(new ExpressionSimplifier<Option>().doSwitch(requiredMapping.getExpression())));

							not.setTerm(leftTerm); // set expression of requiring mapping without system revision
							disjunction.getTerm().add(not);
							disjunction.getTerm().add(rightTerm); // set expression of required mapping without system revision

							ctc.setExpression(disjunction);

							repairHappened = true;

							System.out.println("ADD CTC: " + new ExpressionPrinter().doSwitch(ctc.getExpression()));
						}
					}
				} catch (ContradictionException e) {
					System.out.println("CONTRADICTION!");
				} catch (TimeoutException e) {
					System.out.println("TIMEOUT!");
				}
			}
		}
		for (Entry<Mapping, Set<Mapping>> entry : excludes.entrySet()) {
			// for every mapping that is excluded, we check if its absence is guaranteed
			for (Mapping excludedMapping : entry.getValue()) {
				// create new solver instance
				ISolver solver = SolverFactory.newDefault();
				try {
					// add feature model constraints to solver
					for (int[] clause : computeFMClauses(fm, fmOptionToIntMap)) {
						solver.addClause(new VecInt(clause));
					}
					// add requiring mapping positively
					for (int[] clause : mappingToPosClauses.get(entry.getKey())) {
						solver.addClause(new VecInt(clause));
					}
					// add required mapping negatively
					for (int[] clause : mappingToPosClauses.get(excludedMapping)) {
						solver.addClause(new VecInt(clause));
					}
					// check for satisfiability
					if (solver.isSatisfiable()) {
						System.out.println("VIOLATION! MAPPING " + entry.getKey() + " excludes MAPPING " + excludedMapping + "!!!");
						// if both mappings have only one feature in their expression, both features have the same parent and are optional, then we can move them into an alternative group
						// TODO
						// if above is not the case, then simply add a ctc
						// TODO

						repairHappened = true;
					}
				} catch (ContradictionException e) {
					System.out.println("CONTRADICTION!");
				} catch (TimeoutException e) {
					System.out.println("TIMEOUT!");
				}
			}
		}

		if (!repairHappened)
			return null;
		else
			return fm;
	}

	private static Feature getParent(final FeatureModel fm, final Feature f) {
		Optional<TreeConstraint> of = fm.getTreeConstraints().stream().filter(tc -> tc.getFeature().contains(f)).findAny();
		if (of.isPresent()) {
			return (Feature) of.get().eContainer();
		} else {
			return null;
		}
	}

	private static Collection<int[]> computeFMClauses(final FeatureModel fm, Map<FeatureOption, Integer> featureOptionToIntMap) {
		Collection<int[]> fmClauses = new ArrayList<>();
		Set<TreeConstraint> treeconstr = fm.getTreeConstraints();
		Set<CrossTreeConstraint> crosstreeconstr = fm.getCrossTreeConstraints();
//		Map<FeatureOption, Integer> featureOptionToIntMap = new HashMap<>();
//		int i = 0;
//		for (FeatureOption fo : fm.getFeatureOptions()) {
//			featureOptionToIntMap.put(fo, i);
//			i++;
//		}
		for (Option fo : featureOptionToIntMap.keySet()) {
			if (fo instanceof FeatureRevision) {
				// add clause that says "feature revision implies feature"
				// also the other way round?
				// this is not needed!
			} else if (fo instanceof Feature) {
				// do nothing?
				// do nothing!
			}
		}
		for (TreeConstraint tc : treeconstr) {
			// first, children imply parents
			for (Feature feature : tc.getFeature()) {
				fmClauses.add(new int[] { -featureOptionToIntMap.get(feature), featureOptionToIntMap.get(tc.eContainer()) });
			}
			// second, depending on tc type, add relation between siblings and/or parent
			if (tc.getType() == GroupType.ORNONE) {
				// do nothing (only parent implies child)
			} else if (tc.getType() == GroupType.OR) {
				ArrayList<Integer> parentchild = new ArrayList<>();
				ArrayList<Integer> siblings = new ArrayList<>();
				parentchild.add(-featureOptionToIntMap.get(tc.eContainer()));
				for (Feature feature : tc.getFeature()) {
					parentchild.add(featureOptionToIntMap.get(feature));
//					siblings.add(featureOptionToIntMap.get(feature));
				}
				fmClauses.add(parentchild.stream().mapToInt(val -> val).toArray());
//				fmClauses.add(siblings.stream().mapToInt(val -> val).toArray());
			} else if (tc.getType() == GroupType.XOR) { // !D v !E // mandatory or alternative group
				for (int i = 0; i < tc.getFeature().size(); i++) {
					for (int j = i + 1; j < tc.getFeature().size(); j++) {
						ArrayList<Integer> literals = new ArrayList<>();
						literals.add(-featureOptionToIntMap.get(tc.getFeature().get(i)));
						literals.add(-featureOptionToIntMap.get(tc.getFeature().get(j)));
						fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
					}
				}
				ArrayList<Integer> literals = new ArrayList<>();
				literals.add(-featureOptionToIntMap.get(tc.eContainer()));
				for (Feature feature : tc.getFeature()) {
					literals.add(featureOptionToIntMap.get(feature));
				}
				fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
			} else if (tc.getType() == GroupType.XORNONE) { // at most one, no parent-child relation required
				for (int i = 0; i < tc.getFeature().size(); i++) {
					for (int j = i + 1; j < tc.getFeature().size(); j++) {
						ArrayList<Integer> literals = new ArrayList<>();
						literals.add(-featureOptionToIntMap.get(tc.getFeature().get(i)));
						literals.add(-featureOptionToIntMap.get(tc.getFeature().get(j)));
						fmClauses.add(literals.stream().mapToInt(val -> val).toArray());
					}
				}
			}
		}
		for (CrossTreeConstraint ctc : crosstreeconstr) {
			// add clause for ctc
			ExpressionToSATConverter e2sc = new ExpressionToSATConverter();
			e2sc.setIntsForOptions(featureOptionToIntMap.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())));
			Collection<int[]> ctcCnf = e2sc.convertExpr2Sat(ctc.getExpression());
			fmClauses.addAll(ctcCnf);
		}
//		for (int[] clause : fmClauses) {
//			String modelString = "";
//			for (int val : clause)
//				modelString += val + ", ";
//			System.out.println("FM CLAUSE: " + modelString);
//		}
		return fmClauses;
	}

}
