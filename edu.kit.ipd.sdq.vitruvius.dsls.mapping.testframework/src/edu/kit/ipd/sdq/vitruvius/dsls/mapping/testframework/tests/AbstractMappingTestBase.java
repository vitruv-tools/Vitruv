package edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests;

import java.util.Collection;

import org.junit.runner.RunWith;

import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.AbstractMappingChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests.MappingLanguageTestRunner;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.Pair;

@RunWith(MappingLanguageTestRunner.class)
public abstract class AbstractMappingTestBase {
	protected abstract String getPluginName();
	protected abstract Collection<Pair<String, String>> getMetamodelURIsAndExtensions();
	protected abstract Class<? extends AbstractMappingChange2CommandTransforming> getChange2CommandTransformingClass();
}
