package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.junit.Test;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.CompilationUnitManipulatorHelper;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class JaMoPPParameterMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    @Test
    public void testAddParameter() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();
        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());

        final Parameter parameter = super.addParameterToSignature(opInterface.getEntityName(), opSig.getEntityName(),
                "String", PCM2JaMoPPTestUtils.PARAMETER_NAME);

        this.assertParameter(opSig, parameter, "String", PCM2JaMoPPTestUtils.PARAMETER_NAME);
    }

    /**
     *
     * @throws Throwable
     */
    @Test
    public void testRenameParameter() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();
        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());
        final Parameter parameter = super.addParameterToSignature(opInterface.getEntityName(), opSig.getEntityName(),
                "String", PCM2JaMoPPTestUtils.PARAMETER_NAME);

        final Parameter newParameter = this.renameParameterInSignature(opInterface.getEntityName(),
                opSig.getEntityName(), parameter.getEntityName(),
                PCM2JaMoPPTestUtils.PARAMETER_NAME + PCM2JaMoPPTestUtils.RENAME);

        this.assertParameter(opSig, newParameter, "String",
                PCM2JaMoPPTestUtils.PARAMETER_NAME + PCM2JaMoPPTestUtils.RENAME);
    }

    @Test
    public void testChangeParameterType() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();
        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());
        final Parameter parameter = super.addParameterToSignature(opInterface.getEntityName(), opSig.getEntityName(),
                "String", PCM2JaMoPPTestUtils.PARAMETER_NAME);
        final String expectedParamType = "int";

        final Parameter changedParameter = this.changeParameterType(opInterface.getEntityName(), opSig.getEntityName(),
                parameter.getEntityName(), expectedParamType);

        this.assertParameter(opSig, changedParameter, expectedParamType, changedParameter.getEntityName());
    }

    private Parameter renameParameterInSignature(final String interfaceName, final String methodName,
            final String oldParameterName, final String newParameterName) throws Throwable {
        final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(interfaceName,
                this.currentTestProject);
        final IMethod iMethod = this.findIMethodByName(interfaceName, methodName, icu);
        final ILocalVariable localVariable = this.findParameterInIMethod(iMethod, oldParameterName);
        final String typeName = localVariable.getSource().split(" ")[0];
        final String paramName = localVariable.getSource().split(" ")[1];
        final int offset = localVariable.getSourceRange().getOffset() + typeName.length() + 1;
        final int length = paramName.length();
        final DeleteEdit deleteEdit = new DeleteEdit(offset, length);
        final InsertEdit insertEdit = new InsertEdit(offset, newParameterName);
        CompilationUnitManipulatorHelper.editCompilationUnit(icu, deleteEdit, insertEdit);
        TestUtil.waitForSynchronization();
        final org.emftext.language.java.parameters.Parameter newJaMoPPParameter = super.findJaMoPPParameterInICU(icu,
                interfaceName, methodName, newParameterName);
        return CollectionBridge.claimOne(CorrespondenceInstanceUtil
                .getCorrespondingEObjectsByType(this.getCorrespondenceInstance(), newJaMoPPParameter, Parameter.class));
    }

    private Parameter changeParameterType(final String interfaceName, final String methodName, final String paramName,
            final String newTypeName) throws Throwable {
        final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(interfaceName,
                this.currentTestProject);
        final IMethod iMethod = this.findIMethodByName(interfaceName, methodName, icu);
        final ILocalVariable parameter = this.findParameterInIMethod(iMethod, paramName);
        final int offset = parameter.getSourceRange().getOffset();
        final int length = parameter.getSource().split(" ")[0].length();
        final DeleteEdit deleteEdit = new DeleteEdit(offset, length);
        final InsertEdit insertEdit = new InsertEdit(offset, newTypeName + " ");
        CompilationUnitManipulatorHelper.editCompilationUnit(icu, deleteEdit, insertEdit);
        TestUtil.waitForSynchronization();
        final org.emftext.language.java.parameters.Parameter newJaMoPPParameter = super.findJaMoPPParameterInICU(icu,
                interfaceName, methodName, paramName);
        return CollectionBridge.claimOne(CorrespondenceInstanceUtil
                .getCorrespondingEObjectsByType(this.getCorrespondenceInstance(), newJaMoPPParameter, Parameter.class));
    }

    private ILocalVariable findParameterInIMethod(final IMethod iMethod, final String parameterName)
            throws JavaModelException {
        for (final ILocalVariable localVariable : iMethod.getParameters()) {
            if (localVariable.getElementName().equals(parameterName)) {
                return localVariable;
            }
        }
        throw new RuntimeException("Old parameter with name " + parameterName + " not found");
    }

    private void assertParameter(final OperationSignature opSig, final Parameter parameter,
            final String expectedTypeName, final String expectedName) throws Throwable {
        assertEquals("The parameter is not contained in the expected operation signature", opSig.getId(),
                parameter.getOperationSignature__Parameter().getId());
        this.assertPCMNamedElement(parameter, expectedName);
        if (parameter.getDataType__Parameter() instanceof CollectionDataType
                || parameter.getDataType__Parameter() instanceof CompositeDataType) {
            this.assertPCMNamedElement((NamedElement) parameter.getDataType__Parameter(), expectedTypeName);
        } else {
            final String primitiveTypeName = this
                    .getNameFromPCMPrimitiveDataType((PrimitiveDataType) parameter.getDataType__Parameter());
            assertTrue("The primitve type parameter has the wrong name",
                    expectedTypeName.equalsIgnoreCase(primitiveTypeName));
        }
    }
}
