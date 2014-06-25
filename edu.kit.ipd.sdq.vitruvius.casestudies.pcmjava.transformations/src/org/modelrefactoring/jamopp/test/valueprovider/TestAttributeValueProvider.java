///*******************************************************************************
// * Copyright (c) 2006-2012
// * Software Technology Group, Dresden University of Technology
// * DevBoost GmbH, Berlin, Amtsgericht Charlottenburg, HRB 140026
// * 
// * All rights reserved. This program and the accompanying materials
// * are made available under the terms of the Eclipse Public License v1.0
// * which accompanies this distribution, and is available at
// * http://www.eclipse.org/legal/epl-v10.html
// * 
// * Contributors:
// *   Software Technology Group - TU Dresden, Germany;
// *   DevBoost GmbH - Berlin, Germany
// *      - initial API and implementation
// ******************************************************************************/
//package org.modelrefactoring.jamopp.test.valueprovider;
//
//import org.eclipse.emf.ecore.EAttribute;
//import org.eclipse.emf.ecore.EObject;
//import org.emftext.refactoring.interpreter.AbstractValueProvider;
//import org.emftext.refactoring.interpreter.IAttributeValueProvider;
//import org.emftext.refactoring.interpreter.IRefactoringFakeInterpreter;
//import org.emftext.refactoring.interpreter.IRefactoringInterpreter;
//
//public class TestAttributeValueProvider extends AbstractValueProvider<EAttribute, Object> implements
//        IAttributeValueProvider {
//
//    private String newMethodName;
//
//    private EAttribute attribute;
//    private EObject fakeAttributeOwner;
//    private EObject realAttributeOwner;
//    private EAttribute fakeAttribute;
//    private EAttribute realAttribute;
//
//    public void setNewMethodName(String newMethodName) {
//        this.newMethodName = newMethodName;
//    }
//
//    public EAttribute getAttribute() {
//        return attribute;
//    }
//
//    public EObject getAttributeOwner() {
//        return realAttributeOwner;
//    }
//
//    public EObject getFakeAttributeOwner() {
//        return fakeAttributeOwner;
//    }
//
//    public boolean isValueValid(String text) {
//        if (text != null) {
//            return true;
//        }
//        return false;
//    }
//
//    public Object getFakeObject() {
//        return fakeAttribute;
//    }
//
//    public String getName() {
//        return "";
//    }
//
//    public int getReturnCode() {
//        // doesn't matter
//        return 0;
//    }
//
//    public void propagateValueToFakeObject() {
//        if (getValue() != null) {
//            fakeAttributeOwner.eSet(fakeAttribute, getValue());
//        }
//    }
//
//    public Object provideValue(IRefactoringInterpreter interpreter, EAttribute from, Object... context) {
//        if (interpreter instanceof IRefactoringFakeInterpreter) {
//            fakeAttribute = attribute;
//            fakeAttributeOwner = (EObject) context[0];
//        }
//        attribute = from;
//        setValue(newMethodName);
//        return getValue();
//    }
//
//    public void provideValue() {
//        realAttributeOwner = getInverseCopier().get(fakeAttributeOwner);
//        if (realAttributeOwner != null) {
//            realAttribute = (EAttribute) realAttributeOwner.eClass().getEStructuralFeature(fakeAttribute.getName());
//            attribute = realAttribute;
//        }
//    }
// }
