package edu.kit.ipd.sdq.vitruvius.framework.code.jamopp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.emftext.language.java.containers.CompilationUnit;

public class JaMoPPParser {

    public CompilationUnit parseCompilationUnitFromDisk(IPath compilationUnitPath) throws IOException {
        String path = compilationUnitPath.toOSString();
        return parseCompilationUnitFromDisk(path);
    }

    public CompilationUnit parseCompilationUnitFromDisk(String absPath) throws IOException {
        JaMoPP jamopp = new JaMoPP();
        File compilationUnitFile = new File(absPath);
        jamopp.parseResource(compilationUnitFile);
        return getCompilationUnit(jamopp);
    }

    public CompilationUnit parseCompilationUnitFromDisk(URI uri) throws IOException {
        JaMoPP jamopp = new JaMoPP();
        jamopp.loadResource(uri);
        return getCompilationUnit(jamopp);
    }

    public CompilationUnit parseCompilationUnitFromInputStream(URI uri, InputStream in) throws IOException {
        JaMoPP jamopp = new JaMoPP();
        jamopp.loadResource(uri, in);
        return getCompilationUnit(jamopp);
    }

    private CompilationUnit getCompilationUnit(JaMoPP jamopp) {
        ResourceSet rs = jamopp.getResourceSet();
        EObject jamoppCU = rs.getResources().get(0).getAllContents().next();
        if (!(jamoppCU instanceof CompilationUnit))
            // e.g. empty .java files are 'EmptyModel's, not CompilationUnits
            return null;
        else
            return (CompilationUnit) jamoppCU;
    }

}
