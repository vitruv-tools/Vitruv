package tools.vitruvius.dsls.mapping.generator;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;

public class NoGenerator implements IGenerator {

	@Override
	public void doGenerate(Resource input, IFileSystemAccess fsa) {
		throw new UnsupportedOperationException("This generator should not be called.");
	}

}
