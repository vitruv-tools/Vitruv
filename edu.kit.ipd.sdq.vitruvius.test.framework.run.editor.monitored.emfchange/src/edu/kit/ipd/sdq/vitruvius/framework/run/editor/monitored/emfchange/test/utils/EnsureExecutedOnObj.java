package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.utils;

public class EnsureExecutedOnObj<T> extends EnsureExecuted {
    private T obj;
    
    public void markExecuted(T obj) {
        markExecuted();
        this.obj = obj;
    }
    
    public T getCarriedObject() {
        return obj;
    }
    
    public boolean isIndicatingFail(T obj) {
        return isIndicatingFail() || this.obj != obj;
    }
}