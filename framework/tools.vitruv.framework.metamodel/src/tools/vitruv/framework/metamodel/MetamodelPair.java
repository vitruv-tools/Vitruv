package tools.vitruv.framework.metamodel;

public class MetamodelPair {
    private Metamodel metamodelA;
    private Metamodel metamodelB;

    /**
     * Orders the given metamodels in ascending lexicographic order to avoid double put, get and
     * update in all maps etc.: returns Mapping(A,B) if A < B and Mapping(B,A) if B < A.
     *
     * @param metamodelA
     * @param metamodelB
     */
    public MetamodelPair(Metamodel metamodelA, Metamodel metamodelB) {
    	if (metamodelA == null || metamodelB == null) {
    		throw new IllegalArgumentException("Metamodels must not be null");
    	}
        if (metamodelA.compareTo(metamodelB) > 0) {
            // swap
            Metamodel tmp = metamodelA;
            metamodelA = metamodelB;
            metamodelB = tmp;
        }
        this.metamodelA = metamodelA;
        this.metamodelB = metamodelB;
    }

    public Metamodel getMetamodelA() {
        return this.metamodelA;
    }

    public Metamodel getMetamodelB() {
        return this.metamodelB;
    }
    
    @Override
    public boolean equals(Object otherObj) {
    	if (null == otherObj || !(otherObj instanceof MetamodelPair)) {
            return false;
        }
        final MetamodelPair otherPair = (MetamodelPair) otherObj;
        return this.getMetamodelA().equals(otherPair.getMetamodelA()) && this.getMetamodelB().equals(this.getMetamodelB());
    	
    	
    }
    
//    public boolean isPairOf(VURI metamodel1vuri, VURI metamodel2vuri) {
//    	return (metamodelA.isMetamodelFor(metamodel1vuri) && metamodelB.isMetamodelFor(metamodel2vuri) ||
//    		metamodelB.isMetamodelFor(metamodel1vuri) && metamodelA.isMetamodelFor(metamodel2vuri));
//    }
    
}
