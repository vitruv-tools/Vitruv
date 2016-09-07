package tools.vitruv.domains.java.monitorededitor.astchangelistener;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;

import tools.vitruv.domains.java.monitorededitor.javamodel2ast.JavaModel2AST;

/**
 * @author messinger
 * 
 *         Holds a map of {@link CompilationUnit} and can retrieve an old unit to a given a new
 *         unit, i.e. returns the old unit with the same internal identifier. On construction, the
 *         {@link PreviousASTState} parses and stores all compilation units of the projects to be
 *         monitored. Cf. method <code>buildInitialSnapshot(String[])</code>.
 * 
 */
public class PreviousASTState {
    private final HashMap<String, CompilationUnit> state;
    private final Logger log = Logger.getLogger(PreviousASTState.class);

    public PreviousASTState(String... projectNames) {
        this.state = new HashMap<String, CompilationUnit>();
        try {
            buildInitialSnaphot(projectNames);
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
    }

    private void buildInitialSnaphot(String... projectNames) throws JavaModelException {
        this.log.info("INITIAL AST SNAPSHOT");

        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        for (String projectName : projectNames) {
            IProject project = root.getProject(projectName);
            // project.open(null /* IProgressMonitor */);

            IJavaProject javaProject = JavaCore.create(project);
            for (IPackageFragment pkg : javaProject.getPackageFragments()) {
                for (ICompilationUnit unit : pkg.getCompilationUnits()) {
                    // if (!unit.isOpen())
                    // // preserve errors through "unparsable", broken compilation
                    // // units (e.g. an empty .java file)
                    // continue;
                    update(JavaModel2AST.parseCompilationUnit(unit));
                }
            }
        }
        this.log.info("FINISHED INITIAL AST SNAPSHOT");
    }

    public boolean update(CompilationUnit unit) {
        String key = getCompilationUnitIdentifier(unit);
        if (key == null)
            return false;
        this.state.put(key, unit);
        return true;
    }

    private static String getCompilationUnitIdentifier(CompilationUnit unit) {
        if (unit == null || unit.getTypeRoot() == null)
            return null;
        else if (unit.getPackage() != null)
            // default package
            return unit.getPackage().getName() + "." + unit.getTypeRoot().getElementName();
        else
            return unit.getTypeRoot().getElementName();
    }

    private String getCompilationUnitIdentifier(ICompilationUnit unit) {
        return unit != null ? unit.getParent().getElementName() + "." + unit.getElementName() : null;
    }

    public CompilationUnit getOldCompilationUnit(CompilationUnit unit) {
        return this.state.get(getCompilationUnitIdentifier(unit));
    }

    public CompilationUnit getOldCompilationUnit(String packageName, String typeRootName) {
        String compilationUnitIdentifier = "";
        if (packageName != null && !packageName.equals(""))
            compilationUnitIdentifier = packageName + ".";
        compilationUnitIdentifier += typeRootName;
        return this.state.get(compilationUnitIdentifier);
    }

    public CompilationUnit getOldCompilationUnit(ICompilationUnit unit) {
        return this.state.get(getCompilationUnitIdentifier(unit));
    }

    public Collection<CompilationUnit> getAllCompilationUnits() {
        return this.state.values();
    }
}
