package tools.vitruv.dsls.mappings.tests

import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.XtextRunner
import org.junit.Test
import org.junit.runner.RunWith
import tools.vitruv.dsls.mappings.generator.XExpressionParser

@RunWith(XtextRunner)
@InjectWith(MappingsLanguageInjectorProvider)
class XExpressionParserTest {

	@Test
	def void test(){
		val f= XExpressionParser.parseExpression("4 == 4");
		 println(f)
	}
}