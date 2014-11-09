package edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Invariant;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.Response;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.ResponseAction;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.MappingOperation;
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.reflection.MappingType;

/**
 * An abstract response that delegates its invocation to a method that is implementeded
 * in the concrete implementation.
 * <p>
 * The subclass must contain exactly one method called doInvoke, that takes arguments with
 * the names and types defined in the invariant.
 * 
 * @author Dominik Werle
 */
public abstract class DynamicDispatchResponse implements Response {
	private ResponseAction responseAction;
	private EClassifier contextClassifier;
	private String invariantName;
	
	public final static String DISPATCH_METHOD_NAME = "doInvoke"; 
	
	public DynamicDispatchResponse(ResponseAction responseAction,
			EClassifier contextClassifier, String invariantName) {
		super();
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

	private Method getDispatchMethod() {
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().equals(DISPATCH_METHOD_NAME)) {
				return m;
			}
		}
		
		throw new IllegalStateException("No dispatch method " + DISPATCH_METHOD_NAME + " found in type " + this.getClass());
	}
	
	@Override
	public void invoke(EObject context, Map<String, EObject> params) {
		if (!getContextClassifier().isInstance(context))
			throw new IllegalArgumentException("Argument is not instance of context type");
		else {
			Method m = getDispatchMethod();
			Object[] args = new Object[m.getParameterCount()];
			int pindex = 0;
			for (Parameter p : m.getParameters()) {
				args[pindex] = params.get(p.getName());
				pindex++;
			}
			
			try {
				m.invoke(this, args);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
