package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.members.Method;
import org.junit.Test;

import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationSignature;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class MethodMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    @Test
    public void testAddMethod() throws Throwable {
        super.addFirstPackage();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();

        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());

        this.assertOperationSignature(opSig, opInterface, PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME);
    }

    @Test
    public void testRenameMethod() throws Throwable {
        this.addFirstPackage();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();
        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());

        final OperationSignature newOpSig = super.renameMethodInClassWithName(opInterface.getEntityName(),
                opSig.getEntityName());

        this.assertOperationSignature(newOpSig, opInterface, PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME
                + PCM2JaMoPPTestUtils.RENAME);
    }

    @Test
    public void testAddReturnType() throws Throwable {
        this.addFirstPackage();

        final OperationInterface opInterface = super.addInterfaceInContractsPackage();
        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());

        final OperationSignature newOpSig = this.changeReturnType(opSig);

        this.assertOperationSignature(newOpSig, opInterface, PCM2JaMoPPTestUtils.OPERATION_SIGNATURE_1_NAME);
    }

    private OperationSignature changeReturnType(final OperationSignature opSig) throws Throwable {
        final String className = opSig.getInterface__OperationSignature().getEntityName();
        final String methodName = opSig.getEntityName();
        final ICompilationUnit cu = super.findCompilationWithClassName(className);
        final IMethod iMethod = cu.getType(className).getMethod(methodName, null);
        final int returnTypeOffset = iMethod.getSourceRange().getOffset();
        final String retTypeName = iMethod.getSource().split(" ")[0];
        final int returnTypeLength = retTypeName.length();
        final String newReturnTypeName = "String ";
        final DeleteEdit deleteEdit = new DeleteEdit(returnTypeOffset, returnTypeLength);
        final InsertEdit insertEdit = new InsertEdit(returnTypeOffset, newReturnTypeName);
        super.editCompilationUnit(cu, deleteEdit, insertEdit);
        TestUtil.waitForSynchronization();
        return super.findOperationSignatureForJaMoPPMethodInCompilationUnit(methodName, className, cu);
    }

    private void assertOperationSignature(final OperationSignature opSig, final OperationInterface opInterface,
            final String expectedName) throws Throwable {
        assertEquals("OperationSignature " + opSig + " is not in OperationInterface " + opInterface, opSig
                .getInterface__OperationSignature().getId(), opInterface.getId());
        this.assertPCMNamedElement(opSig, expectedName);

        final Method jaMoPPMethod = this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(opSig,
                Method.class);
        super.assertDataTypeName(jaMoPPMethod.getTypeReference(), opSig.getReturnType__OperationSignature());
    }
}
