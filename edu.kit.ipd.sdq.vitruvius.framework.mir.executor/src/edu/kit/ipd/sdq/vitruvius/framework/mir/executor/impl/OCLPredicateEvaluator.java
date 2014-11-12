package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;

public class OCLPredicateEvaluator {

	private String oclPredicate;
	private Object sourceName;
	private Object varName;

	public OCLPredicateEvaluator(String oclPredicate, String varName) {
		this.oclPredicate = oclPredicate;
		this.varName = sourceName;
	}
	
	public boolean check(EObject context) {
		OCL ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();

		OCLExpression<EClassifier> predicate = null;
		try {
			predicate = helper.createQuery(oclPredicate);
		} catch (ParserException e) {
			predicate = null;
			e.printStackTrace();
		}
		
		// TODO: set up variable
		return ocl.check(null, predicate);
	}

}
