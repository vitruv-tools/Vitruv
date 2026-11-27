package tools.vitruv.change.testutils.printing

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName
import tools.vitruv.change.testutils.printing.DefaultPrintTarget
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.CoreMatchers.is
import static tools.vitruv.change.testutils.printing.PrintResult.*
import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.fail
import static tools.vitruv.change.testutils.printing.PrintMode.*
import tools.vitruv.change.testutils.printing.PrintResult
import tools.vitruv.change.testutils.printing.PrintTarget
import java.util.List

class DefaultPrintTargetTest {
	@Test
	@DisplayName("stores the empty string by default")
	def void emptyByDefault() {
		assertThat(new DefaultPrintTarget().toString(), is(""))
	}
	
	@Test
	@DisplayName("indicates if printed text was empty")
	def void indicatesEmptyText() {
		assertThat(new DefaultPrintTarget().print(''), is(PRINTED_NO_OUTPUT))
		assertThat((new DefaultPrintTarget() => [print('preprint')]).print(''), is(PRINTED_NO_OUTPUT))
	}
	
	@Test
	@DisplayName("indicates if printed text was not empty")
	def void indicatesNotEmptyText() {
		assertThat(new DefaultPrintTarget().print(' '), is(PRINTED))
		assertThat((new DefaultPrintTarget() => [print('')]).print(' '), is(PRINTED))
	}
	
	@Test
	@DisplayName("stores printed text")
	def void storesPrintedText() {
		val printed = new DefaultPrintTarget => [print('test')]
		assertThat(printed.toString(), is("test"))
	}
	
	@Test
	@DisplayName("stores multiple lines")
	def void multipleLines() {
		val printed = new DefaultPrintTarget => [
			print('test')
			newLine()
			print('more')
			newLine()
			print('text')
		]
		assertThat(printed.toString(), is('''
			test
			more
			text'''
		))
	} 
	
	@Test
	@DisplayName("allows to start with a new line")
	def void newlineAtStart() {
		val printed = new DefaultPrintTarget => [newLine()]
		assertThat(printed.toString(), is(System.lineSeparator))
	} 
	
	@Test
	@DisplayName("indicates printed text after a new line") 
	def void newLinePrinted() {
		assertThat(new DefaultPrintTarget().newLine(), is(PRINTED))
	}
	
	@Test
	@DisplayName("can increase and decrease the indent")
	def void indent() {
		val printed = new DefaultPrintTarget => [
			print('test')
			newLineIncreaseIndent()
			print('more')
			newLineIncreaseIndent()
			print('text')
			newLineDecreaseIndent()
			print("on")
			newLineIncreaseIndent()
			print("different")
			newLineDecreaseIndent()
			newLineDecreaseIndent()
			print("levels")
		]
		assertThat(printed.toString(), is('''
			test
			        more
			                text
			        on
			                different
			        
			levels'''
		))
	}
	
	@Test
	@DisplayName("forbids decreasing the indent below 0")
	def void noLessThanZeroIndent() {
		assertThrows(IllegalStateException) [
			new DefaultPrintTarget().newLineDecreaseIndent()			
		]
		
		val target = new DefaultPrintTarget()
		target.newLineIncreaseIndent()		
		target.newLineIncreaseIndent()	
		target.newLineDecreaseIndent()
		target.newLineIncreaseIndent()
		target.newLineDecreaseIndent()
		target.newLineDecreaseIndent()		
		assertThrows(IllegalStateException) [
			target.newLineDecreaseIndent()			
		]
	}
	
	@Test
	@DisplayName("prints empty iterables")
	def void emptyIterables() {
		val (PrintTarget, Object)=>PrintResult shouldNotBeCalled = [fail("the print function should not be called!")]
		var printed = new DefaultPrintTarget() => [
			printIterableElements(emptyList, SINGLE_LINE_LIST, shouldNotBeCalled)
		]
		assertThat(printed.toString(), is(""))
		
		printed = new DefaultPrintTarget() => [
			printIterable('/{', '}!', emptyList, SINGLE_LINE_LIST, shouldNotBeCalled)
		]
		assertThat(printed.toString(), is("/{}!"))
		
		printed = new DefaultPrintTarget() => [
			printList(emptyList, SINGLE_LINE_LIST, shouldNotBeCalled)
		]
		assertThat(printed.toString(), is("[]"))
		
		printed = new DefaultPrintTarget() => [
			printSet(emptyList, SINGLE_LINE_LIST, shouldNotBeCalled)
		]
		assertThat(printed.toString(), is("{}"))
	}
	
	@Test
	@DisplayName("reports if nothing was printed when printing an empty iterables")
	def void printedNoOutputIfNoOutputForIterable() {
		assertThat(new DefaultPrintTarget().printIterableElements(emptyList, SINGLE_LINE_LIST)[], is(PRINTED_NO_OUTPUT))
	}
	
	@Test
	@DisplayName("prints iterables as a single line")
	def void iterableSingleLine() {
		var printed = new DefaultPrintTarget() => [
			printIterable('(', ')', List.of(1), SINGLE_LINE_LIST) [$0.print(String.valueOf($1))]
		]
		assertThat(printed.toString(), is("(1)"))
		
		printed = new DefaultPrintTarget() => [
			printIterable('(', ')', List.of(1, 2, 3, 4, 5, 6), SINGLE_LINE_LIST) [$0.print(String.valueOf($1))]
		]
		assertThat(printed.toString(), is("(1, 2, 3, 4, 5, 6)"))
	}
	
