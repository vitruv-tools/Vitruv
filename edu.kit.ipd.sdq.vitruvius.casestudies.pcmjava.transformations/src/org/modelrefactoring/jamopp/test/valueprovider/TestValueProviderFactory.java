/*******************************************************************************
 * Copyright (c) 2006-2012
 * Software Technology Group, Dresden University of Technology
 * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Software Technology Group - TU Dresden, Germany;
 *   DevBoost GmbH - Berlin, Germany
 *      - initial API and implementation
 ******************************************************************************/
package org.modelrefactoring.jamopp.test.valueprovider;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.emftext.refactoring.interpreter.IValueProvider;
import org.emftext.refactoring.interpreter.IValueProviderFactory;

public class TestValueProviderFactory implements IValueProviderFactory {

	private TestAttributeValueProvider testAttributeValueProvider = new TestAttributeValueProvider();
	
	public IValueProvider<?, ?> getValueProviderForCommand(EObject command, Object... context) {
		return testAttributeValueProvider;
	}

	public void registerValueProviderForCommand(EObject command, IValueProvider<?, ?> valueProvider) {
		// empty
	}

	public IValueProvider<?, ?> registerValueProviderForCommand(EObject command, Object... context) {
		return null;
	}

	public List<IValueProvider<?, ?>> getValuesToProvide() {
		// empty
		return null;
	}

	public void setNewMethodName(String newMethodName) {
		testAttributeValueProvider.setNewMethodName(newMethodName);
	}

}
