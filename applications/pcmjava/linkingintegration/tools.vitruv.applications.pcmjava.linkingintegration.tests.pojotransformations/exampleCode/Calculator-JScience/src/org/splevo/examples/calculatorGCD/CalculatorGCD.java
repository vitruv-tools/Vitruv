package org.splevo.examples.calculatorGCD;

import java.io.InputStream;

import org.jscience.mathematics.number.LargeInteger;

/**
 * The calculator to perform numeric calculations. This makes use of the
 * LargeInteger class of the JScience mathematics library (www.jscience.org)
 * 
 * @author Benjamin Klatt
 * @author adapted by langhamm
 *
 */
public class CalculatorGCD {

	/**
	 * Calculate the greatest common denominator (divider) (GCD) of an integer
	 * 
	 * @param value1
	 *            The first number to get the gcd.
	 * @param value2
	 *            The second number to get the gcd.
	 * @return
	 */
	public String gcd(String value1, String value2,
			Class<? extends InputStream> someInputStream) {

		LargeInteger integerValue1 = valueOfHelper(value1);
		LargeInteger integerValue2 = valueOfHelper(value2);
		CalculatorGCDHelper calculatorGCDHelper = new CalculatorGCDHelper();
		LargeInteger gcd = calculatorGCDHelper.calculateGCD(integerValue1, integerValue2);

		return gcd.toString();
	}

	public String gcd2(int value1, int value2, int value) {

		LargeInteger integerValue1 = LargeInteger.valueOf(value1);
		LargeInteger integerValue2 = LargeInteger.valueOf(value2);
		LargeInteger gcd = integerValue1.gcd(integerValue2);

		return gcd.toString();
	}
	
	private LargeInteger valueOfHelper(String value){
		LargeInteger intValue = LargeInteger.valueOf(value);
		return intValue;
	}
}
