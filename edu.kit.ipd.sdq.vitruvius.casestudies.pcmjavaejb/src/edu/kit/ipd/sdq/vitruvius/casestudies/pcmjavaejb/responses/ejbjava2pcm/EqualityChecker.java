package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjavaejb.responses.ejbjava2pcm;

import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;

//import de.fzi.gast.functions.Function;

/**
 * Helper class
 *
 * @author Klaus Krogmann
 *
 */
public class EqualityChecker {

    /**
     * Checks the equality of two functions by comparing their signatures. Checks name, return type,
     * number of parameters and types of parameters.
     *
     * @param function1
     * @param function2
     * @return true if both functions are equal; false else
     */
    public static boolean areFunctionsEqual(final Method function1, final Method function2) {
        // preconditions
        if (function1 == function2) {
            return true;
        }

        // checks
        if (!checkNameEqual(function1, function2)) {
            return false;
        }

        if (getReturnTypeAccess(function1) == null || getReturnTypeAccess(function2) == null
                || function1.getParameters() == null || function2.getParameters() == null) {
            return false;
        }

        if (!targetInTypeReferenceEquals(getReturnTypeAccess(function1), getReturnTypeAccess(function2))) {
            return false;
        }

        if (!checkParametersEqual(function1, function2)) {
            return false;
        }

        return true;
    }

    private static boolean checkNameEqual(final NamedElement namedElement1, final NamedElement namedElement2) {
        return namedElement1.getName().equals(namedElement2.getName());
    }

    private static boolean checkParametersEqual(final Parametrizable function1, final Parametrizable function2) {
        if (function1.getParameters() != null && function2.getParameters() != null) { // parameter
            if (!(function1.getParameters().size() == function2.getParameters().size())) {
                return false;
            }
        }

        for (int i = 0; i < function1.getParameters().size(); i++) { // parameter types
            if (!targetInTypeReferenceEquals(function1.getParameters().get(i).getTypeReference(),
                    function2.getParameters().get(i).getTypeReference())) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks whether the constructor are equal. First check object identity.
     *
     * @param constructor1
     * @param constructor2
     * @return
     */
    public static boolean areConstructorsEqual(final Constructor constructor1, final Constructor constructor2) {
        if (constructor1 == constructor2) {
            return true;
        }
        if (!checkNameEqual(constructor1, constructor2)) {
            return false;
        }
        if (!checkParametersEqual(constructor1, constructor2)) {
            return false;
        }
        return true;
    }

    private static boolean targetInTypeReferenceEquals(final TypeReference typeRef1, final TypeReference typeRef2) {
        if (typeRef1.getTarget() == null && typeRef2.getTarget() == null) {
            return true;
        }
        if (typeRef1.getTarget() == null) {
            return false;
        }
        if (typeRef2.getTarget() == null) {
            return false;
        }
        if (!typeEquals(typeRef1.getTarget(), typeRef2.getTarget())) {
            return false;
        }
        return true;
    }

    /**
     * compares two types and returns if there are equal
     *
     * @param type1
     * @param type2
     * @return
     */
    private static boolean typeEquals(final Type type1, final Type type2) {
        if (type1 == type2) {
            return true;
        }

        final boolean sameType = type1.getClass().equals(type2.getClass());
        if (!sameType) {
            // both types have to be from the same type e.g. ConcreteClassifier
            return false;
        }
        if (type1 instanceof PrimitiveType && type2 instanceof PrimitiveType) {
            // both have the same type and they are primitive types-->same type
            return true;
        }
        if (type1 instanceof NamedElement && type2 instanceof NamedElement) {
            final NamedElement ne1 = (NamedElement) type1;
            final NamedElement ne2 = (NamedElement) type2;
            return ne1.getName().equals(ne2.getName());
        }
        return false;
    }

    private static TypeReference getReturnTypeAccess(final Method function) {
        if (function instanceof Method) {
            final Method method = function;
            return method.getTypeReference();
        } else {
            return null;
        }
    }
}