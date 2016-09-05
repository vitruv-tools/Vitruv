package edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.tests;

import org.eclipse.emf.common.util.URI;
import org.junit.Test;

import edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.extractors.JaMoPPContentValidator;
import edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.extractors.IContentValidator;

import org.junit.Assert;

public class JaMoPPContentValidatorTest {
	
	@Test
	public void testPositiveSample() throws Exception {
		IContentValidator validator = new JaMoPPContentValidator();
		String validClass = "package eu.fpetersen.test;\npublic class Test {\n  public void above(){\n  }\n  public void testRenamedAgain(){\n  }\n  public void below(){\n  }\n}\n";
		boolean result = validator.isValid(validClass, URI.createPlatformResourceURI("test", true));
		Assert.assertTrue("Class is valid", result);
	}
	
	@Test
	public void testObviousNegativeSample() throws Exception {
		IContentValidator validator = new JaMoPPContentValidator();
		String validClass = "dsfhiohfp+sdhgioafdhgodhghpihihie86738zoshfuuß0hrofh08z83urhdfh/%=76s fdjf";
		boolean result = validator.isValid(validClass, URI.createPlatformResourceURI("test", true));
		Assert.assertFalse("Random string is invalid", result);
	}
	
	@Test
	public void testNegativeSample() throws Exception {
		IContentValidator validator = new JaMoPPContentValidator();
		String validClass = "package eu.fpetersen.test;\npublic class Test {\n  public void above()\n  }\n  public void testRenamedAgain(){\n  }\n  public void below(){\n  }\n}\n";
		boolean result = validator.isValid(validClass, URI.createPlatformResourceURI("test", true));
		Assert.assertFalse("Missing brackets string is invalid", result);
	}

}
