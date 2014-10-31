package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CompositeChange extends Change {

    private List<Change> changes;

    public CompositeChange(final Change... changes) {
        this.changes = new LinkedList<Change>(Arrays.asList(changes));
    }

    public List<Change> getChanges() {
        if (this.changes == null) {
            this.changes = new LinkedList<Change>();
        }
        return this.changes;
    }

    public void addChange(final Change change) {
        this.changes.add(change);
    }
}
