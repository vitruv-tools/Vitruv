package tools.vitruv.applications.pcmjava.tests.pojotransformations.java2pcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.text.edits.ReplaceEdit;
import org.junit.Test;
import org.palladiosimulator.pcm.core.entity.NamedElement;
import org.palladiosimulator.pcm.repository.CollectionDataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.Parameter;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;

import tools.vitruv.applications.pcmjava.tests.util.CompilationUnitManipulatorHelper;
import tools.vitruv.applications.pcmjava.tests.util.PCM2JaMoPPTestUtils;
import tools.vitruv.framework.correspondence.CorrespondenceModelUtil;
import tools.vitruv.framework.util.bridges.CollectionBridge;

public class JaMoPPParameterMappingTransformationTest extends Java2PCMPackageMappingTransformationTest {

    @Test
    public void testAddParameter() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        final OperationInterface opInterface = super.addInterfaceInContractsPackage();
        final OperationSignature opSig = super.addMethodToInterfaceWithCorrespondence(opInterface.getEntityName());

        final Parameter parameter = super.addParameterToSignature(opInterface.getEntityName(), opSig.getEntityName(),
                "String", PCM2JaMoPPTestUtils.PARAMETER_NAME, null);

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
                "String", PCM2JaMoPPTestUtils.PARAMETER_NAME, null);

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
                "String", PCM2JaMoPPTestUtils.PARAMETER_NAME, null);
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
        final ReplaceEdit replaceEdit = new ReplaceEdit(offset, length, newParameterName);
        editCompilationUnit(icu, replaceEdit);
        final org.emftext.language.java.parameters.Parameter newJaMoPPParameter = super.findJaMoPPParameterInICU(icu,
                interfaceName, methodName, newParameterName);
        return CollectionBridge.claimOne(CorrespondenceModelUtil
                .getCorrespondingEObjectsByType(this.getCorrespondenceModel(), newJaMoPPParameter, Parameter.class));
    }

    private Parameter changeParameterType(final String interfaceName, final String methodName, final String paramName,
            final String newTypeName) throws Throwable {
        final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(interfaceName,
                this.currentTestProject);
        final IMethod iMethod = this.findIMethodByName(interfaceName, methodName, icu);
        final ILocalVariable parameter = this.findParameterInIMethod(iMethod, paramName);
        final int offset = parameter.getSourceRange().getOffset();
        final int length = parameter.getSource().split(" ")[0].length();
        final ReplaceEdit replaceEdit = new ReplaceEdit(offset, length, newTypeName);
        editCompilationUnit(icu, replaceEdit);
        final org.emftext.language.java.parameters.Parameter newJaMoPPParameter = super.findJaMoPPParameterInICU(icu,
                interfaceName, methodName, paramName);
        return CollectionBridge.claimOne(CorrespondenceModelUtil
                .getCorrespondingEObjectsByType(this.getCorrespondenceModel(), newJaMoPPParameter, Parameter.class));
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
