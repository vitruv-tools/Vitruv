package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl;

import org.eclipse.xtext.xbase.impl.XBlockExpressionImpl

class SimpleTextXBlockExpression extends XBlockExpressionImpl {
	private val String text;
	
	public new(String text) {
		this.text = text;
	} 
	
	public def String getText() {
		return text;
	}
}