package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

public class ModelInstance {
    private VURI vuri;
    
    //TODO: check if we need a constructor without parameter
    public ModelInstance(){
    }
    
    public ModelInstance(VURI vuri){
        this.setVURI(vuri);
    }

    public VURI getVURI() {
        return vuri;
    }

    public void setVURI(VURI vuri) {
        this.vuri = vuri;
    }
    
}
