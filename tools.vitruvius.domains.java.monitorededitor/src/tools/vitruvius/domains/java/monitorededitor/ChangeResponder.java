package tools.vitruvius.domains.java.monitorededitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.modifiers.Modifier;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;

import tools.vitruvius.framework.change.echange.EChange;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.AddAnnotationEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.AddFieldEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.AddImportEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.AddJavaDocEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.AddMethodEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.AddSuperClassEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.AddSuperInterfaceEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassModifiersEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeClassifyingEventExtension;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeEventVisitor;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeFieldModifiersEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeFieldTypeEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeInterfaceModifiersEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeJavaDocEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeMethodModifiersEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeMethodParameterEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangeMethodReturnTypeEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.ChangePackageDeclarationEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.CreateClassEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.CreateInterfaceEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.CreatePackageEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.DeleteClassEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.DeleteInterfaceEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.DeletePackageEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.MoveMethodEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RemoveAnnotationEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RemoveFieldEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RemoveImportEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RemoveJavaDocEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RemoveMethodEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RemoveSuperClassEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RemoveSuperInterfaceEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameClassEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameFieldEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameInterfaceEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameMethodEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenamePackageDeclarationEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenamePackageEvent;
import tools.vitruvius.domains.java.monitorededitor.changeclassification.events.RenameParameterEvent;
import tools.vitruvius.domains.java.monitorededitor.jamopputil.AST2JaMoPP;
import tools.vitruvius.domains.java.monitorededitor.jamopputil.CompilationUnitAdapter;
import tools.vitruvius.domains.java.monitorededitor.jamopputil.JaMoPPChangeBuildHelper;
import tools.vitruvius.framework.change.description.CompositeChange;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.change.description.VitruviusChangeFactory;
import tools.vitruvius.framework.util.VitruviusConstants;
import tools.vitruvius.framework.util.bridges.EclipseBridge;
import tools.vitruvius.framework.util.datatypes.VURI;

/**
 * The {@link ChangeResponder} implements a {@link ChangeEventVisitor} for
 * {@link ChangeClassifyingEvent}s. It uses the AST information in the change events to build
 * {@link EMFModelChange}s with the {@link JaMoPPChangeBuildHelper}. These EMF changes are submitted
 * to the {@link MonitoredEditor}.
 */
public class ChangeResponder implements ChangeEventVisitor {

    private static final Logger logger = Logger.getLogger(ChangeResponder.class.getSimpleName());

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
        for (final ChangeEventExtendedVisitor visitor : getRegisteredVisitors(
                "tools.vitruvius.domains.java.monitorededitor")) {
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
        final Parametrizable newMethodOrConstructor = originalCU
                .getMethodOrConstructorForMethodDeclaration(newMethodDeclaration);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(addMethodEvent.typeBeforeAdd);
        final ConcreteClassifier classifierBeforeAdd = changedCU
                .getConcreteClassifierForTypeDeclaration(addMethodEvent.typeBeforeAdd);
        final EChange eChange = JaMoPPChangeBuildHelper.createAddMethodChange(newMethodOrConstructor,
                classifierBeforeAdd);
        this.util.submitVitruviusModelChange(eChange, addMethodEvent.method);
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
        this.util.submitVitruviusModelChange(eChange, createInterfaceEvent.type);

    }

    @Override
    public void visit(final CreateClassEvent createClassEvent) {
        final TypeDeclaration type = createClassEvent.type;
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(createClassEvent.compilationUnitBeforeCreate);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(type);
        final Class newClass = (Class) changedCU.getConcreteClassifierForTypeDeclaration(type);
        final CompilationUnit beforeChange = null == originalCU ? null : originalCU.getCompilationUnit();
        final EChange eChange = JaMoPPChangeBuildHelper.createAddClassChange(newClass, beforeChange);
        this.util.submitVitruviusModelChange(eChange, createClassEvent.type);

    }

