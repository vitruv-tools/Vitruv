package edu.kit.ipd.sdq.seifermann.thesis.monitorededitor.java.utils;

import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.dom.ASTNode;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil.AST2JaMoPP;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil.CompilationUnitAdapter;

/**
 * Utility class for JaMoPP helper methods.
 */
public class JaMoPPUtilities {

	/**
	 * Creates an adapter for a JaMoPP compilation unit by using a given JDT AST node.
	 * @param astNode A JDT AST node, which is part of the compilation unit to find.
	 * @return An adapter for a JaMoPP compilation unit.
	 */
    public static CompilationUnitAdapter getUnsavedCompilationUnitAdapter(ASTNode astNode) {
        URI uri = VURI.getInstance(AST2JaMoPP.getIResource(astNode)).getEMFUri();
        return new CompilationUnitAdapter(astNode, uri, false);
    }
    
}
