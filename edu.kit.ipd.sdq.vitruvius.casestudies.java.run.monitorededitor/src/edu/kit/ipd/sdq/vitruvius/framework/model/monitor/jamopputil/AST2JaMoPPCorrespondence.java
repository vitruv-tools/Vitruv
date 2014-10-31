package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil;

import java.util.HashMap;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.classifiers.Interface;
import org.emftext.language.java.generics.QualifiedTypeArgument;
import org.emftext.language.java.generics.TypeArgument;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.instantiations.impl.NewConstructorCallImpl;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;
import org.emftext.language.java.types.TypedElement;
import org.emftext.language.java.types.impl.BooleanImpl;
import org.emftext.language.java.types.impl.ByteImpl;
import org.emftext.language.java.types.impl.CharImpl;
import org.emftext.language.java.types.impl.DoubleImpl;
import org.emftext.language.java.types.impl.FloatImpl;
import org.emftext.language.java.types.impl.IntImpl;
import org.emftext.language.java.types.impl.LongImpl;
import org.emftext.language.java.types.impl.ShortImpl;
import org.emftext.language.java.types.impl.VoidImpl;

public final class AST2JaMoPPCorrespondence {

    private static HashMap<Class<?>, String> primitiveTypeStrings = new HashMap<Class<?>, String>() {
        {
            put(IntImpl.class, "int");
            put(CharImpl.class, "char");
            put(ByteImpl.class, "byte");
            put(DoubleImpl.class, "double");
            put(LongImpl.class, "long");
            put(VoidImpl.class, "void");
            put(BooleanImpl.class, "boolean");
            put(FloatImpl.class, "float");
            put(ShortImpl.class, "short");
        }
    };

    private AST2JaMoPPCorrespondence() {
    }

    static boolean corresponds(MethodDeclaration methodDeclaration, Method method, boolean compareOnlyName) {
        boolean sameName = methodDeclaration.getName().getFullyQualifiedName().equals(method.getName());
        boolean sameMethodParameters = sameMethodParameters(methodDeclaration, method);
        return sameName && (compareOnlyName || sameMethodParameters);
    }

    public static boolean corresponds(FieldDeclaration fieldDeclaration, Field field, boolean compareOnlyName) {
        boolean sameName = varOfSameNameExists(fieldDeclaration, field);
        String fieldType = typeToString(field);
        boolean sameTypes = fieldDeclaration.getType().toString().equals(fieldType); // sameMethodParameters(methodDeclaration,
                                                                                     // method);
        return sameName && (compareOnlyName || sameTypes);
    }

    public static boolean correspondsByName(VariableDeclarationFragment fieldFragment, Field field) {
        return field.getName().equals(fieldFragment.getName().toString());
    }

    private static String typeToString(TypedElement typedElement) {
        return typeToString(typedElement, 0);
    }

    private static String typeToString(TypedElement typedElement, long arrayDimension) {
        String arrayPostfix = "";
        for (long i = 0; i < arrayDimension; ++i) {
            arrayPostfix += "[]";
        }
        TypeReference typeReference = typedElement.getTypeReference();
        return classifierReferenceToString(typeReference) + arrayPostfix;
    }

    private static boolean varOfSameNameExists(FieldDeclaration fieldDeclaration, Field field) {
        for (Object variable : fieldDeclaration.fragments()) {
            if (correspondsByName((VariableDeclarationFragment) variable, field))
                return true;
        }
        return false;
    }

    private static boolean sameMethodParameters(MethodDeclaration methodDeclaration, Method method) {
        if (methodDeclaration.parameters().size() != method.getParameters().size())
            return false;

        int size = method.getParameters().size();
        String[] expectedParameterTypes = new String[size];
        int i = 0;
        for (Parameter param : method.getParameters()) {
            String typeName = typeToString(param, param.getArrayDimension());
            expectedParameterTypes[i++] = typeName;
        }
        i = 0;
        for (Object parameter : methodDeclaration.parameters()) {
            SingleVariableDeclaration var = (SingleVariableDeclaration) parameter;
            String parameterTypeName = expectedParameterTypes[i++];
            if (!parameterTypeName.equals(var.getType().toString()))
                return false;
        }
        return true;
    }

    private static String classifierReferenceToString(TypeReference typeReference) {
        String prefix = "";
        ClassifierReference classifierReference = typeReference.getPureClassifierReference();
        if (classifierReference == null) {
            Type type = typeReference.getTarget();
            if (!(type instanceof PrimitiveType))
                throw new IllegalStateException("This should have been a primtive type.");
            return primitiveTypeStrings.get(type.getClass());
        }

        if (typeReference instanceof NamespaceClassifierReference) {
            prefix = namespacesToString((NamespaceClassifierReference) typeReference);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        Classifier target = classifierReference.getTarget();
        sb.append(target.getName());
        if (classifierReference.getTypeArguments().size() != 0) {
            sb.append('<');
            for (TypeArgument typeArg : classifierReference.getTypeArguments()) {
                sb.append(classifierReferenceToString(((QualifiedTypeArgument) typeArg).getTypeReference()
                        .getPureClassifierReference()));
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('>');
        }
        return sb.toString();
    }

    private static String namespacesToString(NamespaceClassifierReference classifierReference) {
        StringBuilder sb = new StringBuilder();
        for (String segment : classifierReference.getNamespaces()) {
            sb.append(segment);
            sb.append('.');
        }
        return sb.toString();
    }

    //
    // private static String getTypeString(String typeName) {
    // int index = typeName.lastIndexOf("Impl");
    // return typeName.substring(0, index);
    // }

    // TODO don't only check names but more properties to assure 'equality'
    public static boolean corresponds(TypeDeclaration typeDeclaration, ConcreteClassifier classifier) {
        if (classifier.getName().equals(typeDeclaration.getName().getIdentifier())) {
            if (classifier instanceof Interface && typeDeclaration.isInterface())
                return true;
            if (classifier instanceof org.emftext.language.java.classifiers.Class && !typeDeclaration.isInterface())
                return true;
        }
        return false;
    }

    // FIXME only checks simple names not fully qualified
    public static boolean corresponds(ClassInstanceCreation classInstanceCreation,
            NewConstructorCallImpl newConstructorCall) {
        String astTypeName = ((SimpleType) classInstanceCreation.getType()).getName().getFullyQualifiedName();
        String jamoppTypeName = newConstructorCall.getTypeReference().getPureClassifierReference().getTarget()
                .getName();
        boolean sameName = astTypeName.equals(jamoppTypeName);
        // cannot check method correspondence for add/removeMethodEvents...
        // boolean sameMethods = true;

        return sameName;
    }

    public static boolean corresponds(ImportDeclaration importDeclaration, Import imprt) {
        String qualifiedName = imprt.getNamespacesAsString().replace("$", "");
        EStructuralFeature classifierReference = imprt.eClass().getEStructuralFeature("classifier");
        Classifier importedClassifier = (Classifier) imprt.eGet(classifierReference);
        String className = importedClassifier.getName();
        qualifiedName += "." + className;
        return qualifiedName.equals(importDeclaration.getName().getFullyQualifiedName());
    }
}
