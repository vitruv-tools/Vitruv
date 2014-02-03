package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public class Change {

    public enum KIND {
        ADD, CHANGE, REMOVE, CREATE, DELETE
    }

    protected final Object oldValue;
    protected final Object newValue;
    protected KIND kind;

    public Change() {
        this.oldValue = this.newValue = this.kind = null;
    }

    public Change(final Object oldValue, final Object newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        if (null == oldValue && null != newValue) {
            this.kind = KIND.ADD;
        } else if (null != oldValue && null != newValue) {
            this.kind = KIND.CHANGE;
        } else {// if (null != oldValue && null == newValue) {
            this.kind = KIND.REMOVE;
        }
    }

    public Object getOldValue() {
        return this.oldValue;
    }

    public Object getNewValue() {
        return this.newValue;
    }

    public KIND getKind() {
        return this.kind;
    }
}
