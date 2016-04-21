package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui.commands;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.ui.texteditor.ITextEditor;

import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ScmChangeResult;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.GitChangeExtractor;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.GumTreeChangeExtractor;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.JaMoPPContentValidator;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui.ApplyScmChangesDialog;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui.ValidationStatistics;

public class ApplyScmChangesCommand extends AbstractHandler {

	private static final Logger logger = Logger.getLogger(ApplyScmChangesCommand.class.getName());

	public ApplyScmChangesCommand() {
		super();
		logger.setLevel(Level.ALL);
	}
	
	public boolean isJavaChange(ScmChangeResult changeResult) {
		boolean newFileHasJavaExtension =  hasJavaExtension(changeResult.getNewFile());
		boolean oldFileHasJavaExtension =  hasJavaExtension(changeResult.getOldFile());
		return oldFileHasJavaExtension || newFileHasJavaExtension;
	}

	private boolean hasJavaExtension(IPath file) {
		return file.getFileExtension() != null && file.getFileExtension().equals("java");
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		final IStructuredSelection structuredSelection = (IStructuredSelection) selection;

		final Object firstElement = structuredSelection.getFirstElement();
		final IJavaProject javaProject = (IJavaProject) firstElement;
		final IProject project = javaProject.getProject();

		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		Shell activeShell = window.getShell();
		ApplyScmChangesDialog dialog = new ApplyScmChangesDialog(activeShell, project);
		dialog.create();
		int dialogResponse = dialog.open();
		if (dialogResponse == Window.OK) {
			logger.info("User pressed OK");
			Repository repo = dialog.getRepository();
			String newVersion = dialog.getNewVersion();
			String oldVersion = dialog.getOldVersion();
			
			int replaySpeedInMs = dialog.getReplaySpeed();

			logger.info("Checking out oldversion in git");
			checkoutCommitInWorkingTree(repo, oldVersion);
			try {
				project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e) {
				logger.error("Failed to refresh project", e);
				return null;
			}

			GitChangeExtractor changeExtractor = new GitChangeExtractor(repo);
			List<ScmChangeResult> results;
			try {
				results = changeExtractor.extract(repo.resolve(newVersion), repo.resolve(oldVersion));
			} catch (RevisionSyntaxException | IOException e) {
				logger.error("Failed to resolve version ids", e);
				return null;
			}
			long delay = replaySpeedInMs * 2;
			List<ScmChangeResult> javaResults = results.parallelStream().filter(r -> isJavaChange(r)).collect(Collectors.toList());
			ValidationStatistics stats = new ValidationStatistics();
			for (ScmChangeResult result : javaResults) {
				delay = splitAndReplayScmChange(project, window, replaySpeedInMs, delay, result, stats);
			}
			logger.info(String.format("%s/%s extractions were valid", stats.getValidExtractions(), stats.getTotalExtractions()));
			scheduleCleanup(project, repo, replaySpeedInMs, delay);
		} else if (dialogResponse == Window.CANCEL) {
			logger.warn("User pressed Cancel");
		}

		return null;
	}

