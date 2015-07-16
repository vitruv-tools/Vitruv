package edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.jamopp2pcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.text.edits.DeleteEdit;
import org.eclipse.text.edits.InsertEdit;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.junit.Test;

import org.palladiosimulator.pcm.repository.CompositeDataType;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.tests.casestudies.pcmjava.transformations.utils.PCM2JaMoPPTestUtils;
import edu.kit.ipd.sdq.vitruvius.tests.util.TestUtil;

public class FieldMappingTransformationTest extends JaMoPP2PCMTransformationTest {

    @Test
    public void testAddFieldToClassThatCorrespondsToCompositeDatatype() throws Throwable {
        super.addFirstPackage();
        final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
        final String fieldType = "String";
        final String fieldName = "stringField";

        final InnerDeclaration innerDeclaration = this.addFieldToClassWithName(cdt.getEntityName(), fieldType,
                fieldName);

        this.assertInnerDeclaration(innerDeclaration, fieldType, fieldName);
    }

    @Test
    public void testRenameFieldInClassThatCorrespondsToCompositeDatatype() throws Throwable {
        final String fieldTypeName = "String";
        final String fieldName = "stringField";
        super.addFirstPackage();
        final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
        this.addFieldToClassWithName(cdt.getEntityName(), fieldTypeName, fieldName);

        final String newFieldName = fieldName + PCM2JaMoPPTestUtils.RENAME;
        final InnerDeclaration newInnerDeclaration = this.renameFieldInClass(cdt.getEntityName(), fieldName,
                newFieldName);

        this.assertInnerDeclaration(newInnerDeclaration, fieldTypeName, newFieldName);
    }

    @Test
    public void testChangeTypeOfFieldInClassThatCorrespondsToCompositeDatatype() throws Throwable {
        final String fieldTypeName = "String";
        final String fieldName = "stringField";
        super.addFirstPackage();
        final CompositeDataType cdt = super.addClassThatCorrespondsToCompositeDatatype();
        this.addFieldToClassWithName(cdt.getEntityName(), fieldTypeName, fieldName);

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
    public void testAddFieldWithTypeOfBasicComponentToClass() {
        fail("Not yet implemented");
    }

    private InnerDeclaration addFieldToClassWithName(final String className, final String fieldType,
            final String fieldName) throws Throwable {
        final ICompilationUnit icu = super.findICompilationUnitWithClassName(className);
        final IType iClass = icu.getAllTypes()[0];
        final int offset = super.getOffsetForClassifierManipulation(iClass);
        final String fieldStr = "private " + fieldType + " " + fieldName + ";";
        final InsertEdit insertEdit = new InsertEdit(offset, fieldStr);
        super.editCompilationUnit(icu, insertEdit);
        TestUtil.waitForSynchronization();
        final Field jaMoPPField = this.getJaMoPPFieldFromClass(icu, fieldName);
        return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(jaMoPPField,
                InnerDeclaration.class);
    }

    private InnerDeclaration renameFieldInClass(final String className, final String fieldName,
            final String newFieldName) throws Throwable {
        final ICompilationUnit icu = super.findICompilationUnitWithClassName(className);
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
        super.editCompilationUnit(icu, deleteEdit, insertEdit);
        TestUtil.waitForSynchronization();
        final Field newJaMoPPField = this.getJaMoPPFieldFromClass(icu, newFieldName);
        return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(newJaMoPPField,
                InnerDeclaration.class);

    }

    private InnerDeclaration changeFieldTypeInClass(final String className, final String fieldName,
            final String newFieldTypeName) throws Throwable {
        final ICompilationUnit icu = super.findICompilationUnitWithClassName(className);
        final IType type = icu.getType(className);
        final IField fieldToRename = type.getField(fieldName);
        final String fieldSrc = fieldToRename.getSource();
        final String fieldType = fieldSrc.split(" ")[1];
        final int offset = fieldToRename.getSourceRange().getOffset() + fieldSrc.indexOf(fieldType);
        final int lengthToDelete = fieldType.length();
        final DeleteEdit deleteEdit = new DeleteEdit(offset, lengthToDelete);
        final InsertEdit insertEdit = new InsertEdit(offset, newFieldTypeName);
        super.editCompilationUnit(icu, deleteEdit, insertEdit);
        TestUtil.waitForSynchronization();
        final Field newJaMoPPField = this.getJaMoPPFieldFromClass(icu, fieldName);
        return this.getCorrespondenceInstance().claimUniqueCorrespondingEObjectByType(newJaMoPPField,
                InnerDeclaration.class);
    }

    private Field getJaMoPPFieldFromClass(final ICompilationUnit icu, final String fieldName) {
        final ConcreteClassifier cc = super.getJaMoPPClassifierForVURI(VURI.getInstance(icu.getResource()));
        final Field field = (Field) cc.getMembersByName(fieldName).get(0);
        return field;
    }

    private void assertInnerDeclaration(final InnerDeclaration innerDeclaration, final String fieldType,
            final String fieldName) {
        super.assertPCMNamedElement(innerDeclaration, fieldName);
        final String pcmDataTypeName = super.getNameFromPCMDataType(innerDeclaration.getDatatype_InnerDeclaration());
        assertEquals("The name of the PCM datatype does not equal the JaMoPP type name", pcmDataTypeName, fieldType);
    }

}
