//package tools.vitruv.dsls.mapping.testframework.tests
//
//import com.google.inject.Singleton
//import tools.vitruv.framework.change.processing.Change2CommandTransforming
//import tools.vitruv.framework.util.VitruviusConstants
//import org.eclipse.core.runtime.CoreException
//import org.eclipse.core.runtime.IConfigurationElement
//import org.eclipse.core.runtime.Platform
//import org.junit.runner.notification.RunNotifier
//import org.junit.runners.BlockJUnit4ClassRunner
//import org.junit.runners.model.InitializationError
//
//import static tools.vitruv.framework.util.bridges.JavaHelper.*
//import static tools.vitruv.dsls.mapping.testframework.util.MappingLanguageTestUtil.*
//import org.junit.runners.model.FrameworkMethod
//import org.junit.runners.model.Statement
//import com.google.inject.Inject
//
//class MappingLanguageTestRunner extends BlockJUnit4ClassRunner {
//	private MappingLanguageTestEnvironment mlte
//
//	new(Class<?> klass) throws InitializationError {
//		super(klass)
//	}
//
//	override protected createTest() throws Exception {
//		val newTest = super.createTest()
//
//		if (!(newTest instanceof AbstractMappingTestBase)) {
//			throw new InitializationError('''The class under test «testClass.name» does not extend «AbstractMappingTestBase.name».''')
//		}
//
//		val test = newTest as AbstractMappingTestBase
//		val pluginName = test.pluginName
//
//		val c2cTransforming = getChange2CommandTransforming(pluginName)
//
//		if (c2cTransforming === null) {
//			throw new InitializationError(
//			'''
//				Could not find «Change2CommandTransforming.name» for plugin "«pluginName»" for extension point "«Change2CommandTransforming.ID»".
//				Please make sure that the plugin project has been generated and loaded correctly in the plugin test configuration.
//			''')
//		}
//
//		if (!test.change2CommandTransformingClass.isInstance(c2cTransforming)) {
//			throw new InitializationError(
//			'''
//				The loaded «Change2CommandTransforming.name» for the plugin "«pluginName»" («c2cTransforming.toString») is not
//				an instance of «test.change2CommandTransformingClass.name» as expected.
//				Please make sure that the plugin project has been generated and loaded correctly in the plugin test configuration.
//			''')
//		}
//
//		with(injector [
//			bind(Change2CommandTransforming).toInstance(c2cTransforming)
//			bind(AbstractMappingTestBase).toInstance(test)
//			bind(MappingLanguageTestEnvironment).in(Singleton)
//		]) [
//			injectMembers(test)
//			injectMembers(this)
//		]
//
//		test
//	}
//
//	/* Add magic to move and delete correspondence / vsum before/after each test */
//	override protected methodBlock(FrameworkMethod method) {
//		val signature = super.methodBlock(method)
//
//		val result = [
//			mlte.setup(method);
//			try {
//				signature.evaluate
//			} finally {
//				mlte.after
//			}
//		] as Statement
//
//		return result
//	}
//
//	private static def Change2CommandTransforming getChange2CommandTransforming(
//		String contributorName) throws CoreException {
//		val config = Platform.getExtensionRegistry().getConfigurationElementsFor(Change2CommandTransforming.ID);
//		for (IConfigurationElement extConfElem : config) {
//			if (contributorName.equals(extConfElem.getContributor().getName())) {
//				return extConfElem.createExecutableExtension(
//					VitruviusConstants.getExtensionPropertyName()) as Change2CommandTransforming;
//			}
//		}
//		return null;
//	}
//
//}