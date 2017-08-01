package tools.vitruv.dsls.reactions.generator

import org.junit.Test
import java.io.ByteArrayInputStream
import static tools.vitruv.dsls.reactions.generator.ReactionsEnvironmentGenerator.*

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import tools.vitruv.dsls.reactions.generator.ChangePropagationFileParser.ParseException
import java.util.List
import java.io.ByteArrayOutputStream
import com.google.common.io.ByteStreams
import java.util.LinkedList
import java.util.function.Predicate

class ChangePropagationFileParserTest {

	private static val EXPECTED_INDENT = '\n\t\t'

	def private parse(CharSequence s) {
		val input = new ByteArrayInputStream((s.toString).bytes)
		val output = ByteStreams.nullOutputStream
		val readExecutors = new LinkedList<String>
		new ChangePropagationFileParser(input, output).changeExecutors(#[], [readExecutors.add(it); true])
		readExecutors
	}
	
	def private withVersion(CharSequence s) {
		'''some text «VERSION_MARKER» «TEMPLATE_VERSION»''' + s
	}

	def private modify(CharSequence input, List<String> newExecutors, Predicate<String> retainExecutor) {
		val inputStream = new ByteArrayInputStream(input.toString.bytes)
		val outputStream = new ByteArrayOutputStream
		new ChangePropagationFileParser(inputStream, outputStream).changeExecutors(newExecutors, retainExecutor)
		outputStream.toString()
	}

	@Test(expected=ParseException)
	def void danglingComment() {
		parse('''abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, \n\n\n X::new, «EXECUTORS_MARKER_END» ignore'''.withVersion)
	}

	@Test(expected=ParseException)
	def void noConstructorReference() {
		parse('''abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar, X::new «EXECUTORS_MARKER_END» ignore'''.withVersion)
	}

	@Test(expected=ParseException)
	def void malformedConstructorReference() {
		parse('''abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar, X::ne «EXECUTORS_MARKER_END» ignore'''.withVersion)
	}

	@Test(expected=ParseException)
	def void noStart() {
		parse('''abc  HelloWorld::new, FooBar::new, X::new «EXECUTORS_MARKER_END» ignore'''.withVersion)
	}

	@Test(expected=ParseException)
	def void noEnd() {
		parse('''abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new ignore'''.withVersion)
	}
	
	@Test(expected=ParseException)
	def void noVersion() {
		parse('''abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new «EXECUTORS_MARKER_END» ignore''')
	}
	
	@Test(expected=ParseException)
	def void oldVersion() {
		parse('''foo bar «VERSION_MARKER» «TEMPLATE_VERSION - 1» abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new «EXECUTORS_MARKER_END» ignore''')
	}

	@Test
	def void correctResult() {
		var parsed = parse('''abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new «EXECUTORS_MARKER_END» ignore'''.withVersion)
		assertThat(parsed, is(#['HelloWorld', 'FooBar', 'X']))
		
		
		parsed = parse('''abc «EXECUTORS_MARKER_START» standard.test.HelloWorld::new, baz.FooBar::new, a.b.c.X::new «EXECUTORS_MARKER_END» ignore'''.withVersion)
		assertThat(parsed, is(#['standard.test.HelloWorld', 'baz.FooBar', 'a.b.c.X']))
	}

	@Test
	def void similarPrefix() {
		var List<String> parsed
		parsed = parse('''abc «EXECUTORS_MARKER_START.substring(0, EXECUTORS_MARKER_START.length - 1)»«EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new «EXECUTORS_MARKER_END» ignore'''.withVersion)
		assertThat(parsed, is(#['HelloWorld', 'FooBar', 'X']))
		
		
		parsed = parse('''abc «EXECUTORS_MARKER_START.substring(0, EXECUTORS_MARKER_START.length - 10)»«EXECUTORS_MARKER_START.substring(0, 5)»«EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new «EXECUTORS_MARKER_END» ignore'''.withVersion)
		assertThat(parsed, is(#['HelloWorld', 'FooBar', 'X']))

		parsed = parse('''abc «EXECUTORS_MARKER_START.substring(0, EXECUTORS_MARKER_START.length - 1)» in between «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new «EXECUTORS_MARKER_END» ignore'''.withVersion)
		assertThat(parsed, is(#['HelloWorld', 'FooBar', 'X']))

		parsed = parse('''abc «EXECUTORS_MARKER_START.substring(0, EXECUTORS_MARKER_START.length - 10)» in between «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new «EXECUTORS_MARKER_END» ignore'''.withVersion)
		assertThat(parsed, is(#['HelloWorld', 'FooBar', 'X']))
	}

	@Test
	def void empty() {
		val parsed = parse('''abc «EXECUTORS_MARKER_START»«EXECUTORS_MARKER_END» ignore'''.withVersion)
		assertThat(parsed, is(#[]))
	}

	@Test
	def void oneElement() {
		val parsed = parse('''abc «EXECUTORS_MARKER_START»Test::new«EXECUTORS_MARKER_END» ignore'''.withVersion)
		assertThat(parsed, is(#["Test"]))
	}

	@Test
	def void addSame() {
		val input = '''abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new«EXPECTED_INDENT»«EXECUTORS_MARKER_END» ignore'''.withVersion
		var result = modify(input, #['HelloWorld', 'FooBar', 'X'], [true])
		assertThat(result, is(input))

		result = modify(input, #['HelloWorld', 'FooBar', 'X'], [false])
		assertThat(result, is(input))
	}

	@Test
	def void replaceAll() {
		val input = '''abc «EXECUTORS_MARKER_START» HelloWorld::new, FooBar::new, X::new«EXECUTORS_MARKER_END» ignore'''.withVersion
		val result = modify(input, #['One', 'Two', 'Three'], [false])

		val expected = '''abc «EXECUTORS_MARKER_START» One::new,«EXPECTED_INDENT»Two::new,«EXPECTED_INDENT»Three::new«EXPECTED_INDENT»«EXECUTORS_MARKER_END» ignore'''.withVersion
		assertThat(result, is(expected))
	}
}
