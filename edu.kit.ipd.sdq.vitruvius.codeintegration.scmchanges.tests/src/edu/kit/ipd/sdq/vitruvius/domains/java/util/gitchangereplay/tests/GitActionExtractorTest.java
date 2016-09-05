package edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.tests;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.After;
import org.junit.Test;
import org.osgi.framework.Bundle;

import edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.extractors.GitChangeExtractor;

public class GitActionExtractorTest {
	
	private static final Logger logger = Logger.getLogger(Logger.class.getName());
	
	private static final String TEST_BUNDLE_NAME = "edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.tests";
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
	public void testSimpleActionExtraction() throws Exception {
		Repository repo = getTestRepo();
		
		//Checkout old commit in working tree, so we can reapply changes to it
		//TODO move somewhere else. Doesn't really belong here
		checkoutCommitInWorkingTree(repo, OLD_COMMIT_ID);
		
		ObjectId oldId = repo.resolve(OLD_COMMIT_ID);
		ObjectId newId = repo.resolve(NEW_COMMIT_ID);
		
		GitChangeExtractor extractorUnderTest = new GitChangeExtractor(repo, Path.fromOSString(""));
		extractorUnderTest.extract(newId, oldId);

	}

	private Repository getTestRepo() throws IOException, URISyntaxException {
		Bundle bundle = Platform.getBundle(getTestBundleName());
		URL testRepoBundleURL = bundle.getEntry(getTestRepoFolder());
		URL fileURL = FileLocator.resolve(testRepoBundleURL);
		File file = new File(fileURL.toURI());
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repo = builder.setGitDir(file).setMustExist(true).readEnvironment().findGitDir().build();
		return repo;
	}
	
	private void checkoutCommitInWorkingTree(Repository repository, String name) {
		try (Git git = new Git(repository);) {
			git.checkout().setName(name).call();
		} catch (GitAPIException e) {
			logger.error("Couldn't checkout old commit to working tree");
			e.printStackTrace();
		}
	}
	
	@After
	public void checkoutMasterBranch() throws IOException, URISyntaxException {
		try (Git git = new Git(getTestRepo());) {
			git.checkout().setName("master").call();
		} catch (GitAPIException e) {
			logger.error("Couldn't checkout old commit to working tree");
			e.printStackTrace();
		}
	}


}
