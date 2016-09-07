package tools.vitruvius.applications.jmljava.vitruvius.changesynchronizer.internal;

import tools.vitruvius.framework.change.processing.impl.AbstractChange2CommandTransformingProviding;

/**
 * Provider for transformation executings based on @Change2CommandTransformingProvidingImpl
 * TransformationExecutings can be registered via a method in addition to the usual Eclipse
 * extension point mechanism of Eclipse.
 */
public class Change2CommandTransformingProvidingImplCustom extends AbstractChange2CommandTransformingProviding {

	@Override
	protected void setup() {
	}

}
