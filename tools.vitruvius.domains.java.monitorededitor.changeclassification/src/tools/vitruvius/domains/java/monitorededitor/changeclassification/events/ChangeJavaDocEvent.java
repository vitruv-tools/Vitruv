package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Javadoc;

public class ChangeJavaDocEvent extends JavaDocEvent {

    Javadoc original;

    public ChangeJavaDocEvent(Javadoc original, Javadoc changed) {
        super(changed);
        this.original = original;
    }

    @Override
    public String toString() {
        return "ChangeJavaDocEvent [original=" + this.original.toString() + ", changed=" + this.comment.toString()
                + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
