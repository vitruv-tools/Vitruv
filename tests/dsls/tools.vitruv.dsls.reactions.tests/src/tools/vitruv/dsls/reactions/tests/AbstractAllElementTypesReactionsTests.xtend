package tools.vitruv.dsls.reactions.tests

import tools.vitruv.framework.util.datatypes.VURI
import java.util.Arrays
import java.util.HashSet
import tools.vitruv.framework.metamodel.Metamodel
import allElementTypes.AllElementTypesPackage
import tools.vitruv.framework.tuid.AttributeTuidCalculatorAndResolver
import tools.vitruv.framework.tests.VitruviusEmfApplicationTest

abstract class AbstractAllElementTypesReactionsTests extends VitruviusEmfApplicationTest {
	protected static val MODEL_FILE_EXTENSION = "minimalAllElements";
	
	protected override createMetamodels() {
		// Simple changes model
		val metamodelUri = VURI.getInstance(AllElementTypesPackage.eNS_URI);
		val metamodelNSUris = new HashSet<String>();
		metamodelNSUris.addAll(Arrays.asList(AllElementTypesPackage.eNS_URI));
		val String[] fileExtensions = newArrayOfSize(1);
		fileExtensions.set(0, MODEL_FILE_EXTENSION);
		val minimalMetamodel = new Metamodel(metamodelUri, metamodelNSUris, 
			new AttributeTuidCalculatorAndResolver(AllElementTypesPackage.eNS_URI, AllElementTypesPackage.Literals.IDENTIFIED__ID.name),
			fileExtensions);
		return #[minimalMetamodel];
	}

}