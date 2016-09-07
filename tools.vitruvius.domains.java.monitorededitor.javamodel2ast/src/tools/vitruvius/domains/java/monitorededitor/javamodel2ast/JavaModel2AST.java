package tools.vitruvius.domains.java.monitorededitor.javamodel2ast;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.core.IAnnotatable;
import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IImportDeclaration;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.ILocalVariable;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
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
@SuppressWarnings("deprecation")
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
    @SuppressWarnings("unchecked")
	public static TypeDeclaration getTypeDeclaration(final IType itype, final CompilationUnit astUnit) {
        try {
            if (itype.exists() && itype.isAnonymous()) {
                return null;
            }
        } catch (final JavaModelException e1) {
            e1.printStackTrace();
        }
        TypeDeclaration[] typeDeclarations = filterTypeDeclaration(astUnit.types());
        final Deque<IType> typeHierarchy = getTypeHierarchy(itype);
        TypeDeclaration levelASTType = null;
        while (!typeHierarchy.isEmpty()) {
            final IType levelIType = typeHierarchy.pollFirst();
            try {

                if (typeDeclarations.length == 0) {
                    return null;
                }
                levelASTType = matchITypeWithTypeDeclarations(levelIType, typeDeclarations);
            } catch (final JavaModelException e) {
                e.printStackTrace();
                return null;
            }
            // FIXME DM: AST type does not link anonymous type as child -> ASTNodeFinder search type
            // "ClassInstanceCreation"
            typeDeclarations = levelASTType.getTypes();
        }
        return levelASTType;
    }

    public static TypeDeclaration getAnonymousTypeDeclaration(final IType anonymousIType,
            final TypeDeclaration oneLevelUpASTType) throws JavaModelException {
        final IMethod parentMethod = (IMethod) anonymousIType.getParent();
        for (final MethodDeclaration astMethod : oneLevelUpASTType.getMethods()) {
            if (JavaModel2ASTCorrespondence.corresponds(parentMethod, astMethod, false)) {
                final ASTNodeByTypeFinder finder = new ASTNodeByTypeFinder(AnonymousClassDeclaration.class);
                astMethod.accept(finder);
                final List<ASTNode> types = finder.getFoundASTNodes();
                for (final ASTNode anonymousClassDeclaration : types) {
                    if (JavaModel2ASTCorrespondence.corresponds(anonymousIType,
                            (AnonymousClassDeclaration) anonymousClassDeclaration)) {
                        return (TypeDeclaration) anonymousClassDeclaration;
                    }
                }
            }
        }
        return null;
    }

    // ICompilationUnit not parsed yet
    public static TypeDeclaration getTypeDeclaration(final IType itype, final ICompilationUnit unit) {
        final CompilationUnit astUnit = parseCompilationUnit(unit);
        return getTypeDeclaration(itype, astUnit);
    }

    private static TypeDeclaration[] filterTypeDeclaration(final List<Object> types) {
        final List<Object> typeDeclarations = Util.filterBySupertype(types, TypeDeclaration.class);
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
    private static TypeDeclaration matchITypeWithTypeDeclarations(final IType itype,
            final TypeDeclaration[] typeDeclarations) throws JavaModelException {
        for (final TypeDeclaration typeDeclaration : typeDeclarations) {
            if (JavaModel2ASTCorrespondence.corresponds(itype, typeDeclaration)) {
                return typeDeclaration;
            }
        }
        return null;
    }

    private static Deque<IType> getTypeHierarchy(final IType itype) {
        final Deque<IType> typeHierarchy = new LinkedList<IType>();
        IType level = itype;
        do {
            typeHierarchy.addFirst(level);
            level = level.getDeclaringType();
        } while (level != null);
        return typeHierarchy;
    }

    public static MethodDeclaration getMethodDeclaration(final IMethod imethod, final CompilationUnit unit) {
        final IType itype = imethod.getDeclaringType();
        try {
            if (itype.exists() && itype.isAnonymous()) {
                return getMethodDeclarationInAnonymousClass(imethod, unit, itype);
            }
        } catch (final JavaModelException e) {
            e.printStackTrace();
        }

        final TypeDeclaration type = getTypeDeclaration(itype, unit);
        for (final MethodDeclaration method : type.getMethods()) {
            if (JavaModel2ASTCorrespondence.corresponds(imethod, method, false)) {
                return method;
            }
        }
        return null;
    }

    public static BodyDeclaration getBodyDeclaration(final IAnnotatable iAnnotatable, final CompilationUnit unit) {
        if (iAnnotatable instanceof IField) {
            return getFieldDeclarationByName((IField) iAnnotatable, unit);
        }
        if (iAnnotatable instanceof IMethod) {
            return getMethodDeclaration((IMethod) iAnnotatable, unit);
        }
        if (iAnnotatable instanceof IType) {
            return getTypeDeclaration((IType) iAnnotatable, unit);
        }
        return null;
    }

    public static IBinding getIBindung(final BodyDeclaration bodyDeclaration) {
        if (bodyDeclaration instanceof MethodDeclaration) {
            return ((MethodDeclaration) bodyDeclaration).resolveBinding();
        }
        if (bodyDeclaration instanceof TypeDeclaration) {
            return ((TypeDeclaration) bodyDeclaration).resolveBinding();
        }
        if (bodyDeclaration instanceof FieldDeclaration) {
            // return ((FieldDeclaration) bodyDeclaration).;
        }
        return null;
    }

    @SuppressWarnings("unchecked")
	private static MethodDeclaration getMethodDeclarationInAnonymousClass(final IMethod imethod,
            final CompilationUnit unit, final IType itype) {
        final String anonymousClassToString = itype.toString();
        final int indexOfAnonymousClass = Integer.parseInt(anonymousClassToString
                .substring(anonymousClassToString.indexOf('#') + 1, anonymousClassToString.indexOf('>'))) - 1;
        final IMethod parentIMethod = (IMethod) itype.getParent();
        final MethodDeclaration parentMethodDeclaration = getMethodDeclaration(parentIMethod, unit);
        final ASTNodeByTypeFinder anonFinder = new ASTNodeByTypeFinder(AnonymousClassDeclaration.class);
        parentMethodDeclaration.accept(anonFinder);
        final AnonymousClassDeclaration astAnonymousClass = (AnonymousClassDeclaration) anonFinder.getFoundASTNodes()
                .get(indexOfAnonymousClass);
//        final ASTNodeByTypeFinder methodFinder = new ASTNodeByTypeFinder(MethodDeclaration.class);
        for (final Object method : Util.filterBySupertype(astAnonymousClass.bodyDeclarations(),
                MethodDeclaration.class)) {
            final MethodDeclaration methodDeclaration = (MethodDeclaration) method;
            if (JavaModel2ASTCorrespondence.corresponds(imethod, methodDeclaration, false)) {
                return methodDeclaration;
            }
        }
        return null;
    }

    public static MethodDeclaration getMethodDeclaration(final IMethod imethod) {
        final CompilationUnit astUnit = parseCompilationUnit(imethod.getCompilationUnit());
        return getMethodDeclaration(imethod, astUnit);
    }

    // does NOT ignore comments, but AST from ElementChangedEvent does!!
    public static CompilationUnit parseCompilationUnit(final ICompilationUnit unit) {
        parser.setSource(unit);

        final ASTNode ast = parser.createAST(null);
        final CompilationUnit astUnit = (CompilationUnit) ast;
        return astUnit;
    }

    // does NOT ignore comments, but AST from ElementChangedEvent does!!
    public static CompilationUnit parseCompilationUnit(final char[] source) {
        parser.setSource(source);

        final ASTNode ast = parser.createAST(null);
        final CompilationUnit astUnit = (CompilationUnit) ast;
        return astUnit;
    }

    public static ImportDeclaration getImportDeclaration(final IImportDeclaration iimportDeclaration,
            final CompilationUnit compilationUnit) {

        for (final Object importDeclaration : compilationUnit.imports()) {
            if (JavaModel2ASTCorrespondence.corresponds(iimportDeclaration, (ImportDeclaration) importDeclaration)) {
                return (ImportDeclaration) importDeclaration;
            }
        }
        return null;
    }

    public static ImportDeclaration getImportDeclaration(final String importName,
            final CompilationUnit compilationUnit) {
        for (final Object importDec : compilationUnit.imports()) {
            final ImportDeclaration importDeclaration = (ImportDeclaration) importDec;
            final String name = importDeclaration.getName().toString();
            if (importName.equals(name)) {
                return importDeclaration;
            }
        }
        return null;
    }

    public static FieldDeclaration getFieldDeclarationByName(final IField ifield) {
        final CompilationUnit astUnit = parseCompilationUnit(ifield.getCompilationUnit());
        return getFieldDeclarationByName(ifield, astUnit);
    }

    public static FieldDeclaration getFieldDeclarationByName(final IField ifield,
            final CompilationUnit compilationUnit) {
        final IType itype = ifield.getDeclaringType();
        final TypeDeclaration type = getTypeDeclaration(itype, compilationUnit);

        for (final FieldDeclaration field : type.getFields()) {
            if (JavaModel2ASTCorrespondence.weaklyCorresponds(ifield, field)) {
                return field;
            }
        }
        return null;
    }

    public static VariableDeclarationFragment getVariableDeclarationFragmentByName(final IField ifield) {
        final CompilationUnit astUnit = parseCompilationUnit(ifield.getCompilationUnit());
        return getVariableDeclarationFragmentByName(ifield, astUnit);
    }

    public static VariableDeclarationFragment getVariableDeclarationFragmentByName(final IField ifield,
            final CompilationUnit compilationUnit) {
        final FieldDeclaration fieldDeclaration = getFieldDeclarationByName(ifield, compilationUnit);
        @SuppressWarnings("unchecked")
        final Iterator<VariableDeclarationFragment> fragmentIterator = fieldDeclaration.fragments().iterator();
        while (fragmentIterator.hasNext()) {
            final VariableDeclarationFragment fragment = fragmentIterator.next();
            if (JavaModel2ASTCorrespondence.correspondsByName(ifield, fragment)) {
                return fragment;
            }
        }
        return null;
    }

    public static VariableDeclaration getParameterVariableDeclarationByName(final ILocalVariable locVar) {
        final CompilationUnit astUnit = parseCompilationUnit(((IMethod) locVar.getParent()).getCompilationUnit());
        return getParameterVariableDeclarationByName(locVar, astUnit);
    }

    public static VariableDeclaration getParameterVariableDeclarationByName(final ILocalVariable locVar,
            final CompilationUnit cu) {
        final IMethod correspondingMethod = (IMethod) locVar.getParent();
        final MethodDeclaration md = getMethodDeclaration(correspondingMethod, cu);
        for (final Object parameter : md.parameters()) {
            final VariableDeclaration vd = (VariableDeclaration) parameter;
            if (vd.getName().getIdentifier().equals(locVar.getElementName())) {
                return vd;
            }
        }
        return null;
    }

    public static FieldDeclaration getFieldDeclarationByFullSignature(final IField ifield,
            final CompilationUnit compilationUnit) {
        final IType itype = ifield.getDeclaringType();
        final TypeDeclaration type = getTypeDeclaration(itype, compilationUnit);

        for (final FieldDeclaration field : type.getFields()) {
            try {
                if (JavaModel2ASTCorrespondence.strictlyCorresponds(ifield, field)) {
                    return field;
                }
            } catch (final IllegalArgumentException e) {
                e.printStackTrace();
            } catch (final JavaModelException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Annotation getAnnotation(final IAnnotation iannotation, final BodyDeclaration bodyDeclaration) {
        if (null == bodyDeclaration || null == bodyDeclaration.modifiers()) {
            return null;
        }
        for (final Object modifier : bodyDeclaration.modifiers()) {
            if (modifier instanceof Annotation) {
                if (JavaModel2ASTCorrespondence.corresponds(iannotation, (Annotation) modifier)) {
                    return (Annotation) modifier;
                }
            }
        }
        return null;
    }

}
