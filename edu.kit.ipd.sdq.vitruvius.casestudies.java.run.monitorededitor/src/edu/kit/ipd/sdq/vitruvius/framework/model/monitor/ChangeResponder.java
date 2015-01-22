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
import org.emftext.language.java.containers.CompilationUnit;
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
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.RenameParameterEvent;
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

    public ChangeResponder(final MonitoredEditor monitoredEditor) {
        this.monitoredEditor = monitoredEditor;
        this.util = new ChangeResponderUtility();
        this.dispatcher = new HashMap<java.lang.Class<? extends ChangeClassifyingEventExtension>, ChangeEventExtendedVisitor>();
        this.fillDispatcherMap();
    }

    private void fillDispatcherMap() {
        for (final ChangeEventExtendedVisitor visitor : getRegisteredVisitors("edu.kit.ipd.sdq.vitruvius.framework.model.monitor.changeeventextendedvisitors")) {
            for (final java.lang.Class<? extends ChangeClassifyingEventExtension> clazz : visitor.getTreatedClasses()) {
                this.dispatcher.put(clazz, visitor);
            }
        }
    }

    private static List<ChangeEventExtendedVisitor> getRegisteredVisitors(final String extensionPointName) {
        return EclipseBridge.getRegisteredExtensions(extensionPointName, VitruviusConstants.getExtensionPropertyName(),
                ChangeEventExtendedVisitor.class);
    }

    @Override
    public void visit(final ChangeClassifyingEventExtension changeClassifyingEvent) {
        this.dispatcher.get(changeClassifyingEvent.getClass()).visit(changeClassifyingEvent, this.monitoredEditor);
    }

    @Override
    public void visit(final AddMethodEvent addMethodEvent) {
        final MethodDeclaration newMethodDeclaration = addMethodEvent.method;
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(newMethodDeclaration);
        final Method newMethod = originalCU.getMethodForMethodDeclaration(newMethodDeclaration);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(addMethodEvent.typeBeforeAdd);
        final ConcreteClassifier classifierBeforeAdd = changedCU
                .getConcreteClassifierForTypeDeclaration(addMethodEvent.typeBeforeAdd);
        final EChange eChange = JaMoPPChangeBuildHelper.createAddMethodChange(newMethod, classifierBeforeAdd);
        this.util.submitEMFModelChange(eChange, addMethodEvent.method);
    }

    @Override
    public void visit(final CreateInterfaceEvent createInterfaceEvent) {
        final TypeDeclaration type = createInterfaceEvent.type;
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(createInterfaceEvent.compilationUnitBeforeCreate);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(type);
        final Interface newInterface = (Interface) changedCU.getConcreteClassifierForTypeDeclaration(type);
        final EChange eChange = JaMoPPChangeBuildHelper.createCreateInterfaceChange(newInterface,
                null == originalCU ? null : originalCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, createInterfaceEvent.type);

    }

    @Override
    public void visit(final CreateClassEvent createClassEvent) {
        final TypeDeclaration type = createClassEvent.type;
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(createClassEvent.compilationUnitBeforeCreate);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(type);
        final Class newClass = (Class) changedCU.getConcreteClassifierForTypeDeclaration(type);
        final CompilationUnit beforeChange = (null == originalCU ? null : originalCU.getCompilationUnit());
        final EChange eChange = JaMoPPChangeBuildHelper.createAddClassChange(newClass, beforeChange);
        this.util.submitEMFModelChange(eChange, createClassEvent.type);

    }

    @Override
    public void visit(final ChangeMethodReturnTypeEvent changeMethodReturnTypeEvent) {
        this.setLastCallTime();
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodReturnTypeEvent.original);
        final Method original = originalCU.getMethodForMethodDeclaration(changeMethodReturnTypeEvent.original);
        final CompilationUnitAdapter cu = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodReturnTypeEvent.renamed);
        final Method changed = cu.getMethodForMethodDeclaration(changeMethodReturnTypeEvent.renamed);
        final EChange eChange = JaMoPPChangeBuildHelper.createChangeMethodReturnTypeChange(original, changed);
        this.util.submitEMFModelChange(eChange, changeMethodReturnTypeEvent.original);

    }

    @Override
    public void visit(final RemoveMethodEvent removeMethodEvent) {
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(removeMethodEvent.method);
        final Method removedMethod = originalCU.getMethodForMethodDeclaration(removeMethodEvent.method);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeMethodEvent.typeAfterRemove);
        final ConcreteClassifier classifierAfterRemove = changedCU
                .getConcreteClassifierForTypeDeclaration(removeMethodEvent.typeAfterRemove);
        final EChange eChange = JaMoPPChangeBuildHelper.createRemoveMethodChange(removedMethod, classifierAfterRemove);
        this.util.submitEMFModelChange(eChange, removeMethodEvent.method);
    }

    @Override
    public void visit(final DeleteClassEvent deleteClassEvent) {
        final TypeDeclaration type = deleteClassEvent.type;
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(type);
        final Class deletedClass = (Class) originalCU.getConcreteClassifierForTypeDeclaration(type);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(deleteClassEvent.compilationUnitAfterDelete);
        final EChange eChange = JaMoPPChangeBuildHelper.createRemovedClassChange(deletedClass,
                changedCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, deleteClassEvent.type);
    }

    @Override
    public void visit(final DeleteInterfaceEvent deleteInterfaceEvent) {
        final TypeDeclaration type = deleteInterfaceEvent.type;
        final CompilationUnitAdapter oldCU = this.util.getUnsavedCompilationUnitAdapter(type);
        final Interface deletedInterface = (Interface) oldCU.getConcreteClassifierForTypeDeclaration(type);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(deleteInterfaceEvent.compilationUnitAfterDelete);
        final EChange eChange = JaMoPPChangeBuildHelper.createRemovedInterfaceChange(deletedInterface,
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
    public void visit(final RenameMethodEvent renameMethodEvent) {
        this.setLastCallTime();
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(renameMethodEvent.original);
        final Method original = originalCU.getMethodForMethodDeclaration(renameMethodEvent.original);
        final URI uri = this.util.getFirstExistingURI(renameMethodEvent.renamed, renameMethodEvent.original);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(renameMethodEvent.renamed,
                uri);
        final Method changed = changedCU.getMethodForMethodDeclaration(renameMethodEvent.renamed);
        final EChange eChange = JaMoPPChangeBuildHelper.createRenameMethodChange(original, changed);
        this.util.submitEMFModelChange(eChange, renameMethodEvent.original);
        // this.monitoredEditor.showMessage(UserInteractionType.MODAL,
        // "You just renamed a method.");
    }

    @Override
    public void visit(final RenameFieldEvent renameFieldEvent) {
        this.setLastCallTime();
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(renameFieldEvent.original);
        final Field original = originalCU.getFieldForVariableDeclarationFragment(renameFieldEvent.originalFragment);
        final URI uri = this.util.getFirstExistingURI(renameFieldEvent.changed, renameFieldEvent.original);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(renameFieldEvent.changed,
                uri);
        final Field renamed = changedCU.getFieldForVariableDeclarationFragment(renameFieldEvent.changedFragment);
        final EChange eChange = JaMoPPChangeBuildHelper.createRenameFieldChange(original, renamed);
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
    public void visit(final RenameInterfaceEvent renameInterfaceEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(renameInterfaceEvent.original);
        final Interface originalInterface = (Interface) originalCU
                .getConcreteClassifierForTypeDeclaration(renameInterfaceEvent.original);
        final URI uri = this.util.getFirstExistingURI(renameInterfaceEvent.renamed, renameInterfaceEvent.original);
        final CompilationUnitAdapter cuRenamed = this.util.getUnsavedCompilationUnitAdapter(
                renameInterfaceEvent.renamed, uri);
        final Interface renamedInterface = (Interface) cuRenamed
                .getConcreteClassifierForTypeDeclaration(renameInterfaceEvent.renamed);

        final EChange eChange = JaMoPPChangeBuildHelper
                .createRenameInterfaceChange(originalInterface, renamedInterface);
        this.util.submitEMFModelChange(eChange, renameInterfaceEvent.original);
    }

    @Override
    public void visit(final RenameClassEvent renameClassEvent) {
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(renameClassEvent.original);
        final Class originalClass = (Class) originalCU
                .getConcreteClassifierForTypeDeclaration(renameClassEvent.original);
        final URI uri = this.util.getFirstExistingURI(renameClassEvent.renamed, renameClassEvent.original);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(renameClassEvent.renamed,
                uri);
        final Class renamedClass = (Class) changedCU.getConcreteClassifierForTypeDeclaration(renameClassEvent.renamed);
        final EChange eChange = JaMoPPChangeBuildHelper.createRenameClassChange(originalClass, renamedClass);
        this.util.submitEMFModelChange(eChange, renameClassEvent.original);
    }

    @Override
    public void visit(final AddImportEvent addImportEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(addImportEvent.importDeclaration);
        final Import imp = originalCU.getImportForImportDeclaration(addImportEvent.importDeclaration);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(addImportEvent.compilationUnitBeforeAdd);
        final EChange eChange = JaMoPPChangeBuildHelper.createAddImportChange(imp, changedCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, addImportEvent.importDeclaration);
    }

    @Override
    public void visit(final RemoveImportEvent removeImportEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(removeImportEvent.importDeclaration);
        final Import imp = originalCU.getImportForImportDeclaration(removeImportEvent.importDeclaration);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeImportEvent.compilationUnitAfterRemove);
        final EChange eChange = JaMoPPChangeBuildHelper.createRemoveImportChange(imp, changedCU.getCompilationUnit());
        this.util.submitEMFModelChange(eChange, removeImportEvent.importDeclaration);
    }

    @Override
    public void visit(final MoveMethodEvent moveMethodEvent) {
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(moveMethodEvent.original);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(moveMethodEvent.moved);
        final ConcreteClassifier classifierMovedFromAfterRemove = originalCU
                .getConcreteClassifierForTypeDeclaration(moveMethodEvent.typeMovedFromAfterRemove);
        final ConcreteClassifier classifierMovedToBeforeAdd = originalCU
                .getConcreteClassifierForTypeDeclaration(moveMethodEvent.typeMovedToBeforeAdd);
        final Method removedMethod = originalCU.getMethodForMethodDeclaration(moveMethodEvent.original);
        final Method addedMethod = changedCU.getMethodForMethodDeclaration(moveMethodEvent.moved);
        final EChange[] eChanges = JaMoPPChangeBuildHelper.createMoveMethodChange(removedMethod,
                classifierMovedFromAfterRemove, addedMethod, classifierMovedToBeforeAdd);
        final CompositeChange moveMethodChange = new CompositeChange(new Change[] {});
        // [0] is remove, [1] is add
        final EMFModelChange removeMethodChange = this.util.wrapToEMFModelChange(eChanges[0], moveMethodEvent.original);
        final EMFModelChange addMethodChange = this.util.wrapToEMFModelChange(eChanges[1], moveMethodEvent.moved);
        moveMethodChange.addChange(removeMethodChange);
        moveMethodChange.addChange(addMethodChange);
        this.monitoredEditor.submitChange(moveMethodChange);
    }

    @Override
    public void visit(final AddSuperInterfaceEvent addSuperInterfaceEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(addSuperInterfaceEvent.baseType);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(addSuperInterfaceEvent.superType);
        final ConcreteClassifier base = originalCU
                .getConcreteClassifierForTypeDeclaration(addSuperInterfaceEvent.baseType);
    }

    @Override
    public void visit(final RemoveSuperInterfaceEvent removeSuperInterfaceEvent) {
        // TODO Auto-generated method stub
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(removeSuperInterfaceEvent.baseType);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeSuperInterfaceEvent.superType);
    }

    @Override
    public void visit(final AddSuperClassEvent addSuperClassEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final RemoveSuperClassEvent removeSuperClassEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final ChangeMethodParameterEvent changeMethodParameterEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodParameterEvent.original);
        final Method original = originalCU.getMethodForMethodDeclaration(changeMethodParameterEvent.original);
        final CompilationUnitAdapter cu = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodParameterEvent.renamed);
        final Method changed = cu.getMethodForMethodDeclaration(changeMethodParameterEvent.renamed);
        this.handleParameterChanges(changed, original, original.getParameters(), changed.getParameters(),
                changeMethodParameterEvent.original);
    }

    private void handleParameterChanges(final Method methodAfterRemove, final Method methodBeforeAdd,
            final List<Parameter> oldParameters, final List<Parameter> newParameters, final ASTNode oldNode) {
        final CompositeChange compositeChange = new CompositeChange(new Change[] {});
        for (final Parameter oldParameter : oldParameters) {
            final EChange eChange = JaMoPPChangeBuildHelper
                    .createRemoveParameterChange(oldParameter, methodAfterRemove);
            compositeChange.addChange(this.util.wrapToEMFModelChange(eChange, oldNode));
        }
        for (final Parameter newParameter : newParameters) {
            final EChange eChange = JaMoPPChangeBuildHelper.createAddParameterChange(newParameter, methodBeforeAdd);
            compositeChange.addChange(this.util.wrapToEMFModelChange(eChange, oldNode));
        }
        this.monitoredEditor.submitChange(compositeChange);
    }

    @Override
    public void visit(final ChangeMethodModifiersEvent changeMethodModifierEvent) {
        this.setLastCallTime();
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodModifierEvent.original);
        final Method originalMethod = originalCU.getMethodForMethodDeclaration(changeMethodModifierEvent.original);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodModifierEvent.renamed);
        final Method changedMethod = changedCU.getMethodForMethodDeclaration(changeMethodModifierEvent.renamed);

        final CompositeChange change = this.buildModifierChanges(originalMethod, changedMethod,
                originalMethod.getModifiers(), changedMethod.getModifiers(), changeMethodModifierEvent.original);
        this.monitoredEditor.submitChange(change);
    }

    @Override
    public void visit(final ChangeClassModifiersEvent changeClassModifiersEvent) {
        this.handleClassifierModifierChanges(changeClassModifiersEvent.original, changeClassModifiersEvent.changed);
    }

    @Override
    public void visit(final ChangeInterfaceModifiersEvent changeInterfaceModifiersEvent) {
        this.handleClassifierModifierChanges(changeInterfaceModifiersEvent.original,
                changeInterfaceModifiersEvent.changed);
    }

    private void handleClassifierModifierChanges(final TypeDeclaration original, final TypeDeclaration changed) {
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(original);
        final ConcreteClassifier originalClassifier = originalCU.getConcreteClassifierForTypeDeclaration(original);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(changed);
        final ConcreteClassifier changedClassifier = changedCU.getConcreteClassifierForTypeDeclaration(changed);

        final CompositeChange change = this.buildModifierChanges(originalClassifier, changedClassifier,
                originalClassifier.getModifiers(), changedClassifier.getModifiers(), original);
        this.monitoredEditor.submitChange(change);
    }

    private CompositeChange buildModifierChanges(final EObject modifiableBeforeChange,
            final EObject modifiableAfterChange, final List<Modifier> oldModifiers, final List<Modifier> newModifiers,
            final ASTNode oldNode) {
        final List<Modifier> originalModifiers = new ArrayList<Modifier>(oldModifiers);
        final List<Modifier> changedModifiers = new ArrayList<Modifier>(newModifiers);

        for (final Modifier changedModifier : newModifiers) {
            for (final Modifier origModifier : oldModifiers) {
                if (changedModifier.getClass() == origModifier.getClass()) {
                    originalModifiers.remove(origModifier);
                    changedModifiers.remove(changedModifier);
                    break;
                }
            }
        }

        final CompositeChange modifierChanges = new CompositeChange(new Change[] {});
        for (final Modifier removedModifier : originalModifiers) {
            final EChange eChange = JaMoPPChangeBuildHelper.createRemoveModifierChange(removedModifier,
                    modifiableAfterChange);
            modifierChanges.addChange(this.util.wrapToEMFModelChange(eChange, oldNode));
        }
        for (final Modifier newModifier : changedModifiers) {
            final EChange eChange = JaMoPPChangeBuildHelper
                    .createAddModifierChange(newModifier, modifiableBeforeChange);
            modifierChanges.addChange(this.util.wrapToEMFModelChange(eChange, oldNode));
        }
        return modifierChanges;
    }

    @Override
    public void visit(final AddFieldEvent addFieldEvent) {
        this.setLastCallTime();
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(addFieldEvent.typeBeforeAdd);
        final ConcreteClassifier classifierBeforeAdd = originalCU
                .getConcreteClassifierForTypeDeclaration(addFieldEvent.typeBeforeAdd);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(addFieldEvent.field);
        final Field field = changedCU.getFieldForVariableDeclarationFragment(addFieldEvent.fieldFragment);
        final EChange eChange = JaMoPPChangeBuildHelper.createAddFieldChange(field, classifierBeforeAdd);
        this.util.submitEMFModelChange(eChange, addFieldEvent.field);
    }

    @Override
    public void visit(final RemoveFieldEvent removeFieldEvent) {
        this.setLastCallTime();
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(removeFieldEvent.field);
        final Field field = originalCU.getFieldForVariableDeclarationFragment(removeFieldEvent.fieldFragment);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeFieldEvent.typeAfterRemove);
        final ConcreteClassifier classiferAfterRemove = changedCU
                .getConcreteClassifierForTypeDeclaration(removeFieldEvent.typeAfterRemove);
        final EChange eChange = JaMoPPChangeBuildHelper.createAddFieldChange(field, classiferAfterRemove);
        this.util.submitEMFModelChange(eChange, removeFieldEvent.field);
    }

    @Override
    public void visit(final ChangeFieldModifiersEvent changeFieldModifiersEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeFieldModifiersEvent.original);
        final List<Field> originalFields = originalCU.getFieldsForFieldDeclaration(changeFieldModifiersEvent.original);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(changeFieldModifiersEvent.changed);
        final List<Field> changedFields = changedCU.getFieldsForFieldDeclaration(changeFieldModifiersEvent.changed);

        final CompositeChange allFieldModifierChanges = new CompositeChange(new Change[] {});
        final ListIterator<Field> ofit = originalFields.listIterator();
        while (ofit.hasNext()) {
            final Field oField = ofit.next();
            final ListIterator<Field> cfit = changedFields.listIterator();
            while (cfit.hasNext()) {
                final Field cField = cfit.next();
                if (oField.getName().equals(cField.getName())) {
                    cfit.remove();
                    final CompositeChange fieldModifierChanges = this.buildModifierChanges(oField, cField,
                            oField.getModifiers(), cField.getModifiers(), changeFieldModifiersEvent.original);
                    allFieldModifierChanges.addChange(fieldModifierChanges);
                }
            }
        }
        this.monitoredEditor.submitChange(allFieldModifierChanges);
    }

    @Override
    public void visit(final ChangeFieldTypeEvent changeFieldTypeEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeFieldTypeEvent.original);
        final List<Field> originalFields = originalCU.getFieldsForFieldDeclaration(changeFieldTypeEvent.original);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(changeFieldTypeEvent.changed);
        final List<Field> changedFields = changedCU.getFieldsForFieldDeclaration(changeFieldTypeEvent.changed);

        final CompositeChange typeChanges = new CompositeChange(new Change[] {});
        final ListIterator<Field> ofit = originalFields.listIterator();
        while (ofit.hasNext()) {
            final Field oField = ofit.next();
            final ListIterator<Field> cfit = changedFields.listIterator();
            while (cfit.hasNext()) {
                final Field cField = cfit.next();
                if (oField.getName().equals(cField.getName())) {
                    cfit.remove();
                    final EChange eChange = JaMoPPChangeBuildHelper.createChangeFieldTypeChange(oField, cField);
                    typeChanges.addChange(this.util.wrapToEMFModelChange(eChange, changeFieldTypeEvent.original));
                }
            }
        }
        this.monitoredEditor.submitChange(typeChanges);
    }

    @Override
    public void visit(final AddMethodAnnotationEvent addMethodAnnotationEvent) {
        // TODO
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(addMethodAnnotationEvent.annotation);

    }

    @Override
    public void visit(final RemoveMethodAnnotationEvent removeMethodAnnotationEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final RenamePackageEvent renamePackageEvent) {
        final EChange renamePackageChange = JaMoPPChangeBuildHelper.createRenamePackageChange(
                renamePackageEvent.originalPackageName, renamePackageEvent.renamedPackageName);
        this.util.submitEMFModelChange(renamePackageChange, renamePackageEvent.originalIResource);

    }

    @Override
    public void visit(final DeletePackageEvent deletePackageEvent) {
        final EChange deletePackageChange = JaMoPPChangeBuildHelper
                .createDeletePackageChange(deletePackageEvent.packageName);
        this.util.submitEMFModelChange(deletePackageChange, deletePackageEvent.iResource);

    }

    @Override
    public void visit(final CreatePackageEvent addPackageEvent) {
        final EChange createPackageChange = JaMoPPChangeBuildHelper
                .createCreatePackageChange(addPackageEvent.packageName);
        this.util.submitEMFModelChange(createPackageChange, addPackageEvent.iResource);
    }
    

    @Override
    public void visit(RenameParameterEvent renameParameterEvent) {
        CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(renameParameterEvent.original);
        Parameter original = originalCU.getParameterForVariableDeclaration(renameParameterEvent.originalParam);
        URI uri = this.util.getFirstExistingURI(renameParameterEvent.original, renameParameterEvent.renamed);
        CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(renameParameterEvent.renamed, uri);
        Parameter renamed = changedCU.getParameterForVariableDeclaration(renameParameterEvent.changedParam);
        EChange eChange = JaMoPPChangeBuildHelper.createRenameParameterChange(original, renamed);
        this.util.submitEMFModelChange(eChange, renameParameterEvent.original);
    }

    @Override
    public void visit(final RenamePackageDeclarationEvent renamePackageDeclarationEvent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(final ChangePackageDeclarationEvent changePackageDeclarationEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final AddJavaDocEvent addJavaDocEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final RemoveJavaDocEvent removeJavaDocEvent) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(final ChangeJavaDocEvent changeJavaDocEvent) {
        // TODO Auto-generated method stub

    }

    protected final class ChangeResponderUtility {

        private ChangeResponderUtility() {
        }

        public CompilationUnitAdapter getUnsavedCompilationUnitAdapter(final ASTNode astNode) {
            final URI uri = this.getURIFromCompilationUnit(astNode);
            return this.getUnsavedCompilationUnitAdapter(astNode, uri);
        }

        public CompilationUnitAdapter getUnsavedCompilationUnitAdapter(final ASTNode astNode, final URI uri) {
            CompilationUnitAdapter cu = null;
            if (null == astNode) {
                return null;
            }
            cu = new CompilationUnitAdapter(astNode, uri, false);
            if (cu.getCompilationUnit() == null) {
                cu = null;
            }
            return cu;
        }

        private void submitEMFModelChange(final EChange eChange, final ASTNode astNodeWithIResource) {
            final EMFModelChange change = this.wrapToEMFModelChange(eChange, astNodeWithIResource);
            ChangeResponder.this.monitoredEditor.submitChange(change);
        }

        private void submitEMFModelChange(final EChange eChange, final IResource originalIResource) {
            final EMFModelChange change = this.wrapToEMFModelChange(eChange, originalIResource);
            ChangeResponder.this.monitoredEditor.submitChange(change);
        }

        private EMFModelChange wrapToEMFModelChange(final EChange eChange, final ASTNode astNodeWithIResource) {
            final VURI vuri = VURI.getInstance(AST2JaMoPP.getIResource(astNodeWithIResource));
            return new EMFModelChange(eChange, vuri);
        }

        private EMFModelChange wrapToEMFModelChange(final EChange eChange, final IResource originalIResource) {
            final VURI vuri = VURI.getInstance(originalIResource);
            return new EMFModelChange(eChange, vuri);
        }

        // returns URI from node1 if exists, otherwise URI from node2 or null if both have no
        // attached
        // IResource
        URI getFirstExistingURI(final ASTNode node1, final ASTNode node2) {
            URI uri = this.getURIFromCompilationUnit(node1);
            if (uri == null) {
                uri = this.getURIFromCompilationUnit(node2);
            }
            return uri;
        }

        private URI getURIFromCompilationUnit(final ASTNode astNode) {
            // TODO IPath for CompilationUnit without linked IResource
            // IPath iPath = AST2JaMoPP.getIPathFromCompilationUnitWithResource(astNode);
            final IResource iResource = AST2JaMoPP.getIResource(astNode);
            if (null == iResource) {
                return null;
            }
            final IPath iPath = iResource.getFullPath();
            if (iPath == null) {
                return null;
            }
            return URI.createPlatformResourceURI(iPath.toString(), true);
        }
    }
}
