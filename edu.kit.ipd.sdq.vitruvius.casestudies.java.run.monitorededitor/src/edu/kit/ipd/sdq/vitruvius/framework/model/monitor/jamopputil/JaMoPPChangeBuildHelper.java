package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.containers.ContainersFactory;
import org.emftext.language.java.containers.Package;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypedElement;

import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributeFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.UpdateSingleValuedEAttribute;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferenceFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.UpdateSingleValuedNonContainmentEReference;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ReplaceNonRootEObjectSingle;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.CreateRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.DeleteRootEObject;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.object.ObjectFactory;

// TODO are those EChanges correct?
public class JaMoPPChangeBuildHelper {

    private static final String NAME_ATTRIBUTE = "name";

    private static <T extends NamedElement> EChange createRenameChange(T originalEObject, T renamedEObject) {
        UpdateSingleValuedEAttribute<String> updateEAttribute = AttributeFactory.eINSTANCE
                .createUpdateSingleValuedEAttribute();
        updateEAttribute.setOldAffectedEObject(originalEObject);
        updateEAttribute.setNewAffectedEObject(renamedEObject);
        EClass eClass = originalEObject.eClass();
        EAttribute nameAttribute = (EAttribute) eClass.getEStructuralFeature(NAME_ATTRIBUTE);
        updateEAttribute.setAffectedFeature(nameAttribute);
        String oldName = originalEObject.getName();
        String newName = renamedEObject.getName();
        updateEAttribute.setOldValue(oldName);
        updateEAttribute.setNewValue(newName);
        return updateEAttribute;
    }

    public static EChange createRenameMethodChange(Method originalMethod, Method renamedMethod) {
        return createRenameChange(originalMethod, renamedMethod);
    }

    public static EChange createRenameFieldChange(Field originalField, Field renamedField) {
        return createRenameChange(originalField, renamedField);
    }

    public static EChange createRenameClassChange(Class originalClass, Class renamedClass) {
        return createRenameChange(originalClass, renamedClass);
    }

    public static EChange createRenameInterfaceChange(Interface originalInterface, Interface renamedInterface) {
        return createRenameChange(originalInterface, renamedInterface);
    }

    /**
     * @param originalMethod
     * @param changedMethod
     * @return
     * 
     * @throws IllegalStateException
     *             if new return type is unknown
     */
    public static EChange createChangeMethodReturnTypeChange(Method originalMethod, Method changedMethod) {
        return createChangeTypeChange(originalMethod, changedMethod);
    }

    public static EChange createChangeFieldTypeChange(Field originalField, Field changedField) {
        return createChangeTypeChange(originalField, changedField);
    }

    private static EChange createChangeTypeChange(TypedElement original, TypedElement changed) {
        ReplaceNonRootEObjectSingle<TypeReference> updateEReference = ContainmentFactory.eINSTANCE.createReplaceNonRootEObjectSingle();
        updateEReference.setOldAffectedEObject(original);
        updateEReference.setNewAffectedEObject(changed);
        EReference typeReference = original.getTypeReference().eContainmentFeature();
        updateEReference.setAffectedFeature(typeReference);
        updateEReference.setOldValue(original.getTypeReference());
        updateEReference.setNewValue(changed.getTypeReference());
        return updateEReference;
    }

    private static EChange createChangeSuperClassChange(Class changedClass, TypeReference superClass) {
        ReplaceNonRootEObjectSingle<TypeReference> updateEReference = ContainmentFactory.eINSTANCE.createReplaceNonRootEObjectSingle();
        updateEReference.setOldAffectedEObject(changedClass);
        updateEReference.setNewAffectedEObject(superClass.eContainer());
        EReference superClassReference = superClass.eContainmentFeature();
        updateEReference.setAffectedFeature(superClassReference);
        updateEReference.setOldValue((TypeReference) changedClass.eGet(superClassReference));
        updateEReference.setNewValue(superClass);
        return updateEReference;
    }

    private static EChange createAddSuperTypeChange(ConcreteClassifier originalBase, TypeReference superType) {
        return createAddNonRootEObjectInListChange(superType, originalBase);
    }

    public static EChange createAddClassChange(Class newClass, CompilationUnit beforeChange) {
        return createAddNonRootEObjectInListChange(newClass, beforeChange);
    }

    public static EChange createRemovedClassChange(Class removedClass, CompilationUnit afterChange) {
        return createDeleteNonRootEObjectInListChange(removedClass, afterChange);
    }

    public static EChange createCreateInterfaceChange(Interface newInterface, CompilationUnit beforeChange) {
        return createAddNonRootEObjectInListChange(newInterface, beforeChange);
    }

    public static EChange createRemovedInterfaceChange(Interface removedInterface, CompilationUnit afterChange) {
        return createDeleteNonRootEObjectInListChange(removedInterface, afterChange);
    }

    public static EChange createAddImportChange(Import imp, CompilationUnit beforeChange) {
        return createAddNonRootEObjectInListChange(imp, beforeChange);
    }

