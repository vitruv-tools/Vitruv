package tools.vitruv.variability.vave.consistency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import tools.vitruv.variability.vave.model.expression.BinaryExpression;
import tools.vitruv.variability.vave.model.expression.Disjunction;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.expression.ExpressionFactory;
import tools.vitruv.variability.vave.model.expression.NaryExpression;
import tools.vitruv.variability.vave.model.expression.Not;
import tools.vitruv.variability.vave.model.expression.UnaryExpression;
import tools.vitruv.variability.vave.model.expression.Variable;
import tools.vitruv.variability.vave.model.expression.util.ExpressionSwitch;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import tools.vitruv.variability.vave.model.featuremodel.FeaturemodelFactory;
import tools.vitruv.variability.vave.model.featuremodel.ViewCrossTreeConstraint;
import tools.vitruv.variability.vave.model.featuremodel.ViewFeature;
import tools.vitruv.variability.vave.model.featuremodel.ViewTreeConstraint;
import tools.vitruv.variability.vave.model.vave.DeltaModule;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.model.vave.FeatureRevision;
import tools.vitruv.variability.vave.model.vave.GroupType;
import tools.vitruv.variability.vave.model.vave.Mapping;
import tools.vitruv.variability.vave.model.vave.Option;
import tools.vitruv.variability.vave.model.vave.SystemRevision;
import tools.vitruv.variability.vave.util.ExpressionToSATConverter;
import tools.vitruv.variability.vave.util.ExpressionUtil;
import tools.vitruv.variability.vave.util.FeatureModelUtil;
import tools.vitruv.variability.vave.util.OptionUtil;

/**
 * Performs consistency preservation from solution space (product) to problem space (feature model) by computing dependencies between deltas and lifting them to constraints between features.
 */
public class DependencyLifting implements ConsistencyRule {

	public class Result implements ConsistencyResult {
		private FeatureModel repairedFeatureModel;

		public Result(FeatureModel repairedFeatureModel) {
			this.repairedFeatureModel = repairedFeatureModel;
		}

		public FeatureModel getRepairedFeatureModel() {
			return this.repairedFeatureModel;
		}
	}

