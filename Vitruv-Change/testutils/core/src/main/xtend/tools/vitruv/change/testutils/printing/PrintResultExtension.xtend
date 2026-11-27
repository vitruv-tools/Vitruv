package tools.vitruv.change.testutils.printing

import edu.kit.ipd.sdq.activextendannotations.Utility
import static tools.vitruv.change.testutils.printing.PrintResult.*
import static com.google.common.base.Preconditions.checkNotNull

// Necessary because Xtend does not support methods on enums
@Utility
class PrintResultExtension {
	static def operator_plus(PrintResult a, PrintResult b) {
		checkNotNull(a, "previous result")
		checkNotNull(b, "latest result")
		switch (a) {
			case PRINTED:
				switch (b) {
					case PRINTED,
					case PRINTED_NO_OUTPUT: PRINTED
					case NOT_RESPONSIBLE: throw new IllegalStateException('''Got «NOT_RESPONSIBLE» after «PRINTED»!''')
				}
			case PRINTED_NO_OUTPUT:
				switch (b) {
					case PRINTED_NO_OUTPUT: PRINTED_NO_OUTPUT
					case NOT_RESPONSIBLE: NOT_RESPONSIBLE
					case PRINTED: PRINTED
				}
			case NOT_RESPONSIBLE: {
				switch (b) {
					case PRINTED: throw new IllegalStateException('''Got «PRINTED» after «NOT_RESPONSIBLE»!''')
					case PRINTED_NO_OUTPUT,
					case NOT_RESPONSIBLE: NOT_RESPONSIBLE
				}
			}
		}
	}
	
	static def combine(Iterable<? extends PrintResult> results) {
		results.fold(PRINTED_NO_OUTPUT)[$0 + $1]
	}

	static def appendIfPrinted(PrintResult result, ()=>PrintResult printer) {
		switch (result) {
			case PRINTED: result + printer.apply()
			case PRINTED_NO_OUTPUT,
			case NOT_RESPONSIBLE: result
		}
	}
}
