/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package tools.vitruv.domains.emf.monitorededitor.test.utils;

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