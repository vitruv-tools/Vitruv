package tools.vitruv.framework.domains.repository;

import tools.vitruv.framework.domains.TuidAwareVitruvDomain;

public class VitruvDomainPair {
    private TuidAwareVitruvDomain metamodelA;
    private TuidAwareVitruvDomain metamodelB;

    /**
     * Orders the given metamodels in ascending lexicographic order to avoid double put, get and
     * update in all maps etc.: returns Mapping(A,B) if A < B and Mapping(B,A) if B < A.
     *
     * @param metamodelA
     * @param metamodelB
     */
    public VitruvDomainPair(TuidAwareVitruvDomain metamodelA, TuidAwareVitruvDomain metamodelB) {
    	if (metamodelA == null || metamodelB == null) {
    		throw new IllegalArgumentException("Metamodels must not be null");
    	}
        if (metamodelA.compareTo(metamodelB) > 0) {
            // swap
        	TuidAwareVitruvDomain tmp = metamodelA;
            metamodelA = metamodelB;
            metamodelB = tmp;
        }
        this.metamodelA = metamodelA;
        this.metamodelB = metamodelB;
    }

    public TuidAwareVitruvDomain getMetamodelA() {
        return this.metamodelA;
    }

    public TuidAwareVitruvDomain getMetamodelB() {
        return this.metamodelB;
    }
    
    @Override
    public boolean equals(Object otherObj) {
    	if (null == otherObj || !(otherObj instanceof VitruvDomainPair)) {
            return false;
        }
        final VitruvDomainPair otherPair = (VitruvDomainPair) otherObj;
        return this.getMetamodelA().equals(otherPair.getMetamodelA()) && this.getMetamodelB().equals(this.getMetamodelB());
    	
    	
    }
    
//    public boolean isPairOf(VURI metamodel1vuri, VURI metamodel2vuri) {
//    	return (metamodelA.isMetamodelFor(metamodel1vuri) && metamodelB.isMetamodelFor(metamodel2vuri) ||
//    		metamodelB.isMetamodelFor(metamodel1vuri) && metamodelA.isMetamodelFor(metamodel2vuri));
//    }
    
}
