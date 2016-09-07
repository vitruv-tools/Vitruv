package org.splevo.examples.calculatorGCD;

import org.jscience.mathematics.number.LargeInteger;

public class CalculatorGCDHelper {
	
	public LargeInteger calculateGCD(LargeInteger integerValue1, LargeInteger integerValue2) {
		LargeInteger gcd = integerValue1.gcd(integerValue2);
		return gcd;
	}
}
