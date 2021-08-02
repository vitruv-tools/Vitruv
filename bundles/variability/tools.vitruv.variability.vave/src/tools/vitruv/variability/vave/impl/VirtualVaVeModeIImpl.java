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
import tools.vitruv.framework.userinteraction.InternalUserInteractor;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.framework.vsum.VirtualModelManager;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import vavemodel.BinaryExpression;
import vavemodel.Configuration;
import vavemodel.Conjunction;
import vavemodel.Constraint;
import vavemodel.DeltaModule;
import vavemodel.Expression;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.FeatureRevision;
import vavemodel.Mapping;
import vavemodel.Option;
import vavemodel.SystemRevision;
import vavemodel.Term;
import vavemodel.UnaryExpression;
import vavemodel.Variable;
import vavemodel.VavemodelFactory;
import vavemodel.util.VavemodelSwitch;

public class VirtualVaVeModeIImpl implements VirtualVaVeModel {

	private VitruvDomainRepository domainRepository = null;
	private vavemodel.System system;
	private Resource resource;
	private final Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<ChangePropagationSpecification>();

	public VirtualVaVeModeIImpl(Set<VitruvDomain> domains, Set<ChangePropagationSpecification> changePropagationSpecifications, Path storageFolder) throws Exception {

		if (Files.exists(storageFolder.resolve("vavemodel.vave"))) {
			// load
			this.resource = new XMIResourceImpl();
			File source = new File(storageFolder.resolve("vavemodel.vave").toString());
			this.resource.load(new FileInputStream(source), new HashMap<Object, Object>());
			this.system = vavemodel.System.class.cast(this.resource.getContents().get(0));
		} else {
			// create
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
			Map<String, Object> m = reg.getExtensionToFactoryMap();
			m.put("vave", new XMIResourceFactoryImpl());
			ResourceSet resSet = new ResourceSetImpl();
			this.resource = resSet.createResource(URI.createFileURI(storageFolder.resolve("vavemodel.vave").toString()));
			this.system = VavemodelFactory.eINSTANCE.createSystem();
			this.resource.getContents().add(this.system);
			try {
				this.resource.save(Collections.EMPTY_MAP);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.domainRepository = new VitruvDomainRepositoryImpl(domains);

		this.changePropagationSpecifications.addAll(changePropagationSpecifications);
	}

	public void init(Path storageFolder) throws IOException {

	}

	private void save() throws IOException {
		this.resource.save(Collections.EMPTY_MAP);
	}

	@Override
	public vavemodel.System getSystem() {
		return this.system;
	}

	public static <K, V> K getKey(Map<K, V> map, V value) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public VirtualProductModel externalizeProduct(Path storageFolder, Configuration configuration) throws Exception {

		// FIRST WE DO THE VITRUV STUFF

//		final VirtualModelProductImpl vsum = new VirtualModelProductBuilder().withStorageFolder(storageFolder)
//				.withDomainRepository(this.domainRepository)
//				.withUserInteractor(UserInteractionFactory.instance.createUserInteractor(UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null)))
//				.buildAndInitialize();

//		final Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<ChangePropagationSpecification>();
		InternalUserInteractor userInteractor = UserInteractionFactory.instance.createUserInteractor(UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null));

		if (storageFolder == null)
			throw new Exception("No storage folder was configured!");

		if (userInteractor == null)
			throw new Exception("No user interactor was configured!");

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
					throw new Exception("The change propagation specification’s source domain ‹" + changePropagationSpecification.getSourceDomain() + "› has not been configured: " + changePropagationSpecification);
				if (!containsSourceDomain)
					throw new Exception("The change propagation specification’s target domain ‹" + changePropagationSpecification.getTargetDomain() + "› has not been configured: " + changePropagationSpecification);
			}
		}

		for (final ChangePropagationSpecification changePropagationSpecification_1 : this.changePropagationSpecifications) {
			changePropagationSpecification_1.setUserInteractor(userInteractor);
		}

		final VsumFileSystemLayout fileSystemLayout = new VsumFileSystemLayout(storageFolder);
		fileSystemLayout.prepare();

