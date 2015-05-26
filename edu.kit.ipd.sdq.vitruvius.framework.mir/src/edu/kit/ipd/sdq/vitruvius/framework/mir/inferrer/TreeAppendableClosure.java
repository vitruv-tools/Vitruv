package edu.kit.ipd.sdq.vitruvius.framework.mir.inferrer;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.compiler.output.ITreeAppendable;
import org.eclipse.xtext.xbase.lib.Procedures;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import com.google.inject.Inject;

@SuppressWarnings("restriction")
public class TreeAppendableClosure implements Procedures.Procedure1<ITreeAppendable> {

	protected List<Procedures.Procedure1<ITreeAppendable>> closures;
	private XbaseCompiler compiler;

	@Override
	public void apply(ITreeAppendable p) {
		for (Procedures.Procedure1<ITreeAppendable> closure : closures) {
			closure.apply(p);
		}
	}
	
	@Inject
	TreeAppendableClosure(XbaseCompiler compiler) {
		this.closures = new ArrayList<Procedures.Procedure1<ITreeAppendable>>();
		this.compiler = compiler;
	}
	
	public List<Procedures.Procedure1<ITreeAppendable>> getClosures() {
		return closures;
	}
	
	public void addClosure(Procedures.Procedure1<ITreeAppendable> newClosure) {
		closures.add(newClosure);
	}
	
	public void addCode(final CharSequence code) {
		closures.add(new Procedure1<ITreeAppendable>() {
			@Override
			public void apply(ITreeAppendable p) {
				p.append(code);
			}
		});
	}
	
	public void addXExpression(final XExpression expression) {
		closures.add(new Procedure1<ITreeAppendable>() {
			@Override
			public void apply(ITreeAppendable p) {
				compiler.toJavaStatement(expression, p, false);
			}
		});
	}

}
