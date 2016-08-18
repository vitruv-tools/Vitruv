package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.java2pcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.concurrent.Callable;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.members.Field;
import org.junit.Test;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.bridges.EMFCommandBridge;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.CompilationUnitManipulatorHelper;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.util.PCM2JaMoPPTestUtils;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class FieldMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    @Test
    public void testAddFieldToClassThatCorrespondsToCompositeDatatype() throws Throwable {
        super.addRepoContractsAndDatatypesPackage();
        final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
        final String fieldType = "String";
        final String fieldName = "stringField";

        final InnerDeclaration innerDeclaration = this.addFieldToClassWithName(cdt.getEntityName(), fieldType,
                fieldName, InnerDeclaration.class);

        this.assertInnerDeclaration(innerDeclaration, fieldType, fieldName);
    }

    @Test
    public void testRenameFieldInClassThatCorrespondsToCompositeDatatype() throws Throwable {
        final String fieldTypeName = "String";
        final String fieldName = "stringField";
        super.addRepoContractsAndDatatypesPackage();
        final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
        this.addFieldToClassWithName(cdt.getEntityName(), fieldTypeName, fieldName, InnerDeclaration.class);

        final String newFieldName = fieldName + PCM2JaMoPPTestUtils.RENAME;
        final InnerDeclaration newInnerDeclaration = this.renameFieldInClass(cdt.getEntityName(), fieldName,
                newFieldName);

        this.assertInnerDeclaration(newInnerDeclaration, fieldTypeName, newFieldName);
    }

    @Test
    public void testChangeTypeOfFieldInClassThatCorrespondsToCompositeDatatype() throws Throwable {
        final String fieldTypeName = "String";
        final String fieldName = "stringField";
        super.addRepoContractsAndDatatypesPackage();
        final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
        this.addFieldToClassWithName(cdt.getEntityName(), fieldTypeName, fieldName, InnerDeclaration.class);

        final String newFieldTypeName = "int";
        final InnerDeclaration newInnerDeclaration = this.changeFieldTypeInClass(cdt.getEntityName(), fieldName,
                newFieldTypeName);

        this.assertInnerDeclaration(newInnerDeclaration, newFieldTypeName, fieldName);
    }

    @Test
    public void testRemoveFieldInClassThatCorrespondsToBasicComponent() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddFieldToClassThatCorrespondsToBasicComponent() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddFieldInClassWithoutCorrespondence() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddFieldWithTypeOfInterface() throws Throwable {
        this.createRepoBasicComponentAndInterface();

        // create required role from PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Requiring" to
        // Interface
        final OperationRequiredRole orrToInterface = this.addFieldToClassWithName(
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Requiring" + "Impl", PCM2JaMoPPTestUtils.INTERFACE_NAME,
                "i" + PCM2JaMoPPTestUtils.INTERFACE_NAME, OperationRequiredRole.class);

        this.assertOperationRequiredRole(orrToInterface);
    }

    @Test
    public void testAddFieldWithTypeOfBasicComponentToClass() throws Throwable {
        this.createRepoBasicComponentAndInterface();

        // create required role from PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Requiring" to
        // PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Providing"
        final OperationRequiredRole orrToInterface = this.addFieldToClassWithName(
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Requiring" + "Impl",
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Providing" + "Impl",
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME.toLowerCase() + "Providing", OperationRequiredRole.class);

        this.assertOperationRequiredRole(orrToInterface);
    }

    private void createRepoBasicComponentAndInterface() throws Throwable, CoreException, InterruptedException {
        // create main package
        super.addRepoContractsAndDatatypesPackage();
        // create package and classes
        this.addPackageAndImplementingClass(PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Providing");
        this.addPackageAndImplementingClass(PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Requiring");
        // create interface
        final boolean throwExceptionIfFails = true;
        super.createInterfaceInPackage("contracts", throwExceptionIfFails, PCM2JaMoPPTestUtils.INTERFACE_NAME);
        // create provided role from providing compontent to interface
        super.addImplementsCorrespondingToOperationProvidedRoleToClass(
                PCM2JaMoPPTestUtils.BASIC_COMPONENT_NAME + "Providing" + "Impl", PCM2JaMoPPTestUtils.INTERFACE_NAME);
    }

    private void assertOperationRequiredRole(final OperationRequiredRole operationRequiredRole) throws Throwable {
        EMFCommandBridge.createAndExecuteVitruviusRecordingCommand(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
                Set<EObject> correspondingEObjects;
                try {
                    correspondingEObjects = CorrespondenceInstanceUtil.getCorrespondingEObjects(
                            FieldMappingTransformationTest.this.getCorrespondenceInstance(), operationRequiredRole);

                    boolean fieldFound = false;
                    for (final EObject correspondingEObject : correspondingEObjects) {
                        if (correspondingEObject instanceof Field) {
                            fieldFound = true;
                        } else {
                            fail("OperationRequiredRole should correspond to field only, but corresonds also to: "
                                    + correspondingEObject);
                        }
                    }
                    assertTrue("OperationRequiredRole does not correspond to a field", fieldFound);
                } catch (final Throwable e) {
                    if (e instanceof Exception) {
                        throw (Exception) e;
                    }
                    throw new RuntimeException(e);
                }
                return null;
            }
        }, this.getVSUM());

    }

    private InnerDeclaration renameFieldInClass(final String className, final String fieldName,
            final String newFieldName) throws Throwable {
        final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
                this.currentTestProject);
        final IType type = icu.getType(className);
        final IField fieldToRename = type.getField(fieldName);
        final String fieldToRenameStr = fieldToRename.getSource();
        final String fieldToRenameType = fieldToRenameStr.split(" ")[1];
        final String fieldToRenameName = fieldToRenameStr.split(" ")[2];
        final int offset = fieldToRename.getSourceRange().getOffset() + fieldToRenameStr.indexOf(fieldToRenameType)
                + fieldToRenameType.length() + 1;
        final int lengthToDelete = fieldToRenameName.length();
        final DeleteEdit deleteEdit = new DeleteEdit(offset, lengthToDelete);
        final InsertEdit insertEdit = new InsertEdit(offset, newFieldName + ";");
        CompilationUnitManipulatorHelper.editCompilationUnit(icu, deleteEdit, insertEdit);
        TestUtil.waitForSynchronization();
        final Field newJaMoPPField = this.getJaMoPPFieldFromClass(icu, newFieldName);
        return CollectionBridge.claimOne(CorrespondenceInstanceUtil.getCorrespondingEObjectsByType(
                this.getCorrespondenceInstance(), newJaMoPPField, InnerDeclaration.class));
    }

    private InnerDeclaration changeFieldTypeInClass(final String className, final String fieldName,
            final String newFieldTypeName) throws Throwable {
        final ICompilationUnit icu = CompilationUnitManipulatorHelper.findICompilationUnitWithClassName(className,
                this.currentTestProject);
        final IType type = icu.getType(className);
        final IField fieldToRename = type.getField(fieldName);
        final String fieldSrc = fieldToRename.getSource();
        final String fieldType = fieldSrc.split(" ")[1];
        final int offset = fieldToRename.getSourceRange().getOffset() + fieldSrc.indexOf(fieldType);
        final int lengthToDelete = fieldType.length();
        final DeleteEdit deleteEdit = new DeleteEdit(offset, lengthToDelete);
        final InsertEdit insertEdit = new InsertEdit(offset, newFieldTypeName);
        CompilationUnitManipulatorHelper.editCompilationUnit(icu, deleteEdit, insertEdit);
        TestUtil.waitForSynchronization();
        final Field newJaMoPPField = this.getJaMoPPFieldFromClass(icu, fieldName);
        return CollectionBridge.claimOne(CorrespondenceInstanceUtil.getCorrespondingEObjectsByType(
                this.getCorrespondenceInstance(), newJaMoPPField, InnerDeclaration.class));
    }

    private void assertInnerDeclaration(final InnerDeclaration innerDeclaration, final String fieldType,
            final String fieldName) throws Throwable {
        super.assertPCMNamedElement(innerDeclaration, fieldName);
        final String pcmDataTypeName = super.getNameFromPCMDataType(innerDeclaration.getDatatype_InnerDeclaration());
        assertEquals("The name of the PCM datatype does not equal the JaMoPP type name", pcmDataTypeName, fieldType);
    }

}