		final VirtualProductModelImpl vsum = new VirtualProductModelImpl(configuration, fileSystemLayout, userInteractor, this.domainRepository, changeSpecificationRepository);
		vsum.loadExistingModels();
		VirtualModelManager.getInstance().putVirtualModel(vsum);

		// HERE STARTS THE VAVE STUFF

		// optional: add configuration to unified system
		// this.system.getConfiguration().add(configuration);

		ExpressionEvaluator ee = new ExpressionEvaluator(configuration);

		for (Mapping mapping : this.system.getMapping()) {
			// retrieve only these delta modules whose mapping evaluates to true
			if (mapping.getExpression() == null || ee.eval(mapping.getExpression())) {
				for (DeltaModule deltamodule : mapping.getDeltamodule()) {
					// EStructuralFeature eStructFeature = deltamodule.eClass().getEStructuralFeature("change");
					// echanges.add((EChange) deltamodule.eGet(eStructFeature));
					// One VitruviusChange per DeltaModule
					VitruviusChange vitruvchange = new TransactionalChangeImpl(deltamodule.getChange());
					vsum.propagateChange(vitruvchange);
				}
			}
		}
		return vsum;
	}

	public void internalizeChanges(VirtualProductModel virtualProductModel, Expression<FeatureOption> expression) throws Exception {
		// NOTE: for now we treat the expression provided by the user as a simple set of features. we ignore negations, disjunctions, feature revisions, etc.

		// NOTE: the following could be achieved with an OCL constraint
		boolean expressionIsWellFormed = new ExpressionValidator().doSwitch(expression);
		if (!expressionIsWellFormed) {
			throw new IllegalArgumentException("Expression is not well formed");
		}

		// create a new system revision and link it to predecessor system revision
		SystemRevision tempcursysrev = null;
		SystemRevision newsysrev = VavemodelFactory.eINSTANCE.createSystemRevision();
		if (system.getSystemrevision().isEmpty()) {
			newsysrev.setRevisionID(1);
		} else {
			tempcursysrev = this.system.getSystemrevision().get(this.system.getSystemrevision().size() - 1); // TODO add branch (by using sys rev of product) and merge points
			tempcursysrev.getSuccessors().add(newsysrev);
			newsysrev.getPredecessors().add(tempcursysrev);
			newsysrev.setRevisionID(tempcursysrev.getRevisionID() + 1);
		}
		this.system.getSystemrevision().add(newsysrev);
		final SystemRevision cursysrev = tempcursysrev;

		// create new feature revisions for features that appear in expression
		Collection<Option> options = new FeatureCollector().doSwitch(expression);
		List<FeatureRevision> newFeatureRevisions = new ArrayList<>();
		for (Option option : options) {
			if (option instanceof Feature) {
				Feature feature = (Feature) option;
				FeatureRevision featurerev = VavemodelFactory.eINSTANCE.createFeatureRevision();
				if (feature.getFeaturerevision().isEmpty()) {
					featurerev.setRevisionID(1);
					feature.getFeaturerevision().add(featurerev);
				} else {
					FeatureRevision curfeaturerev = feature.getFeaturerevision().get(feature.getFeaturerevision().size() - 1);
					featurerev.setRevisionID(featurerev.getRevisionID() + 1);
					featurerev.getPredecessors().add(curfeaturerev);
					curfeaturerev.getSuccessors().add(featurerev);
				}
				feature.getFeaturerevision().add(featurerev); // set feature as container for feature revision
				newFeatureRevisions.add(featurerev);
				newsysrev.getEnablesoptions().add(featurerev); // enable feature revisions by new system revision
			}
		}

		if (cursysrev != null) {
			// enable same features and feature revisions as the current system revision also in the new system revision, except those for which a new feature revision was created.
			for (FeatureOption featureoption : cursysrev.getEnablesoptions()) {
				if (!(featureoption instanceof FeatureRevision) || !options.contains(featureoption.eContainer()))
					newsysrev.getEnablesoptions().add(featureoption);
			}

			// enable the same constraints
			for (Constraint constraint : cursysrev.getEnablesconstraints()) {
				newsysrev.getEnablesconstraints().add(constraint);
			}
		}

		// Every mapping receives a new expression such that it contains the new system revision, the new feature revisions and features, and add expression to mapping.

		// create mapping for new fragments
		Mapping mapping = VavemodelFactory.eINSTANCE.createMapping();

		// create mapping expression
		Expression<Option> mappingExpression;
		if (newFeatureRevisions.isEmpty()) {
			Variable<Option> srvariable = VavemodelFactory.eINSTANCE.createVariable();
			srvariable.setOption(newsysrev);
			mappingExpression = srvariable;
		} else {
			// NOTE: for now, we assume the user provides expression consisting only of conjunctions of variables (features)
			Conjunction<Option> initialConjunction = VavemodelFactory.eINSTANCE.createConjunction();
			mappingExpression = initialConjunction;
			vavemodel.Variable<Option> expression_sysrev = VavemodelFactory.eINSTANCE.createVariable();
			expression_sysrev.setOption(newsysrev);
			initialConjunction.getTerm().add(expression_sysrev);
			Conjunction<Option> currentConjunction = initialConjunction;
			if (newFeatureRevisions.size() > 1) {
				for (int i = 0; i < newFeatureRevisions.size() - 1; i++) {
					Conjunction<Option> newConjunction = VavemodelFactory.eINSTANCE.createConjunction();
					FeatureRevision featrev = newFeatureRevisions.get(i);

					vavemodel.Variable<Option> expression_featurerev = VavemodelFactory.eINSTANCE.createVariable();
					expression_featurerev.setOption(featrev);
					newConjunction.getTerm().add(expression_featurerev);
					currentConjunction.getTerm().add(newConjunction);
					currentConjunction = newConjunction;
				}
			}

			// add last feature revision
			vavemodel.Variable<Option> expression_featurerev = VavemodelFactory.eINSTANCE.createVariable();
			expression_featurerev.setOption(newFeatureRevisions.get(newFeatureRevisions.size() - 1));
			currentConjunction.getTerm().add(expression_featurerev);
		}

		// set expression of mapping
		mapping.setExpression(mappingExpression);

		// add mapping to us
		this.system.getMapping().add(mapping);

		// set fragments (i.e., deltas recorded in product) of mapping
		for (VitruviusChange change : virtualProductModel.getDeltas()) {
			DeltaModule dm = VavemodelFactory.eINSTANCE.createDeltaModule();
			System.out.println("DELTA: " + change);
			for (EChange echange : change.getEChanges()) {
				dm.getChange().add(EcoreUtil.copy(echange));
			}
			this.system.getDeltamodule().add(dm);
			mapping.getDeltamodule().add(dm);
		}

		// clear deltas in product
		virtualProductModel.clearDeltas();

		// create new mappings (where old system revision is replaced by new system revision) for every existing mapping with the old system revision in its expression
		for (Mapping oldMapping : new ArrayList<>(system.getMapping())) {
			Collection<Option> mappingOptions = new FeatureCollector().doSwitch(oldMapping.getExpression());
			if (mappingOptions.contains(cursysrev)) {
				Mapping newMapping = VavemodelFactory.eINSTANCE.createMapping();
				newMapping.getDeltamodule().addAll(oldMapping.getDeltamodule());
				newMapping.setExpression(EcoreUtil.copy(mappingExpression));

				VavemodelSwitch<Object> exprSwitch = new VavemodelSwitch<>() {
					@Override
					public <T extends Option> Collection<Option> caseBinaryExpression(BinaryExpression<T> e) {
						for (Term t : e.getTerm())
							doSwitch(t);
						return null;
					}

					@Override
					public <T extends Option> Collection<Option> caseUnaryExpression(UnaryExpression<T> e) {
						doSwitch(e.getTerm());
						return null;
					}

					@Override
					public <T extends Option> Collection<Option> caseVariable(Variable<T> v) {
						if (v.getOption() instanceof SystemRevision && v.getOption().equals(cursysrev)) {
							Variable<SystemRevision> vcast = (Variable<SystemRevision>) v;
							vcast.setOption(newsysrev);
						}
						return null;
					}
				};
				exprSwitch.doSwitch(newMapping.getExpression());

				this.system.getMapping().add(newMapping);
			}
		}

		// save us
		this.save();
	}

}
