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

public class EnsureNotExecuted {
    private boolean hasExecuted = false;
    
    public void markExecuted() {
        hasExecuted = true;
    }
    
    public boolean isIndicatingFail() {
        return hasExecuted;
    }
}
