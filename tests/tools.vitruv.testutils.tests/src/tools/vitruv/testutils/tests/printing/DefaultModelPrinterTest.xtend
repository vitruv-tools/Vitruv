package tools.vitruv.testutils.tests.printing

import tools.vitruv.testutils.printing.DefaultPrintTarget
import tools.vitruv.testutils.printing.PrintTarget
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import tools.vitruv.testutils.printing.DefaultModelPrinter
import tools.vitruv.testutils.printing.PrintIdProvider
import tools.vitruv.testutils.printing.DefaultPrintIdProvider
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import allElementTypes.ValueBased
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.common.util.URI
import org.junit.jupiter.api.^extension.ExtendWith
import tools.vitruv.testutils.RegisterMetamodelsInStandalone

@ExtendWith(RegisterMetamodelsInStandalone)
class DefaultModelPrinterTest {
	@Test
	@DisplayName("prints integer values")
	def void printInteger() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, 5)
		], is("5"))	
	}
	
	@Test
	@DisplayName("prints integer values (shortened)")
	def void printIntegerShortened() {
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, 5)
		], is("5"))	
	}
	
	@Test
	@DisplayName("prints double values")
	def void printDouble() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, 3.5)
		], is("3.5"))	
	}
	
	@Test
	@DisplayName("prints double values (shortened)")
	def void printDoubleShortened() {
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, 3.5)
		], is("3.5"))	
	}
	
	@Test
	@DisplayName("prints boolean values")
	def void printBoolean() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, true)
		], is("true"))	
	}
	
	@Test
	@DisplayName("prints boolean values (shortened)")
	def void printBooleanShortened() {
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, true)
		], is("true"))	
	}
	
	@Test
	@DisplayName("prints null")
	def void printNull() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, null)
		], is('\u2205' /* empty set */))	
	}
	
	@Test
	@DisplayName("prints null (shortened)")
	def void printNullShortened() {
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, null)
		], is('\u2205' /* empty set */))	
	}
	
	@Test
	@DisplayName("prints string values")
	def void printString() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, 'test')
		], is('test'))	
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, 'veryverylongtestthatcouldtbeshortenedwithevenmoreletters')
		], is('veryverylongtestthatcouldtbeshortenedwithevenmoreletters'))	
	}
	
	@Test
	@DisplayName("prints string values (shortened)")
	def void printStringShortened() {
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, 'test')
		], is('test'))
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, 'veryverylo')
		], is('veryverylo'))
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, 'veryverylon')
		], is('veryveryl…'))	
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, 'veryverylongtestthatcouldtbeshortenedwithevenmoreletters')
		], is('veryveryl…'))	
	}
	
	@Test
	@DisplayName("prints simple objects")
	def void printSimpleObject() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, aet.NonRoot => [id = 'id'; value = 'test'])
		], is('''
			NonRoot(
			        id="id"
			        value="test"
			)'''
		))
	}
	
	@Test
	@DisplayName("prints simple objects shortened by id")
	def void printSimpleObjectShortened() {
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, aet.NonRoot => [id = 'id'; value = 'test'])
		], is('''
			NonRoot(id="id")'''
		))
	}
	
	@Test
	@DisplayName("prints value-based objects")
	def void printValueBasedObject() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, aet.ValueBased => [value = 'test'])
		], is('''
			ValueBased#1(
			        value="test"
			        children=[]
			        referenced=[]
			)'''
		))
	}
	
	@Test
	@DisplayName("prints simple objects shortened by fallback id")
	def void printValuedObjectShortened() {
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1, aet.ValueBased => [value = 'test'])
		], is('''
			ValueBased#1'''
		))
	}
	
	@Test
	@DisplayName("prints resources")
	def void printResource() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, 
				new ResourceSetImpl().createResource(URI.createURI('test://foobar')) => [
					contents += aet.NonRoot => [id = 'first']
					contents += aet.ValueBased => [value = 'second']
					contents += aet.NonRootObjectContainerHelper => [id = 'third']
				]
		)], is('''
			Resource@<test://foobar>[
			        NonRoot(
			                id="first"
			                value=«'\u2205' /* empty set */»
			        ),
			        ValueBased#1(
			                value="second"
			                children=[]
			                referenced=[]
			        ),
			        NonRootObjectContainerHelper(
			                id="third"
			                nonRootObjectsContainment=[]
			        )
			]'''
		))
	}
	
	@Test
	@DisplayName("prints resources (shortened)")
	def void printResourceShortened() {
		assertThat(toString [
			new DefaultModelPrinter().printObjectShortened($0, $1,
				new ResourceSetImpl().createResource(URI.createURI('test://foobar')) => [
					contents += aet.NonRoot => [id = 'first']
					contents += aet.ValueBased => [value = 'second']
					contents += aet.NonRootObjectContainerHelper => [id = 'third']
				]
		)], is('''
			Resource@<test://foobar>[NonRoot(id="first"), ValueBased#1, NonRootObjectContainerHelper(id="third")]'''
		))
	}
	
	@Test
	@DisplayName("prints references by ID")
	def void printReferenceById() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, aet.ValueBased => [
				referenced += aet.ValueBased => [value = 'foo']
				referenced += aet.NonRoot => [id = 'subId']
			])
		], is('''
			ValueBased#1(
			        value=«'\u2205' /* empty set */»
			        children=[]
			        referenced=[
			                ValueBased#2,
			                NonRoot(id="subId")
			        ]
			)'''))
	}
	
	@Test
	@DisplayName("prints nested objects")
	def void printNested() {
		assertThat(toString [
			new DefaultModelPrinter().printObject($0, $1, aet.ValueBased => [
				val root = it
				value = 'root'
				children += aet.NonRoot => [
					id = "sub"
				]
				children += aet.ValueBased => [
					value = "sub"
					children += aet.ValueBased => [
						value = "subsub 1"
					]
					children += aet.ValueBased => [
						value = "subsub 2"
					]
					referenced += root.children.get(0)
				]
				referenced += (children.get(1) as ValueBased).children.get(1)
				referenced += children.get(0)
			])
		], is('''
			ValueBased#1(
			        value="root"
			        children=[
			                NonRoot(
			                        id="sub"
			                        value=«'\u2205' /* empty set */»
			                ),
			                ValueBased#2(
			                        value="sub"
			                        children=[
			                                ValueBased#3(
			                                        value="subsub 1"
			                                        children=[]
			                                        referenced=[]
			                                ),
			                                ValueBased#4(
			                                        value="subsub 2"
			                                        children=[]
			                                        referenced=[]
			                                )
			                        ]
			                        referenced=[NonRoot(id="sub")]
			                )
			        ]
			        referenced=[
			                ValueBased#4,
			                NonRoot(id="sub")
			        ]
			)'''
		))
	}
	
	def private static toString((PrintTarget, PrintIdProvider) => void printer) {
		val target = new DefaultPrintTarget
		printer.apply(target, new DefaultPrintIdProvider)
		return target.toString()
	}
}