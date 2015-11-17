package edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.tests

import com.google.inject.Singleton
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappingChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IConfigurationElement
import org.eclipse.core.runtime.Platform
import org.junit.runner.notification.RunNotifier
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.InitializationError

import static edu.kit.ipd.sdq.vitruvius.framework.mir.executor.helpers.JavaHelper.*
import static edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MappingLanguageTestUtil.*

class MappingLanguageTestRunner extends BlockJUnit4ClassRunner {
	new(Class<?> klass) throws InitializationError {
		super(klass)
	}
	
	override protected createTest() throws Exception {
		val newTest = super.createTest()
		
		if (!(newTest instanceof AbstractMappingTestBase)) {
			throw new InitializationError('''The class under test «testClass.name» does not extend «AbstractMappingTestBase.name».''')
		}
		
		val test = newTest as AbstractMappingTestBase
		val pluginName = test.pluginName
		
		val c2cTransforming = getChange2CommandTransforming(pluginName)
		
		if (c2cTransforming == null) {
			throw new InitializationError(
			'''
				Could not find «Change2CommandTransforming.name» for plugin "«pluginName»" for extension point "«Change2CommandTransforming.ID»".
				Please make sure that the plugin project has been generated and loaded correctly in the plugin test configuration.
			''')
		}
		
		if (!test.change2CommandTransformingClass.isInstance(c2cTransforming)) {
			throw new InitializationError(
			'''
				The loaded «Change2CommandTransforming.name» for the plugin "«pluginName»" («c2cTransforming.toString») is not
				an instance of «test.change2CommandTransformingClass.name» as expected.
				Please make sure that the plugin project has been generated and loaded correctly in the plugin test configuration.
			''')
		}
		
		with(injector [
			bind(Change2CommandTransforming).toInstance(c2cTransforming)
			bind(AbstractMappingTestBase).toInstance(test)
			bind(MappingLanguageTestEnvironment).in(Singleton)
			bind(MappingLanguageTestUserInteracting).in(Singleton)
		]) [
			injectMembers(test)
			getInstance(MappingLanguageTestEnvironment).setup
		]
		
		test
	}

	override run(RunNotifier notifier) {
		super.run(notifier)
	}

	private static def AbstractMappingChange2CommandTransforming getChange2CommandTransforming(
		String contributorName) throws CoreException {
		val config = Platform.getExtensionRegistry().getConfigurationElementsFor(Change2CommandTransforming.ID);
		for (IConfigurationElement extConfElem : config) {
			if (contributorName.equals(extConfElem.getContributor().getName())) {
				return extConfElem.createExecutableExtension(
					VitruviusConstants.getExtensionPropertyName()) as AbstractMappingChange2CommandTransforming;
			}
		}
		return null;
	}

}