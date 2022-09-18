package tools.vitruv.variability.vave.impl;

import tools.vitruv.variability.vave.OperationResult;
import tools.vitruv.variability.vave.VirtualProductModel;

/**
 * Provides a Virtual Product Model as result of the vave operation externalizeProduct.
 */
public class ExternalizeProductResult extends OperationResult<VirtualProductModel> {

	private VirtualProductModel product;
	
	public ExternalizeProductResult(VirtualProductModel product) {
		if (product==null) throw new RuntimeException("Resulting product must not be null.");
		this.product=product;
	}
	
	@Override
	public VirtualProductModel getResult() {
		return this.product;
	}
	
	@Override
	public void setResult(VirtualProductModel product) {
		this.product = product;
	}
}
