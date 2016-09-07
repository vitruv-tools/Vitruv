package tools.vitruvius.domains.java.monitorededitor.jamopputil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.emftext.language.java.annotations.AnnotationInstance;
import org.emftext.language.java.classifiers.AnonymousClass;
import org.emftext.language.java.classifiers.Class;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.instantiations.impl.NewConstructorCallImpl;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.modifiers.AnnotableAndModifiable;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.parameters.Parametrizable;
import org.emftext.language.java.types.ClassifierReference;
import org.emftext.language.java.types.NamespaceClassifierReference;
import org.emftext.language.java.types.PrimitiveType;
import org.emftext.language.java.types.TypeReference;

/**
 * @author messinger
 *
 *         Bridge from Eclipse ASTNode to JaMoPP Model. More precise name:
 *         AST2JaMoPP-CompilationUnit-Adapter
 *
 */
public class CompilationUnitAdapter {

    private static final Logger logger = Logger.getLogger(CompilationUnitAdapter.class.getSimpleName());

    private CompilationUnit compilationUnit;

    public CompilationUnitAdapter(final CompilationUnit unit) {
        this.compilationUnit = unit;
    }

    public CompilationUnitAdapter(final ASTNode astNode, final URI uri, final boolean isSaved) {
        final ASTNode root = astNode.getRoot();
        if (!(root instanceof org.eclipse.jdt.core.dom.CompilationUnit)) {
            throw new IllegalStateException("astNode argument has to be in the subtree of a CompilationUnit.");
        }
        final org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit = (org.eclipse.jdt.core.dom.CompilationUnit) root;
        if (isSaved) {
            try {
                this.compilationUnit = AST2JaMoPP.getCompilationUnitForSerializedCompilationUnit(astCompilationUnit);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.compilationUnit = AST2JaMoPP.getCompilationUnitForInMemoryCompilationUnit(astCompilationUnit, uri);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    public CompilationUnitAdapter(final IPath compilationUnitPath) throws IOException {
        this.compilationUnit = AST2JaMoPP.getCompilationUnitFromAbsolutePath(compilationUnitPath);
    }

    public Parametrizable getMethodOrConstructorForMethodDeclaration(final MethodDeclaration methodDeclaration) {
        final ASTNode parent = methodDeclaration.getParent();
        final int nodeType = parent.getNodeType();
        if (nodeType != ASTNode.ANONYMOUS_CLASS_DECLARATION && nodeType != ASTNode.TYPE_DECLARATION) {
            return null;
        }
        if (nodeType == ASTNode.ANONYMOUS_CLASS_DECLARATION) {
            final List<AnonymousClass> anonymousClasses = this
                    .getAnonymousClassesForAnonymousClassDeclaration((AnonymousClassDeclaration) parent);
            for (final AnonymousClass anonymousClass : anonymousClasses) {
                for (final Member member : anonymousClass.getMethods()) {
                    if (member instanceof Parametrizable
                            && AST2JaMoPPCorrespondence.corresponds(methodDeclaration, member, false)) {
                        return (Parametrizable) member;
                    }
                }
            }
            return null;
        }

        final ConcreteClassifier classifier = this.getConcreteClassifierForTypeDeclaration((TypeDeclaration) parent);
        for (final Member member : classifier.getMembers()) {
            if (member instanceof Parametrizable
                    && AST2JaMoPPCorrespondence.corresponds(methodDeclaration, member, false)) {
                return (Parametrizable) member;
            }
        }
        return null;
    }

    public List<Field> getFieldsForFieldDeclaration(final FieldDeclaration fieldDeclaration) {
        final ASTNode parent = fieldDeclaration.getParent();
        final int nodeType = parent.getNodeType();
        if (nodeType != ASTNode.ANONYMOUS_CLASS_DECLARATION && nodeType != ASTNode.TYPE_DECLARATION) {
            return null;
        }
        if (nodeType == ASTNode.ANONYMOUS_CLASS_DECLARATION) {
            // TODO Anonymous class support
            return null;
        }

        final List<Field> fields = new ArrayList<Field>(2);

        final ConcreteClassifier classifier = this.getConcreteClassifierForTypeDeclaration((TypeDeclaration) parent);
        for (final Field field : classifier.getFields()) {
            if (AST2JaMoPPCorrespondence.corresponds(fieldDeclaration, field, false)) {
                fields.add(field);
            }
        }
        return fields;
    }

    public Field getFieldForVariableDeclarationFragment(final VariableDeclarationFragment fieldFragment) {
        final List<Field> fields = this.getFieldsForFieldDeclaration((FieldDeclaration) fieldFragment.getParent());

        for (final Field field : fields) {
            if (AST2JaMoPPCorrespondence.correspondsByName(fieldFragment, field)) {
                return field;
            }
        }
        return null;
    }

    // FIXME DM: what if there are several anonymous classes of the same type?
    private List<AnonymousClass> getAnonymousClassesForAnonymousClassDeclaration(
            final AnonymousClassDeclaration anonymousClassDeclaration) {
        final MethodDeclaration parentMethodDeclaration = this
                .getNextParentMethodDeclaration(anonymousClassDeclaration);
        final ClassInstanceCreation classInstanceCreation = (ClassInstanceCreation) anonymousClassDeclaration
                .getParent();
        final Parametrizable parentMethod = this.getMethodOrConstructorForMethodDeclaration(parentMethodDeclaration);
        final List<NewConstructorCallImpl> newConstructorCalls = parentMethod
                .getChildrenByType(NewConstructorCallImpl.class);
        final List<AnonymousClass> anonymousClasses = new LinkedList<AnonymousClass>();
        for (final NewConstructorCallImpl newConstructorCall : newConstructorCalls) {
            if (AST2JaMoPPCorrespondence.corresponds(classInstanceCreation, newConstructorCall)) {
                anonymousClasses.add(newConstructorCall.getAnonymousClass());
            }
        }
        return anonymousClasses;
    }

    private MethodDeclaration getNextParentMethodDeclaration(final ASTNode astNode) {
        if (astNode == null) {
            return null;
        }
        ASTNode parent = astNode.getParent();
        while (parent != null && parent.getNodeType() != ASTNode.METHOD_DECLARATION) {
            parent = parent.getParent();
        }
        return (MethodDeclaration) parent;
    }

    public Parameter getParameterForVariableDeclaration(final VariableDeclaration parameterDeclaration) {
        final Parametrizable method = this
                .getMethodOrConstructorForMethodDeclaration((MethodDeclaration) parameterDeclaration.getParent());
        for (final Parameter parameter : method.getParameters()) {
            if (parameter.getName().equals(parameterDeclaration.getName().getIdentifier())) {
                return parameter;
            }
        }
        return null;
    }

    // TODO test if it works with anonymous and inner classes
    public ConcreteClassifier getConcreteClassifierForTypeDeclaration(final TypeDeclaration typeDeclaration) {
        final EList<ConcreteClassifier> classifiers = this.compilationUnit.getClassifiers();
        return this.getConcreteClassifierForTypeDeclaration(typeDeclaration, classifiers);
    }

    private ConcreteClassifier getConcreteClassifierForTypeDeclaration(final TypeDeclaration typeDeclaration,
            final EList<ConcreteClassifier> classifiers) {
        for (final ConcreteClassifier classifier : classifiers) {
            if (AST2JaMoPPCorrespondence.corresponds(typeDeclaration, classifier)) {
                return classifier;
            } else {
                final ConcreteClassifier innerClassifier = this.getConcreteClassifierForTypeDeclaration(typeDeclaration,
                        this.getInnerClassifiers(classifier));
                if (innerClassifier != null) {
                    return innerClassifier;
                }
            }
        }
        return null;
    }

    // ConcreteClassifier.getAllInnerClassifiers() only returns Classifier with members set to null
    // -> Bug in JaMoPP?
    private EList<ConcreteClassifier> getInnerClassifiers(final ConcreteClassifier classifier) {
        final EList<ConcreteClassifier> innerClassifiers = new BasicEList<ConcreteClassifier>(3);
        for (final Member member : classifier.getMembers()) {
            if (member instanceof ConcreteClassifier) {
                innerClassifiers.add((ConcreteClassifier) member);
            }
        }
        return innerClassifiers;
    }

    public CompilationUnit getCompilationUnit() {
        return this.compilationUnit;
    }

    public Import getImportForImportDeclaration(final ImportDeclaration importDeclaration) {
        for (final Import imp : this.compilationUnit.getImports()) {
            if (AST2JaMoPPCorrespondence.corresponds(importDeclaration, imp)) {
                return imp;
            }
        }
        return null;
    }

    public TypeReference getImplementsForSuperType(final SimpleType simpleType) {
        if (null == simpleType.getName() || !simpleType.getName().isSimpleName()) {
            logger.warn("simpleType.getName is null or not a simple name: " + simpleType.getName());
            return null;
        }
        final SimpleName simpleName = (SimpleName) simpleType.getName();
        final String implementsName = simpleName.getIdentifier();
        for (final ConcreteClassifier classifier : this.compilationUnit.getClassifiers()) {
            if (classifier instanceof org.emftext.language.java.classifiers.Class) {
                final Class jaMoPPClass = (Class) classifier;
                for (final TypeReference typeReference : jaMoPPClass.getImplements()) {
                    final String typeRefName = this.getNameFromTypeReference(typeReference);
                    if (typeRefName.equals(implementsName)) {
                        return typeReference;
                    }
                }
            }
        }
        logger.info("no matching type reference found for implements name " + implementsName);
        return null;
    }

    private String getNameFromTypeReference(final TypeReference typeReference) {
        if (typeReference instanceof ClassifierReference) {
            final ClassifierReference cr = (ClassifierReference) typeReference;
            return cr.getTarget().getName();
        }
        if (typeReference instanceof NamespaceClassifierReference) {
            final NamespaceClassifierReference ncr = (NamespaceClassifierReference) typeReference;
            if (!ncr.getClassifierReferences().isEmpty()) {
                return this.getNameFromTypeReference(ncr.getClassifierReferences().get(0));
            }
            logger.info("no ClassifierReference found for NamespaceClassifierReference");
            return "";
        }
        if (typeReference instanceof PrimitiveType) {
            logger.warn("Type Reference should not be a primitive reference");
            return "";
        }
        return "";
    }

    public AnnotationInstance getAnnotationInstanceForMethodAnnotation(final Annotation annotation,
            final BodyDeclaration bodyDeclaration) {
        final AnnotableAndModifiable annotableAndModifiable = this
                .getAnnotableAndModifiableForBodyDeclaration(bodyDeclaration);
        final Name typeName = annotation.getTypeName();
        final String astName = typeName.toString();
        for (final AnnotationInstance annotationInstance : annotableAndModifiable.getAnnotationInstances()) {
            final String jaMoPPAnnotationName = annotationInstance.getAnnotation().getName();
            if (astName.contains(jaMoPPAnnotationName)) {
                return annotationInstance;
            }
        }
        return null;
    }

    public AnnotableAndModifiable getAnnotableAndModifiableForBodyDeclaration(final BodyDeclaration bodyDeclaration) {
        if (bodyDeclaration instanceof FieldDeclaration) {
            final List<Field> fields = this.getFieldsForFieldDeclaration((FieldDeclaration) bodyDeclaration);
            if (fields == null || fields.size() == 0) {
                return null;
            }
            return fields.get(0);
        }
        if (bodyDeclaration instanceof MethodDeclaration) {
            return (AnnotableAndModifiable) this
                    .getMethodOrConstructorForMethodDeclaration((MethodDeclaration) bodyDeclaration);
        }
        if (bodyDeclaration instanceof TypeDeclaration) {
            return this.getConcreteClassifierForTypeDeclaration((TypeDeclaration) bodyDeclaration);
        }
        return null;
    }
}
