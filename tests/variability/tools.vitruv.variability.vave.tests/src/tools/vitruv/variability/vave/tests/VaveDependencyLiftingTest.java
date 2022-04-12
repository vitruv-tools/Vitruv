package tools.vitruv.variability.vave.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.ISolver;
import org.sat4j.tools.ModelIterator;

import allElementTypes.ValueBased;
import edu.kit.ipd.sdq.commons.util.org.eclipse.emf.ecore.resource.ResourceSetUtil;
import tools.vitruv.framework.change.description.TransactionalChange;
import tools.vitruv.framework.change.recording.ChangeRecorder;
import tools.vitruv.framework.domains.VitruvDomain;
import tools.vitruv.framework.propagation.ChangePropagationSpecification;
import tools.vitruv.framework.userinteraction.UserInteractionFactory;
import tools.vitruv.testutils.RegisterMetamodelsInStandalone;
import tools.vitruv.testutils.TestLogging;
import tools.vitruv.testutils.TestProject;
import tools.vitruv.testutils.TestProjectManager;
import tools.vitruv.testutils.domains.AllElementTypesDomain;
import tools.vitruv.testutils.domains.AllElementTypesDomainProvider;
import tools.vitruv.testutils.metamodels.AllElementTypesCreators;
import tools.vitruv.variability.vave.VirtualProductModel;
import tools.vitruv.variability.vave.VirtualVaVeModel;
import tools.vitruv.variability.vave.impl.DependencyLifting;
import tools.vitruv.variability.vave.impl.FeatureModel;
import tools.vitruv.variability.vave.impl.VirtualVaVeModelImpl;
import tools.vitruv.variability.vave.tests.VaveTest.RedundancyChangePropagationSpecification;
import tools.vitruv.variability.vave.util.ExpressionToCNFConverter;
import tools.vitruv.variability.vave.util.ExpressionToSATConverter;
import tools.vitruv.variability.vave.util.ExpressionValidator;
import vavemodel.BinaryExpression;
import vavemodel.Configuration;
import vavemodel.Conjunction;
import vavemodel.CrossTreeConstraint;
import vavemodel.Disjunction;
import vavemodel.Expression;
import vavemodel.Feature;
import vavemodel.FeatureOption;
import vavemodel.Not;
import vavemodel.Option;
import vavemodel.Term;
import vavemodel.TreeConstraint;
import vavemodel.UnaryExpression;
import vavemodel.Variable;
import vavemodel.VavemodelFactory;

@ExtendWith({ TestProjectManager.class, TestLogging.class, RegisterMetamodelsInStandalone.class })
public class VaveDependencyLiftingTest {

	private URI createTestModelResourceUri(final String suffix, Path projectFolder) {
		return URI.createFileURI(projectFolder.resolve((("root" + suffix) + ".allElementTypes")).toString());
	}

	private VirtualVaVeModel setupVave(final Path projectFolder) throws Exception {
		Set<VitruvDomain> domains = new HashSet<>();
		final AllElementTypesDomain aetDomain = new AllElementTypesDomainProvider().getDomain();
		domains.add(aetDomain);

		Set<ChangePropagationSpecification> changePropagationSpecifications = new HashSet<>();
		RedundancyChangePropagationSpecification _redundancyChangePropagationSpecification = new RedundancyChangePropagationSpecification(aetDomain, aetDomain);
		changePropagationSpecifications.add(_redundancyChangePropagationSpecification);

		VirtualVaVeModel vave = new VirtualVaVeModelImpl(domains, changePropagationSpecifications, UserInteractionFactory.instance.createPredefinedInteractionResultProvider(null), projectFolder);
		return vave;
	}

	private int countSize(Term<? extends Option> expr) {
		if (expr instanceof UnaryExpression) {
			return 1 + countSize(((UnaryExpression<? extends Option>) expr).getTerm());
		} else if (expr instanceof BinaryExpression) {
			return 1 + countSize(((BinaryExpression<? extends Option>) expr).getTerm().get(0)) + countSize(((BinaryExpression<? extends Option>) expr).getTerm().get(1));
		} else {
			return 1;
		}
	}

