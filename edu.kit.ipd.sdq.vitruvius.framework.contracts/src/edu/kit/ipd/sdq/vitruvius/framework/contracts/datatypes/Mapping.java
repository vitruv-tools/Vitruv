package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;

public class Mapping implements MetamodelsReferring {
    private Metamodel metamodelA;
    private Metamodel metamodelB;

    /**
     * Orders the given metamodels in ascending lexicographic order to avoid double put, get and
     * update in all maps etc.: returns Mapping(A,B) if A < B and Mapping(B,A) if B < A.
     *
     * @param metamodelA
     * @param metamodelB
     */
    public Mapping(Metamodel metamodelA, Metamodel metamodelB) {
        if (metamodelA.compareTo(metamodelB) > 0) {
            // swap
            Metamodel tmp = metamodelA;
            metamodelA = metamodelB;
            metamodelB = tmp;
        }
        this.metamodelA = metamodelA;
        this.metamodelB = metamodelB;
    }

    @Override
    public VURI[] getMetamodelURIs() {
        return new VURI[] { this.metamodelA.getURI(), this.metamodelB.getURI() };
    }

    public Metamodel getMetamodelA() {
        return this.metamodelA;
    }

    public Metamodel getMetamodelB() {
        return this.metamodelB;
    }
}
