package tools.vitruvius.domains.java.monitorededitor.jamopputil;

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
import org.emftext.language.java.members.Member;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.Parametrizable;
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
		private static final long serialVersionUID = 8465597480374410579L;

		{
            this.put(IntImpl.class, "int");
            this.put(CharImpl.class, "char");
            this.put(ByteImpl.class, "byte");
            this.put(DoubleImpl.class, "double");
            this.put(LongImpl.class, "long");
            this.put(VoidImpl.class, "void");
            this.put(BooleanImpl.class, "boolean");
            this.put(FloatImpl.class, "float");
            this.put(ShortImpl.class, "short");
        }
    };

    private AST2JaMoPPCorrespondence() {
    }

    static boolean corresponds(final MethodDeclaration methodDeclaration, final Member member,
            final boolean compareOnlyName) {
        if (!(member instanceof Parametrizable)) {
            return false;
        }
        final boolean sameName = methodDeclaration.getName().getFullyQualifiedName().equals(member.getName());
        final boolean sameMethodParameters = sameMethodParameters(methodDeclaration, (Parametrizable) member);
        return sameName && (compareOnlyName || sameMethodParameters);
    }

    public static boolean corresponds(final FieldDeclaration fieldDeclaration, final Field field,
            final boolean compareOnlyName) {
        final boolean sameName = varOfSameNameExists(fieldDeclaration, field);
        final String fieldType = typeToString(field, field.getArrayDimension());
        final boolean sameTypes = fieldDeclaration.getType().toString().equals(fieldType); // sameMethodParameters(methodDeclaration,
        // method);
        return sameName && (compareOnlyName || sameTypes);
    }

    public static boolean correspondsByName(final VariableDeclarationFragment fieldFragment, final Field field) {
        return field.getName().equals(fieldFragment.getName().toString());
    }

    private static String typeToString(final TypedElement typedElement, final long arrayDimension) {
        String arrayPostfix = "";
        for (long i = 0; i < arrayDimension; ++i) {
            arrayPostfix += "[]";
        }
        final TypeReference typeReference = typedElement.getTypeReference();
        return classifierReferenceToString(typeReference) + arrayPostfix;
    }

    private static boolean varOfSameNameExists(final FieldDeclaration fieldDeclaration, final Field field) {
        for (final Object variable : fieldDeclaration.fragments()) {
            if (correspondsByName((VariableDeclarationFragment) variable, field)) {
                return true;
            }
        }
        return false;
    }

    private static boolean sameMethodParameters(final MethodDeclaration methodDeclaration,
            final Parametrizable member) {
        if (methodDeclaration.parameters().size() != member.getParameters().size()) {
            return false;
        }

        final int size = member.getParameters().size();
        final String[] expectedParameterTypes = new String[size];
        int i = 0;
        for (final Parameter param : member.getParameters()) {
            final String typeName = typeToString(param, param.getArrayDimension());
            expectedParameterTypes[i++] = typeName;
        }
        i = 0;
        for (final Object parameter : methodDeclaration.parameters()) {
            final SingleVariableDeclaration var = (SingleVariableDeclaration) parameter;
            final String parameterTypeName = expectedParameterTypes[i++];
            if (!parameterTypeName.equals(var.getType().toString())) {
                return false;
            }
        }
        return true;
    }

    private static String classifierReferenceToString(final TypeReference typeReference) {
        String prefix = "";
        final ClassifierReference classifierReference = typeReference.getPureClassifierReference();
        if (classifierReference == null) {
            final Type type = typeReference.getTarget();
            if (!(type instanceof PrimitiveType)) {
                throw new IllegalStateException("This should have been a primtive type.");
            }
            return primitiveTypeStrings.get(type.getClass());
        }

        if (typeReference instanceof NamespaceClassifierReference) {
            prefix = namespacesToString((NamespaceClassifierReference) typeReference);
        }
        final StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        final Classifier target = classifierReference.getTarget();
        sb.append(target.getName());
        if (classifierReference.getTypeArguments().size() != 0) {
            sb.append('<');
            for (final TypeArgument typeArg : classifierReference.getTypeArguments()) {
                sb.append(classifierReferenceToString(
                        ((QualifiedTypeArgument) typeArg).getTypeReference().getPureClassifierReference()));
                sb.append(',');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('>');
        }
        return sb.toString();
    }

    private static String namespacesToString(final NamespaceClassifierReference classifierReference) {
        final StringBuilder sb = new StringBuilder();
        for (final String segment : classifierReference.getNamespaces()) {
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
    public static boolean corresponds(final TypeDeclaration typeDeclaration, final ConcreteClassifier classifier) {
        if (classifier.getName().equals(typeDeclaration.getName().getIdentifier())) {
            if (classifier instanceof Interface && typeDeclaration.isInterface()) {
                return true;
            }
            if (classifier instanceof org.emftext.language.java.classifiers.Class && !typeDeclaration.isInterface()) {
                return true;
            }
        }
        return false;
    }

    // FIXME DM: only checks simple names not fully qualified
    public static boolean corresponds(final ClassInstanceCreation classInstanceCreation,
            final NewConstructorCallImpl newConstructorCall) {
        final String astTypeName = ((SimpleType) classInstanceCreation.getType()).getName().getFullyQualifiedName();
        final String jamoppTypeName = newConstructorCall.getTypeReference().getPureClassifierReference().getTarget()
                .getName();
        final boolean sameName = astTypeName.equals(jamoppTypeName);
        // cannot check method correspondence for add/removeMethodEvents...
        // boolean sameMethods = true;

        return sameName;
    }

    public static boolean corresponds(final ImportDeclaration importDeclaration, final Import imprt) {
        String qualifiedName = imprt.getNamespacesAsString().replace("$", "");
        final EStructuralFeature classifierReference = imprt.eClass().getEStructuralFeature("classifier");
        final Classifier importedClassifier = (Classifier) imprt.eGet(classifierReference);
        final String className = importedClassifier.getName();
        qualifiedName += !qualifiedName.endsWith(".") ? "." : "";
        qualifiedName += className;
        final String importDeclarationQualifiedName = importDeclaration.getName().getFullyQualifiedName();
        return qualifiedName.equals(importDeclarationQualifiedName);
    }
}
