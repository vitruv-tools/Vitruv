package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events;

import org.eclipse.jdt.core.dom.Javadoc;

public class AddJavaDocEvent extends JavaDocEvent {

    public AddJavaDocEvent(Javadoc comment) {
        super(comment);
    }

    @Override
    public String toString() {
        return "AddJavaDocEvent [comment=" + this.comment.toString() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