	@Override
	public Result internalizeChangesPost(VirtualVaVeModel vave, SystemRevision newSystemRevision) {
		// After every internalizeChanges, retrieve dependencies between deltas of vave model
		// If dependencies have been detected, find mapped features of deltas
		// check if features already depend on problem space (via implication or exclusion)
		// otherwise, add implication of depending features

		// select relevant mappings for system revision
		List<Mapping> selectedMappings = new ArrayList<>();
		for (Mapping mapping : newSystemRevision.getEnablesMappings()) {
//			Collection<FeatureOption> options = OptionUtil.collect(mapping.getExpression());
//			if (options.contains(newSystemRevision)) {
			selectedMappings.add(mapping);
			System.out.println("SELECTED MAPPING: " + mapping);
//			}
		}

		// compute dependencies between mappings based on their fragments
		Map<String, Set<Mapping>> objectIdInsertedByMappings = new LinkedHashMap<>();
		Map<String, Set<Mapping>> objectIdRemovedByMappings = new LinkedHashMap<>();
		Map<String, Set<Mapping>> objectIdUsedByMappings = new LinkedHashMap<>(); // all use-changes require insert changes of the same id and exclude remove changes

		// traverse every mapping, delta module, change, and check to which there are dependencies
		// example: this change in mapping M1 adds statement to method, other change in other mapping M2 adds method. then M1 requires M2.
		for (Mapping mapping : selectedMappings) {
			for (DeltaModule deltaModule : mapping.getDeltaModules()) {
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
		List<Entry<Mapping, Set<Mapping>>> orderedRequires = requires.entrySet().stream().sorted((entry1, entry2) -> {
			int numberDependenciesEntry1 = entry1.getValue().size();
			if (entry1.getValue().contains(entry1.getKey()))
				numberDependenciesEntry1--;
			int numberDependenciesEntry2 = entry2.getValue().size();
			if (entry2.getValue().contains(entry2.getKey()))
				numberDependenciesEntry2--;
			if (numberDependenciesEntry1 < numberDependenciesEntry2)
				return -1;
			else if (numberDependenciesEntry1 > numberDependenciesEntry2)
				return 1;
			else
				return 0;
		}).collect(Collectors.toList());

		// start by translating the feature model at given system revision to sat

		// externalize domain at system revision to obtain a feature model, add missing constraints to feature model, return feature model
		FeatureModel currentFeatureModel = vave.externalizeDomain(newSystemRevision).getResult();

		// assign integers to every feature that is enabled by system revision
		Collection<FeatureOption> enabledFeatureOptions = newSystemRevision.getEnablesFeatureOptions();
		int currentInt = 0;
		Map<FeatureOption, Integer> featureOptionToIntMap = new HashMap<>();
		for (FeatureOption fo : enabledFeatureOptions) {
			if (fo instanceof Feature) {
				featureOptionToIntMap.put(fo, ++currentInt);
				System.out.println("ASSIGN VALUE " + featureOptionToIntMap.get(fo) + " TO FEATURE " + fo);
			}
		}
		for (FeatureOption fo : enabledFeatureOptions) {
			if (fo instanceof FeatureRevision) {
				Feature feature = (Feature) ((FeatureRevision) fo).eContainer();
				if (featureOptionToIntMap.get(feature) == null) {
					featureOptionToIntMap.put(feature, ++currentInt);
					System.out.println("ASSIGN VALUE " + featureOptionToIntMap.get(feature) + " TO FEATURE " + fo);
				}
				featureOptionToIntMap.put(fo, featureOptionToIntMap.get(feature));
				System.out.println("ASSIGN VALUE " + featureOptionToIntMap.get(fo) + " TO FEATURE REVISION " + fo);
			}
		}
		Map<Option, Integer> optionToIntMap = new HashMap<>(featureOptionToIntMap);
		optionToIntMap.put(newSystemRevision, ++currentInt);

		// compute clauses for mappings
		Map<Mapping, Collection<int[]>> mappingToPosClauses = new HashMap<>();
		Map<Mapping, Collection<int[]>> mappingToNegClauses = new HashMap<>();
		for (Mapping mapping : selectedMappings) {
			// convert positive expression to CNF
			{
				// convert expression to CNF SAT int vector
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
				Expression<FeatureOption> cnfCopyExpr = ExpressionUtil.copy(mapping.getExpression());
				// negate
				Not<FeatureOption> not = ExpressionFactory.eINSTANCE.createNot();
				not.setExpression(cnfCopyExpr);
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

		// Collection<int[]> featureModelClauses = FeatureModelUtil.computeClauses(currentFeatureModel, featureOptionToIntMap);

		// REQUIRES
		// for every dependency, create sat solver instance, add respective clauses, and check if it is satisfiable
		// for (Entry<Mapping, Set<Mapping>> entry : requires.entrySet()) {
		for (Entry<Mapping, Set<Mapping>> entry : orderedRequires) {
			// check if feature model has no root and if current mapping has only a single feature and requires no other mapping but itself
			List<FeatureOption> requiringFeatures = OptionUtil.collect(entry.getKey().getExpression()).stream().filter(o -> o instanceof FeatureOption).collect(Collectors.toList());
			if (currentFeatureModel.getRootFeatures().isEmpty() && requiringFeatures.size() == 1 && (entry.getValue().isEmpty() || entry.getValue().size() == 1 && entry.getValue().contains(entry.getKey()))) {
				Feature requiringFeature = null;
				if (requiringFeatures.get(0) instanceof FeatureRevision)
					requiringFeature = (Feature) requiringFeatures.get(0).eContainer();
				else
					requiringFeature = (Feature) requiringFeatures.get(0);
//				final Feature finalRequiringFeature = requiringFeature;
//				final Feature fmRequiringFeature = fm.getFeatureOptions().stream().filter(fo -> fo instanceof Feature).map(f -> (Feature) f).filter(f -> f.getName().equals(finalRequiringFeature.getName())).findAny().get();
				ViewFeature viewRequiringFeature = FeaturemodelFactory.eINSTANCE.createViewFeature();
				viewRequiringFeature.setOriginalFeature(requiringFeature);
				viewRequiringFeature.setName(requiringFeature.getName());
				// add it as root feature
				currentFeatureModel.getRootFeatures().add(viewRequiringFeature);

				System.out.println("ADDED ROOT FEATURE TO FEATURE MODEL: " + viewRequiringFeature.getName());

				repairHappened = true;
			}

			// order the required mappings such that dependencies to the mappings with most dependencies are processed first (this is a heuristic)
			List<Mapping> orderedRequired = entry.getValue().stream().sorted((entry1, entry2) -> {
				int numberDependenciesEntry1 = requires.get(entry1).size();
				if (requires.get(entry1).contains(entry1))
					numberDependenciesEntry1--;
				int numberDependenciesEntry2 = requires.get(entry2).size();
				if (requires.get(entry2).contains(entry2))
					numberDependenciesEntry2--;
				if (numberDependenciesEntry1 < numberDependenciesEntry2)
					return 1;
				else if (numberDependenciesEntry1 > numberDependenciesEntry2)
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
					for (int[] clause : FeatureModelUtil.computeClauses(currentFeatureModel, featureOptionToIntMap)) {
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
						System.out.println("VIOLATION! MAPPING EXPRESSION " + ExpressionUtil.toString(entry.getKey().getExpression()) + " requires MAPPING EXPRESSION " + ExpressionUtil.toString(requiredMapping.getExpression()) + "!!!");

						// collect options to check how many features exist in expression of requiring and required mapping
						List<FeatureOption> requiredFeatures = OptionUtil.collect(requiredMapping.getExpression()).stream().filter(o -> o instanceof FeatureOption).collect(Collectors.toList());
						// if its only a single feature each and the requiring feature does not have a parent, add it as child of the required feature
						boolean addedTreeConstraint = false;
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

							// NOTE: at this point (after iC) every view feature should have an original feature

							// obtain view features in feature model
							ViewFeature viewRequiredFeature = this.findViewFeatureForFeature(currentFeatureModel.getRootFeatures().get(0), requiredFeature);
							ViewFeature viewRequiringFeature = this.findViewFeatureForFeature(currentFeatureModel.getRootFeatures().get(0), requiringFeature);

							// check if required feature is direct optional child of requiring feature
							if (viewRequiredFeature != null && viewRequiredFeature.getParentTreeConstraint() != null && viewRequiredFeature.getParentTreeConstraint().getParentFeature() != null && viewRequiredFeature.getParentTreeConstraint().getParentFeature().getOriginalFeature() == requiringFeature) {
								// if yes, and it is optional and only child, make it mandatory
								if (viewRequiredFeature.getParentTreeConstraint().getType() == GroupType.OPTIONAL && viewRequiredFeature.getParentTreeConstraint().getChildFeatures().size() == 1) {
									viewRequiredFeature.getParentTreeConstraint().setType(GroupType.MANDATORY);
									addedTreeConstraint = true;
									repairHappened = true;

									System.out.println("MADE OPTIONAL CHILD MANDATORY: " + viewRequiredFeature.getName());
								}
							}

							// check if requiring feature already has a parent. if requiring feature does not have a parent, add it as optional child to required feature.
							if (!addedTreeConstraint && (viewRequiringFeature == null || viewRequiringFeature.getParentTreeConstraint() == null)) { // currentFeatureModel.getTreeConstraints().stream().filter(tc -> tc.getFeature().contains(fmRequiringFeature)).findAny().isEmpty()) {
								// check if required feature is indirect child of requiring feature (to ensure there is no cycle in the fm)
								ViewFeature viewParentFeatureOfRequiredFeature = viewRequiredFeature; // .getParentTreeConstraint().getParentFeature();
								while (viewParentFeatureOfRequiredFeature != null && viewParentFeatureOfRequiredFeature.getOriginalFeature() != requiringFeature) {
									if (viewParentFeatureOfRequiredFeature.getParentTreeConstraint() != null && viewParentFeatureOfRequiredFeature.getParentTreeConstraint().getParentFeature() != null)
										viewParentFeatureOfRequiredFeature = viewParentFeatureOfRequiredFeature.getParentTreeConstraint().getParentFeature();
									else
										viewParentFeatureOfRequiredFeature = null;
								}
								if (viewParentFeatureOfRequiredFeature == null) {
									// if not, then add requiring feature as optional child of required feature
									ViewTreeConstraint newViewTreeConstraint = FeaturemodelFactory.eINSTANCE.createViewTreeConstraint();
									newViewTreeConstraint.setType(GroupType.OPTIONAL);
									if (viewRequiringFeature == null) {
										viewRequiringFeature = FeaturemodelFactory.eINSTANCE.createViewFeature();
										viewRequiringFeature.setOriginalFeature(requiringFeature);
										viewRequiringFeature.setName(requiringFeature.getName());
									}
									newViewTreeConstraint.getChildFeatures().add(viewRequiringFeature);
									if (viewRequiredFeature == null) {
										viewRequiredFeature = FeaturemodelFactory.eINSTANCE.createViewFeature();
										viewRequiredFeature.setOriginalFeature(requiredFeature);
										viewRequiredFeature.setName(requiredFeature.getName());
									}
									viewRequiredFeature.getChildTreeConstraints().add(newViewTreeConstraint);
									addedTreeConstraint = true;
									repairHappened = true;
									System.out.println("ADDED TC: " + requiringFeature + " as optional child of " + requiredFeature);
									System.out.println("ADDED TC: " + entry.getKey() + " as optional child of " + requiredMapping);
								}
							}
						}
						if (!addedTreeConstraint) { // if no tree constraint was added so far, add a cross-tree constraint
							ExpressionSwitch<Expression<FeatureOption>> systemRevisionRemover = new ExpressionSwitch<Expression<FeatureOption>>() {
								public <T> Expression<FeatureOption> caseNaryExpression(NaryExpression<T> object) {
									for (int i = 0; i < object.getExpressions().size(); i++)
										object.getExpressions().set(i, (Expression<T>) doSwitch(object.getExpressions().get(i)));
									return (NaryExpression<FeatureOption>) object;
								};

								public <T> Expression<FeatureOption> caseBinaryExpression(BinaryExpression<T> object) {
									object.setLeft((Expression<T>) doSwitch(object.getLeft()));
									object.setRight((Expression<T>) doSwitch(object.getRight()));
									return (BinaryExpression<FeatureOption>) object;
								};

								public <T> Expression<FeatureOption> caseUnaryExpression(UnaryExpression<T> object) {
									object.setExpression((Expression<T>) doSwitch(object.getExpression()));
									return (UnaryExpression<FeatureOption>) object;
								};

								public <T> Expression<FeatureOption> caseVariable(Variable<T> object) {
									if (object.getValue() instanceof SystemRevision) {
										if (object.getValue() == newSystemRevision) {
											return ExpressionFactory.eINSTANCE.createTrue();
										} else {
											return ExpressionFactory.eINSTANCE.createFalse();
										}
									} else {
										return (Variable<FeatureOption>) object;
									}
								};
							};

							Expression<FeatureOption> leftTerm = ExpressionUtil.simplify(systemRevisionRemover.doSwitch(ExpressionUtil.simplify(entry.getKey().getExpression())));
							Expression<FeatureOption> rightTerm = ExpressionUtil.simplify(systemRevisionRemover.doSwitch(ExpressionUtil.simplify(requiredMapping.getExpression())));

							Disjunction<FeatureOption> disjunction = ExpressionFactory.eINSTANCE.createDisjunction();
							Not<FeatureOption> not = ExpressionFactory.eINSTANCE.createNot();
							not.setExpression(leftTerm); // set expression of requiring mapping without system revision
							disjunction.getExpressions().add(not);
							disjunction.getExpressions().add(rightTerm); // set expression of required mapping without system revision

							ViewCrossTreeConstraint viewCrossTreeConstraint = FeaturemodelFactory.eINSTANCE.createViewCrossTreeConstraint();
							viewCrossTreeConstraint.setExpression(disjunction);

							currentFeatureModel.getCrossTreeConstraints().add(viewCrossTreeConstraint);

							repairHappened = true;

							System.out.println("ADD CTC: " + ExpressionUtil.toString(viewCrossTreeConstraint.getExpression()));
						}
					}
				} catch (ContradictionException e) {
					System.out.println("CONTRADICTION!");
				} catch (TimeoutException e) {
					System.out.println("TIMEOUT!");
				}
			}

			// deal with features that are not part of the feature tree yet and still no requires dependencies are violated
			// if the requiring feature is only one ...
			if (requiringFeatures.size() == 1) {
				Feature requiringFeature = null;
				if (requiringFeatures.get(0) instanceof FeatureRevision)
					requiringFeature = (Feature) requiringFeatures.get(0).eContainer();
				else
					requiringFeature = (Feature) requiringFeatures.get(0);
				if (requiringFeature.getName().equals("Fav"))
					System.out.println("ASF");
				ViewFeature viewRequiringFeature = this.findViewFeatureForFeature(currentFeatureModel.getRootFeatures().get(0), requiringFeature);
				// ... and it does not have a parent ...
				if (viewRequiringFeature == null || viewRequiringFeature.getParentTreeConstraint() == null && requiringFeature != currentFeatureModel.getRootFeatures().get(0).getOriginalFeature()) {
					Feature newParentRequiredFeature = null;
					for (Mapping requiredMapping : orderedRequired) {
						List<FeatureOption> requiredFeatures = OptionUtil.collect(requiredMapping.getExpression()).stream().filter(o -> o instanceof FeatureOption).collect(Collectors.toList());
						if (requiredFeatures.size() == 1) {
							Feature requiredFeature = null;
							if (requiredFeatures.get(0) instanceof FeatureRevision)
								requiredFeature = (Feature) requiredFeatures.get(0).eContainer();
							else
								requiredFeature = (Feature) requiredFeatures.get(0);
							if (requiredFeature != requiringFeature) {
								newParentRequiredFeature = requiredFeature;
								break;
							}
						}
					}
					// ... and exactly one feature is required ...
					if (newParentRequiredFeature != null) {
						// ... add requiring feature as optional child of required feature

						ViewFeature viewRequiredFeature = this.findViewFeatureForFeature(currentFeatureModel.getRootFeatures().get(0), newParentRequiredFeature);

						// check if required feature is indirect child of requiring feature (to ensure there is no cycle in the fm)
						ViewFeature viewParentFeatureOfRequiredFeature = viewRequiredFeature; // .getParentTreeConstraint().getParentFeature();
						while (viewParentFeatureOfRequiredFeature != null && viewParentFeatureOfRequiredFeature.getOriginalFeature() != requiringFeature) {
							if (viewParentFeatureOfRequiredFeature.getParentTreeConstraint() != null && viewParentFeatureOfRequiredFeature.getParentTreeConstraint().getParentFeature() != null)
								viewParentFeatureOfRequiredFeature = viewParentFeatureOfRequiredFeature.getParentTreeConstraint().getParentFeature();
							else
								viewParentFeatureOfRequiredFeature = null;
						}
						if (viewParentFeatureOfRequiredFeature == null) {
							// if not, then add requiring feature as optional child of required feature
							ViewTreeConstraint newViewTreeConstraint = FeaturemodelFactory.eINSTANCE.createViewTreeConstraint();
							newViewTreeConstraint.setType(GroupType.OPTIONAL);
							if (viewRequiringFeature == null) {
								viewRequiringFeature = FeaturemodelFactory.eINSTANCE.createViewFeature();
								viewRequiringFeature.setOriginalFeature(requiringFeature);
								viewRequiringFeature.setName(requiringFeature.getName());
							}
							newViewTreeConstraint.getChildFeatures().add(viewRequiringFeature);
							if (viewRequiredFeature == null) {
								viewRequiredFeature = FeaturemodelFactory.eINSTANCE.createViewFeature();
								viewRequiredFeature.setOriginalFeature(newParentRequiredFeature);
								viewRequiredFeature.setName(newParentRequiredFeature.getName());
							}
							viewRequiredFeature.getChildTreeConstraints().add(newViewTreeConstraint);
							System.out.println("ADDED TC LATER: " + requiringFeature + " as optional child of " + newParentRequiredFeature);
							System.out.println("ADDED TC LATER: " + entry.getKey() + " as optional child of " + newParentRequiredFeature);
						}
					}
				}
			}

		}

		// EXCLUDES
		for (Entry<Mapping, Set<Mapping>> entry : excludes.entrySet()) {
			// for every mapping that is excluded, we check if its absence is guaranteed
			for (Mapping excludedMapping : entry.getValue()) {
				// create new solver instance
				ISolver solver = SolverFactory.newDefault();
				try {
					// add feature model constraints to solver
					for (int[] clause : FeatureModelUtil.computeClauses(currentFeatureModel, featureOptionToIntMap)) {
						solver.addClause(new VecInt(clause));
					}
					for (int[] clause : mappingToPosClauses.get(entry.getKey())) {
						solver.addClause(new VecInt(clause));
					}
					for (int[] clause : mappingToPosClauses.get(excludedMapping)) {
						solver.addClause(new VecInt(clause));
					}
					// check for satisfiability
					if (solver.isSatisfiable()) {
						System.out.println("VIOLATION! MAPPING " + entry.getKey() + " excludes MAPPING " + excludedMapping + "!!!");
						// POSSIBLE IMPROVEMENT: if both mappings have only one feature in their expression, both features have the same parent and are optional, then we can move them into an alternative group
						// POSSIBLE IMPROVEMENT: if above is not the case, then simply add a cross-tree constraint

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
			return new Result(null);
		else
			return new Result(currentFeatureModel);
	}

	private ViewFeature findViewFeatureForFeature(ViewFeature viewFeature, Feature feature) {
		if (viewFeature.getOriginalFeature() == feature)
			return viewFeature;
		else {
			for (ViewTreeConstraint childViewTreeConstraint : viewFeature.getChildTreeConstraints()) {
				for (ViewFeature childViewFeature : childViewTreeConstraint.getChildFeatures()) {
					ViewFeature foundViewFeature = this.findViewFeatureForFeature(childViewFeature, feature);
					if (foundViewFeature != null)
						return foundViewFeature;
				}
			}
			return null;
		}
	}

}
