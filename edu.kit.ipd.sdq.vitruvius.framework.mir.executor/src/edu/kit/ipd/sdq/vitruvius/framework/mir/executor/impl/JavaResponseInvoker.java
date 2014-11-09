package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseAction;

public class JavaResponseInvoker implements Response {
	public final static String DISPATCH_METHOD_NAME = "doInvoke";

	private final ResponseAction responseAction;
	private final EClassifier contextClassifier;
	private final String invariantName;
	
	private Object dynamicResponse;

	public JavaResponseInvoker(Class<?> dynamicResponseClass,
			ResponseAction responseAction, EClassifier contextClassifier,
			String invariantName) {

		try {
			this.dynamicResponse = dynamicResponseClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.responseAction = responseAction;
		this.contextClassifier = contextClassifier;
		this.invariantName = invariantName;
	}

	@Override
	public ResponseAction getResponseAction() {
		return responseAction;
	}

	@Override
	public EClassifier getContextClassifier() {
		return contextClassifier;
	}

	@Override
	public String getInvariantName() {
		return invariantName;
	}

	@Override
	public void invoke(EObject context, Map<String, EObject> parameters) {
		if (dynamicResponse != null) {
			// TODO: ... reflection magic ...
			throw new UnsupportedOperationException();
		} else {
			System.out.println("Error while invoking Response");
		}
	}

}
