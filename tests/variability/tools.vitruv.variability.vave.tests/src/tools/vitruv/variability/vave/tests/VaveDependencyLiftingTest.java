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
import tools.vitruv.variability.vave.consistency.DependencyLifting;
import tools.vitruv.variability.vave.impl.VirtualVaVeModelImpl;
import tools.vitruv.variability.vave.model.expression.BinaryExpression;
import tools.vitruv.variability.vave.model.expression.Conjunction;
import tools.vitruv.variability.vave.model.expression.Disjunction;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.expression.ExpressionFactory;
import tools.vitruv.variability.vave.model.expression.NaryExpression;
import tools.vitruv.variability.vave.model.expression.Not;
import tools.vitruv.variability.vave.model.expression.UnaryExpression;
import tools.vitruv.variability.vave.model.expression.Variable;
import tools.vitruv.variability.vave.model.featuremodel.FeatureModel;
import tools.vitruv.variability.vave.model.featuremodel.FeaturemodelFactory;
import tools.vitruv.variability.vave.model.featuremodel.ViewFeature;
import tools.vitruv.variability.vave.model.featuremodel.ViewTreeConstraint;
import tools.vitruv.variability.vave.model.vave.Configuration;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureOption;
import tools.vitruv.variability.vave.model.vave.GroupType;
import tools.vitruv.variability.vave.model.vave.Option;
import tools.vitruv.variability.vave.model.vave.VaveFactory;
import tools.vitruv.variability.vave.tests.VaveTest.RedundancyChangePropagationSpecification;
import tools.vitruv.variability.vave.util.ExpressionToSATConverter;
import tools.vitruv.variability.vave.util.ExpressionUtil;

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

	private int countSize(Expression<? extends Option> expr) {
		if (expr instanceof UnaryExpression) {
			return 1 + countSize(((UnaryExpression<? extends Option>) expr).getExpression());
		} else if (expr instanceof BinaryExpression) {
			return 1 + countSize(((BinaryExpression<? extends Option>) expr).getLeft()) + countSize(((BinaryExpression<? extends Option>) expr).getRight());
		} else if (expr instanceof NaryExpression) {
			int sum = 1;
			for (Expression<? extends Option> childExpr : ((NaryExpression<? extends Option>) expr).getExpressions())
				sum += countSize(childExpr);
			return sum;
		} else {
			return 1;
		}
	}

	private boolean checkNormalForm(Expression<? extends Option> expr) {
		if (expr instanceof Variable) {
			return true;
		} else if (expr instanceof Not) {
			if (((Not<? extends Option>) expr).getExpression() instanceof Variable) {
				return true;
			}
		} else if (expr instanceof Disjunction) {
			for (Expression<? extends Option> childExpression : ((Disjunction<? extends Option>) expr).getExpressions())
				if (!(childExpression instanceof Not || childExpression instanceof Variable || childExpression instanceof Disjunction) || !checkNormalForm(childExpression))
					return false;
			return true;

		} else if (expr instanceof Conjunction) {
			for (Expression<? extends Option> childExpression : ((Conjunction<? extends Option>) expr).getExpressions())
				if (!checkNormalForm(childExpression))
					return false;
			return true;
		}
		return false;
	}

	@Test
	public void Expression2CNFSimple(@TestProject final Path projectFolder) throws Exception {
		Variable<FeatureOption> variableA = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableB = ExpressionFactory.eINSTANCE.createVariable();

		// Case simple disjunction A⋁B
		Disjunction<FeatureOption> disjunction = ExpressionFactory.eINSTANCE.createDisjunction();
		disjunction.getExpressions().add(variableA);
		disjunction.getExpressions().add(variableB);

		Expression<? extends Option> cnfExpr = ExpressionUtil.convertToCNF(disjunction);

		Disjunction<FeatureOption> groundTruth = ExpressionFactory.eINSTANCE.createDisjunction();
		groundTruth.getExpressions().add(variableA);
		groundTruth.getExpressions().add(variableB);

		assertEquals(cnfExpr.eContents().size(), groundTruth.eContents().size());
		assertTrue(checkNormalForm(cnfExpr));
	}

	@Test
	public void Expression2CNFComplex(@TestProject final Path projectFolder) throws Exception {
		Variable<FeatureOption> variableA = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableB = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableC = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableD = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableE = ExpressionFactory.eINSTANCE.createVariable();

		// Case complex conjunction (((A⋀B)⋁(C⋀D))⋁E)
		Disjunction<FeatureOption> outerDisjunction = ExpressionFactory.eINSTANCE.createDisjunction();
		Disjunction<FeatureOption> innerDisjunction = ExpressionFactory.eINSTANCE.createDisjunction();
		Conjunction<FeatureOption> leftConjunction = ExpressionFactory.eINSTANCE.createConjunction();
		Conjunction<FeatureOption> rightConjunction = ExpressionFactory.eINSTANCE.createConjunction();

		leftConjunction.getExpressions().add(variableA);
		leftConjunction.getExpressions().add(variableB);
		rightConjunction.getExpressions().add(variableC);
		rightConjunction.getExpressions().add(variableD);
		innerDisjunction.getExpressions().add(leftConjunction);
		innerDisjunction.getExpressions().add(rightConjunction);
		outerDisjunction.getExpressions().add(innerDisjunction);
		outerDisjunction.getExpressions().add(variableE);

		assertTrue(ExpressionUtil.validate(outerDisjunction));

		Expression<? extends Option> cnfExpr = ExpressionUtil.convertToCNF(outerDisjunction);

		System.out.println("SIZE INPUT: " + countSize(outerDisjunction));
		System.out.println("SIZE OUTPUT: " + countSize(cnfExpr));

		assertTrue(checkNormalForm(cnfExpr)); // we don't know whether its the right CNF, but IF it is a CNF
	}

	@Test
	public void Expression2CNFComplexWithSatCheck(@TestProject final Path projectFolder) throws Exception {
		Variable<FeatureOption> variableA = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableB = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableC = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableD = ExpressionFactory.eINSTANCE.createVariable();
		Variable<FeatureOption> variableE = ExpressionFactory.eINSTANCE.createVariable();

		variableA.setValue(VaveFactory.eINSTANCE.createFeature());
		variableB.setValue(VaveFactory.eINSTANCE.createFeature());
		variableC.setValue(VaveFactory.eINSTANCE.createFeature());
		variableD.setValue(VaveFactory.eINSTANCE.createFeature());
		variableE.setValue(VaveFactory.eINSTANCE.createFeature());

		// Case complex conjunction (((A⋀B)⋁(C⋀D))⋁E)
		Disjunction<FeatureOption> outerDisjunction = ExpressionFactory.eINSTANCE.createDisjunction();
		Disjunction<FeatureOption> innerDisjunction = ExpressionFactory.eINSTANCE.createDisjunction();
		Conjunction<FeatureOption> leftConjunction = ExpressionFactory.eINSTANCE.createConjunction();
		Conjunction<FeatureOption> rightConjunction = ExpressionFactory.eINSTANCE.createConjunction();

		leftConjunction.getExpressions().add(variableA);
		leftConjunction.getExpressions().add(variableB);
		rightConjunction.getExpressions().add(variableC);
		rightConjunction.getExpressions().add(variableD);
		innerDisjunction.getExpressions().add(leftConjunction);
		innerDisjunction.getExpressions().add(rightConjunction);
		outerDisjunction.getExpressions().add(innerDisjunction);
		outerDisjunction.getExpressions().add(variableE);

		assertTrue(ExpressionUtil.validate(outerDisjunction));

		Expression<? extends Option> cnfExpr = ExpressionUtil.convertToCNF(outerDisjunction);

		assertTrue(checkNormalForm(cnfExpr)); // we don't know whether its the right CNF, but IF it is a CNF

		ExpressionToSATConverter e2sc = new ExpressionToSATConverter();

		e2sc.setIntForOption(1, variableA.getValue());
		e2sc.setIntForOption(2, variableB.getValue());
		e2sc.setIntForOption(3, variableC.getValue());
		e2sc.setIntForOption(4, variableD.getValue());
		e2sc.setIntForOption(5, variableE.getValue());

		Collection<int[]> clauses = e2sc.convertExpr2Sat(cnfExpr);
		ISolver solver = new ModelIterator(SolverFactory.newDefault());
		for (int[] clause : clauses) {
			solver.addClause(new VecInt(clause));
		}
		Set<int[]> models = new HashSet<>();
		int i = 0;
		while (solver.isSatisfiable()) {
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
		ViewFeature viewFeatureCore = FeaturemodelFactory.eINSTANCE.createViewFeature();
		viewFeatureCore.setName("featureCore");

		// Feature A
		ViewFeature viewFeatureA = FeaturemodelFactory.eINSTANCE.createViewFeature();
		viewFeatureA.setName("featureA");

		// Feature B
		ViewFeature viewFeatureB = FeaturemodelFactory.eINSTANCE.createViewFeature();
		viewFeatureB.setName("featureB");

		FeatureModel fm = FeaturemodelFactory.eINSTANCE.createFeatureModel();
		fm.getRootFeatures().add(viewFeatureCore);

		ViewTreeConstraint viewTreeConstraint = FeaturemodelFactory.eINSTANCE.createViewTreeConstraint();
		viewTreeConstraint.setType(GroupType.OPTIONAL);
		viewTreeConstraint.getChildFeatures().add(viewFeatureA);
		viewTreeConstraint.getChildFeatures().add(viewFeatureB);
		viewFeatureCore.getChildTreeConstraints().add(viewTreeConstraint);

		vave.internalizeDomain(fm);

		Feature featureA = vave.getSystem().getFeatures().stream().filter(f -> f.getName().equals("featureA")).findAny().get();
		Feature featureB = vave.getSystem().getFeatures().stream().filter(f -> f.getName().equals("featureB")).findAny().get();
		Feature featureCore = vave.getSystem().getFeatures().stream().filter(f -> f.getName().equals("featureCore")).findAny().get();

		Configuration config = VaveFactory.eINSTANCE.createConfiguration();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
		config.getOptions().add(featureCore);

		final VirtualProductModel vmp1 = vave.externalizeProduct(projectFolder.resolve("vsum"), config).getResult();
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
		Variable<FeatureOption> variableCore = ExpressionFactory.eINSTANCE.createVariable();
		variableCore.setValue(featureCore);
		vave.internalizeChanges(vmp1, variableCore); // system revision 1

		assertEquals(1, vave.getSystem().getDeltaModules().size());

		// externalize product with core config
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
		config.getOptions().add(featureCore.getFeatureRevisions().get(0));
		config.getOptions().add(featureA);
		final VirtualProductModel vmp0ext = vave.externalizeProduct(projectFolder.resolve("vmp0ext"), config).getResult();
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
		Variable<FeatureOption> variableA = ExpressionFactory.eINSTANCE.createVariable();
		variableA.setValue(featureA);
		vave.internalizeChanges(vmp0ext, variableA); // system revision 2 and feature revision 1 of feature A

		assertEquals(2, vave.getSystem().getDeltaModules().size());

		// externalize product with feature A
		config.getOptions().clear();
		config.getOptions().add(vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1));
		config.getOptions().add(featureCore.getFeatureRevisions().get(0));
		config.getOptions().add(featureA.getFeatureRevisions().get(0));
		config.getOptions().add(featureB);
		final VirtualProductModel vmp1ext = vave.externalizeProduct(projectFolder.resolve("vmp1ext"), config).getResult();
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
		Variable<FeatureOption> variableB = ExpressionFactory.eINSTANCE.createVariable();
		variableB.setValue(featureB);
		vave.internalizeChanges(vmp1ext, variableB); // system revision 3 and feature revision 1 of feature B

		assertEquals(3, vave.getSystem().getDeltaModules().size());

		DependencyLifting dl = new DependencyLifting();
		FeatureModel updatedFM = dl.internalizeChangesPost(vave, vave.getSystem().getSystemRevisions().get(vave.getSystem().getSystemRevisions().size() - 1)).getRepairedFeatureModel();

	}

}
