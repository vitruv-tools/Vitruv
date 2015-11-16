package edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.tests

import com.google.inject.Inject
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.MappingLanguageInjectorProvider
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.mappingLanguage.MappingFile
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.impl.AbstractMappingChange2CommandTransforming
import edu.kit.ipd.sdq.vitruvius.framework.mir.executor.interfaces.MIRUserInteracting
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IConfigurationElement
import org.eclipse.core.runtime.Platform
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.runner.notification.RunNotifier
import org.junit.runners.BlockJUnit4ClassRunner
import org.junit.runners.model.InitializationError
import static edu.kit.ipd.sdq.vitruvius.framework.mir.testframework.util.MappingLanguageTestUtil.*

class MappingLanguageTestRunner extends BlockJUnit4ClassRunner {
	@Inject ParseHelper<MappingFile> parseHelper
	val String pluginName
	val VitruviusTestEnv testEnv

	new(Class<?> klass) throws InitializationError {
		super(klass)
		val mappingTest = klass.getAnnotation(MappingLanguageTest)
		if (mappingTest == null || mappingTest.value.empty) {
			throw new InitializationError(
				'''No mapping file uri specified. Use @«MappingLanguageTest.name»(uri="...") to specify the URI or the content of the mapping file that is to be tested with this Test.''')
		}

		new MappingLanguageInjectorProvider().injector.injectMembers(this)

		var String fileContent = null
		try {
			if (!mappingTest.value.empty) {
				val inputStream = new BufferedReader(
					new InputStreamReader(new URL(mappingTest.value).openConnection.inputStream))
				fileContent = inputStream.lines.collect(Collectors.joining("\n"))
			}
		} catch (IOException e) {
			throw new InitializationError(e)
		}

		val mappingFile = parseHelper.parse(fileContent)
		pluginName = mappingFile.pluginName

		// klass.fields.filter[it.name=="pluginName"].claimExactlyOne.set(null, pluginName);
		val c2cTransforming = getChange2CommandTransforming(pluginName)
		if (c2cTransforming == null) {
			throw new InitializationError(
			'''
				Could not find «Change2CommandTransforming.name» for plugin "«pluginName»" for extension point "«Change2CommandTransforming.ID»".
				Please make sure that the plugin project from "«mappingTest.value»" has been generated and loaded in the plugin test configuration.
			''')
		}
		testEnv = new VitruviusTestEnv(c2cTransforming)
	}

	override protected createTest() throws Exception {
		val testUserInteracting = new MappingLanguageTestUserInteracting

		val newTest = super.createTest()
		val inj = injector [
			bind(VitruviusTestEnv).toInstance(testEnv)
			bind(MIRUserInteracting).toInstance(testUserInteracting)
		]
		
		inj.injectMembers(testEnv)
		inj.injectMembers(newTest)
		
		newTest
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