package tools.vitruvius.domains.java.util.gitchangereplay.converters;
import org.eclipse.jdt.core.dom.CompilationUnit;

import com.github.gumtreediff.tree.ITree;

public interface GumTree2JdtAstConverter {
	
	CompilationUnit convertTree(ITree gumTree);
	
	String getLastConvertedAsText();

}
