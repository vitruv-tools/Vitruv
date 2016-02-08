package edu.kit.ipd.sdq.vitruvius.dsls.response.generator.impl;

import org.eclipse.xtext.xbase.impl.XBlockExpressionImpl
import org.eclipse.xtend2.lib.StringConcatenationClient

class SimpleTextXBlockExpression extends XBlockExpressionImpl {
	private val StringConcatenationClient text;
	
	public new(StringConcatenationClient text) {
		this.text = text;
	} 
	
	public def StringConcatenationClient getText() {
		return text;
	}
}