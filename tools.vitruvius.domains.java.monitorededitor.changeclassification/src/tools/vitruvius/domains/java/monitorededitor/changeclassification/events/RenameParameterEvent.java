package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;

public class RenameParameterEvent extends ChangeMethodParameterEvent {

    public final VariableDeclaration originalParam;
    public final VariableDeclaration changedParam;
    
    public RenameParameterEvent(VariableDeclaration original, VariableDeclaration renamed, int line) {
        super((MethodDeclaration)original.getParent(), (MethodDeclaration)renamed.getParent(), line);
        this.originalParam = original;
        this.changedParam = renamed;
    }
    
    @Override
    public String toString() {
        return "RenameParameterEvent [original=" + this.originalParam.toString().replace(";\n", "") + ", changed="
                + this.changedParam.toString().replace(";\n", "") + ", line=" + this.line + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }
}
