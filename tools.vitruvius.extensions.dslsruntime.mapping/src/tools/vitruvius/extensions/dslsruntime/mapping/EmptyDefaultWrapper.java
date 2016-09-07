package tools.vitruvius.extensions.dslsruntime.mapping;

import java.util.Collections;

import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.AbstractCorrespondenceWrapper;
import tools.vitruvius.extensions.dslsruntime.mapping.interfaces.ElementProvider;

public class EmptyDefaultWrapper extends AbstractWrapper {
	public enum Side { A, B }

	public EmptyDefaultWrapper(ElementProvider elementProvider) {
		super(Collections.emptyList(), elementProvider);
	}

	public static EmptyDefaultWrapper createHalfMappedCorrespondence(AbstractCorrespondenceWrapper correspondenceWrapper, Side side) {
		return new EmptyDefaultWrapper(() ->
			(side == Side.A)
			? correspondenceWrapper.getCorrespondence().getAs()
			: correspondenceWrapper.getCorrespondence().getBs());
	}
}