	private boolean checkNormalForm(Term<? extends Option> expr) {
		if (expr instanceof Variable) {
			return true;
		} else if (expr instanceof Not) {
			if (((Not<? extends Option>) expr).getTerm() instanceof Variable) {
				return true;
			}
		} else if (expr instanceof Disjunction) {
			if (((Disjunction<? extends Option>) expr).getTerm().get(0) instanceof Not || ((Disjunction<? extends Option>) expr).getTerm().get(0) instanceof Variable || ((Disjunction<? extends Option>) expr).getTerm().get(0) instanceof Disjunction) {
				if (((Disjunction<? extends Option>) expr).getTerm().get(1) instanceof Not || ((Disjunction<? extends Option>) expr).getTerm().get(1) instanceof Variable || ((Disjunction<? extends Option>) expr).getTerm().get(1) instanceof Disjunction) {
					return checkNormalForm(((Disjunction<? extends Option>) expr).getTerm().get(0)) && checkNormalForm(((Disjunction<? extends Option>) expr).getTerm().get(1));
				}

			}
		} else if (expr instanceof Conjunction) {
			return checkNormalForm(((Conjunction<? extends Option>) expr).getTerm().get(0)) && checkNormalForm(((Conjunction<? extends Option>) expr).getTerm().get(1));
		}
		return false;
	}

	@Test
	public void Expression2CNFSimple(@TestProject final Path projectFolder) throws Exception {
		vavemodel.Variable<FeatureOption> variableA = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableB = VavemodelFactory.eINSTANCE.createVariable();

		// Case simple disjunction A⋁B
		Disjunction<FeatureOption> disjunction = VavemodelFactory.eINSTANCE.createDisjunction();
		disjunction.getTerm().add(variableA);
		disjunction.getTerm().add(variableB);

		ExpressionToCNFConverter ec = new ExpressionToCNFConverter();
		Expression<? extends Option> cnfExpr = ec.convert(disjunction);

		Disjunction<FeatureOption> groundTruth = VavemodelFactory.eINSTANCE.createDisjunction();
		groundTruth.getTerm().add(variableA);
		groundTruth.getTerm().add(variableB);

		assertEquals(cnfExpr.eContents().size(), groundTruth.eContents().size());
		assertTrue(checkNormalForm(cnfExpr));
	}

	@Test
	public void Expression2CNFComplex(@TestProject final Path projectFolder) throws Exception {
		vavemodel.Variable<FeatureOption> variableA = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableB = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableC = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableD = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableE = VavemodelFactory.eINSTANCE.createVariable();

		// Case complex conjunction (((A⋀B)⋁(C⋀D))⋁E)
		Disjunction<FeatureOption> outerDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
		Disjunction<FeatureOption> innerDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
		Conjunction<FeatureOption> leftConjunction = VavemodelFactory.eINSTANCE.createConjunction();
		Conjunction<FeatureOption> rightConjunction = VavemodelFactory.eINSTANCE.createConjunction();

		leftConjunction.getTerm().add(variableA);
		leftConjunction.getTerm().add(variableB);
		rightConjunction.getTerm().add(variableC);
		rightConjunction.getTerm().add(variableD);
		innerDisjunction.getTerm().add(leftConjunction);
		innerDisjunction.getTerm().add(rightConjunction);
		outerDisjunction.getTerm().add(innerDisjunction);
		outerDisjunction.getTerm().add(variableE);

		ExpressionValidator ev = new ExpressionValidator();
		assertTrue(ev.doSwitch(outerDisjunction));

		ExpressionToCNFConverter ec = new ExpressionToCNFConverter();
		Expression<? extends Option> cnfExpr = ec.convert(outerDisjunction);

		System.out.println("SIZE INPUT: " + countSize(outerDisjunction));
		System.out.println("SIZE OUTPUT: " + countSize(cnfExpr));

		assertTrue(checkNormalForm(cnfExpr)); // we don't know whether its the right CNF, but IF it is a CNF
	}

