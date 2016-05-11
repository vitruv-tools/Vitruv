package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
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
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
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
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui.NonBlockingNextStepDialog;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui.ValidationStatistics;

public class ApplyScmChangesCommand extends AbstractHandler {

	private static final Logger logger = Logger.getLogger(ApplyScmChangesCommand.class.getName());

	public ApplyScmChangesCommand() {
		super();
		logger.setLevel(Level.ALL);
	}
	
	public boolean isJavaChange(ScmChangeResult changeResult) {
		boolean newFileHasJavaExtension =  hasJavaExtension(changeResult.getNewFileWithOffset());
		boolean oldFileHasJavaExtension =  hasJavaExtension(changeResult.getOldFileWithOffset());
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
			boolean manualControl = dialog.isManualControlEnabled();
			

			String repoRoot = repo.getDirectory().getAbsoluteFile().toString();
			IPath repoPath = Path.fromOSString(repoRoot);
			repoPath = repoPath.removeLastSegments(1);
			String relativeProjectInRepo = repoPath.toFile().toURI().relativize(project.getLocation().toFile().toURI()).getPath();
			logger.info(String.format("Project location relative to repo: %s", relativeProjectInRepo));

			logger.info("Checking out oldversion in git");
			checkoutCommitInWorkingTree(repo, oldVersion);
			try {
				project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			} catch (CoreException e) {
				logger.error("Failed to refresh project", e);
				return null;
			}

			GitChangeExtractor changeExtractor = new GitChangeExtractor(repo, Path.fromOSString(relativeProjectInRepo));
			List<ScmChangeResult> results;
			try {
				results = changeExtractor.extract(repo.resolve(newVersion), repo.resolve(oldVersion));
			} catch (RevisionSyntaxException | IOException e) {
				logger.error("Failed to resolve version ids", e);
				return null;
			}
			List<ScmChangeResult> javaResults = results.parallelStream().filter(r -> isJavaChange(r)).collect(Collectors.toList());
			ValidationStatistics stats = new ValidationStatistics();
			List<UIJob> jobs = new ArrayList<UIJob>();
			createJobs(project, window, javaResults, stats, jobs, repo, replaySpeedInMs, dialog, relativeProjectInRepo, manualControl);
		} else if (dialogResponse == Window.CANCEL) {
			logger.warn("User pressed Cancel");
		}

