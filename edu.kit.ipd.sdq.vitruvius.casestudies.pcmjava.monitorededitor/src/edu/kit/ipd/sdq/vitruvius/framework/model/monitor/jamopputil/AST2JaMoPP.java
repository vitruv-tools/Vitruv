package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.emftext.language.java.containers.CompilationUnit;

import edu.kit.ipd.sdq.vitruvius.framework.code.jamopp.JaMoPPParser;

public final class AST2JaMoPP {

    private static JaMoPPParser jamopp = new JaMoPPParser();

    private AST2JaMoPP() {
    }

    public static CompilationUnit getCompilationUnitForSerializedCompilationUnit(
            org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit) throws IOException {
        return jamopp.parseCompilationUnitFromDisk(getIPathFromCompilationUnitWithResource(astCompilationUnit));
    }

    public static CompilationUnit getCompilationUnitForInMemoryCompilationUnit(
            org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit, URI uri) throws IOException {
        CompilationUnit cu = jamopp.parseCompilationUnitFromInputStream(uri, getInputStream(astCompilationUnit));
        String cuName = getCompilationUnitName(astCompilationUnit);
        restoreCompilationUnitName(cuName, cu);
        return cu;
    }

    private static InputStream getInputStream(org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit) {
        String newSource = astCompilationUnit.toString();
        return new ByteArrayInputStream(newSource.getBytes());
    }

    private static String getCompilationUnitName(org.eclipse.jdt.core.dom.CompilationUnit astCompilationUnit) {
        // if no JavaElement is reference, the first top-level Type is assumed to be the compilation
        // unit's name
        IJavaElement element = astCompilationUnit.getJavaElement();
        if (element != null)
            return element.getElementName();
        else {
            for (Object type : astCompilationUnit.types()) {
                if (!(type instanceof TypeDeclaration))
                    continue;
                return ((TypeDeclaration) type).getName().toString();
            }
            return null;
        }
    }

    private static void restoreCompilationUnitName(String compilationUnitName, CompilationUnit compilationUnit) {
        if (compilationUnit == null)
            return;
        String fullCuName = getFullCompilationUnitName(compilationUnitName, compilationUnit);
        compilationUnit.setName(fullCuName);
    }

    private static String getFullCompilationUnitName(String compilationUnitName, CompilationUnit compilationUnit) {
        StringBuilder projectPrefix = new StringBuilder();
        for (String ns : compilationUnit.getNamespaces()) {
            projectPrefix.append(ns);
            projectPrefix.append('.');
        }
        String fullCuName = projectPrefix + compilationUnitName;
        return fullCuName;
    }

    public static CompilationUnit getCompilationUnitFromAbsolutePath(IPath compilationUnitPath) throws IOException {
        return jamopp.parseCompilationUnitFromDisk(compilationUnitPath);
    }

    public static IPath getIPathFromCompilationUnitWithResource(ASTNode astNode) {
        IResource iResource = getIResource(astNode);
        if (iResource == null)
            return null;
        return iResource.getLocation();
    }

    public static IResource getIResource(ASTNode astNode) {
        ASTNode astCompilationUnit = astNode.getRoot();
        if (!(astCompilationUnit instanceof org.eclipse.jdt.core.dom.CompilationUnit))
            throw new IllegalStateException("astNode.getRoot() is not a CompilationUnit");
        ICompilationUnit iCU = (ICompilationUnit) ((org.eclipse.jdt.core.dom.CompilationUnit) astCompilationUnit)
                .getJavaElement();
        if (iCU == null)
            return null;
        IResource resource = null;
        try {
            resource = iCU.getCorrespondingResource();
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
        return resource;
    }

}
