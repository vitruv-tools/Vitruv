package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.plugintests;

import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IMethod;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class Tries extends FrameworkTestBase {

	@Test
	public void testRenameMethod_APDU_getCurrentState() throws Exception {
		IMethod methodToRename = codeElementUtil.getMethod("APDU.java", "APDU", "getCurrentState");
		assertNotNull(methodToRename);
		editorManipulator.renameMethod(methodToRename);
		waitForSynchronisationToFinish();
		this.createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRenameMethod_AID_equals() throws Exception {
		IMethod methodToRename = codeElementUtil.getMethod("AID.java", "AID", "equals", "Object");
		assertNotNull(methodToRename);
		editorManipulator.renameMethod(methodToRename);
		waitForSynchronisationToFinish();
		this.createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRenameMethod_JCSystem_isTransient() throws Exception {
		IMethod methodToRename = codeElementUtil.getMethod("JCSystem.java", "JCSystem", "isTransient");
		assertNotNull(methodToRename);
		editorManipulator.renameMethod(methodToRename);
		waitForSynchronisationToFinish();
		this.createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRenameMethod_BasicService_processCommand() throws Exception {
		IMethod methodToRename = codeElementUtil.getMethod("BasicService.java", "BasicService", "processCommand");
		assertNotNull(methodToRename);
		editorManipulator.renameMethod(methodToRename);
		waitForSynchronisationToFinish();
		this.createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRenameMethod_RMIService_processCommand() throws Exception {
		IMethod methodToRename = codeElementUtil.getMethod("RMIService.java", "RMIService", "processCommand");
		assertNotNull(methodToRename);
		editorManipulator.renameMethod(methodToRename);
		waitForSynchronisationToFinish();
		this.createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRenameField_APDU_STATE_INITIAL() throws Exception {
		IField fieldToRename = codeElementUtil.getField("APDU.java", "APDU", "STATE_INITIAL");
		assertNotNull(fieldToRename);
		editorManipulator.renameField(fieldToRename);
		waitForSynchronisationToFinish();
		this.createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRenameField_Signature_MODE_VERIFY() throws Exception {
		IField fieldToRename = codeElementUtil.getField("Signature.java", "Signature", "MODE_VERIFY");
		assertNotNull(fieldToRename);
		editorManipulator.renameField(fieldToRename);
		waitForSynchronisationToFinish();
		this.createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
	@Test
	public void testRenameField_Signature_Cipher_ALG_DES_CBC_ISO9797_M1() throws Exception {
		IField fieldToRename = codeElementUtil.getField("Cipher.java", "Cipher", "ALG_DES_CBC_ISO9797_M1");
		assertNotNull(fieldToRename);
		editorManipulator.renameField(fieldToRename);
		waitForSynchronisationToFinish();
		this.createAndCompareDiff(new Object(){}.getClass().getEnclosingMethod().getName());
	}
	
}