		return null;
	}

	private void createJobs(final IProject project, IWorkbenchWindow window, List<ScmChangeResult> javaResults,
			ValidationStatistics stats, List<UIJob> jobs, Repository repo, int replaySpeedInMs, ApplyScmChangesDialog dialog, String relativeProjectInRepo, boolean manualControl) {
		Job gumtreeJob = new Job("Extracting AST Changes") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor progress = SubMonitor.convert(monitor, javaResults.size());
				for (ScmChangeResult result : javaResults) {
					if (monitor.isCanceled()) {
						return Status.CANCEL_STATUS;
					}
					jobs.addAll(createJobsForScmChange(project, window, result, stats));
					progress.worked(1);
				}
				return Status.OK_STATUS;
			}
		};
		gumtreeJob.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				logger.info(String.format("%s/%s extractions were valid", stats.getValidExtractions(), stats.getTotalExtractions()));
				
				String cleanupCheckoutVersion = dialog.getCleanupCheckoutVersion();
				if (!cleanupCheckoutVersion.isEmpty()) {
					jobs.add(createCleanupJob(project, repo, cleanupCheckoutVersion));
				}
				if (!manualControl) {
					scheduleJobs(jobs, replaySpeedInMs);
				} else {
					startJobsInManualControlMode(jobs);
				}
			}
		});
		gumtreeJob.schedule();
		
	}

	private void startJobsInManualControlMode(List<UIJob> jobs) {
		Job replayJob = new Job("Replay Job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor progress = SubMonitor.convert(monitor, jobs.size());
				int currentJobIdx = 0;
				return scheduleRecursively(jobs, currentJobIdx, progress);
			}
		};
		replayJob.schedule();
	}

	private IStatus scheduleRecursively(List<UIJob> jobs, int currentJobIdx, SubMonitor progress) {
		if (progress.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		UIJob currentJob = jobs.get(currentJobIdx);
		currentJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				progress.worked(1);
				int nextJobIdx = currentJobIdx + 1;
				if (nextJobIdx < jobs.size()) {
					UIJob job = new UIJob("Next Step Dialog") {
						@Override
						public IStatus runInUIThread(IProgressMonitor monitor) {
							IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
							Shell activeShell = window.getShell();
							NonBlockingNextStepDialog nextStepDialog = new NonBlockingNextStepDialog(activeShell) {
								
								@Override
								protected void onClose(int returnCode) {
									if (returnCode == 0) {
										scheduleRecursively(jobs, nextJobIdx, progress);
									}
								}
							};
							nextStepDialog.open();
							return Status.OK_STATUS;
						}
					};
					job.schedule();
				}
			}
		});
		currentJob.schedule();
		return Status.OK_STATUS;
	}

	private void scheduleJobs(final List<UIJob> jobs, int replaySpeedInMs) {
		Job replayJob = new Job("Replay Job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor progress = SubMonitor.convert(monitor, jobs.size());
				try {
					for (UIJob job : jobs) {
						Thread.sleep(replaySpeedInMs);
						if (monitor.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
						job.schedule();
						progress.worked(1);
					}
					return Status.OK_STATUS;
				} catch (InterruptedException e) {
					logger.error("Unexpected interupt", e);
					return Status.CANCEL_STATUS;
				}
			}
		};
		replayJob.schedule();
	}

	private List<UIJob> createJobsForScmChange(final IProject project, IWorkbenchWindow window,
			ScmChangeResult result, ValidationStatistics stats) {
		List<UIJob> jobs = new ArrayList<UIJob>();
		List<String> contentList;
		if (result.getNewContent() != null && result.getOldContent() != null) {
			logger.info(String.format("Extracting atomic changes between version %s and %s for file %s.", result.getOldVersionId(), result.getNewVersionId(), result.getNewFileWithOffset()));
			IFile file = project.getFile(result.getNewFileWithOffset());
			URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
			GumTreeChangeExtractor gumTreeChangeExtractor = new GumTreeChangeExtractor(result.getOldContent(), result.getNewContent(), uri);
			gumTreeChangeExtractor.setValidator(new JaMoPPContentValidator());
			contentList = gumTreeChangeExtractor.extract();
			stats.addValidExtractions(gumTreeChangeExtractor.getNumberOfValidExtractions());
			stats.addTotalExtractions(gumTreeChangeExtractor.getNumberOfTotalExtractions());
		} else {
			contentList = new ArrayList<String>();
		}

		jobs.add(createScmStepInitJob(project, window, result));
		for (String content : contentList) {
			jobs.add(createContentSetJob(content, project, result.getNewFileWithOffset(), window));
		}
		if (contentList.size() > 1) {
			jobs.add(createEditorCloseJob(project, result.getNewFileWithOffset(), window));
		}
		return jobs;
	}

	private UIJob createScmStepInitJob(final IProject project, IWorkbenchWindow window, ScmChangeResult result) {
		UIJob scmJob = new UIJob("SCM Job") {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					if (!handleFileOperations(project, result)) {
						return Status.OK_STATUS;
					};
					IPath path = result.getNewFileWithOffset();
					if (project.exists(path)) {
						logger.info(String.format("File %s found in project %s.", path.toOSString(),
								project.getLocation().toOSString()));
						IPath absoluteFilePath = project.getLocation().append(path);
						logger.info(String.format("Absolute file path: %s", absoluteFilePath.toOSString()));
						IFile file = project.getFile(path);
						IWorkbenchPage page = window.getActivePage();
						if (page != null) {
							IDE.openEditor(page, file);
							return Status.OK_STATUS;
						}
					} else {
						logger.warn(String.format("Could not find file %s in project %s.", path.toOSString(),
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
		return scmJob;
	}
	
	private boolean handleFileOperations(final IProject project, ScmChangeResult result) throws CoreException {
		if (result.getNewContent() == null) {
			logger.info(String.format("File %s deletion found in SCM. Deleting.",
					result.getOldFileWithOffset().toOSString()));
			project.getFile(result.getOldFileWithOffset()).delete(true, new NullProgressMonitor());
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			return false;
		} else if (result.getOldContent() == null) {
			logger.info(String.format("File %s creation found in SCM. Creating",
					result.getNewFileWithOffset().toOSString()));
			InputStream stream = new ByteArrayInputStream(
					result.getNewContent().getBytes(StandardCharsets.UTF_8));
			createFileWithMissingParentDirs(project, result.getNewFileWithOffset(), stream);
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			return false;
		} else if (!result.getNewFileWithOffset().equals(result.getOldFileWithOffset())) {
			logger.info(String.format("File %s moved to %s found in SCM. Deleting old and creating new.",
					result.getOldFileWithOffset().toOSString(), result.getNewFileWithOffset().toOSString()));
			project.getFile(result.getOldFileWithOffset()).delete(true, new NullProgressMonitor());
			// Here we apply old contents, so we can later apply
			// atomic changes from GumTree one by one, like the file
			// hasn't been moved.
			InputStream stream = new ByteArrayInputStream(
					result.getOldContent().getBytes(StandardCharsets.UTF_8));
			createFileWithMissingParentDirs(project, result.getNewFileWithOffset(), stream);
			project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		}
		return true;
	}

	private void createFileWithMissingParentDirs(final IProject project, IPath filePath, InputStream stream)
			throws CoreException {
		IPath dirPath = filePath.removeLastSegments(1);
		IFolder directory = project.getFolder(dirPath);
		if (!directory.exists()) {
			logger.info("Parent directory does not exist. Creating: " + dirPath.toOSString());
			IPath absolutePath = directory.getLocation();
			File dir = absolutePath.toFile();
			dir.mkdirs();
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		project.getFile(filePath).create(stream, true, new NullProgressMonitor());
	}

	private UIJob createCleanupJob(final IProject project, Repository repo, String cleanupCheckoutVersion) {
		UIJob cleanupJob = new UIJob("CleanUp") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					logger.info("Reseting, cleaning and checking out version: " + cleanupCheckoutVersion);
					resetCleanAndCheckoutVersion(repo, cleanupCheckoutVersion);
				} catch (IOException | URISyntaxException e) {
					logger.error("Failed to checkout master branch", e);
					return Status.CANCEL_STATUS;
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
		return cleanupJob;
	}
	
	private UIJob createEditorCloseJob(IProject project, IPath path, IWorkbenchWindow window) {
		UIJob job = new UIJob("Close editor") {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					IFile file = project.getFile(path);
					IWorkbenchPage page = window.getActivePage();
					if (page != null) {
						ITextEditor editor;
						editor = (ITextEditor) IDE.openEditor(page, file);
						editor.doSave(new NullProgressMonitor());
						editor.close(true);
						return Status.OK_STATUS;
					} else {
						logger.warn("Couldn't save and close editor because page was null.");
						return Status.CANCEL_STATUS;
					}
				} catch (PartInitException e) {
					logger.error("Failed to close editor", e);
				}
				return Status.CANCEL_STATUS;
			}
		};
		return job;
	}
	
	private UIJob createContentSetJob(String content, IProject project, IPath path, IWorkbenchWindow window) {
		UIJob contentJob = new UIJob("Set document content") {

			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				try {
					IFile file = project.getFile(path);
					IWorkbenchPage page = window.getActivePage();
					if (page != null) {
						ITextEditor editor;
						editor = (ITextEditor) IDE.openEditor(page, file);
						IEditorInput input = editor.getEditorInput();
						IDocument document = editor.getDocumentProvider().getDocument(input);

						document.set(content);
						return Status.OK_STATUS;
					} else {
						logger.warn("Couldn't set content because page was null.");
						return Status.CANCEL_STATUS;
					}
				} catch (PartInitException e) {
					logger.error("Failed to set content", e);
				}
				return Status.CANCEL_STATUS;
			}
		};
		return contentJob;
	}

	private void checkoutCommitInWorkingTree(Repository repository, String name) {
		try (Git git = new Git(repository);) {
			git.checkout().setName(name).call();
		} catch (GitAPIException e) {
			logger.error("Couldn't checkout old commit to working tree");
			e.printStackTrace();
		}
	}

	private void resetCleanAndCheckoutVersion(Repository repo, String version) throws IOException, URISyntaxException {
		try (Git git = new Git(repo);) {
			git.reset().setMode( ResetType.HARD ).call();
			git.clean().setCleanDirectories(true).call();
			git.checkout().setName(version).call();
			git.reset().setMode( ResetType.HARD ).call();
		} catch (GitAPIException e) {
			logger.error("Couldn't checkout master branch");
			e.printStackTrace();
		}
	}

}
