package tools.vitruvius.domains.java.monitorededitor.changeclassification.events;

import org.eclipse.jdt.core.dom.Javadoc;

public class RemoveJavaDocEvent extends JavaDocEvent {

    public RemoveJavaDocEvent(Javadoc comment) {
        super(comment);
    }

    @Override
    public String toString() {
        return "RemoveJavaDocEvent [comment=" + this.comment.toString() + "]";
    }

    @Override
    public void accept(ChangeEventVisitor visitor) {
        visitor.visit(this);
    }

}