	@Test
	public void Expression2CNFComplexWithSatCheck(@TestProject final Path projectFolder) throws Exception {
		vavemodel.Variable<FeatureOption> variableA = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableB = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableC = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableD = VavemodelFactory.eINSTANCE.createVariable();
		vavemodel.Variable<FeatureOption> variableE = VavemodelFactory.eINSTANCE.createVariable();

		variableA.setOption(VavemodelFactory.eINSTANCE.createFeature());
		variableB.setOption(VavemodelFactory.eINSTANCE.createFeature());
		variableC.setOption(VavemodelFactory.eINSTANCE.createFeature());
		variableD.setOption(VavemodelFactory.eINSTANCE.createFeature());
		variableE.setOption(VavemodelFactory.eINSTANCE.createFeature());

		// Case complex conjunction (((A⋀B)⋁(C⋀D))⋁E)
		Disjunction<FeatureOption> outerDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
		Disjunction<FeatureOption> innerDisjunction = VavemodelFactory.eINSTANCE.createDisjunction();
		Conjunction<FeatureOption> leftConjunction = VavemodelFactory.eINSTANCE.createConjunction();
		Conjunction<FeatureOption> rightConjunction = VavemodelFactory.eINSTANCE.createConjunction();

		leftConjunction.getTerm().add(variableA);
		leftConjunction.getTerm().add(variableB);
		rightConjunction.getTerm().add(variableC);
		rightConjunction.getTerm().add(variableD);
		innerDisjunction.getTerm().add(leftConjunction);
		innerDisjunction.getTerm().add(rightConjunction);
		outerDisjunction.getTerm().add(innerDisjunction);
		outerDisjunction.getTerm().add(variableE);

		ExpressionValidator ev = new ExpressionValidator();
		assertTrue(ev.doSwitch(outerDisjunction));

		ExpressionToCNFConverter ec = new ExpressionToCNFConverter();
		Expression<? extends Option> cnfExpr = ec.convert(outerDisjunction);

		System.out.println("SIZE INPUT: " + countSize(outerDisjunction));
		System.out.println("SIZE OUTPUT: " + countSize(cnfExpr));

		assertTrue(checkNormalForm(cnfExpr)); // we don't know whether its the right CNF, but IF it is a CNF

		ExpressionToSATConverter e2sc = new ExpressionToSATConverter();

		e2sc.setIntForOption(1, variableA.getOption());
		e2sc.setIntForOption(2, variableB.getOption());
		e2sc.setIntForOption(3, variableC.getOption());
		e2sc.setIntForOption(4, variableD.getOption());
		e2sc.setIntForOption(5, variableE.getOption());

		Collection<int[]> clauses = e2sc.convertExpr2Sat(cnfExpr);
		ISolver solver = new ModelIterator(SolverFactory.newDefault());
		for (int[] clause : clauses) {
			solver.addClause(new VecInt(clause));
		}
		Set<int[]> models = new HashSet<>();
		int i = 0;
		while (solver.isSatisfiable()) {
			// int[] model = solver.findModel();
			int[] model = solver.model();
			models.add(model);
			String modelString = "";
			for (int val : model)
				modelString += val + ", ";
			System.out.println("MODEL " + (i++) + " : " + modelString);
		}
		i = 0;

		// (((A⋀B)⋁(C⋀D))⋁E) = (A or C or E) and (A or D or E) and (B or C or E) and (B or D or E)
		ISolver solver2 = new ModelIterator(SolverFactory.newDefault());
		solver2.addClause(new VecInt(new int[] { 1, 3, 5 }));
		solver2.addClause(new VecInt(new int[] { 1, 4, 5 }));
		solver2.addClause(new VecInt(new int[] { 2, 3, 5 }));
		solver2.addClause(new VecInt(new int[] { 2, 4, 5 }));
		Set<int[]> models2 = new HashSet<>();
		while (solver2.isSatisfiable()) {
			int[] model = solver2.model();
			models2.add(model);

			String modelString = "";
			for (int val : model)
				modelString += val + ", ";
			System.out.println("MODEL2 " + (i++) + " : " + modelString);
		}

		assertTrue(models.size() == models2.size() && models.stream().filter(v -> models2.stream().filter(v2 -> Arrays.equals(v, v2)).findAny().isEmpty()).findAny().isEmpty());
	}

	@Test
	public void simpleDependencyLiftingTest(@TestProject final Path projectFolder) throws Exception {
		VirtualVaVeModel vave = setupVave(projectFolder);

		// Feature Core
		Feature featureCore = VavemodelFactory.eINSTANCE.createFeature();
		featureCore.setName("featureCore");
		vave.getSystem().getFeature().add(featureCore);

		// Feature A
		Feature featureA = VavemodelFactory.eINSTANCE.createFeature();
		featureA.setName("featureA");
		vave.getSystem().getFeature().add(featureA);

		// Feature B
		Feature featureB = VavemodelFactory.eINSTANCE.createFeature();
		featureB.setName("featureB");
		vave.getSystem().getFeature().add(featureB);

		FeatureModel fm = new FeatureModel(null, null, new HashSet<FeatureOption>(), new HashSet<TreeConstraint>(), new HashSet<CrossTreeConstraint>());
		fm.getFeatureOptions().add(featureCore);
		fm.getFeatureOptions().add(featureA);
		fm.getFeatureOptions().add(featureB);
		vave.internalizeDomain(fm);
		
		Configuration config = VavemodelFactory.eINSTANCE.createConfiguration();
		config.getOption().add(vave.getSystem().getSystemrevision().get(vave.getSystem().getSystemrevision().size() - 1));
		config.getOption().add(featureCore);
		
		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config);
		{
			final ResourceSet resourceSet = ResourceSetUtil.withGlobalFactories(new ResourceSetImpl());
			final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
			changeRecorder.addToRecording(resourceSet);
			changeRecorder.beginRecording();

			final Resource monitoredResource = resourceSet.createResource(this.createTestModelResourceUri("", projectFolder));
			ValueBased root = AllElementTypesCreators.aet.ValueBased();
			root.setValue("root");
			monitoredResource.getContents().add(root);

			final TransactionalChange recordedChange = changeRecorder.endRecording();
			changeRecorder.close();

			// propagate recorded changes
			vmp1.propagateChange(recordedChange);
		}
		// internalize product with expression Core
		Variable<FeatureOption> variableCore = VavemodelFactory.eINSTANCE.createVariable();
		variableCore.setOption(featureCore);
		vave.internalizeChanges(vmp1, variableCore); // system revision 1

