package tools.vitruv.variability.vave.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.change.description.impl.TransactionalChangeImpl;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.domains.repository.VitruvDomainRepository;
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.propagation.ChangePropagationSpecificationRepository;
import tools.vitruv.framework.userinteraction.InteractionResultProvider;
import tools.vitruv.framework.userinteraction.InternalUserInteractor;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.variability.vave.OperationResult;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.VirtualProductModelInitializer;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.consistency.ConsistencyResult;
import tools.vitruv.variability.vave.consistency.ConsistencyRule;
import tools.vitruv.variability.vave.model.expression.Conjunction;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.expression.ExpressionFactory;
import tools.vitruv.variability.vave.model.expression.Variable;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import tools.vitruv.variability.vave.model.featuremodel.FeaturemodelFactory;
import tools.vitruv.variability.vave.model.featuremodel.ViewCrossTreeConstraint;
import tools.vitruv.variability.vave.model.featuremodel.ViewFeature;
import tools.vitruv.variability.vave.model.featuremodel.ViewTreeConstraint;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.CrossTreeConstraint;
import tools.vitruv.variability.vave.model.vave.DeltaModule;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.model.vave.FeatureRevision;
import tools.vitruv.variability.vave.model.vave.Mapping;
import tools.vitruv.variability.vave.model.vave.Option;
import tools.vitruv.variability.vave.model.vave.SystemRevision;
import tools.vitruv.variability.vave.model.vave.TreeConstraint;
import tools.vitruv.variability.vave.model.vave.VaveFactory;
import tools.vitruv.variability.vave.util.ExpressionUtil;
import tools.vitruv.variability.vave.util.FeatureModelUtil;
import tools.vitruv.variability.vave.util.OptionUtil;

/**
 * Contains an instance of a unified system from the unified conceptual model. Provides implementations of unified operations eD, iD, eP, and iC. At the beginning and end of each operation, registered variability-aware consistency rules are triggered. Each operation returns its own result object to which consistency rules can add their own consistency preservation results.
 */
public class VirtualVaVeModelImpl implements VirtualVaVeModel {

	private final VitruvDomainRepository domainRepository;
	private final tools.vitruv.variability.vave.model.vave.System system;
	private final Resource resource;
	private final Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<ChangePropagationSpecification>();
	private final InteractionResultProvider irp;
	private final VirtualProductModelInitializer vpmi;
	private final Collection<ConsistencyRule> consistencyRules = new ArrayList<>();

	public VirtualVaVeModelImpl(Set<VitruvDomain> domains, Set<ChangePropagationSpecification> changePropagationSpecifications, InteractionResultProvider irp, Path storageFolder) throws IOException {
		this(domains, changePropagationSpecifications, irp, storageFolder, null);
	}

	public VirtualVaVeModelImpl(Set<VitruvDomain> domains, Set<ChangePropagationSpecification> changePropagationSpecifications, InteractionResultProvider irp, Path storageFolder, VirtualProductModelInitializer vpmi) throws IOException {
		this(domains, changePropagationSpecifications, irp, storageFolder, vpmi, null);
	}

