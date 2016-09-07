package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class AddMethodEvent extends ChangeClassifyingEvent implements HasLineInformation {

    public final TypeDeclaration typeBeforeAdd;
    public final MethodDeclaration method;
    public final int line;

    public AddMethodEvent(TypeDeclaration typeBeforeAdd, MethodDeclaration method, int line) {
        this.typeBeforeAdd = typeBeforeAdd;
        this.method = method;
        this.line = line;
    }

    @Override
    public String toString() {
        return "AddMethodEvent [method=" + this.method.getName().getIdentifier() + ", line=" + this.line + "]";
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