		assertEquals(1, vave.getSystem().getDeltamodule().size());

		// externalize product with core config
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(vave.getSystem().getSystemrevision().size()-1));
		config.getOption().add(featureCore.getFeaturerevision().get(0));
		config.getOption().add(featureA);
		final VirtualProductModel vmp0ext = vave.externalizeProduct(projectFolder.resolve("vmp0ext"), config);
		{
			final ResourceSet resourceSet = vmp0ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
			final ChangeRecorder changeRecorder = new ChangeRecorder(resourceSet);
			changeRecorder.addToRecording(resourceSet);
			changeRecorder.beginRecording();

			final Resource monitoredResource = vmp0ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
			ValueBased valueBasedA = AllElementTypesCreators.aet.ValueBased();
			valueBasedA.setValue("elementA");
			ValueBased root = (ValueBased) monitoredResource.getContents().get(0);
			root.getChildren().add(valueBasedA);

			final TransactionalChange recordedChange = changeRecorder.endRecording();
			changeRecorder.close();
			// propagate recorded changes
			vmp0ext.propagateChange(recordedChange);
		}
		// internalize product with feature A
		vavemodel.Variable<FeatureOption> variableA = VavemodelFactory.eINSTANCE.createVariable();
		variableA.setOption(featureA);
		vave.internalizeChanges(vmp0ext, variableA); // system revision 2 and feature revision 1 of feature A

		assertEquals(2, vave.getSystem().getDeltamodule().size());

		// externalize product with feature A
		config.getOption().clear();
		config.getOption().add(vave.getSystem().getSystemrevision().get(vave.getSystem().getSystemrevision().size()-1));
		config.getOption().add(featureCore.getFeaturerevision().get(0));
		config.getOption().add(featureA.getFeaturerevision().get(0));
		config.getOption().add(featureB);
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config);
		{
			final ResourceSet resourceSet2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource().getResourceSet();
			final ChangeRecorder changeRecorder2 = new ChangeRecorder(resourceSet2);
			changeRecorder2.addToRecording(resourceSet2);
			changeRecorder2.beginRecording();

			final Resource monitoredResource2 = vmp1ext.getModelInstance(this.createTestModelResourceUri("", projectFolder)).getResource();
			ValueBased valueBasedA = (ValueBased) monitoredResource2.getContents().get(0).eContents().get(0);
			ValueBased valueBasedB = AllElementTypesCreators.aet.ValueBased();
			valueBasedB.setValue("elementB");
			valueBasedB.getReferenced().add(valueBasedA);
			ValueBased root = (ValueBased) monitoredResource2.getContents().get(0);
			root.getChildren().add(valueBasedB);

			final TransactionalChange recordedChange2 = changeRecorder2.endRecording();
			changeRecorder2.close();
			vmp1ext.propagateChange(recordedChange2);
		}
		// internalize product with features A and B
		vavemodel.Variable<FeatureOption> variableB = VavemodelFactory.eINSTANCE.createVariable();
		variableB.setOption(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 3 and feature revision 1 of feature B

		assertEquals(3, vave.getSystem().getDeltamodule().size());

		DependencyLifting dl = new DependencyLifting();
		FeatureModel updatedFM = dl.liftingDependenciesBetweenFeatures(vave, vave.getSystem().getSystemrevision().get(vave.getSystem().getSystemrevision().size() - 1));
		
		System.out.println("FM: " + updatedFM);
		
	}

}
