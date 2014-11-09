package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.helper.OCLHelper;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantEvaluationResult;

/**
 * @author Dominik Werle
 */
public class OCLInvariant implements Invariant {

	private final EClassifier contextClassifier;
	private final String name;
	private final Constraint invariant;
	
	private final OCL ocl;

	public OCLInvariant(EClassifier contextClassifier, String name, String oclInvariant) {
		this.ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();

		this.contextClassifier = contextClassifier;
		this.name = name;
				
		Constraint constraint = null;
		try {
			constraint = helper.createInvariant(oclInvariant);
		} catch (ParserException e) {
			constraint = null;
			e.printStackTrace();
		}
		
		this.invariant = constraint;
	}
	
	public OCLInvariant(EClassifier contextClassifier, String name,
			Constraint invariant) {
		
		this.ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		this.contextClassifier = contextClassifier;
		this.name = name;
		this.invariant = invariant;
	}



	@Override
	public EClassifier getContextClassifier() {
		return contextClassifier;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Parameter> getSignature() {
		return Collections.emptyList();
	}

	@Override
	public InvariantEvaluationResult check(EObject context) {
		boolean invariantResult = ocl.check(context, invariant);
		return new InvariantEvaluationResultImpl(this, !invariantResult);
	}
}
