package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * The {@link JavaModel2ASTCorrespondence} is a utility class to check correspondence between
 * {@link IJavaElement}s and {@link ASTNode}s. Checks typically include name equality and
 * furthermore assure equality among types, e.g. variable types, return types, parameter types.
 */
public final class JavaModel2ASTCorrespondence {

    private static final Logger LOG = Logger.getLogger(JavaModel2ASTCorrespondence.class);

    private JavaModel2ASTCorrespondence() {
    }

    // TODO take care of methods with same name but otherwise different
    // signature
    static boolean corresponds(IMethod imethod, MethodDeclaration method, boolean compareOnlyName) {
        boolean sameName = method.getName().getFullyQualifiedName().equals(imethod.getElementName());
        boolean sameMethodParameters = sameMethodParameters(imethod, method);
        return sameName && (compareOnlyName || sameMethodParameters);
    }

    private static boolean sameMethodParameters(IMethod imethod, MethodDeclaration method) {
        if (method.parameters().size() != imethod.getParameterTypes().length)
            return false;

        int i = 0;
        String[] expectedParameterTypes = imethod.getParameterTypes();
        for (Object parameter : method.parameters()) {
            SingleVariableDeclaration var = (SingleVariableDeclaration) parameter;
            String parameterTypeName = Signature.toString(expectedParameterTypes[i++]);
            if (!parameterTypeName.equals(var.getType().toString()))
                return false;
        }
        return true;
    }

    private static NullProgressMonitor nullProgressMonitor = new NullProgressMonitor();

    /**
     * WARNING: does not check type category (interface or class) if itype does currently not exist
     * (cf. <code>{@link IJavaElement}.exists()</code>)
     * 
     * @param itype
     * @param type
     * @return true iff itype corresponds to type
     * @throws JavaModelException
     *             <s>if itype is not open</s>
     */
    static boolean corresponds(IType itype, TypeDeclaration type) throws JavaModelException {
        boolean sameName = false;
        if (!itype.isAnonymous()) {
            sameName = itype.getElementName().equals(type.getName().getIdentifier());
        } else {
            sameName = itype.getSuperclassName().equals(type.getName().getIdentifier());
        }
        if (!itype.exists())
            return sameName;
        else {
            boolean sameTypeCategory = itype.isInterface() == type.isInterface();
            return sameName && sameTypeCategory;
        }
    }

    static boolean corresponds(IImportDeclaration iimportDeclaration, ImportDeclaration importDeclaration) {
        LOG.trace(iimportDeclaration.getElementName().replaceFirst("\\.\\*", "") + " ?= " + importDeclaration.getName());
        return iimportDeclaration.getElementName().replaceFirst("\\.\\*", "")
                .equals(importDeclaration.getName().getFullyQualifiedName());
    }

    /**
     * Checks if ifield and field have the same name. No consistency checks for type or modifiers.
     * 
     * @param ifield
     * @param field
     * @return
     */
    public static boolean weaklyCorresponds(IField ifield, FieldDeclaration field) {
        for (Object variable : field.fragments()) {
            if (correspondsByName(ifield, (VariableDeclarationFragment) variable))
                return true;
        }
        return false;
    }

    /**
     * Checks if ifield and field have the same name, type and modifiers.
     * 
     * @param ifield
     * @param field
     * @return
     * @throws JavaModelException
     *             if ifield is not open, i.e. its ElementInfo has not been loaded
     * @throws IllegalArgumentException
     */
    public static boolean strictlyCorresponds(IField ifield, FieldDeclaration field) throws JavaModelException {
        // assumption: JDT modifier flags are computed the same way as AST modifier flags
        boolean sameModifiers = (ifield.getFlags() == field.getModifiers());
        boolean sameType = Signature.toString(ifield.getTypeSignature()).equals(field.getType().toString());
        if (!sameModifiers || !sameType)
            return false;
        return weaklyCorresponds(ifield, field);
    }

    // field names have to be unique in one type scope
    public static boolean correspondsByName(IField ifield, VariableDeclarationFragment variable) {
        boolean sameName = ifield.getElementName().equals(variable.getName().getIdentifier());
        return sameName;
    }

    public static boolean corresponds(IAnnotation iannotation, Annotation modifier) {
        String name1 = iannotation.getElementName();
        String name2 = modifier.getTypeName().getFullyQualifiedName();
        boolean sameName = name1.equals(name2);
        return sameName;
    }

    public static boolean corresponds(IType anonymousIType, AnonymousClassDeclaration anonymousClassDeclaration)
            throws JavaModelException {
        List<IMethod> imethods = Arrays.asList(anonymousIType.getMethods());

        for (Object method : Util.filterBySupertype(anonymousClassDeclaration.bodyDeclarations(),
                MethodDeclaration.class)) {
            MethodDeclaration methodDeclaration = (MethodDeclaration) method;
            boolean match = false;
            ListIterator<IMethod> imethodIt = imethods.listIterator();
            while (imethodIt.hasNext()) {
                IMethod imethod = imethodIt.next();
                if (JavaModel2ASTCorrespondence.corresponds(imethod, methodDeclaration, false)) {
                    match = true;
                    imethodIt.remove();
                    break;
                }
            }
            if (!match)
                return false;
        }
        return true;
    }
}
