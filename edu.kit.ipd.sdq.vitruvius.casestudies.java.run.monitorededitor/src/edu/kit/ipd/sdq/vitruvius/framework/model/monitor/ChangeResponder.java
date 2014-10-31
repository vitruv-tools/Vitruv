package edu.kit.ipd.sdq.vitruvius.framework.model.monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.parameters.Parameter;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AddFieldEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AddImportEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AddJavaDocEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AddMethodAnnotationEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AddMethodEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AddSuperClassEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.AddSuperInterfaceEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassModifiersEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEventExtension;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeEventVisitor;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeFieldModifiersEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeFieldTypeEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeInterfaceModifiersEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeJavaDocEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeMethodModifiersEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeMethodParameterEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeMethodReturnTypeEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangePackageDeclarationEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.CreateClassEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.CreateInterfaceEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.CreatePackageEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.DeleteClassEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.DeleteInterfaceEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.DeletePackageEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.MoveMethodEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RemoveFieldEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RemoveImportEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RemoveJavaDocEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RemoveMethodAnnotationEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RemoveMethodEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RemoveSuperClassEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RemoveSuperInterfaceEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameClassEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameFieldEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameInterfaceEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameMethodEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenamePackageDeclarationEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenamePackageEvent;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil.AST2JaMoPP;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil.CompilationUnitAdapter;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil.JaMoPPChangeBuildHelper;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EclipseBridge;

/**
 * The {@link ChangeResponder} implements a {@link ChangeEventVisitor} for
 * {@link ChangeClassifyingEvent}s. It uses the AST information in the change events to build
 * {@link EMFModelChange}s with the {@link JaMoPPChangeBuildHelper}. These EMF changes are submitted
 * to the {@link MonitoredEditor}.
 */
public class ChangeResponder implements ChangeEventVisitor {

    protected final MonitoredEditor monitoredEditor;
    private final Map<java.lang.Class<? extends ChangeClassifyingEventExtension>, ChangeEventExtendedVisitor> dispatcher;
    protected final ChangeResponderUtility util;
    static long lastCallTime;

    private void setLastCallTime() {
        lastCallTime = System.nanoTime();
    }

    public ChangeResponder(MonitoredEditor monitoredEditor) {
        this.monitoredEditor = monitoredEditor;
        this.util = new ChangeResponderUtility();
        this.dispatcher = new HashMap<java.lang.Class<? extends ChangeClassifyingEventExtension>, ChangeEventExtendedVisitor>();
        fillDispatcherMap();
    }

    private void fillDispatcherMap() {
        for (ChangeEventExtendedVisitor visitor : getRegisteredVisitors("edu.kit.ipd.sdq.vitruvius.framework.model.monitor.changeeventextendedvisitors")) {
            for (java.lang.Class<? extends ChangeClassifyingEventExtension> clazz : visitor.getTreatedClasses()) {
                this.dispatcher.put(clazz, visitor);
            }
        }
    }

    private static List<ChangeEventExtendedVisitor> getRegisteredVisitors(String extensionPointName) {
        return EclipseBridge.getRegisteredExtensions(extensionPointName, VitruviusConstants.getExtensionPropertyName(),
                ChangeEventExtendedVisitor.class);
    }

    @Override
    public void visit(ChangeClassifyingEventExtension changeClassifyingEvent) {
        this.dispatcher.get(changeClassifyingEvent.getClass()).visit(changeClassifyingEvent, this.monitoredEditor);
    }

    @Override
    public void visit(AddMethodEvent addMethodEvent) {
        MethodDeclaration newMethodDeclaration = addMethodEvent.method;
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(newMethodDeclaration);
        Method newMethod = originalCU.getMethodForMethodDeclaration(newMethodDeclaration);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(addMethodEvent.typeBeforeAdd);
        ConcreteClassifier classifierBeforeAdd = changedCU
                .getConcreteClassifierForTypeDeclaration(addMethodEvent.typeBeforeAdd);
        EChange eChange = JaMoPPChangeBuildHelper.createAddMethodChange(newMethod, classifierBeforeAdd);
        this.util.submitEMFModelChange(eChange, addMethodEvent.method);
    }

    @Override
    public void visit(CreateInterfaceEvent createInterfaceEvent) {
        TypeDeclaration type = createInterfaceEvent.type;
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(createInterfaceEvent.compilationUnitBeforeCreate);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(type);
        Interface newInterface = (Interface) changedCU.getConcreteClassifierForTypeDeclaration(type);
        EChange eChange = JaMoPPChangeBuildHelper.createCreateInterfaceChange(newInterface,
                originalCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, createInterfaceEvent.type);

    }