	@Test
	@DisplayName("prints iterables on multiple lines")
	def void iterableMultiLine() {
		var printed = new DefaultPrintTarget() => [
			printIterable('(', ')', emptyList, MULTI_LINE_LIST) [$0.print($1)]
		]
		assertThat(printed.toString(), is("()"))
		
		printed = new DefaultPrintTarget() => [
			printIterable('(', ')', List.of(1), MULTI_LINE_LIST) [$0.print(String.valueOf($1))]
		]
		assertThat(printed.toString(), is('''
			(
			        1
			)'''
		))
		
		printed = new DefaultPrintTarget() => [
			printIterable('(', ')', List.of(1, 2, 3, 4, 5, 6), MULTI_LINE_LIST) [$0.print(String.valueOf($1))]
		]
				assertThat(printed.toString(), is('''
			(
			        1,
			        2,
			        3,
			        4,
			        5,
			        6
			)'''
		))
	}
	
	@Test
	@DisplayName("respects the requested threshold for multiple lines when printing iterables")
	def void iterableMultilineThreshold() {
		val printMode = multiLineIfAtLeast(4).withSeparator("; ")
		var printed = new DefaultPrintTarget() => [
			printIterable('(', ')', emptyList, printMode) [$0.print($1)]
		]
		assertThat(printed.toString(), is("()"))
		
		printed = new DefaultPrintTarget() => [
			printIterable('(', ')', List.of(1), printMode) [$0.print(String.valueOf($1))]
		]
		assertThat(printed.toString(), is('''(1)'''))
		
		printed = new DefaultPrintTarget() => [
			printIterable('(', ')', List.of(1, 2, 3), printMode) [$0.print(String.valueOf($1))]
		]
		assertThat(printed.toString(), is('''(1; 2; 3)'''))
		
		printed = new DefaultPrintTarget() => [
			printIterable('(', ')', List.of(1, 2, 3, 4), printMode) [$0.print(String.valueOf($1))]
		]
		assertThat(printed.toString(), is('''
			(
			        1; 
			        2; 
			        3; 
			        4
			)'''
		))
		
		printed = new DefaultPrintTarget() => [
			printIterable('(', ')', List.of(1, 2, 3, 4, 5, 6), printMode) [$0.print(String.valueOf($1))]
		]
				assertThat(printed.toString(), is('''
			(
			        1; 
			        2; 
			        3; 
			        4; 
			        5; 
			        6
			)'''
		))
	}
	
	@Test
	@DisplayName("returns NOT_RESPONSIBLE when printing an iterable the printer is not responsible for")
	def void iterableNotResponsible() {
		val target = new DefaultPrintTarget()
		assertThat(target.printIterable('a', 'b', List.of(1, 2, 3, 4), SINGLE_LINE_LIST) [print("bad"); NOT_RESPONSIBLE],
			is(NOT_RESPONSIBLE))
		assertThat(target.toString(), is(""))			
	}
	
	@Test
	@DisplayName("throws if getting NOT_RESPONSIBLE after PRINTED when printing an iterable")
	def void iterableNotResponsibleAfterPrinted() {
		assertThrows(IllegalStateException) [
			new DefaultPrintTarget().printIterable('a', 'b', List.of(1, 2, 3, 4), SINGLE_LINE_LIST) [ target, number |
				 switch (number) {
				 	case 1: PRINTED
				 	case 2: NOT_RESPONSIBLE
				 	default: PRINTED
				 }
			]
		]
	}
	
	@Test
	@DisplayName("tolerates if getting NOT_RESPONSIBLE after PRINTED_NO_OUTPUT when printing an iterable ")
	def void iterableNotResponsibleAfterPrintedNoOutput() {
		val target = new DefaultPrintTarget()
		assertThat(target.printIterable('a', 'b', List.of(1, 2, 3, 4), SINGLE_LINE_LIST) [ subTarget, number |
			subTarget.print("bad")
			 switch (number) {
			 	case 1: PRINTED_NO_OUTPUT
			 	case 2: NOT_RESPONSIBLE
			 	default: PRINTED
			 }
		], is(NOT_RESPONSIBLE))
		assertThat(target.toString(), is(""))
	}
	
	@Test
	@DisplayName("appends printed iterables to previously printed text")
	def void appendIterable() {
		val printed = new DefaultPrintTarget => [
			print("previous")
			printIterable('(', ')', List.of(1, 2, 3), MULTI_LINE_LIST) [$0.print(String.valueOf($1))]
		]
		assertThat(printed.toString(), is('''
			previous(
			        1,
			        2,
			        3
			)'''
		))
	}
	
	@Test
	@DisplayName("manages indents correctly when printing nested iterables")
	def void nestedIterables() {
		val printMode = multiLineIfAtLeast(2).withSeparator(',')
		val printed = new DefaultPrintTarget => [
			printIterable('(', ')', List.of(
				List.of(List.of(1), List.of(2, 2, 2 ,2)),
				emptyList,
				List.of(List.of(3)),
				List.of(emptyList, List.of(4, 4), List.of(5))
			), printMode) [subTarget, subList |
				subTarget.printIterable('[', ']', subList, printMode) [subSubTarget, subSubList |
					subSubTarget.printIterable('{', '}', subSubList, printMode) [$0.print(String.valueOf($1))]
				]
			]
		]
		assertThat(printed.toString(), is('''
			(
			        [
			                {1},
			                {
			                        2,
			                        2,
			                        2,
			                        2
			                }
			        ],
			        [],
			        [{3}],
			        [
			                {},
			                {
			                        4,
			                        4
			                },
			                {5}
			        ]
			)'''
		))
	}
}