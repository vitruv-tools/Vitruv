package edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.syntaxtests;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.FrameworkTestBase;
import edu.kit.ipd.sdq.vitruvius.applications.jmljava.tests.plugintests.util.CodeElementUtil;

@RunWith(Parameterized.class)
public abstract class FrameworkTestParameterizedBase extends FrameworkTestBase {
	
	protected static interface TestParameterConstructor {
		public Collection<Object[]> create(ICompilationUnit cu) throws Exception;
	}
	
	protected static Collection<Object[]> data(TestParameterConstructor testParameterConstructor) throws Exception {
		IProject dummyProject = createNewProject();
		FileUtils.forceDeleteOnExit(dummyProject.getLocation().makeAbsolute().toFile());
		CodeElementUtil codeElementUtil = new CodeElementUtil(dummyProject);
		
		List<Object[]> result = new Vector<Object[]>();
		for (ICompilationUnit cu : codeElementUtil.getCompilationUnits()) {
			result.addAll(testParameterConstructor.create(cu));
		}
		
		dummyProject.delete(true, true, null);
		
		testParameterConstructor = null;
		
		return result;
	}
	
	protected abstract Object[] getCurrentParameters();
	
	protected void checkSyntaxAndCompareDiffIfAvailable(String testMethodName) throws IOException, InterruptedException, ExecutionException, TimeoutException {
		String testMethodExtension = '_' + StringUtils.join(getCurrentParameters(), '_');
		createAndCompareDiff(testMethodName + testMethodExtension, false);
	}
}
