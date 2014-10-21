package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.emftext.language.java.classifiers.AnonymousClass;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.imports.Import;
import org.emftext.language.java.instantiations.impl.NewConstructorCallImpl;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;

/**
 * @author messinger
 * 
 *         Bridge from Eclipse ASTNode to JaMoPP Model. More precise name:
 *         AST2JaMoPP-CompilationUnit-Adapter
 * 
 */
public class CompilationUnitAdapter {

    private CompilationUnit compilationUnit;

    public CompilationUnitAdapter(CompilationUnit unit) {
        this.compilationUnit = unit;
    }

    public CompilationUnitAdapter(ASTNode astNode, URI uri, boolean isSaved) {
        ASTNode root = astNode.getRoot();
        if (!(root instanceof org.eclipse.jdt.core.dom.CompilationUnit))
            throw new IllegalStateException("astNode argument has to be in the subtree of a CompilationUnit.");
        org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit = (org.eclipse.jdt.core.dom.CompilationUnit) root;
        if (isSaved) {
            try {
                this.compilationUnit = AST2JaMoPP.getCompilationUnitForSerializedCompilationUnit(astCompilationUnit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.compilationUnit = AST2JaMoPP.getCompilationUnitForInMemoryCompilationUnit(astCompilationUnit, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public CompilationUnitAdapter(IPath compilationUnitPath) throws IOException {
        this.compilationUnit = AST2JaMoPP.getCompilationUnitFromAbsolutePath(compilationUnitPath);
    }

    public Method getMethodForMethodDeclaration(MethodDeclaration methodDeclaration) {
        ASTNode parent = methodDeclaration.getParent();
        int nodeType = parent.getNodeType();
        if (nodeType != ASTNode.ANONYMOUS_CLASS_DECLARATION && nodeType != ASTNode.TYPE_DECLARATION)
            return null;
        if (nodeType == ASTNode.ANONYMOUS_CLASS_DECLARATION) {
            List<AnonymousClass> anonymousClasses = getAnonymousClassesForAnonymousClassDeclaration((AnonymousClassDeclaration) parent);
            for (AnonymousClass anonymousClass : anonymousClasses) {
                for (Method method : anonymousClass.getMethods()) {
                    if (AST2JaMoPPCorrespondence.corresponds(methodDeclaration, method, false))
                        return method;
                }
            }
            return null;
        }

        ConcreteClassifier classifier = getConcreteClassifierForTypeDeclaration((TypeDeclaration) parent);
        for (Method method : classifier.getMethods()) {
            if (AST2JaMoPPCorrespondence.corresponds(methodDeclaration, method, false)) {
                return method;
            }
        }
        return null;
    }

    public List<Field> getFieldsForFieldDeclaration(FieldDeclaration fieldDeclaration) {
        ASTNode parent = fieldDeclaration.getParent();
        int nodeType = parent.getNodeType();
        if (nodeType != ASTNode.ANONYMOUS_CLASS_DECLARATION && nodeType != ASTNode.TYPE_DECLARATION)
            return null;
        if (nodeType == ASTNode.ANONYMOUS_CLASS_DECLARATION) {
            // TODO Anonymous class support
            return null;
        }

        List<Field> fields = new ArrayList<Field>(2);

        ConcreteClassifier classifier = getConcreteClassifierForTypeDeclaration((TypeDeclaration) parent);
        for (Field field : classifier.getFields()) {
            if (AST2JaMoPPCorrespondence.corresponds(fieldDeclaration, field, false)) {
                fields.add(field);
            }
        }
        return fields;
    }

    public Field getFieldForVariableDeclarationFragment(VariableDeclarationFragment fieldFragment) {
        List<Field> fields = getFieldsForFieldDeclaration((FieldDeclaration) fieldFragment.getParent());

        for (Field field : fields) {
            if (AST2JaMoPPCorrespondence.correspondsByName(fieldFragment, field))
                return field;
        }
        return null;
    }

    // FIXME what if there are several anonymous classes of the same type?
    private List<AnonymousClass> getAnonymousClassesForAnonymousClassDeclaration(
            AnonymousClassDeclaration anonymousClassDeclaration) {
        MethodDeclaration parentMethodDeclaration = getNextParentMethodDeclaration(anonymousClassDeclaration);
        ClassInstanceCreation classInstanceCreation = (ClassInstanceCreation) anonymousClassDeclaration.getParent();
        Method parentMethod = getMethodForMethodDeclaration(parentMethodDeclaration);
        List<NewConstructorCallImpl> newConstructorCalls = parentMethod.getChildrenByType(NewConstructorCallImpl.class);
        List<AnonymousClass> anonymousClasses = new LinkedList<AnonymousClass>();
        for (NewConstructorCallImpl newConstructorCall : newConstructorCalls) {
            if (AST2JaMoPPCorrespondence.corresponds(classInstanceCreation, newConstructorCall))
                anonymousClasses.add(newConstructorCall.getAnonymousClass());
        }
        return anonymousClasses;
    }

    private MethodDeclaration getNextParentMethodDeclaration(ASTNode astNode) {
        if (astNode == null)
            return null;
        ASTNode parent = astNode.getParent();
        while (parent != null && parent.getNodeType() != ASTNode.METHOD_DECLARATION) {
            parent = parent.getParent();
        }
        return (MethodDeclaration) parent;
    }

    // TODO test if it works with anonymous and inner classes
    public ConcreteClassifier getConcreteClassifierForTypeDeclaration(TypeDeclaration typeDeclaration) {
        EList<ConcreteClassifier> classifiers = this.compilationUnit.getClassifiers();
        return getConcreteClassifierForTypeDeclaration(typeDeclaration, classifiers);
    }

    private ConcreteClassifier getConcreteClassifierForTypeDeclaration(TypeDeclaration typeDeclaration,
            EList<ConcreteClassifier> classifiers) {
        for (ConcreteClassifier classifier : classifiers) {
            if (AST2JaMoPPCorrespondence.corresponds(typeDeclaration, classifier))
                return classifier;
            else {
                ConcreteClassifier innerClassifier = getConcreteClassifierForTypeDeclaration(typeDeclaration,
                        getInnerClassifiers(classifier));
                if (innerClassifier != null)
                    return innerClassifier;
            }
        }
        return null;
    }

    // ConcreteClassifier.getAllInnerClassifiers() only returns Classifier with members set to null
    // -> Bug in JaMoPP?
    private EList<ConcreteClassifier> getInnerClassifiers(ConcreteClassifier classifier) {
        EList<ConcreteClassifier> innerClassifiers = new BasicEList<ConcreteClassifier>(3);
        for (Member member : classifier.getMembers()) {
            if (member instanceof ConcreteClassifier)
                innerClassifiers.add((ConcreteClassifier) member);
        }
        return innerClassifiers;
    }

    public CompilationUnit getCompilationUnit() {
        return this.compilationUnit;
    }

    public Import getImportForImportDeclaration(ImportDeclaration importDeclaration) {
        for (Import imp : this.compilationUnit.getImports()) {
            if (AST2JaMoPPCorrespondence.corresponds(importDeclaration, imp)) {
                return imp;
            }
        }
        return null;
    }
}
