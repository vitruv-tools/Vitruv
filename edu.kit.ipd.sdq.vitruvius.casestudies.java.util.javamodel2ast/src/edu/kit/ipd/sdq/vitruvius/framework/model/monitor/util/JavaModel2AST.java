package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * @author messinger
 * 
 *         Utility class to retrieve {@link ASTNode}s that correspond to given {@link IJavaElement}
 *         s. The purpose is to bridge the gap between JDT Java Model and Java AST. ASTs for
 *         CompilationUnits are built if necessary and AST-JavaModel correspondence on node/element
 *         level is declared in {@link JavaModel2ASTCorrespondence}.
 * 
 */
public final class JavaModel2AST {

    private static ASTParser parser;

    static {
        parser = ASTParser.newParser(AST.JLS4);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
    }

    private JavaModel2AST() {
    }

    // in one CompilationUnit type names are unique
    // has to support anonymous types!
    public static TypeDeclaration getTypeDeclaration(IType itype, CompilationUnit astUnit) {
        try {
            if (itype.exists() && itype.isAnonymous()) {
                return null;
            }
        } catch (JavaModelException e1) {
            e1.printStackTrace();
        }
        TypeDeclaration[] typeDeclarations = filterTypeDeclaration(astUnit.types());
        Deque<IType> typeHierarchy = getTypeHierarchy(itype);
        TypeDeclaration levelASTType = null;
        while (!typeHierarchy.isEmpty()) {
            IType levelIType = typeHierarchy.pollFirst();
            try {

                if (typeDeclarations.length == 0) {
                    return null;
                }
                levelASTType = matchITypeWithTypeDeclarations(levelIType, typeDeclarations);
            } catch (JavaModelException e) {
                e.printStackTrace();
                return null;
            }
            // FIXME AST type does not link anonymous type as child -> ASTNodeFinder search type
            // "ClassInstanceCreation"
            typeDeclarations = levelASTType.getTypes();
        }
        return levelASTType;
    }

    public static TypeDeclaration getAnonymousTypeDeclaration(IType anonymousIType, TypeDeclaration oneLevelUpASTType)
            throws JavaModelException {
        IMethod parentMethod = (IMethod) anonymousIType.getParent();
        for (MethodDeclaration astMethod : oneLevelUpASTType.getMethods()) {
            if (JavaModel2ASTCorrespondence.corresponds(parentMethod, astMethod, false)) {
                ASTNodeByTypeFinder finder = new ASTNodeByTypeFinder(AnonymousClassDeclaration.class);
                astMethod.accept(finder);
                List<ASTNode> types = finder.getFoundASTNodes();
                for (ASTNode anonymousClassDeclaration : types) {
                    if (JavaModel2ASTCorrespondence.corresponds(anonymousIType,
                            (AnonymousClassDeclaration) anonymousClassDeclaration))
                        return (TypeDeclaration) anonymousClassDeclaration;
                }
            }
        }
        return null;
    }

    // ICompilationUnit not parsed yet
    public static TypeDeclaration getTypeDeclaration(IType itype, ICompilationUnit unit) {
        CompilationUnit astUnit = parseCompilationUnit(unit);
        return getTypeDeclaration(itype, astUnit);
    }

    private static TypeDeclaration[] filterTypeDeclaration(List<Object> types) {
        List<Object> typeDeclarations = Util.filterBySupertype(types, TypeDeclaration.class);
        return typeDeclarations.toArray(new TypeDeclaration[typeDeclarations.size()]);
    }

    // private static TypeDeclaration findTypeDeclarationForITypeHierarchy(
    // Deque<IType> typeHierarchy, TypeDeclaration typeDeclaration)
    // throws JavaModelException {
    // IType levelIType = typeHierarchy.pollFirst();
    // if (!JavaModel2ASTCorrespondence.corresponds(levelIType,
    // typeDeclaration))
    // return null;
    // TypeDeclaration levelMatch = typeDeclaration;
    // while (!typeHierarchy.isEmpty()) {
    // levelIType = typeHierarchy.pollFirst();
    // TypeDeclaration[] innerTypeDeclarations = levelMatch.getTypes();
    // if (innerTypeDeclarations.length == 0)
    // return null;
    // levelMatch = matchITypeWithTypeDeclarations(levelIType,
    // innerTypeDeclarations);
    // if (levelMatch == null)
    // return null;
    // }
    // return typeDeclaration;
    // }

    // has to support anonymous types!
    private static TypeDeclaration matchITypeWithTypeDeclarations(IType itype, TypeDeclaration[] typeDeclarations)
            throws JavaModelException {
        for (TypeDeclaration typeDeclaration : typeDeclarations) {
            if (JavaModel2ASTCorrespondence.corresponds(itype, typeDeclaration))
                return typeDeclaration;
        }
        return null;
    }

    private static Deque<IType> getTypeHierarchy(IType itype) {
        Deque<IType> typeHierarchy = new LinkedList<IType>();
        IType level = itype;
        do {
            typeHierarchy.addFirst(level);
            level = level.getDeclaringType();
        } while (level != null);
        return typeHierarchy;
    }

    public static MethodDeclaration getMethodDeclaration(IMethod imethod, CompilationUnit unit) {
        IType itype = imethod.getDeclaringType();
        try {
            if (itype.exists() && itype.isAnonymous()) {
                return getMethodDeclarationInAnonymousClass(imethod, unit, itype);
            }
        } catch (JavaModelException e) {
            e.printStackTrace();
        }

        TypeDeclaration type = getTypeDeclaration(itype, unit);
        for (MethodDeclaration method : type.getMethods()) {
            if (JavaModel2ASTCorrespondence.corresponds(imethod, method, false))
                return method;
        }
        return null;
    }

