package edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer;

import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;
import org.eclipse.xtext.xbase.lib.Procedures;

import com.google.inject.Inject;

/**
 * A closure that contains the same methods for adding closures as
 * {@link TreeAppendableClosure} but returns code of the form:
 * <p>
 * <code>
 * return (result of closure 1) && (result of closure 2) && ...;
 * </code>
 * @author Dominik Werle
 *
 */
public class TreeAppendableConjunctionClosure extends TreeAppendableClosure {
	@Inject
	TreeAppendableConjunctionClosure(XbaseCompiler compiler) {
		super(compiler);
	}
	
	@Override
	public void apply(ITreeAppendable p) {
		boolean first = true;
		p.append("return (");
		
		if (closures.isEmpty())
			p.append("true");
		
		for (Procedures.Procedure1<ITreeAppendable> closure : closures) {
			if (!first) {
				p.newLine();
				p.append("\t\t&& ");
			} else {
				first = false;
			}
			
//			p.append("(");
			closure.apply(p);
//			p.append(")");
		}
		p.append(");");
	}

}
