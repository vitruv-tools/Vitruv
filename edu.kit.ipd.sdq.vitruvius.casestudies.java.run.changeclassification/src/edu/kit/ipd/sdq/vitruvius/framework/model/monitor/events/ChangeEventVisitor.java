package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

/**
 * @author messinger
 * 
 *         Visitor for {@link ChangeClassifyingEvent}s. Contains one visit method for
 *         {@link ChangeClassifyingEventExtension} which should be implemented as a dispatch method
 *         that delegates the change to a for its type registered ChangeEventExtendedVisitor.
 * 
 */
public interface ChangeEventVisitor {

    void visit(ChangeClassifyingEventExtension changeClassifyingEvent);

    void visit(CreateInterfaceEvent addInterfaceEvent);

    void visit(CreateClassEvent addClassEvent);

    void visit(ChangeMethodReturnTypeEvent changeMethodReturnTypeEvent);

    void visit(ChangeMethodParameterEvent changeMethodParameterEvent);

    void visit(RemoveMethodEvent removeMethodEvent);

    void visit(DeleteClassEvent removeClassEvent);

    void visit(DeleteInterfaceEvent removeInterfaceEvent);

    void visit(RenameMethodEvent renameMethodEvent);

    void visit(RenameInterfaceEvent renameInterfaceEvent);

    void visit(RenameClassEvent renameClassEvent);

    void visit(AddImportEvent addImportEvent);

    void visit(RemoveImportEvent removeImportEvent);

    void visit(MoveMethodEvent moveMethodEvent);

    void visit(AddSuperInterfaceEvent addSuperInterfaceEvent);

    void visit(RemoveSuperInterfaceEvent removeSuperInterfaceEvent);

    void visit(ChangeMethodModifiersEvent changeMethodModifierEvent);

    void visit(ChangeClassModifiersEvent changeClassModifiersEvent);

    void visit(ChangeInterfaceModifiersEvent changeInterfaceModifiersEvent);

    void visit(AddFieldEvent addFieldEvent);

    void visit(RemoveFieldEvent removeFieldEvent);

    void visit(ChangeFieldModifiersEvent changeFieldModifiersEvent);

    void visit(ChangeFieldTypeEvent changeFieldTypeEvent);

    void visit(RenameFieldEvent renameFieldEvent);

    void visit(AddMethodAnnotationEvent addMethodAnnotationEvent);

    void visit(RemoveMethodAnnotationEvent removeMethodAnnotationEvent);

    void visit(RenamePackageEvent renamePackageEvent);

    void visit(DeletePackageEvent removePackageEvent);

    void visit(CreatePackageEvent addPackageEvent);

    void visit(RenamePackageDeclarationEvent renamePackageDeclarationEvent);

    void visit(ChangePackageDeclarationEvent changePackageDeclarationEvent);

    void visit(AddJavaDocEvent addJavaDocEvent);

    void visit(RemoveJavaDocEvent removeJavaDocEvent);

    void visit(ChangeJavaDocEvent changeJavaDocEvent);

    void visit(AddSuperClassEvent addSuperClassEvent);

    void visit(RemoveSuperClassEvent removeSuperClassEvent);

    void visit(AddMethodEvent addMethodEvent);

}
