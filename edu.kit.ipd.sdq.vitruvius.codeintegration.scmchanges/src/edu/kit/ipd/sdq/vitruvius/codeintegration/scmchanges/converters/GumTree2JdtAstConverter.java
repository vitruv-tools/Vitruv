package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.converters;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.github.gumtreediff.tree.ITree;

public interface GumTree2JdtAstConverter {
	
	CompilationUnit convertTree(ITree gumTree);

}
