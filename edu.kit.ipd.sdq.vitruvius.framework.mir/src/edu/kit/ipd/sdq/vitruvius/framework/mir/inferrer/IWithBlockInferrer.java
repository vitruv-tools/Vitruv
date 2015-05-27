package edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer;

import org.eclipse.xtext.xbase.XExpression;

import com.google.inject.ImplementedBy;

/**
 * A {@link IWithBlockInferrer} infers assignment methods from an expression by putting the correct methods
 * in the closures that can be obtained from a {@link ClosureProvider}.
 * @author Dominik Werle
 *
 */
@ImplementedBy(WithBlockInferrer.class)
public interface IWithBlockInferrer {
	public abstract void infer(XExpression expression);
}
