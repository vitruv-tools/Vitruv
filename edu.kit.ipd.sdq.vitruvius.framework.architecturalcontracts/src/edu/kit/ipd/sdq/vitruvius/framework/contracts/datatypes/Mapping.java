package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public class Mapping implements MetamodelsReferring {
    private Metamodel metamodelA;
    private Metamodel metamodelB;

    public Mapping(final Metamodel metamodelA, final Metamodel metamodelB) {
        this.metamodelA = metamodelA;
        this.metamodelB = metamodelB;
    }

    @Override
    public VURI[] getMetamodelURIs() {
        return new VURI[] { this.metamodelA.getURI(), this.metamodelB.getURI() };
    }
}