    private static MethodDeclaration getMethodDeclarationInAnonymousClass(IMethod imethod, CompilationUnit unit,
            IType itype) {
        String anonymousClassToString = itype.toString();
        int indexOfAnonymousClass = Integer.parseInt(anonymousClassToString.substring(
                anonymousClassToString.indexOf('#') + 1, anonymousClassToString.indexOf('>'))) - 1;
        IMethod parentIMethod = (IMethod) itype.getParent();
        MethodDeclaration parentMethodDeclaration = getMethodDeclaration(parentIMethod, unit);
        ASTNodeByTypeFinder anonFinder = new ASTNodeByTypeFinder(AnonymousClassDeclaration.class);
        parentMethodDeclaration.accept(anonFinder);
        AnonymousClassDeclaration astAnonymousClass = (AnonymousClassDeclaration) anonFinder.getFoundASTNodes().get(
                indexOfAnonymousClass);
        ASTNodeByTypeFinder methodFinder = new ASTNodeByTypeFinder(MethodDeclaration.class);
        for (Object method : Util.filterBySupertype(astAnonymousClass.bodyDeclarations(), MethodDeclaration.class)) {
            MethodDeclaration methodDeclaration = (MethodDeclaration) method;
            if (JavaModel2ASTCorrespondence.corresponds(imethod, methodDeclaration, false))
                return methodDeclaration;
        }
        return null;
    }

    public static MethodDeclaration getMethodDeclaration(IMethod imethod) {
        CompilationUnit astUnit = parseCompilationUnit(imethod.getCompilationUnit());
        return getMethodDeclaration(imethod, astUnit);
    }

    // does NOT ignore comments, but AST from ElementChangedEvent does!!
    public static CompilationUnit parseCompilationUnit(ICompilationUnit unit) {
        parser.setSource(unit);

        ASTNode ast = parser.createAST(null);
        CompilationUnit astUnit = (CompilationUnit) ast;
        return astUnit;
    }

    // does NOT ignore comments, but AST from ElementChangedEvent does!!
    public static CompilationUnit parseCompilationUnit(char[] source) {
        parser.setSource(source);

        ASTNode ast = parser.createAST(null);
        CompilationUnit astUnit = (CompilationUnit) ast;
        return astUnit;
    }

    public static ImportDeclaration getImportDeclaration(IImportDeclaration iimportDeclaration,
            CompilationUnit compilationUnit) {

        for (Object importDeclaration : compilationUnit.imports()) {
            if (JavaModel2ASTCorrespondence.corresponds(iimportDeclaration, (ImportDeclaration) importDeclaration))
                return (ImportDeclaration) importDeclaration;
        }
        return null;
    }

    public static ImportDeclaration getImportDeclaration(String importName, CompilationUnit compilationUnit) {
        for (Object importDec : compilationUnit.imports()) {
            ImportDeclaration importDeclaration = (ImportDeclaration) importDec;
            String name = importDeclaration.getName().toString();
            if (importName.equals(name))
                return importDeclaration;
        }
        return null;
    }

    public static FieldDeclaration getFieldDeclarationByName(IField ifield) {
        CompilationUnit astUnit = parseCompilationUnit(ifield.getCompilationUnit());
        return getFieldDeclarationByName(ifield, astUnit);
    }

    public static FieldDeclaration getFieldDeclarationByName(IField ifield, CompilationUnit compilationUnit) {
        IType itype = ifield.getDeclaringType();
        TypeDeclaration type = getTypeDeclaration(itype, compilationUnit);

        for (FieldDeclaration field : type.getFields()) {
            if (JavaModel2ASTCorrespondence.weaklyCorresponds(ifield, field))
                return field;
        }
        return null;
    }

    public static VariableDeclarationFragment getVariableDeclarationFragmentByName(IField ifield) {
        CompilationUnit astUnit = parseCompilationUnit(ifield.getCompilationUnit());
        return getVariableDeclarationFragmentByName(ifield, astUnit);
    }

    public static VariableDeclarationFragment getVariableDeclarationFragmentByName(IField ifield,
            CompilationUnit compilationUnit) {
        FieldDeclaration fieldDeclaration = getFieldDeclarationByName(ifield, compilationUnit);
        @SuppressWarnings("unchecked")
        Iterator<VariableDeclarationFragment> fragmentIterator = fieldDeclaration.fragments().iterator();
        while (fragmentIterator.hasNext()) {
            VariableDeclarationFragment fragment = fragmentIterator.next();
            if (JavaModel2ASTCorrespondence.correspondsByName(ifield, fragment))
                return fragment;
        }
        return null;
    }

    public static FieldDeclaration getFieldDeclarationByFullSignature(IField ifield, CompilationUnit compilationUnit) {
        IType itype = ifield.getDeclaringType();
        TypeDeclaration type = getTypeDeclaration(itype, compilationUnit);

        for (FieldDeclaration field : type.getFields()) {
            try {
                if (JavaModel2ASTCorrespondence.strictlyCorresponds(ifield, field))
                    return field;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (JavaModelException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Annotation getAnnotation(IAnnotation iannotation, MethodDeclaration methodDeclaration) {
        for (Object modifier : methodDeclaration.modifiers()) {
            if (modifier instanceof Annotation) {
                if (JavaModel2ASTCorrespondence.corresponds(iannotation, (Annotation) modifier)) {
                    return (Annotation) modifier;
                }
            }
        }
        return null;
    }
}