    @Override
    public void visit(CreateClassEvent createClassEvent) {
        TypeDeclaration type = createClassEvent.type;
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(createClassEvent.compilationUnitBeforeCreate);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(type);
        Class newClass = (Class) changedCU.getConcreteClassifierForTypeDeclaration(type);
        EChange eChange = JaMoPPChangeBuildHelper.createAddClassChange(newClass, originalCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, createClassEvent.type);

    }

    @Override
    public void visit(ChangeMethodReturnTypeEvent changeMethodReturnTypeEvent) {
        setLastCallTime();
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodReturnTypeEvent.original);
        Method original = originalCU.getMethodForMethodDeclaration(changeMethodReturnTypeEvent.original);
        CompilationUnitAdapter cu = this.util.getUnsavedCompilationUnitAdapter(changeMethodReturnTypeEvent.renamed);
        Method changed = cu.getMethodForMethodDeclaration(changeMethodReturnTypeEvent.renamed);
        EChange eChange = JaMoPPChangeBuildHelper.createChangeMethodReturnTypeChange(original, changed);
        this.util.submitEMFModelChange(eChange, changeMethodReturnTypeEvent.original);

    }

    @Override
    public void visit(RemoveMethodEvent removeMethodEvent) {
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(removeMethodEvent.method);
        Method removedMethod = originalCU.getMethodForMethodDeclaration(removeMethodEvent.method);
        CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeMethodEvent.typeAfterRemove);
        ConcreteClassifier classifierAfterRemove = changedCU
                .getConcreteClassifierForTypeDeclaration(removeMethodEvent.typeAfterRemove);
        EChange eChange = JaMoPPChangeBuildHelper.createRemoveMethodChange(removedMethod, classifierAfterRemove);
        this.util.submitEMFModelChange(eChange, removeMethodEvent.method);
    }

    @Override
    public void visit(DeleteClassEvent deleteClassEvent) {
        TypeDeclaration type = deleteClassEvent.type;
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(type);
        Class deletedClass = (Class) originalCU.getConcreteClassifierForTypeDeclaration(type);
        CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(deleteClassEvent.compilationUnitAfterDelete);
        EChange eChange = JaMoPPChangeBuildHelper
                .createRemovedClassChange(deletedClass, changedCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, deleteClassEvent.type);
    }

    @Override
    public void visit(DeleteInterfaceEvent deleteInterfaceEvent) {
        TypeDeclaration type = deleteInterfaceEvent.type;
        CompilationUnitAdapter oldCU = this.util.getUnsavedCompilationUnitAdapter(type);
        Interface deletedInterface = (Interface) oldCU.getConcreteClassifierForTypeDeclaration(type);
        CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(deleteInterfaceEvent.compilationUnitAfterDelete);
        EChange eChange = JaMoPPChangeBuildHelper.createRemovedInterfaceChange(deletedInterface,
                changedCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, deleteInterfaceEvent.type);
    }

    // private static void writeToConsole(String output) {
    //
    // MessageConsole console = new MessageConsole("My Console", null);
    // console.activate();
    // ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[] { console });
    // MessageConsoleStream stream = console.newMessageStream();
    // stream.println(output);
    // }

    @Override
    public void visit(RenameMethodEvent renameMethodEvent) {
        setLastCallTime();
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(renameMethodEvent.original);
        Method original = originalCU.getMethodForMethodDeclaration(renameMethodEvent.original);
        URI uri = this.util.getFirstExistingURI(renameMethodEvent.renamed, renameMethodEvent.original);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(renameMethodEvent.renamed, uri);
        Method changed = changedCU.getMethodForMethodDeclaration(renameMethodEvent.renamed);
        EChange eChange = JaMoPPChangeBuildHelper.createRenameMethodChange(original, changed);
        this.util.submitEMFModelChange(eChange, renameMethodEvent.original);
        // this.monitoredEditor.showMessage(UserInteractionType.MODAL,
        // "You just renamed a method.");
    }

    @Override
    public void visit(RenameFieldEvent renameFieldEvent) {
        setLastCallTime();
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(renameFieldEvent.original);
        Field original = originalCU.getFieldForVariableDeclarationFragment(renameFieldEvent.originalFragment);
        URI uri = this.util.getFirstExistingURI(renameFieldEvent.changed, renameFieldEvent.original);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(renameFieldEvent.changed, uri);
        Field renamed = changedCU.getFieldForVariableDeclarationFragment(renameFieldEvent.changedFragment);
        EChange eChange = JaMoPPChangeBuildHelper.createRenameFieldChange(original, renamed);
        this.util.submitEMFModelChange(eChange, renameFieldEvent.original);

        // ++ Test UserInteractor ++
        // this.monitoredEditor.selectFromMessage(UserInteractionType.MODELESS, "What did you do?",
        // "I renamed a field.",
        // "I took a sip of coffee.");
        // this.monitoredEditor.selectFromMessage(UserInteractionType.MODAL_POSTPONABLE,
        // "Are you sure?", "Yes.", "No.",
        // "Why do you ask?");
    }

    @Override
    public void visit(RenameInterfaceEvent renameInterfaceEvent) {
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(renameInterfaceEvent.original);
        Interface originalInterface = (Interface) originalCU
                .getConcreteClassifierForTypeDeclaration(renameInterfaceEvent.original);
        URI uri = this.util.getFirstExistingURI(renameInterfaceEvent.renamed, renameInterfaceEvent.original);
        CompilationUnitAdapter cuRenamed = this.util
                .getUnsavedCompilationUnitAdapter(renameInterfaceEvent.renamed, uri);
        Interface renamedInterface = (Interface) cuRenamed
                .getConcreteClassifierForTypeDeclaration(renameInterfaceEvent.renamed);

        EChange eChange = JaMoPPChangeBuildHelper.createRenameInterfaceChange(originalInterface, renamedInterface);
        this.util.submitEMFModelChange(eChange, renameInterfaceEvent.original);
    }

    @Override
    public void visit(RenameClassEvent renameClassEvent) {
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(renameClassEvent.original);
        Class originalClass = (Class) originalCU.getConcreteClassifierForTypeDeclaration(renameClassEvent.original);
        URI uri = this.util.getFirstExistingURI(renameClassEvent.renamed, renameClassEvent.original);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(renameClassEvent.renamed, uri);
        Class renamedClass = (Class) changedCU.getConcreteClassifierForTypeDeclaration(renameClassEvent.renamed);
        EChange eChange = JaMoPPChangeBuildHelper.createRenameClassChange(originalClass, renamedClass);
        this.util.submitEMFModelChange(eChange, renameClassEvent.original);
    }

    @Override
    public void visit(AddImportEvent addImportEvent) {
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(addImportEvent.importDeclaration);
        Import imp = originalCU.getImportForImportDeclaration(addImportEvent.importDeclaration);
        CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(addImportEvent.compilationUnitBeforeAdd);
        EChange eChange = JaMoPPChangeBuildHelper.createAddImportChange(imp, changedCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, addImportEvent.importDeclaration);
    }

    @Override
    public void visit(RemoveImportEvent removeImportEvent) {
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(removeImportEvent.importDeclaration);
        Import imp = originalCU.getImportForImportDeclaration(removeImportEvent.importDeclaration);
        CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeImportEvent.compilationUnitAfterRemove);
        EChange eChange = JaMoPPChangeBuildHelper.createRemoveImportChange(imp, changedCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, removeImportEvent.importDeclaration);
    }

    @Override
    public void visit(MoveMethodEvent moveMethodEvent) {
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(moveMethodEvent.original);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(moveMethodEvent.moved);
        ConcreteClassifier classifierMovedFromAfterRemove = originalCU
                .getConcreteClassifierForTypeDeclaration(moveMethodEvent.typeMovedFromAfterRemove);
        ConcreteClassifier classifierMovedToBeforeAdd = originalCU
                .getConcreteClassifierForTypeDeclaration(moveMethodEvent.typeMovedToBeforeAdd);
        Method removedMethod = originalCU.getMethodForMethodDeclaration(moveMethodEvent.original);
        Method addedMethod = changedCU.getMethodForMethodDeclaration(moveMethodEvent.moved);
        EChange[] eChanges = JaMoPPChangeBuildHelper.createMoveMethodChange(removedMethod,
                classifierMovedFromAfterRemove, addedMethod, classifierMovedToBeforeAdd);
        CompositeChange moveMethodChange = new CompositeChange(new Change[] {});
        // [0] is remove, [1] is add
        EMFModelChange removeMethodChange = this.util.wrapToEMFModelChange(eChanges[0], moveMethodEvent.original);
        EMFModelChange addMethodChange = this.util.wrapToEMFModelChange(eChanges[1], moveMethodEvent.moved);
        moveMethodChange.addChange(removeMethodChange);
        moveMethodChange.addChange(addMethodChange);
        this.monitoredEditor.submitChange(moveMethodChange);
    }

    @Override
    public void visit(AddSuperInterfaceEvent addSuperInterfaceEvent) {
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(addSuperInterfaceEvent.baseType);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(addSuperInterfaceEvent.superType);
        ConcreteClassifier base = originalCU.getConcreteClassifierForTypeDeclaration(addSuperInterfaceEvent.baseType);
    }

    @Override
    public void visit(RemoveSuperInterfaceEvent removeSuperInterfaceEvent) {
        // TODO Auto-generated method stub
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(removeSuperInterfaceEvent.baseType);
        CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeSuperInterfaceEvent.superType);
    }

    @Override
    public void visit(AddSuperClassEvent addSuperClassEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(RemoveSuperClassEvent removeSuperClassEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ChangeMethodParameterEvent changeMethodParameterEvent) {
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodParameterEvent.original);
        Method original = originalCU.getMethodForMethodDeclaration(changeMethodParameterEvent.original);
        CompilationUnitAdapter cu = this.util.getUnsavedCompilationUnitAdapter(changeMethodParameterEvent.renamed);
        Method changed = cu.getMethodForMethodDeclaration(changeMethodParameterEvent.renamed);
        handleParameterChanges(changed, original, original.getParameters(), changed.getParameters(),
                changeMethodParameterEvent.original);
    }

    private void handleParameterChanges(Method methodAfterRemove, Method methodBeforeAdd,
            List<Parameter> oldParameters, List<Parameter> newParameters, ASTNode oldNode) {
        CompositeChange compositeChange = new CompositeChange(new Change[] {});
        for (Parameter oldParameter : oldParameters) {
            EChange eChange = JaMoPPChangeBuildHelper.createRemoveParameterChange(oldParameter, methodAfterRemove);
            compositeChange.addChange(this.util.wrapToEMFModelChange(eChange, oldNode));
        }
        for (Parameter newParameter : newParameters) {
            EChange eChange = JaMoPPChangeBuildHelper.createAddParameterChange(newParameter, methodBeforeAdd);
            compositeChange.addChange(this.util.wrapToEMFModelChange(eChange, oldNode));
        }
        this.monitoredEditor.submitChange(compositeChange);
    }

    @Override
    public void visit(ChangeMethodModifiersEvent changeMethodModifierEvent) {
        setLastCallTime();
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodModifierEvent.original);
        Method originalMethod = originalCU.getMethodForMethodDeclaration(changeMethodModifierEvent.original);
        CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodModifierEvent.renamed);
        Method changedMethod = changedCU.getMethodForMethodDeclaration(changeMethodModifierEvent.renamed);

        CompositeChange change = buildModifierChanges(originalMethod, changedMethod, originalMethod.getModifiers(),
                changedMethod.getModifiers(), changeMethodModifierEvent.original);
        this.monitoredEditor.submitChange(change);
    }

    @Override
    public void visit(ChangeClassModifiersEvent changeClassModifiersEvent) {
        handleClassifierModifierChanges(changeClassModifiersEvent.original, changeClassModifiersEvent.changed);
    }

    @Override
    public void visit(ChangeInterfaceModifiersEvent changeInterfaceModifiersEvent) {
        handleClassifierModifierChanges(changeInterfaceModifiersEvent.original, changeInterfaceModifiersEvent.changed);
    }

    private void handleClassifierModifierChanges(TypeDeclaration original, TypeDeclaration changed) {
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(original);
        ConcreteClassifier originalClassifier = originalCU.getConcreteClassifierForTypeDeclaration(original);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(changed);
        ConcreteClassifier changedClassifier = changedCU.getConcreteClassifierForTypeDeclaration(changed);

        CompositeChange change = buildModifierChanges(originalClassifier, changedClassifier,
                originalClassifier.getModifiers(), changedClassifier.getModifiers(), original);
        this.monitoredEditor.submitChange(change);
    }

    private CompositeChange buildModifierChanges(EObject modifiableBeforeChange, EObject modifiableAfterChange,
            List<Modifier> oldModifiers, List<Modifier> newModifiers, ASTNode oldNode) {
        List<Modifier> originalModifiers = new ArrayList<Modifier>(oldModifiers);
        List<Modifier> changedModifiers = new ArrayList<Modifier>(newModifiers);

        for (Modifier changedModifier : newModifiers) {
            for (Modifier origModifier : oldModifiers) {
                if (changedModifier.getClass() == origModifier.getClass()) {
                    originalModifiers.remove(origModifier);
                    changedModifiers.remove(changedModifier);
                    break;
                }
            }
        }

        CompositeChange modifierChanges = new CompositeChange(new Change[] {});
        for (Modifier removedModifier : originalModifiers) {
            EChange eChange = JaMoPPChangeBuildHelper
                    .createRemoveModifierChange(removedModifier, modifiableAfterChange);
            modifierChanges.addChange(this.util.wrapToEMFModelChange(eChange, oldNode));
        }
        for (Modifier newModifier : changedModifiers) {
            EChange eChange = JaMoPPChangeBuildHelper.createAddModifierChange(newModifier, modifiableBeforeChange);
            modifierChanges.addChange(this.util.wrapToEMFModelChange(eChange, oldNode));
        }
        return modifierChanges;
    }

    @Override
    public void visit(AddFieldEvent addFieldEvent) {
        setLastCallTime();
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(addFieldEvent.typeBeforeAdd);
        ConcreteClassifier classifierBeforeAdd = originalCU
                .getConcreteClassifierForTypeDeclaration(addFieldEvent.typeBeforeAdd);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(addFieldEvent.field);
        Field field = changedCU.getFieldForVariableDeclarationFragment(addFieldEvent.fieldFragment);
        EChange eChange = JaMoPPChangeBuildHelper.createAddFieldChange(field, classifierBeforeAdd);
        this.util.submitEMFModelChange(eChange, addFieldEvent.field);
    }

    @Override
    public void visit(RemoveFieldEvent removeFieldEvent) {
        setLastCallTime();
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(removeFieldEvent.field);
        Field field = originalCU.getFieldForVariableDeclarationFragment(removeFieldEvent.fieldFragment);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(removeFieldEvent.typeAfterRemove);
        ConcreteClassifier classiferAfterRemove = changedCU
                .getConcreteClassifierForTypeDeclaration(removeFieldEvent.typeAfterRemove);
        EChange eChange = JaMoPPChangeBuildHelper.createAddFieldChange(field, classiferAfterRemove);
        this.util.submitEMFModelChange(eChange, removeFieldEvent.field);
    }

    @Override
    public void visit(ChangeFieldModifiersEvent changeFieldModifiersEvent) {
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeFieldModifiersEvent.original);
        List<Field> originalFields = originalCU.getFieldsForFieldDeclaration(changeFieldModifiersEvent.original);
        CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(changeFieldModifiersEvent.changed);
        List<Field> changedFields = changedCU.getFieldsForFieldDeclaration(changeFieldModifiersEvent.changed);

        CompositeChange allFieldModifierChanges = new CompositeChange(new Change[] {});
        ListIterator<Field> ofit = originalFields.listIterator();
        while (ofit.hasNext()) {
            Field oField = ofit.next();
            ListIterator<Field> cfit = changedFields.listIterator();
            while (cfit.hasNext()) {
                Field cField = cfit.next();
                if (oField.getName().equals(cField.getName())) {
                    cfit.remove();
                    CompositeChange fieldModifierChanges = buildModifierChanges(oField, cField, oField.getModifiers(),
                            cField.getModifiers(), changeFieldModifiersEvent.original);
                    allFieldModifierChanges.addChange(fieldModifierChanges);
                }
            }
        }
        this.monitoredEditor.submitChange(allFieldModifierChanges);
    }

    @Override
    public void visit(ChangeFieldTypeEvent changeFieldTypeEvent) {
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(changeFieldTypeEvent.original);
        List<Field> originalFields = originalCU.getFieldsForFieldDeclaration(changeFieldTypeEvent.original);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(changeFieldTypeEvent.changed);
        List<Field> changedFields = changedCU.getFieldsForFieldDeclaration(changeFieldTypeEvent.changed);

        CompositeChange typeChanges = new CompositeChange(new Change[] {});
        ListIterator<Field> ofit = originalFields.listIterator();
        while (ofit.hasNext()) {
            Field oField = ofit.next();
            ListIterator<Field> cfit = changedFields.listIterator();
            while (cfit.hasNext()) {
                Field cField = cfit.next();
                if (oField.getName().equals(cField.getName())) {
                    cfit.remove();
                    EChange eChange = JaMoPPChangeBuildHelper.createChangeFieldTypeChange(oField, cField);
                    typeChanges.addChange(this.util.wrapToEMFModelChange(eChange, changeFieldTypeEvent.original));
                }
            }
        }
        this.monitoredEditor.submitChange(typeChanges);
    }

    @Override
    public void visit(AddMethodAnnotationEvent addMethodAnnotationEvent) {
        // TODO
        CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(addMethodAnnotationEvent.annotation);

    }

    @Override
    public void visit(RemoveMethodAnnotationEvent removeMethodAnnotationEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(RenamePackageEvent renamePackageEvent) {
        EChange renamePackageChange = JaMoPPChangeBuildHelper.createRenamePackageChange(
                renamePackageEvent.originalPackageName, renamePackageEvent.renamedPackageName);
        this.util.submitEMFModelChange(renamePackageChange, renamePackageEvent.originalIResource);

    }

    @Override
    public void visit(DeletePackageEvent deletePackageEvent) {
        EChange deletePackageChange = JaMoPPChangeBuildHelper.createDeletePackageChange(deletePackageEvent.packageName);
        this.util.submitEMFModelChange(deletePackageChange, deletePackageEvent.iResource);

    }

    @Override
    public void visit(CreatePackageEvent addPackageEvent) {
        EChange createPackageChange = JaMoPPChangeBuildHelper.createCreatePackageChange(addPackageEvent.packageName);
        this.util.submitEMFModelChange(createPackageChange, addPackageEvent.iResource);

    }

    @Override
    public void visit(RenamePackageDeclarationEvent renamePackageDeclarationEvent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(ChangePackageDeclarationEvent changePackageDeclarationEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(AddJavaDocEvent addJavaDocEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(RemoveJavaDocEvent removeJavaDocEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ChangeJavaDocEvent changeJavaDocEvent) {
        // TODO Auto-generated method stub

    }

    protected final class ChangeResponderUtility {

        private ChangeResponderUtility() {
        }

        public CompilationUnitAdapter getUnsavedCompilationUnitAdapter(ASTNode astNode) {
            CompilationUnitAdapter cu = null;
            URI uri = getURIFromCompilationUnit(astNode);
            return getUnsavedCompilationUnitAdapter(astNode, uri);
        }

        public CompilationUnitAdapter getUnsavedCompilationUnitAdapter(ASTNode astNode, URI uri) {
            CompilationUnitAdapter cu = null;
            cu = new CompilationUnitAdapter(astNode, uri, false);
            if (cu.getCompilationUnit() == null)
                cu = null;
            return cu;
        }

        private void submitEMFModelChange(EChange eChange, ASTNode astNodeWithIResource) {
            EMFModelChange change = wrapToEMFModelChange(eChange, astNodeWithIResource);
            ChangeResponder.this.monitoredEditor.submitChange(change);
        }

        private void submitEMFModelChange(EChange eChange, IResource originalIResource) {
            EMFModelChange change = wrapToEMFModelChange(eChange, originalIResource);
            ChangeResponder.this.monitoredEditor.submitChange(change);
        }

        private EMFModelChange wrapToEMFModelChange(EChange eChange, ASTNode astNodeWithIResource) {
            VURI vuri = VURI.getInstance(AST2JaMoPP.getIResource(astNodeWithIResource));
            return new EMFModelChange(eChange, vuri);
        }

        private EMFModelChange wrapToEMFModelChange(EChange eChange, IResource originalIResource) {
            VURI vuri = VURI.getInstance(originalIResource);
            return new EMFModelChange(eChange, vuri);
        }

        // returns URI from node1 if exists, otherwise URI from node2 or null if both have no
        // attached
        // IResource
        URI getFirstExistingURI(ASTNode node1, ASTNode node2) {
            URI uri = getURIFromCompilationUnit(node1);
            if (uri == null)
                uri = getURIFromCompilationUnit(node2);
            return uri;
        }

        private URI getURIFromCompilationUnit(ASTNode astNode) {
            // TODO IPath for CompilationUnit without linked IResource
            // IPath iPath = AST2JaMoPP.getIPathFromCompilationUnitWithResource(astNode);
            IPath iPath = AST2JaMoPP.getIResource(astNode).getFullPath();
            if (iPath == null)
                return null;
            return URI.createPlatformResourceURI(iPath.toString(), true);
        }
    }
}
