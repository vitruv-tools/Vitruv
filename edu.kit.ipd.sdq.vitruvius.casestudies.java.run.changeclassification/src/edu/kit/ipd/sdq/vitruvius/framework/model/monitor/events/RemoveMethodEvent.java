package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class RemoveMethodEvent extends ChangeClassifyingEvent implements HasLineInformation {

    public final MethodDeclaration method;
    public final TypeDeclaration typeAfterRemove;
    public final int line;

    public RemoveMethodEvent(TypeDeclaration typeAfterRemove, MethodDeclaration method, int line) {
        this.typeAfterRemove = typeAfterRemove;
        this.method = method;
        this.line = line;
    }

    @Override
    public String toString() {
        return "RemoveMethodEvent [method=" + this.method.getName().getIdentifier() + ", line=" + this.line + "]";
    }

    @Override
    public int getLine() {
        return this.line;
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