	public VirtualVaVeModelImpl(Set<VitruvDomain> domains, Set<ChangePropagationSpecification> changePropagationSpecifications, InteractionResultProvider irp, Path storageFolder, VirtualProductModelInitializer vpmi, Collection<ConsistencyRule> consistencyRules) throws IOException {
		if (Files.exists(storageFolder.resolve("model.vave"))) {
			// load
			this.resource = new XMIResourceImpl();
			File source = new File(storageFolder.resolve("model.vave").toString());
			this.resource.load(new FileInputStream(source), new HashMap<Object, Object>());
			this.system = tools.vitruv.variability.vave.model.vave.System.class.cast(this.resource.getContents().get(0));
		} else {
			// create
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("vave", new XMIResourceFactoryImpl());
			ResourceSet resSet = new ResourceSetImpl();
			this.resource = resSet.createResource(URI.createFileURI(storageFolder.resolve("model.vave").toString()));
			this.system = VaveFactory.eINSTANCE.createSystem();
			this.resource.getContents().add(this.system);
			try {
				this.resource.save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.domainRepository = new VitruvDomainRepositoryImpl(domains);
		this.changePropagationSpecifications.addAll(changePropagationSpecifications);
		this.irp = irp;
		this.vpmi = vpmi;
		if (consistencyRules != null)
			this.consistencyRules.addAll(consistencyRules);
	}

	private void save() throws IOException {
		this.resource.save(Collections.EMPTY_MAP);
	}

	@Override
	public tools.vitruv.variability.vave.model.vave.System getSystem() {
		return this.system;
	}

	private SystemRevision createNewSystemRevision(Collection<SystemRevision> predecessors, int id) {
		SystemRevision newsysrev = VaveFactory.eINSTANCE.createSystemRevision();
		newsysrev.setRevisionID(id);
		for (SystemRevision predecessor : predecessors) {
			predecessor.getSuccessors().add(newsysrev);
			newsysrev.getPredecessors().add(predecessor);
			newsysrev.setRootFeature(predecessor.getRootFeature());
		}
		this.system.getSystemRevisions().add(newsysrev);
		return newsysrev;
	}

	private void transferDomain(SystemRevision predecessor, SystemRevision newsysrev) {
		if (predecessor != null) {
			// enable the same feature options
			newsysrev.getEnablesFeatureOptions().addAll(predecessor.getEnablesFeatureOptions());

			// enable the same constraints
			newsysrev.getEnablesConstraints().addAll(predecessor.getEnablesConstraints());
		}
	}

	private void transferMappings(SystemRevision predecessor, SystemRevision newSystemRevision) {
		if (predecessor != null) {
			// create new mappings (where old system revision is replaced by new system revision) for every existing mapping with the old system revision in its expression
			for (Mapping predecessorMapping : new ArrayList<>(predecessor.getEnablesMappings())) {
				newSystemRevision.getEnablesMappings().add(predecessorMapping);
				this.system.getMappings().add(predecessorMapping);

//				Collection<FeatureOption> mappingOptions = OptionUtil.collect(predecessorMapping.getExpression());
//				if (mappingOptions.contains(predecessor)) {
//					Mapping newMapping = VaveFactory.eINSTANCE.createMapping();
//					newMapping.getDeltaModules().addAll(predecessorMapping.getDeltaModules());
//					newMapping.setExpression(EcoreUtil.copy(predecessorMapping.getExpression()));
//
//					newSystemRevision.getEnablesMappings().add(newMapping);
//				
//					ExpressionSwitch<Object> exprSwitch = new ExpressionSwitch<>() {
//						@Override
//						public <T> Collection<Option> caseNaryExpression(NaryExpression<T> e) {
//							for (Expression<?> childExpression : e.getExpressions())
//								doSwitch(childExpression);
//							return null;
//						}
//
//						@Override
//						public <T> Collection<Option> caseBinaryExpression(BinaryExpression<T> e) {
//							doSwitch(e.getLeft());
//							doSwitch(e.getRight());
//							return null;
//						}
//
//						@Override
//						public <T> Collection<Option> caseUnaryExpression(UnaryExpression<T> e) {
//							doSwitch(e.getExpression());
//							return null;
//						}
//
//						@Override
//						public <T> Collection<Option> caseVariable(Variable<T> v) {
//							if (v.getValue() instanceof SystemRevision && v.getValue().equals(predecessor)) {
//								Variable<SystemRevision> vcast = (Variable<SystemRevision>) v;
//								vcast.setValue(newsysrev);
//							}
//							return null;
//						}
//					};
//					exprSwitch.doSwitch(newMapping.getExpression());
//					this.system.getMappings().add(newMapping);
//				}
			}
		}
	}

	private void triggerConsistencyRule(OperationResult operationResult, ConsistencyRuleTrigger consistencyRuleTrigger) {
		for (ConsistencyRule consistencyRule : this.consistencyRules) {
			ConsistencyResult consistencyResult = consistencyRuleTrigger.trigger(consistencyRule); // consistencyRule.externalizeProductPost();
			if (consistencyResult != null) {
				operationResult.addConsistencyResult(consistencyRule.getClass(), consistencyResult);
			}
		}
	}

	private interface ConsistencyRuleTrigger {
		public ConsistencyResult trigger(ConsistencyRule consistencyRule);
	}

	public ExternalizeProductResult externalizeProduct(Path storageFolder, Configuration configuration) throws IOException {

		// Check preconditions of configuration
		List<SystemRevision> systemRevisions = configuration.getOptions().stream().filter(o -> o instanceof SystemRevision).map(sr -> (SystemRevision) sr).collect(Collectors.toList());
		if (systemRevisions.isEmpty() && !this.system.getSystemRevisions().isEmpty()) {
			throw new RuntimeException("Configuration does not contain a system revision.");
		} else if (systemRevisions.size() > 1) {
			// more than one system revision in configuration -> merge config
			throw new RuntimeException("Configuration contains more than one system revision. System revisions can only be merged via externalizeDomain/internalizeDomain.");
		} else if (systemRevisions.size() == 1) {
			// check if config is complete and valid
			FeatureModel featureModel = this.externalizeDomain(systemRevisions.get(0)).getResult();
			if (!FeatureModelUtil.isComplete(featureModel, configuration)) {
				throw new RuntimeException("Configuration is not complete.");
			}
			if (!FeatureModelUtil.isValid(featureModel, configuration)) {
				throw new RuntimeException("Configuration is not valid.");
			}
		}

		// Setup vitruv
		InternalUserInteractor userInteractor = UserInteractionFactory.instance.createUserInteractor(this.irp);

		if (storageFolder == null)
			throw new IllegalArgumentException("No storage folder was configured!");

		if (userInteractor == null)
			throw new IllegalArgumentException("No user interactor was configured!");

		final ChangePropagationSpecificationRepository changeSpecificationRepository = new ChangePropagationSpecificationRepository(changePropagationSpecifications);
		for (final ChangePropagationSpecification changePropagationSpecification : changePropagationSpecifications) {
			{
				boolean containsSourceDomain = false;
				boolean containsTargetDomain = false;
				for (VitruvDomain domain : this.domainRepository) {
					if (domain.equals(changePropagationSpecification.getSourceDomain())) {
						containsSourceDomain = true;
					}
					if (domain.equals(changePropagationSpecification.getTargetDomain())) {
						containsTargetDomain = true;
					}
				}
				if (!containsTargetDomain)
					throw new IllegalArgumentException("The change propagation specification’s source domain ‹" + changePropagationSpecification.getSourceDomain() + "› has not been configured: " + changePropagationSpecification);
				if (!containsSourceDomain)
					throw new IllegalArgumentException("The change propagation specification’s target domain ‹" + changePropagationSpecification.getTargetDomain() + "› has not been configured: " + changePropagationSpecification);
			}
		}

		for (final ChangePropagationSpecification changePropagationSpecification_1 : this.changePropagationSpecifications) {
			changePropagationSpecification_1.setUserInteractor(userInteractor);
		}

		final VsumFileSystemLayout fileSystemLayout = new VsumFileSystemLayout(storageFolder);
		fileSystemLayout.prepare();

		final VirtualProductModelImpl vsum = new VirtualProductModelImpl(this, configuration, fileSystemLayout, userInteractor, this.domainRepository, changeSpecificationRepository);
		vsum.loadExistingModels();
		// VirtualModelManager.getInstance().putVirtualModel(vsum);

		if (this.vpmi != null)
			this.vpmi.initialize(vsum);

		ExternalizeProductResult externalizeProductResult = new ExternalizeProductResult(vsum);

		// trigger consistency preservation
		this.triggerConsistencyRule(externalizeProductResult, consistencyRule -> consistencyRule.externalizeProductPre(configuration));

		// HERE STARTS THE VAVE STUFF

		// optional: add configuration to unified system
		this.system.getConfigurations().add(configuration);

		if (!systemRevisions.isEmpty()) {
			for (Mapping mapping : systemRevisions.get(0).getEnablesMappings()) {
				System.out.println("Evaluating Mapping: " + mapping.getExpression() + " : " + OptionUtil.collect(mapping.getExpression()).stream().map(o -> {
					if (o instanceof FeatureRevision)
						return (((Feature) ((FeatureRevision) o).eContainer()).getName() + "." + ((FeatureRevision) o).getRevisionID());
					else
						return o.toString();
				}).collect(Collectors.joining(", ")));
				// retrieve only these delta modules whose mapping evaluates to true
				if (mapping.getExpression() == null || ExpressionUtil.eval(mapping.getExpression(), configuration)) {
					System.out.println("Selected Mapping: " + mapping.getExpression() + " : " + OptionUtil.collect(mapping.getExpression()).stream().map(Object::toString).collect(Collectors.joining(", ")));
					for (DeltaModule deltamodule : mapping.getDeltaModules()) {
						// One VitruviusChange per DeltaModule
						VitruviusChange vitruvchange = new TransactionalChangeImpl(deltamodule.getChange());
						vsum.propagateChange(vitruvchange);
					}
				}
			}
		}
		vsum.clearDeltas();

		// trigger consistency preservation
		this.triggerConsistencyRule(externalizeProductResult, consistencyRule -> consistencyRule.externalizeProductPost());

		return externalizeProductResult;
	}

	public InternalizeChangesResult internalizeChanges(VirtualProductModel virtualProductModel, Expression<FeatureOption> expression) throws IOException {
		// NOTE: for now we treat the expression provided by the user as a simple set of features. we ignore negations, disjunctions, feature revisions, etc.

		// NOTE: the following could be achieved with an OCL constraint
		boolean expressionIsWellFormed = ExpressionUtil.validate(expression);
		if (!expressionIsWellFormed) {
			throw new IllegalArgumentException("Expression is not well formed.");
		}

		Collection<Option> options = new ArrayList<Option>(OptionUtil.collect(expression));

		// check if expression is valid
		Configuration configuration = virtualProductModel.getConfiguration();
		List<SystemRevision> sysrevs = configuration.getOptions().stream().filter(o -> o instanceof SystemRevision).map(sr -> (SystemRevision) sr).collect(Collectors.toList());
		if (sysrevs.isEmpty() && !this.system.getSystemRevisions().isEmpty()) {
			throw new RuntimeException("Configuration does not contain a system revision.");
		} else if (sysrevs.size() > 1) {
			// more than one system revision in configuration -> merge config allowed or can system revisions be only be merged in iD
		} else if (sysrevs.size() == 1) {
			// check if expression is valid
			FeatureModel fm = this.externalizeDomain(sysrevs.get(0)).getResult();
			if (!FeatureModelUtil.isComplete(fm, configuration)) {
				throw new RuntimeException("Configuration is not complete.");
			}
			if (!FeatureModelUtil.isValid(fm, configuration)) {
				throw new RuntimeException("Configuration is not valid.");
			}
			if (!FeatureModelUtil.isValid(fm, expression)) {
				throw new RuntimeException("Expression is not valid.");
			}
		}

		// check if configuration implies expression
		// NOTE: as we currently treat an expression only as a simple set of features, we do not need to use SAT here and instead just check if all feature options in expression are contained in configuration
		boolean allContained = true;
		for (Option option : options) {
			if (option instanceof Feature) {
				boolean found = configuration.getOptions().stream().filter(o -> o == option || (o instanceof FeatureRevision && ((FeatureRevision) o).eContainer() == option)).findAny().isPresent();
				if (!found && !((Feature) option).getFeatureRevisions().isEmpty()) {
					allContained = false;
					break;
				}
			} else if (option instanceof FeatureRevision) {
				if (!configuration.getOptions().contains(option)) {
					allContained = false;
					break;
				}
			} else if (option instanceof SystemRevision) {
				throw new RuntimeException("An expression provided to internalizeChanges should not contain System Revisions.");
			}
		}
		// if (!virtualProductModel.getConfiguration().getOption().containsAll(options)) {
		if (!allContained) {
			throw new RuntimeException("Configuration of product does not imply provided expression.");
		}

		InternalizeChangesResult internalizeChangesResult = new InternalizeChangesResult();

		// trigger consistency preservation
		this.triggerConsistencyRule(internalizeChangesResult, consistencyRule -> consistencyRule.internalizeChangesPre(this, expression));

		// create a new system revision and link it to predecessor system revision
		SystemRevision currentSystemRevision = null;
		List<SystemRevision> predecessors = new ArrayList<>();
		if (!this.system.getSystemRevisions().isEmpty()) {
			currentSystemRevision = this.system.getSystemRevisions().get(this.system.getSystemRevisions().size() - 1); // TODO add branch (by using sys rev of product) and merge points
			predecessors.add(currentSystemRevision);
		}
		SystemRevision newSystemRevision = this.createNewSystemRevision(predecessors, currentSystemRevision != null ? currentSystemRevision.getRevisionID() + 1 : 1);

		if (currentSystemRevision != null) {
			// enable same features and feature revisions as the current system revision also in the new system revision, except those for which a new feature revision was created.
			for (FeatureOption featureoption : currentSystemRevision.getEnablesFeatureOptions()) {
				if (!(featureoption instanceof FeatureRevision) || !options.contains(featureoption.eContainer()))
					newSystemRevision.getEnablesFeatureOptions().add(featureoption);
			}

			// enable same constraints as the current system revision
			newSystemRevision.getEnablesConstraints().addAll(currentSystemRevision.getEnablesConstraints());
		}

		this.transferMappings(currentSystemRevision, newSystemRevision);

		// make sure all options in the expression are enabled by the system revision
		// TODO

		// create new feature revisions for features that appear in expression
		List<FeatureRevision> newFeatureRevisions = new ArrayList<>();
		for (Option option : options) {
			if (option instanceof Feature) {
				Feature feature = (Feature) option;
				FeatureRevision featurerev = VaveFactory.eINSTANCE.createFeatureRevision();
				if (feature.getFeatureRevisions().isEmpty()) {
					featurerev.setRevisionID(1);
					feature.getFeatureRevisions().add(featurerev);
				} else {
					// FeatureRevision curfeaturerev = feature.getFeatureRevisions().get(feature.getFeatureRevisions().size() - 1);
					Collection<FeatureRevision> curfeaturerevs = virtualProductModel.getConfiguration().getOptions().stream().filter(o -> o instanceof FeatureRevision && ((Feature) o.eContainer()) == feature).map(o -> (FeatureRevision) o).collect(Collectors.toList());
					featurerev.setRevisionID(feature.getFeatureRevisions().size() + 1);
					featurerev.getPredecessors().addAll(curfeaturerevs);
					for (FeatureRevision curfeaturerev : curfeaturerevs)
						curfeaturerev.getSuccessors().add(featurerev);
					feature.getFeatureRevisions().add(featurerev);
				}
				feature.getFeatureRevisions().add(featurerev); // set feature as container for feature revision
				newFeatureRevisions.add(featurerev);
				newSystemRevision.getEnablesFeatureOptions().add(featurerev); // enable feature revisions by new system revision
				if (!newSystemRevision.getEnablesFeatureOptions().contains(feature))
					newSystemRevision.getEnablesFeatureOptions().add(feature);
			}
		}

		// NOTE: feature revisions in expression also need to be considered in expression of new mapping

		// Every mapping receives a new expression such that it contains the new system revision, the new feature revisions and features, and add expression to mapping.

		{
			// create mapping for new fragments
			Mapping mapping = VaveFactory.eINSTANCE.createMapping();

			// create mapping expression
			Expression<FeatureOption> mappingExpression;
//			if (newFeatureRevisions.isEmpty()) {
//				Variable<Option> srvariable = ExpressionFactory.eINSTANCE.createVariable();
//				srvariable.setValue(newSystemRevision);
//				mappingExpression = srvariable;
//			} else {
			if (!newFeatureRevisions.isEmpty()) {
				// NOTE: for now, we assume the user provides expression consisting only of conjunctions of variables (features)
//				Conjunction<FeatureOption> initialConjunction = ExpressionFactory.eINSTANCE.createConjunction();
//				mappingExpression = initialConjunction;
//				Variable<FeatureOption> newSystemRevisionVariable = ExpressionFactory.eINSTANCE.createVariable();
//				newSystemRevisionVariable.setValue(newSystemRevision);
//				initialConjunction.getExpressions().add(newSystemRevisionVariable);
//				Conjunction<FeatureOption> currentConjunction = initialConjunction;
//				if (newFeatureRevisions.size() > 1) {
//					for (int i = 0; i < newFeatureRevisions.size() - 1; i++) {
//						Conjunction<Option> newConjunction = ExpressionFactory.eINSTANCE.createConjunction();
//						FeatureRevision featureRevision = newFeatureRevisions.get(i);
//
//						Variable<Option> featureRevisionVariable = ExpressionFactory.eINSTANCE.createVariable();
//						featureRevisionVariable.setValue(featureRevision);
//						newConjunction.getExpressions().add(featureRevisionVariable);
//						currentConjunction.getExpressions().add(newConjunction);
//						currentConjunction = newConjunction;
//					}
//				}

				Conjunction<FeatureOption> currentConjunction = ExpressionFactory.eINSTANCE.createConjunction();
				for (FeatureRevision featureRevision : newFeatureRevisions) {
					Variable<FeatureOption> featureRevisionVariable = ExpressionFactory.eINSTANCE.createVariable();
					featureRevisionVariable.setValue(featureRevision);
					currentConjunction.getExpressions().add(featureRevisionVariable);
				}
				mappingExpression = currentConjunction;

//				// add last feature revision
//				Variable<FeatureOption> expression_featurerev = ExpressionFactory.eINSTANCE.createVariable();
//				expression_featurerev.setValue(newFeatureRevisions.get(newFeatureRevisions.size() - 1));
//				currentConjunction.getExpressions().add(expression_featurerev);
			} else {
				mappingExpression = ExpressionFactory.eINSTANCE.createTrue();
			}

			// set expression of mapping
			mapping.setExpression(mappingExpression);

			newSystemRevision.getEnablesMappings().add(mapping);

			// add mapping to us
			this.system.getMappings().add(mapping);

			// set fragments (i.e., deltas recorded in product) of mapping
			for (VitruviusChange change : virtualProductModel.getDeltas()) {
				VitruviusChange unresolvedChange = change.unresolve();
				DeltaModule dm = VaveFactory.eINSTANCE.createDeltaModule();
				for (EChange echange : unresolvedChange.getEChanges()) {
					dm.getChange().add(EcoreUtil.copy(echange)); // NOTE: this copy operation might be unnecessary
				}
				this.system.getDeltaModules().add(dm);
				mapping.getDeltaModules().add(dm);
			}

			// clear deltas in product
			virtualProductModel.clearDeltas();
		}

		// trigger consistency preservation
		this.triggerConsistencyRule(internalizeChangesResult, consistencyRule -> consistencyRule.internalizeChangesPost(this, newSystemRevision));

		this.save();

		return internalizeChangesResult;
	}

	private ViewFeature traverseFeatureModel(Feature feature, SystemRevision systemRevision, Map<Feature, ViewFeature> featureToViewMap) {
//		ViewFeature viewFeature = FeaturemodelFactory.eINSTANCE.createViewFeature();
//		viewFeature.setOriginalFeature(feature);
//		viewFeature.setName(feature.getName());
//		viewFeature.getOriginalRevisions().addAll(feature.getFeatureRevisions().stream().filter(fr -> systemRevision.getEnablesFeatureOptions().contains(fr)).collect(Collectors.toList()));
		ViewFeature viewFeature = featureToViewMap.get(feature);

		for (TreeConstraint treeConstraint : feature.getChildTreeConstraints()) {
			if (systemRevision.getEnablesConstraints().contains(treeConstraint)) {
				ViewTreeConstraint viewTreeConstraint = FeaturemodelFactory.eINSTANCE.createViewTreeConstraint();
				viewTreeConstraint.setOriginalTreeConstraint(treeConstraint);
				viewTreeConstraint.setType(treeConstraint.getType());
				viewFeature.getChildTreeConstraints().add(viewTreeConstraint);

				for (Feature childFeature : treeConstraint.getChildFeatures()) {
					if (systemRevision.getEnablesFeatureOptions().contains(childFeature)) {
						ViewFeature viewChildFeature = traverseFeatureModel(childFeature, systemRevision, featureToViewMap);
						viewTreeConstraint.getChildFeatures().add(viewChildFeature);
					}
				}
			}
		}

		return viewFeature;
	}

	public ExternalizeDomainResult externalizeDomain(SystemRevision systemRevision) {
		if (systemRevision != null && systemRevision.getEnablesFeatureOptions() == null)
			throw new IllegalArgumentException("The given system revision does not enable any options.");

		// if no system revision is given we return an empty feature model
		if (systemRevision == null) {
			FeatureModel featureModel = FeaturemodelFactory.eINSTANCE.createFeatureModel();
			return new ExternalizeDomainResult(featureModel);
		}

		// create feature model view
		FeatureModel featureModel = FeaturemodelFactory.eINSTANCE.createFeatureModel();
		featureModel.setSystemRevision(systemRevision);

		ExternalizeDomainResult externalizeDomainResult = new ExternalizeDomainResult(featureModel);

		// trigger consistency preservation
		this.triggerConsistencyRule(externalizeDomainResult, consistencyRule -> consistencyRule.externalizeDomainPre());

		Map<Feature, ViewFeature> featureToViewMap = new HashMap<>();
		for (FeatureOption featureOption : systemRevision.getEnablesFeatureOptions()) {
			if (featureOption instanceof Feature) {
				ViewFeature viewFeature = FeaturemodelFactory.eINSTANCE.createViewFeature();
				Feature feature = (Feature) featureOption;
				viewFeature.setOriginalFeature(feature);
				viewFeature.setName(feature.getName());
				featureToViewMap.put(feature, viewFeature);
			} else if (featureOption instanceof FeatureRevision) {
				Feature feature = (Feature) featureOption.eContainer();
				ViewFeature viewFeature = featureToViewMap.get(feature);
				if (viewFeature == null) {
					viewFeature = FeaturemodelFactory.eINSTANCE.createViewFeature();
					viewFeature.setOriginalFeature(feature);
					viewFeature.setName(feature.getName());
					featureToViewMap.put(feature, viewFeature);
				}
				viewFeature.getOriginalRevisions().add((FeatureRevision) featureOption);
			}
		}
		featureModel.getFeatureOptions().addAll(featureToViewMap.values());

		if (systemRevision.getRootFeature() != null) {
			ViewFeature viewRootFeature = this.traverseFeatureModel(systemRevision.getRootFeature(), systemRevision, featureToViewMap);
			featureModel.getRootFeatures().add(viewRootFeature);
		}

		for (CrossTreeConstraint crossTreeConstraint : this.system.getConstraints()) {
			if (systemRevision.getEnablesConstraints().contains(crossTreeConstraint)) {
				ViewCrossTreeConstraint viewCrossTreeConstraint = FeaturemodelFactory.eINSTANCE.createViewCrossTreeConstraint();
				viewCrossTreeConstraint.setOriginalCrossTreeConstraint(crossTreeConstraint);
				viewCrossTreeConstraint.setExpression(ExpressionUtil.copy(crossTreeConstraint.getExpression()));
				featureModel.getCrossTreeConstraints().add(viewCrossTreeConstraint);
			}
		}

		externalizeDomainResult.setResult(featureModel);

		// trigger consistency preservation
		this.triggerConsistencyRule(externalizeDomainResult, consistencyRule -> consistencyRule.externalizeDomainPost());

		return externalizeDomainResult;
	}

	private Feature integrateFeatureModelTree(ViewFeature viewFeature, SystemRevision newSystemRevision) {
		if (viewFeature.getOriginalFeature() != null) {
			// NOTE: currently we do not revision the name of a feature. if a feature name is modified, it is therefore modified in all revisions (past and future).
			if (!Objects.equals(viewFeature.getName(), viewFeature.getOriginalFeature().getName())) {
				viewFeature.getOriginalFeature().setName(viewFeature.getName());
			}

			newSystemRevision.getEnablesFeatureOptions().add(viewFeature.getOriginalFeature());
			newSystemRevision.getEnablesFeatureOptions().addAll(viewFeature.getOriginalRevisions());

			// feature already existed. check if its children (i.e., tree constraints) are new or were modified.
			for (ViewTreeConstraint viewTreeConstraint : viewFeature.getChildTreeConstraints()) {
				// if new tree constraint (i.e., does not have original tree constraint) was added in view, create it, add it to system, and enable it by new system revision
				if (viewTreeConstraint.getOriginalTreeConstraint() == null) {
					TreeConstraint newTreeConstraint = VaveFactory.eINSTANCE.createTreeConstraint();
					newTreeConstraint.setType(viewTreeConstraint.getType());
					viewFeature.getOriginalFeature().getChildTreeConstraints().add(newTreeConstraint);
					newSystemRevision.getEnablesConstraints().add(newTreeConstraint);
					// set newly created original tree constraint
					viewTreeConstraint.setOriginalTreeConstraint(newTreeConstraint);

					// recurse into child features, add resulting feature as child to tree constraint
					for (ViewFeature viewChildFeature : viewTreeConstraint.getChildFeatures()) {
						Feature childFeature = this.integrateFeatureModelTree(viewChildFeature, newSystemRevision);
						newTreeConstraint.getChildFeatures().add(childFeature);
					}
				}
				// if existing tree constraint (i.e., has original tree constraint) was modified (i.e., its parent feature or children features or type were modified), create new tree constraint, add it to system, and enable it by new system revision
				else {
					// tree constraint was not modified
					if (viewTreeConstraint.getOriginalTreeConstraint().getChildFeatures().size() == viewTreeConstraint.getChildFeatures().size() && viewTreeConstraint.getOriginalTreeConstraint().getChildFeatures().containsAll(viewTreeConstraint.getChildFeatures().stream().map(vf -> vf.getOriginalFeature()).collect(Collectors.toList()))
							&& viewTreeConstraint.getOriginalTreeConstraint().getParentFeature() == viewFeature.getOriginalFeature() && viewTreeConstraint.getType() == viewTreeConstraint.getOriginalTreeConstraint().getType()) {
						newSystemRevision.getEnablesConstraints().add(viewTreeConstraint.getOriginalTreeConstraint());

						// recurse into child features, add resulting feature as child to tree constraint
						for (ViewFeature viewChildFeature : viewTreeConstraint.getChildFeatures()) {
							this.integrateFeatureModelTree(viewChildFeature, newSystemRevision);
							// the children of the tree constraint were not modified
						}
					}
					// tree constraint was modified
					else {
						TreeConstraint newTreeConstraint = VaveFactory.eINSTANCE.createTreeConstraint();
						newTreeConstraint.setType(viewTreeConstraint.getType());
						viewFeature.getOriginalFeature().getChildTreeConstraints().add(newTreeConstraint);
						newSystemRevision.getEnablesConstraints().add(newTreeConstraint);
						// overwrite original tree constraint of modified view tree constraint
						viewTreeConstraint.setOriginalTreeConstraint(newTreeConstraint);

						// recurse into child features, add resulting feature as child to tree constraint
						for (ViewFeature viewChildFeature : viewTreeConstraint.getChildFeatures()) {
							Feature childFeature = this.integrateFeatureModelTree(viewChildFeature, newSystemRevision);
							newTreeConstraint.getChildFeatures().add(childFeature);
						}
					}
				}
			}

			return viewFeature.getOriginalFeature();
		} else {
			// it is a new feature
			Feature newFeature = VaveFactory.eINSTANCE.createFeature();
			newFeature.setName(viewFeature.getName());
			newSystemRevision.getEnablesFeatureOptions().add(newFeature);
			viewFeature.setOriginalFeature(newFeature);
			this.system.getFeatures().add(newFeature);

			// process its child tree constraints and recurse into child features
			for (ViewTreeConstraint viewTreeConstraint : viewFeature.getChildTreeConstraints()) {
				// regardless if the view tree constraint has an original tree constraint or not, it is always either new (no original) or modified (because its parent is a new feature), thus we must create a new tree constraint in any case
				TreeConstraint newTreeConstraint = VaveFactory.eINSTANCE.createTreeConstraint();
				newTreeConstraint.setType(newTreeConstraint.getType());
				newFeature.getChildTreeConstraints().add(newTreeConstraint);
				newSystemRevision.getEnablesConstraints().add(newTreeConstraint);
				// overwrite original tree constraint of modified view tree constraint
				viewTreeConstraint.setOriginalTreeConstraint(newTreeConstraint);

				// recurse into child features, add resulting feature as child to tree constraint
				for (ViewFeature viewChildFeature : viewTreeConstraint.getChildFeatures()) {
					Feature childFeature = this.integrateFeatureModelTree(viewChildFeature, newSystemRevision);
					newTreeConstraint.getChildFeatures().add(childFeature);
				}
			}

			return newFeature;
		}
	}

	public InternalizeDomainResult internalizeDomain(FeatureModel featureModel) throws IOException {
		// ensure that the feature model has exactly one root feature (i.e., in case of a merge with multiple root features after eD there must only remain one root feature)
		if (featureModel.getRootFeatures().size() > 1)
			throw new RuntimeException("Feature model has more than one root feature!");
//		else if (featureModel.getRootFeatures().isEmpty())
//			throw new RuntimeException("Feature model does not have a root feature!");

		InternalizeDomainResult internalizeDomainResult = new InternalizeDomainResult();

		// trigger consistency preservation
		this.triggerConsistencyRule(internalizeDomainResult, consistencyRule -> consistencyRule.internalizeDomainPre());

		// create a new system revision and link it to predecessor system revision
		List<SystemRevision> predecessors = new ArrayList<>();
		if (featureModel.getSystemRevision() != null)
			predecessors.add(featureModel.getSystemRevision());
		SystemRevision newSystemRevision = this.createNewSystemRevision(predecessors, this.system.getSystemRevisions().size() + 1); // since system revisions cannot be deleted (as we do not yet support eUS) we can simply use the number of system revisions to compute the new id
		this.transferMappings(featureModel.getSystemRevision(), newSystemRevision);

		// integrate any unconstrained features (not in the feature tree)
		for (ViewFeature viewFeature : featureModel.getFeatureOptions()) {
			if (viewFeature.getOriginalFeature() != null) {
				// NOTE: currently we do not revision the name of a feature. if a feature name is modified, it is therefore modified in all revisions (past and future).
				if (!Objects.equals(viewFeature.getName(), viewFeature.getOriginalFeature().getName())) {
					viewFeature.getOriginalFeature().setName(viewFeature.getName());
				}

				newSystemRevision.getEnablesFeatureOptions().add(viewFeature.getOriginalFeature());
				newSystemRevision.getEnablesFeatureOptions().addAll(viewFeature.getOriginalRevisions());
			} else {
				// it is a new feature
				Feature newFeature = VaveFactory.eINSTANCE.createFeature();
				newFeature.setName(viewFeature.getName());
				newSystemRevision.getEnablesFeatureOptions().add(newFeature);
				newSystemRevision.getEnablesFeatureOptions().addAll(viewFeature.getOriginalRevisions());
				viewFeature.setOriginalFeature(newFeature);
				this.system.getFeatures().add(newFeature);
			}
		}

		// integrate feature model tree (i.e., features and tree constraints) of view into system
		if (!featureModel.getRootFeatures().isEmpty())
			newSystemRevision.setRootFeature(this.integrateFeatureModelTree(featureModel.getRootFeatures().get(0), newSystemRevision)); // diff root feature of feature model with root feature of system revision in system

		// integrate cross-tree constraints of view into system
		for (ViewCrossTreeConstraint viewCrossTreeConstraint : featureModel.getCrossTreeConstraints()) {
			// if view ctc has original ctc check if its expressions is structurally equal and enable it by new system revision
			if (viewCrossTreeConstraint.getOriginalCrossTreeConstraint() != null) {
				if (ExpressionUtil.structuralEquivalence(viewCrossTreeConstraint.getOriginalCrossTreeConstraint().getExpression(), viewCrossTreeConstraint.getExpression())) {
					// ctc was not modified, nothing to do
				} else {
					viewCrossTreeConstraint.getOriginalCrossTreeConstraint().setExpression(ExpressionUtil.copy(viewCrossTreeConstraint.getExpression()));
				}
				newSystemRevision.getEnablesConstraints().add(viewCrossTreeConstraint.getOriginalCrossTreeConstraint());
			}
			// if view ctc has no original ctc create a new ctc in system with same expression (copy it) and enable it by new systme revision
			else {
				CrossTreeConstraint newCrossTreeConstraint = VaveFactory.eINSTANCE.createCrossTreeConstraint();
				newCrossTreeConstraint.setExpression(ExpressionUtil.copy(viewCrossTreeConstraint.getExpression()));
				this.system.getConstraints().add(newCrossTreeConstraint);
				newSystemRevision.getEnablesConstraints().add(newCrossTreeConstraint);
			}
		}

		// Trigger consistency preservation
		FeatureModel featureModelAtOldSystemRevision = this.externalizeDomain(featureModel.getSystemRevision()).getResult();
		this.triggerConsistencyRule(internalizeDomainResult, consistencyRule -> consistencyRule.internalizeDomainPost(newSystemRevision, featureModelAtOldSystemRevision, featureModel));

		this.save();

		return internalizeDomainResult;
	}

}
