package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.InvariantEvaluationResult;

public class JavaInvariantInvoker implements Invariant {
	public final static String CHECK_METHOD_NAME = "check";
	
	private Object dynamicInvariant;
	private String invariantName;
	private EClassifier contextClassifier;
	private List<Invariant.Parameter> signature;
	
	public JavaInvariantInvoker(Class<?> dynamicInvariant,
			String invariantName, EClassifier contextClassifier,
			List<Parameter> signature) {
		
		try {
			this.dynamicInvariant = dynamicInvariant.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.invariantName = invariantName;
		this.contextClassifier = contextClassifier;
		this.signature = signature;
	}

	@Override
	public EClassifier getContextClassifier() {
		return contextClassifier;
	}

	@Override
	public String getName() {
		return invariantName;
	}

	@Override
	public List<Parameter> getSignature() {
		return signature;
	}

	@Override
	public InvariantEvaluationResult check(EObject context) {
		throw new UnsupportedOperationException();
	}
}
