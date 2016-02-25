package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.Bundle;

import com.github.gumtreediff.actions.model.Action;

import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.IAction2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.converters.Action2ChangeConverter;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.GitActionExtractor;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

public class GitActionExtractorTest {
	
	private static final Logger logger = Logger.getLogger(Logger.class.getName());
	
	private static final String TEST_BUNDLE_NAME = "edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.tests";
	private static final String TEST_REPO_FOLDER = "testrepo/.git";
	
	private static final String OLD_COMMIT_ID = "629f0058ccd0e9c58b96cd1bbfc5a2d17b45beeb";
	private static final String NEW_COMMIT_ID = "6e1293a2e85c1db29dc07e36f2c24b48a6c7ffaf";
	

	protected String getTestRepoFolder() {
		return TEST_REPO_FOLDER;
	}

	protected String getTestBundleName() {
		return TEST_BUNDLE_NAME;
	}

	@Test
	public void testSimpleActionExtraction() throws IOException, URISyntaxException {
		Bundle bundle = Platform.getBundle(getTestBundleName());
		URL testRepoBundleURL = bundle.getEntry(getTestRepoFolder());
		URL fileURL = FileLocator.resolve(testRepoBundleURL);
		File file = new File(fileURL.toURI());
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repo = builder.setGitDir(file).setMustExist(true).readEnvironment().findGitDir().build();
		
		ObjectId oldId = repo.resolve(OLD_COMMIT_ID);
		ObjectId newId = repo.resolve(NEW_COMMIT_ID);
		
		GitActionExtractor extractorUnderTest = new GitActionExtractor(repo);
		Iterable<Action> actions = extractorUnderTest.getActions(newId, oldId);
		
		Assert.assertNotNull(actions);
		
		IAction2ChangeConverter converter = new Action2ChangeConverter();
		Iterable<EChange> changes = converter.convertToChanges(actions);
		
	}


}
