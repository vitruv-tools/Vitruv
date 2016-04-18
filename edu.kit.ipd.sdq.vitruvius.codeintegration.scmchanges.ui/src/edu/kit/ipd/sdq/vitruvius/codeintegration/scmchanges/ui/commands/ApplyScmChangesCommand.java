package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui.commands;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui.ApplyScmChangesDialog;

public class ApplyScmChangesCommand extends AbstractHandler {

	private static final Logger logger = Logger.getLogger(ApplyScmChangesCommand.class.getName());

	public ApplyScmChangesCommand() {
		super();
		logger.setLevel(Level.ALL);
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
			long delay = 2000;
			for (ScmChangeResult result : results) {
				GumTreeChangeExtractor gumTreeChangeExtractor = new GumTreeChangeExtractor(result.getOldContent(), result.getNewContent());
				ArrayList<String> contentList = gumTreeChangeExtractor.extract();
				
				UIJob scmJob = new UIJob("SCM Job") {

					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						try {
							logger.info("Found change in " + result.getFile().toOSString());
							IPath path = result.getFile();
							if (project.exists(path)) {
								if (!path.getFileExtension().equals("java")) {
									logger.info(String.format("File extension %s did not match java extension.",
											path.getFileExtension()));
									return Status.OK_STATUS;
								}
								logger.info(String.format("File %s found in project %s.", path.toOSString(),
										project.getLocation().toOSString()));
								IPath absoluteFilePath = project.getLocation().append(path);
								logger.info(String.format("Absolute file path: %s", absoluteFilePath.toOSString()));
								IFile file = project.getFile(path);
								IWorkbenchPage page = window.getActivePage();
								if (page != null) {
									ITextEditor editor = (ITextEditor) IDE.openEditor(page, file);
									
									int contentDelay = 1000;
									for (String content : contentList) {
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
										contentDelay = contentDelay + 1000;
									}
									
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
						}
						return Status.CANCEL_STATUS;
					}
				};
				scmJob.schedule(delay);
				delay = delay + (contentList.size() + 3) * 1000;
			}
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
			cleanupJob.schedule(delay + 5000);
		} else if (dialogResponse == Window.CANCEL) {
			logger.warn("User pressed Cancel");
		}

		return null;
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
			git.checkout().setName("master").call();
			git.reset().setMode( ResetType.HARD ).call();
		} catch (GitAPIException e) {
			logger.error("Couldn't checkout master branch");
			e.printStackTrace();
		}
	}

}
