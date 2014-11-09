package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.WhenEvaluator;

public class OCLPredicateEvaluator implements WhenEvaluator {

	private String targetName;
	private String sourceName;
	private String oclPredicate;

	public OCLPredicateEvaluator(String oclPredicate, String sourceName, String targetName) {
		this.oclPredicate = oclPredicate;
		this.sourceName = sourceName;
		this.targetName = targetName;
	}
	
	@Override
	public boolean check(EObject source, EObject target) {
		OCL ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();

		OCLExpression<EClassifier> predicate = null;
		try {
			predicate = helper.createQuery(oclPredicate);
		} catch (ParserException e) {
			predicate = null;
			e.printStackTrace();
		}
		
		// TODO: set up source and target!
		
		return ocl.check(null, predicate);
	}

}