	private long splitAndReplayScmChange(final IProject project, IWorkbenchWindow window, int replaySpeedInMs,
			long delay, ScmChangeResult result, ValidationStatistics stats) {
		List<String> contentList;
		if (result.getNewContent() != null && result.getOldContent() != null) {
			IFile file = project.getFile(result.getNewFile());
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			GumTreeChangeExtractor gumTreeChangeExtractor = new GumTreeChangeExtractor(result.getOldContent(), result.getNewContent(), uri);
			gumTreeChangeExtractor.setValidator(new JaMoPPContentValidator());
			contentList = gumTreeChangeExtractor.extract();
			stats.addValidExtractions(gumTreeChangeExtractor.getNumberOfValidExtractions());
			stats.addTotalExtractions(gumTreeChangeExtractor.getNumberOfTotalExtractions());
		} else {
			contentList = new ArrayList<String>();
		}

		
		UIJob scmJob = new UIJob("SCM Job") {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					if (result.getNewContent() == null) {
						logger.info(String.format("File %s deletion found in SCM. Deleting.",
								result.getOldFile().toOSString()));
						project.getFile(result.getOldFile()).delete(true, new NullProgressMonitor());
						project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
						return Status.OK_STATUS;
					} else if (result.getOldContent() == null) {
						logger.info(String.format("File %s creation found in SCM. Creating",
								result.getNewFile().toOSString()));
						InputStream stream = new ByteArrayInputStream(
								result.getNewContent().getBytes(StandardCharsets.UTF_8));
						project.getFile(result.getNewFile()).create(stream, true, new NullProgressMonitor());
						project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
						return Status.OK_STATUS;
					} else if (!result.getNewFile().equals(result.getOldFile())) {
						logger.info(String.format("File %s moved to %s found in SCM. Deleting old and creating new.",
								result.getOldFile().toOSString(), result.getNewFile().toOSString()));
						project.getFile(result.getOldFile()).delete(true, new NullProgressMonitor());
						// Here we apply old contents, so we can later apply
						// atomic changes from GumTree one by one, like the file
						// hasn't been moved.
						InputStream stream = new ByteArrayInputStream(
								result.getOldContent().getBytes(StandardCharsets.UTF_8));
						project.getFile(result.getNewFile()).create(stream, true, new NullProgressMonitor());
						project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					}
					IPath path = result.getNewFile();
					if (project.exists(path)) {
						logger.info(String.format("File %s found in project %s.", path.toOSString(),
								project.getLocation().toOSString()));
						IPath absoluteFilePath = project.getLocation().append(path);
						logger.info(String.format("Absolute file path: %s", absoluteFilePath.toOSString()));
						IFile file = project.getFile(path);
						IWorkbenchPage page = window.getActivePage();
						if (page != null) {
							ITextEditor editor = (ITextEditor) IDE.openEditor(page, file);
							
							int contentDelay = replaySpeedInMs;
							for (String content : contentList) {

								scheduleContentSet(editor, contentDelay, content);
								contentDelay = contentDelay + replaySpeedInMs;
							}
							
							scheduleEditorClose(editor, contentDelay);
							
							return Status.OK_STATUS;
						}
					} else {
						logger.info(String.format("Could not find file %s in project %s.", path.toOSString(),
								project.getLocation()));
					}
				} catch (RevisionSyntaxException e) {
					logger.error("Failed to resolve version strings.", e);
				} catch (PartInitException e) {
					logger.error("Failed to open editor.", e);
				} catch (CoreException e) {
					logger.error("Failed to delete/create file", e);
				}
				return Status.CANCEL_STATUS;
			}

		};
		scmJob.schedule(delay);
		delay = delay + (contentList.size() + 3) * replaySpeedInMs;
		return delay;
	}

	private void scheduleCleanup(final IProject project, Repository repo, int replaySpeedInMs, long delay) {
		UIJob cleanupJob = new UIJob("CleanUp") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					logger.info("Checking out master branch");
					checkoutMasterBranch(repo);
				} catch (IOException | URISyntaxException e) {
					logger.error("Failed to checkout master branch", e);
				}
				try {
					project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
					return Status.OK_STATUS;
				} catch (CoreException e) {
					logger.error("Failed to refresh project.", e);
				}
				return Status.CANCEL_STATUS;
			}
		};
		cleanupJob.schedule(delay + replaySpeedInMs * 5);
	}
	
	private void scheduleEditorClose(ITextEditor editor, int contentDelay) {
		UIJob job = new UIJob("Close editor") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				logger.info("Closing editor");
				editor.doSave(new NullProgressMonitor());
				editor.close(true);
				return Status.OK_STATUS;
			}
		};
		job.schedule(contentDelay);
	}
	
	private void scheduleContentSet(ITextEditor editor, int contentDelay, String content) {
		UIJob contentJob = new UIJob("Set document content") {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				IEditorInput input = editor.getEditorInput();
				IDocument document = editor.getDocumentProvider().getDocument(input);
															
				document.set(content);
				return Status.OK_STATUS;
			}
		};
		logger.info("Set file content in " + contentDelay + " milliseconds");
		contentJob.schedule(contentDelay);
	}

	private void checkoutCommitInWorkingTree(Repository repository, String name) {
		try (Git git = new Git(repository);) {
			git.checkout().setName(name).call();
		} catch (GitAPIException e) {
			logger.error("Couldn't checkout old commit to working tree");
			e.printStackTrace();
		}
	}

	private void checkoutMasterBranch(Repository repo) throws IOException, URISyntaxException {
		try (Git git = new Git(repo);) {
			git.reset().setMode( ResetType.HARD ).call();
			git.clean().setCleanDirectories(true).call();
			git.checkout().setName("master").call();
			git.reset().setMode( ResetType.HARD ).call();
		} catch (GitAPIException e) {
			logger.error("Couldn't checkout master branch");
			e.printStackTrace();
		}
	}

}