    public static EChange createRemoveImportChange(Import imp, CompilationUnit afterChange) {
        return createDeleteNonRootEObjectInListChange(imp, afterChange);
    }

    public static EChange createAddMethodChange(Method newMethod, ConcreteClassifier beforeChange) {
        return createAddNonRootEObjectInListChange(newMethod, beforeChange);
    }

    public static EChange createRemoveMethodChange(Method deletedMethod, ConcreteClassifier afterChange) {
        return createDeleteNonRootEObjectInListChange(deletedMethod, afterChange);
    }

    public static EChange createAddParameterChange(Parameter newParameter, Method beforeChange) {
        return createAddNonRootEObjectInListChange(newParameter, beforeChange);
    }

    public static EChange createRemoveParameterChange(Parameter oldParameter, Method afterChange) {
        return createDeleteNonRootEObjectInListChange(oldParameter, afterChange);
    }

    public static EChange createAddModifierChange(Modifier newModifier, EObject beforeChange) {
        return createAddNonRootEObjectInListChange(newModifier, beforeChange);
    }

    public static EChange createRemoveModifierChange(Modifier oldModifier, EObject afterChange) {
        return createDeleteNonRootEObjectInListChange(oldModifier, afterChange);
    }

    public static EChange createAddFieldChange(Field field, ConcreteClassifier beforeChange) {
        return createAddNonRootEObjectInListChange(field, beforeChange);
    }

    public static EChange createRemoveFieldChange(Field field, ConcreteClassifier afterChange) {
        return createDeleteNonRootEObjectInListChange(field, afterChange);
    }

    public static EChange createAddSuperInterfaceChange(ConcreteClassifier superInterface) {
        // TODO
        return null;
    }

    public static EChange createRemoveSuperInterfaceChange(ConcreteClassifier superInterface) {
        // TODO
        return null;
    }

    private static <T extends EObject> EChange createAddNonRootEObjectInListChange(T createdEObject,
            EObject containerBeforeAdd) {
        CreateNonRootEObjectInList<T> createChange = ContainmentFactory.eINSTANCE.createCreateNonRootEObjectInList();
        createChange.setNewAffectedEObject(createdEObject.eContainer());
        createChange.setOldAffectedEObject(containerBeforeAdd);
        EReference containingReference = (EReference) createdEObject.eContainingFeature();
        int index = containingReference.eContents().indexOf(createChange);
        createChange.setAffectedFeature(containingReference);
        createChange.setIndex(index);
        createChange.setNewValue(createdEObject);
        return createChange;
    }

    private static <T extends EObject> EChange createDeleteNonRootEObjectInListChange(T deletedEObject,
            EObject containerAfterDelete) {
        DeleteNonRootEObjectInList<T> deleteChange = ContainmentFactory.eINSTANCE.createDeleteNonRootEObjectInList();
        deleteChange.setOldAffectedEObject(containerAfterDelete);
        deleteChange.setNewAffectedEObject(deletedEObject.eContainer());
        EReference containingReference = (EReference) deletedEObject.eContainingFeature();
        int index = containingReference.eContents().indexOf(deleteChange);
        deleteChange.setAffectedFeature(containingReference);
        deleteChange.setIndex(index);
        deleteChange.setOldValue(deletedEObject);
        return deleteChange;
    }

    public static EChange createCreatePackageChange(String packageName) {
        Package pkg = ContainersFactory.eINSTANCE.createPackage();
        pkg.setName(packageName);
        CreateRootEObject<Package> createPackage = ObjectFactory.eINSTANCE.createCreateRootEObject();
        createPackage.setNewValue(pkg);
        return createPackage;
    }

    public static EChange createDeletePackageChange(String packageName) {
        Package pkg = ContainersFactory.eINSTANCE.createPackage();
        pkg.setName(packageName);
        DeleteRootEObject<Package> deletePackage = ObjectFactory.eINSTANCE.createDeleteRootEObject();
        deletePackage.setOldValue(pkg);
        return deletePackage;
    }

    public static EChange createRenamePackageChange(String oldPackageName, String newPackageName) {
        Package originalPackage = ContainersFactory.eINSTANCE.createPackage();
        originalPackage.setName(oldPackageName);
        Package renamedPackage = ContainersFactory.eINSTANCE.createPackage();
        renamedPackage.setName(newPackageName);
        return createRenameChange(originalPackage, renamedPackage);
    }

    public static EChange[] createMoveMethodChange(Method removedMethod, ConcreteClassifier movedFromAfterRemove,
            Method addedMethod, ConcreteClassifier movedToBeforeAdd) {
        EChange removeMethodChange = JaMoPPChangeBuildHelper.createRemoveMethodChange(removedMethod,
                movedFromAfterRemove);
        EChange addMethodChange = JaMoPPChangeBuildHelper.createAddMethodChange(addedMethod, movedToBeforeAdd);
        return new EChange[] { removeMethodChange, addMethodChange };
    }
}