    @Override
    public void visit(final ChangeMethodReturnTypeEvent changeMethodReturnTypeEvent) {
        this.setLastCallTime();
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodReturnTypeEvent.original);
        final Parametrizable original = originalCU
                .getMethodOrConstructorForMethodDeclaration(changeMethodReturnTypeEvent.original);
        final CompilationUnitAdapter cu = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodReturnTypeEvent.renamed);
        final Parametrizable changed = cu
                .getMethodOrConstructorForMethodDeclaration(changeMethodReturnTypeEvent.renamed);
        if (changed instanceof Method && original instanceof Method) {
            final EChange eChange = JaMoPPChangeBuildHelper.createChangeMethodReturnTypeChange((Method) original,
                    (Method) changed);
            this.util.submitVitruviusModelChange(eChange, changeMethodReturnTypeEvent.original);
        } else {
            logger.info(
                    "Change method return type could not be reported. Either original or changed is not instanceof method: orginal: "
                            + " changed: " + changed);
        }
    }

    @Override
    public void visit(final RemoveMethodEvent removeMethodEvent) {
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(removeMethodEvent.method);
        final Parametrizable removedMethod = originalCU
                .getMethodOrConstructorForMethodDeclaration(removeMethodEvent.method);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeMethodEvent.typeAfterRemove);
        final ConcreteClassifier classifierAfterRemove = changedCU
                .getConcreteClassifierForTypeDeclaration(removeMethodEvent.typeAfterRemove);
        final EChange eChange = JaMoPPChangeBuildHelper.createRemoveMethodChange(removedMethod, classifierAfterRemove);
        this.util.submitVitruviusModelChange(eChange, removeMethodEvent.method);
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
        this.util.submitVitruviusModelChange(eChange, deleteClassEvent.type);
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
        this.util.submitVitruviusModelChange(eChange, deleteInterfaceEvent.type);
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
        final Parametrizable original = originalCU
                .getMethodOrConstructorForMethodDeclaration(renameMethodEvent.original);
        final URI uri = this.util.getFirstExistingURI(renameMethodEvent.renamed, renameMethodEvent.original);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(renameMethodEvent.renamed,
                uri);
        final Parametrizable changed = changedCU.getMethodOrConstructorForMethodDeclaration(renameMethodEvent.renamed);
        if (changed instanceof Member && original instanceof Member) {
            final EChange eChange = JaMoPPChangeBuildHelper.createRenameMethodChange((Member) original,
                    (Member) changed);
            this.util.submitVitruviusModelChange(eChange, renameMethodEvent.original);
        } else {
            logger.info(
                    "Could not execute rename method event, cause original or changed is not instance of Member. Original: "
                            + original + " Changed: " + changed);
        }
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
        this.util.submitVitruviusModelChange(eChange, renameFieldEvent.original);

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
        final CompilationUnitAdapter cuRenamed = this.util
                .getUnsavedCompilationUnitAdapter(renameInterfaceEvent.renamed, uri);
        final Interface renamedInterface = (Interface) cuRenamed
                .getConcreteClassifierForTypeDeclaration(renameInterfaceEvent.renamed);

        final EChange eChange = JaMoPPChangeBuildHelper.createRenameInterfaceChange(originalInterface,
                renamedInterface);
        this.util.submitVitruviusModelChange(eChange, renameInterfaceEvent.original);
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
        this.util.submitVitruviusModelChange(eChange, renameClassEvent.original);
    }

    @Override
    public void visit(final AddImportEvent addImportEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(addImportEvent.importDeclaration);
        final Import imp = originalCU.getImportForImportDeclaration(addImportEvent.importDeclaration);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(addImportEvent.compilationUnitBeforeAdd);
        final EChange eChange = JaMoPPChangeBuildHelper.createAddImportChange(imp, changedCU.getCompilationUnit());
        this.util.submitVitruviusModelChange(eChange, addImportEvent.importDeclaration);
    }

    @Override
    public void visit(final RemoveImportEvent removeImportEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(removeImportEvent.importDeclaration);
        final Import imp = originalCU.getImportForImportDeclaration(removeImportEvent.importDeclaration);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeImportEvent.compilationUnitAfterRemove);
        final EChange eChange = JaMoPPChangeBuildHelper.createRemoveImportChange(imp, changedCU.getCompilationUnit());
        this.util.submitVitruviusModelChange(eChange, removeImportEvent.importDeclaration);
    }

    @Override
    public void visit(final MoveMethodEvent moveMethodEvent) {
        final CompilationUnitAdapter originalCU = this.util.getUnsavedCompilationUnitAdapter(moveMethodEvent.original);
        final CompilationUnitAdapter changedCU = this.util.getUnsavedCompilationUnitAdapter(moveMethodEvent.moved);
        final ConcreteClassifier classifierMovedFromAfterRemove = originalCU
                .getConcreteClassifierForTypeDeclaration(moveMethodEvent.typeMovedFromAfterRemove);
        final ConcreteClassifier classifierMovedToBeforeAdd = originalCU
                .getConcreteClassifierForTypeDeclaration(moveMethodEvent.typeMovedToBeforeAdd);
        final Parametrizable removedParametrizable = originalCU
                .getMethodOrConstructorForMethodDeclaration(moveMethodEvent.original);
        final Parametrizable addedParametrizable = changedCU
                .getMethodOrConstructorForMethodDeclaration(moveMethodEvent.moved);
        if (removedParametrizable instanceof Method && addedParametrizable instanceof Method) {
            final Method addedMethod = (Method) addedParametrizable;
            final Method removedMethod = (Method) removedParametrizable;

            final EChange[] eChanges = JaMoPPChangeBuildHelper.createMoveMethodChange(removedMethod,
                    classifierMovedFromAfterRemove, addedMethod, classifierMovedToBeforeAdd);
            final CompositeChange moveMethodChange = VitruviusChangeFactory.getInstance().createCompositeChange();
            // [0] is remove, [1] is add
            final GeneralChange removeMethodChange = this.util.wrapToVitruviusModelChange(eChanges[0],
                    moveMethodEvent.original);
            final GeneralChange addMethodChange = this.util.wrapToVitruviusModelChange(eChanges[1], moveMethodEvent.moved);
            moveMethodChange.addChange(removeMethodChange);
            moveMethodChange.addChange(addMethodChange);
            this.monitoredEditor.submitChange(moveMethodChange);
        } else {
            logger.info("could not report move method because either added or removed method is not a method. Added: "
                    + addedParametrizable + " Removed: " + removedParametrizable);
        }
    }

    @Override
    public void visit(final AddSuperInterfaceEvent addSuperInterfaceEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(addSuperInterfaceEvent.baseType);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(addSuperInterfaceEvent.superType);
        if (!(addSuperInterfaceEvent.superType instanceof SimpleType)) {
            logger.warn("visit AddSuperInterfaceEvent failed: super type is not an instance of SimpleType: "
                    + addSuperInterfaceEvent.superType);
            return;
        }
        final TypeReference implementsTypeRef = changedCU
                .getImplementsForSuperType((SimpleType) addSuperInterfaceEvent.superType);
        final ConcreteClassifier affectedClassifier = originalCU
                .getConcreteClassifierForTypeDeclaration(addSuperInterfaceEvent.baseType);
        final EChange eChange = JaMoPPChangeBuildHelper.createAddSuperInterfaceChange(affectedClassifier,
                implementsTypeRef);
        this.util.submitVitruviusModelChange(eChange, addSuperInterfaceEvent.baseType);
    }

    @Override
    public void visit(final RemoveSuperInterfaceEvent removeSuperInterfaceEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(removeSuperInterfaceEvent.baseType);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(removeSuperInterfaceEvent.superType);
        if (!(removeSuperInterfaceEvent.superType instanceof SimpleType)) {
            logger.warn("visit AddSuperInterfaceEvent failed: super type is not an instance of SimpleType: "
                    + removeSuperInterfaceEvent.superType);
            return;
        }
        final TypeReference implementsTypeRef = originalCU
                .getImplementsForSuperType((SimpleType) removeSuperInterfaceEvent.superType);
        final ConcreteClassifier affectedClassifier = changedCU
                .getConcreteClassifierForTypeDeclaration(removeSuperInterfaceEvent.baseType);
        final EChange eChange = JaMoPPChangeBuildHelper.createRemoveSuperInterfaceChange(affectedClassifier,
                implementsTypeRef);
        this.util.submitVitruviusModelChange(eChange, removeSuperInterfaceEvent.baseType);
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
        final Parametrizable original = originalCU
                .getMethodOrConstructorForMethodDeclaration(changeMethodParameterEvent.original);
        final CompilationUnitAdapter cu = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodParameterEvent.renamed);
        final Parametrizable changed = cu
                .getMethodOrConstructorForMethodDeclaration(changeMethodParameterEvent.renamed);
        this.handleParameterChanges(changed, original, original.getParameters(), changed.getParameters(),
                changeMethodParameterEvent.original);
    }

    private void handleParameterChanges(final Parametrizable methodAfterRemove, final Parametrizable methodBeforeAdd,
            final List<Parameter> oldParameters, final List<Parameter> newParameters, final ASTNode oldNode) {
        final CompositeChange compositeChange = VitruviusChangeFactory.getInstance().createCompositeChange();
        /*
         * for (final Parameter oldParameter : oldParameters) { final EChange eChange =
         * JaMoPPChangeBuildHelper.createRemoveParameterChange(oldParameter, methodAfterRemove);
         * compositeChange.addChange(this.util.wrapToEMFModelChange(eChange, oldNode)); } for (final
         * Parameter newParameter : newParameters) { final EChange eChange =
         * JaMoPPChangeBuildHelper.createAddParameterChange(newParameter, methodBeforeAdd);
         * compositeChange.addChange(this.util.wrapToEMFModelChange(eChange, oldNode)); }
         */

        // diff the parameter list to figure out which parameters are added respectievly removed
        for (final Parameter oldParameter : oldParameters) {
            if (!this.containsParameter(oldParameter, newParameters)) {
                // old Parameter is no longer contained in newParameters list
                final EChange eChange = JaMoPPChangeBuildHelper.createRemoveParameterChange(oldParameter,
                        methodAfterRemove);
                compositeChange.addChange(this.util.wrapToVitruviusModelChange(eChange, oldNode));
            }
        }
        for (final Parameter newParameter : newParameters) {
            if (!this.containsParameter(newParameter, oldParameters)) {
                // new Parameter is not contained in oldParameters list --> new Parameter has been
                // created
                final EChange eChange = JaMoPPChangeBuildHelper.createAddParameterChange(newParameter, methodBeforeAdd);
                compositeChange.addChange(this.util.wrapToVitruviusModelChange(eChange, oldNode));
            }
        }
        this.monitoredEditor.submitChange(compositeChange);
    }

    boolean containsParameter(final Parameter parameter, final List<Parameter> parameterList) {
		for (final Parameter parameterInList : parameterList) {
			// we consider parameters equal if they name is identical, the
			// return type is
			// identical, and they arrayDimension is equal
			if (parameterInList.getName().equals(parameter.getName())
					&& targetInTypeReferenceEquals(parameter.getTypeReference(), parameterInList.getTypeReference())
					&& parameterInList.getArrayDimension() == parameter.getArrayDimension()) {
				return true;
			}
		}
		return false;
	}

	private boolean targetInTypeReferenceEquals(final TypeReference typeRef1, final TypeReference typeRef2) {
		if (typeRef1.getTarget() == null && typeRef2.getTarget() == null) {
			return true;
		}
		if (typeRef1.getTarget() == null) {
			return false;
		}
		if (typeRef2.getTarget() == null) {
			return false;
		}
		if (!typeEquals(typeRef1.getTarget(), typeRef2.getTarget())) {
			return false;
		}
		return true;
	}
	
	/**
	* compares two types and returns if they are equal
	*
	* @param type1
	* @param type2
	* @return
	*/
	private static boolean typeEquals(final Type type1, final Type type2) {
		if (type1 == type2) {
			return true;
		}

		final boolean sameType = type1.getClass().equals(type2.getClass());
		if (!sameType) {
			// both types have to be from the same type e.g. ConcreteClassifier
			return false;
		}
		if (type1 instanceof PrimitiveType && type2 instanceof PrimitiveType) {
			// both have the same type and they are primitive types-->same type
			return true;
		}
		if (type1 instanceof NamedElement && type2 instanceof NamedElement) {
			final NamedElement ne1 = (NamedElement) type1;
			final NamedElement ne2 = (NamedElement) type2;
			return ne1.getName().equals(ne2.getName());
		}
		return false;
	}


    @Override
    public void visit(final ChangeMethodModifiersEvent changeMethodModifierEvent) {
        this.setLastCallTime();
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodModifierEvent.original);
        final Parametrizable originalMethod = originalCU
                .getMethodOrConstructorForMethodDeclaration(changeMethodModifierEvent.original);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(changeMethodModifierEvent.renamed);
        final Parametrizable changedMethod = changedCU
                .getMethodOrConstructorForMethodDeclaration(changeMethodModifierEvent.renamed);
        if (originalMethod instanceof AnnotableAndModifiable && changedMethod instanceof AnnotableAndModifiable) {
            final AnnotableAndModifiable originalModifiable = (AnnotableAndModifiable) originalMethod;
            final AnnotableAndModifiable changedModifiable = (AnnotableAndModifiable) changedMethod;
            final CompositeChange change = this.buildModifierChanges(originalMethod, changedMethod,
                    originalModifiable.getModifiers(), changedModifiable.getModifiers(),
                    changeMethodModifierEvent.original);
            this.monitoredEditor.submitChange(change);
        } else {
            logger.info(
                    "ChangeMethodModifiersEvent type could not be reported. Either original or changed is not instanceof Modifiable: orginal: "
                            + originalMethod + " changed: " + changedMethod);
        }
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

        final CompositeChange modifierChanges = VitruviusChangeFactory.getInstance().createCompositeChange();
        for (final Modifier removedModifier : originalModifiers) {
            final EChange eChange = JaMoPPChangeBuildHelper.createRemoveAnnotationOrModifierChange(removedModifier,
                    modifiableAfterChange);
            modifierChanges.addChange(this.util.wrapToVitruviusModelChange(eChange, oldNode));
        }
        for (final Modifier newModifier : changedModifiers) {
            final EChange eChange = JaMoPPChangeBuildHelper.createAddAnnotationOrModifierChange(newModifier,
                    modifiableBeforeChange);
            modifierChanges.addChange(this.util.wrapToVitruviusModelChange(eChange, oldNode));
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
        this.util.submitVitruviusModelChange(eChange, addFieldEvent.field);
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
        this.util.submitVitruviusModelChange(eChange, removeFieldEvent.field);
    }

    @Override
    public void visit(final ChangeFieldModifiersEvent changeFieldModifiersEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(changeFieldModifiersEvent.original);
        final List<Field> originalFields = originalCU.getFieldsForFieldDeclaration(changeFieldModifiersEvent.original);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(changeFieldModifiersEvent.changed);
        final List<Field> changedFields = changedCU.getFieldsForFieldDeclaration(changeFieldModifiersEvent.changed);

        final CompositeChange allFieldModifierChanges = VitruviusChangeFactory.getInstance().createCompositeChange();
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

        final CompositeChange typeChanges = VitruviusChangeFactory.getInstance().createCompositeChange();
        final ListIterator<Field> ofit = originalFields.listIterator();
        while (ofit.hasNext()) {
            final Field oField = ofit.next();
            final ListIterator<Field> cfit = changedFields.listIterator();
            while (cfit.hasNext()) {
                final Field cField = cfit.next();
                if (oField.getName().equals(cField.getName())) {
                    cfit.remove();
                    final EChange eChange = JaMoPPChangeBuildHelper.createChangeFieldTypeChange(oField, cField);
                    typeChanges.addChange(this.util.wrapToVitruviusModelChange(eChange, changeFieldTypeEvent.original));
                }
            }
        }
        this.monitoredEditor.submitChange(typeChanges);
    }

    @Override
    public void visit(final AddAnnotationEvent addAnnotationEvent) {
        logger.info("React to AddMethodAnnotationEvent");
        final CompilationUnitAdapter oldCU = this.util
                .getUnsavedCompilationUnitAdapter(addAnnotationEvent.bodyDeclaration);
        final AnnotableAndModifiable annotableAndModifiable = oldCU
                .getAnnotableAndModifiableForBodyDeclaration(addAnnotationEvent.bodyDeclaration);

        final CompilationUnitAdapter newCu = this.util.getUnsavedCompilationUnitAdapter(addAnnotationEvent.annotation);
        final AnnotationInstance annotationInstance = newCu.getAnnotationInstanceForMethodAnnotation(
                addAnnotationEvent.annotation, addAnnotationEvent.bodyDeclaration);
        if (null != annotationInstance) {
            final EChange eChange = JaMoPPChangeBuildHelper.createAddAnnotationOrModifierChange(annotationInstance,
                    annotableAndModifiable);
            this.util.submitVitruviusModelChange(eChange, addAnnotationEvent.annotation);
        }
    }

    @Override
    public void visit(final RemoveAnnotationEvent removeAnnotationEvent) {
        final CompilationUnitAdapter cuWithAnnotation = this.util
                .getUnsavedCompilationUnitAdapter(removeAnnotationEvent.annotation);
        final AnnotationInstance removedAnnotation = cuWithAnnotation.getAnnotationInstanceForMethodAnnotation(
                removeAnnotationEvent.annotation, removeAnnotationEvent.bodyAfterChange);
        if (null != removedAnnotation) {
            final CompilationUnitAdapter cuWithoutAnnotation = this.util
                    .getUnsavedCompilationUnitAdapter(removeAnnotationEvent.bodyAfterChange);
            final AnnotableAndModifiable annotableAndModifiable = cuWithoutAnnotation
                    .getAnnotableAndModifiableForBodyDeclaration(removeAnnotationEvent.bodyAfterChange);
            final EChange eChange = JaMoPPChangeBuildHelper.createRemoveAnnotationOrModifierChange(removedAnnotation,
                    annotableAndModifiable);
            this.util.submitVitruviusModelChange(eChange, removeAnnotationEvent.annotation);
        }
    }

    @Override
    public void visit(final RenamePackageEvent renamePackageEvent) {
        final EChange renamePackageChange = JaMoPPChangeBuildHelper.createRenamePackageChange(
                renamePackageEvent.originalPackageName, renamePackageEvent.renamedPackageName);
        this.util.submitVitruviusModelChange(renamePackageChange, renamePackageEvent.originalIResource);

    }

    @Override
    public void visit(final DeletePackageEvent deletePackageEvent) {
        final EChange deletePackageChange = JaMoPPChangeBuildHelper
                .createDeletePackageChange(deletePackageEvent.packageName);
        this.util.submitVitruviusModelChange(deletePackageChange, deletePackageEvent.iResource);

    }

    @Override
    public void visit(final CreatePackageEvent addPackageEvent) {
        final EChange createPackageChange = JaMoPPChangeBuildHelper
                .createCreatePackageChange(addPackageEvent.packageName);
        this.util.submitVitruviusModelChange(createPackageChange, addPackageEvent.iResource);
    }

    @Override
    public void visit(final RenameParameterEvent renameParameterEvent) {
        final CompilationUnitAdapter originalCU = this.util
                .getUnsavedCompilationUnitAdapter(renameParameterEvent.original);
        final Parameter original = originalCU.getParameterForVariableDeclaration(renameParameterEvent.originalParam);
        final URI uri = this.util.getFirstExistingURI(renameParameterEvent.original, renameParameterEvent.renamed);
        final CompilationUnitAdapter changedCU = this.util
                .getUnsavedCompilationUnitAdapter(renameParameterEvent.renamed, uri);
        final Parameter renamed = changedCU.getParameterForVariableDeclaration(renameParameterEvent.changedParam);
        final EChange eChange = JaMoPPChangeBuildHelper.createRenameParameterChange(original, renamed);
        this.util.submitVitruviusModelChange(eChange, renameParameterEvent.original);
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

        private void submitVitruviusModelChange(final EChange eChange, final ASTNode astNodeWithIResource) {
            final GeneralChange change = this.wrapToVitruviusModelChange(eChange, astNodeWithIResource);
            ChangeResponder.this.monitoredEditor.submitChange(change);
        }

        private void submitVitruviusModelChange(final EChange eChange, final IResource originalIResource) {
            final GeneralChange change = this.wrapToVitruviusModelChange(eChange, originalIResource);
            ChangeResponder.this.monitoredEditor.submitChange(change);
        }

        private GeneralChange wrapToVitruviusModelChange(final EChange eChange, final ASTNode astNodeWithIResource) {
            final VURI vuri = VURI.getInstance(AST2JaMoPP.getIResource(astNodeWithIResource));
            return VitruviusChangeFactory.getInstance().createGeneralChange(Collections.singletonList(eChange), vuri);
        }

        private GeneralChange wrapToVitruviusModelChange(final EChange eChange, final IResource originalIResource) {
            final VURI vuri = VURI.getInstance(originalIResource);
            return VitruviusChangeFactory.getInstance().createGeneralChange(Collections.singletonList(eChange), vuri);
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
