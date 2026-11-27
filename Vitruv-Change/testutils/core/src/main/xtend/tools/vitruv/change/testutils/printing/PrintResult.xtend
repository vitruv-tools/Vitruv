package tools.vitruv.change.testutils.printing

enum PrintResult {
	/**
	 * The subject was printed entirely and successfully and produced some output.
	 */
	PRINTED,
	/**
	 * The subject was printed entirely and successfully and produced no output.
	 */
	PRINTED_NO_OUTPUT,
	/**
	 * This printer is not responsible for printing the subject and produced no ouptut..
	 */
	NOT_RESPONSIBLE
}
