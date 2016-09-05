package edu.kit.ipd.sdq.vitruvius.domains.java.util.gitchangereplay.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Text;

public class ApplyScmChangesDialog extends TitleAreaDialog {

	private static final Logger logger = Logger.getLogger(ApplyScmChangesDialog.class.getName());

	private IProject project;

	private Text txtOldVersion;

	private Text txtNewVersion;

	private ObjectId newVersion;

	private ObjectId oldVersion;

	private Repository repo;
	
	private int replaySpeedInMs;

	private Slider replaySpeedSlider;

	private Text txtCheckoutVersion;

	private String cleanupCheckoutVersion;

	protected boolean allowManualControl = false;

	private Text txtIgnorePaths;

	private List<IPath> ignorePaths;

	private boolean enableJamoppValidation;

	public ApplyScmChangesDialog(Shell parentShell, IProject project) {
		super(parentShell);
		this.project = project;
		logger.setLevel(Level.ALL);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Apply SCM Changes");
		setHelpAvailable(false);
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		try {
			repo = builder.findGitDir(project.getLocation().toFile()).setMustExist(true).readEnvironment().build();
		} catch (IOException e) {
			setMessage("Failed to find git repo.", IMessageProvider.ERROR);
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			logger.error("Failed to find git repo for chosen project.", e);
		}
		if (repo != null) {
			setMessage("Choose the target version", IMessageProvider.INFORMATION);
			String repoRoot = repo.getDirectory().getAbsoluteFile().toString();
			logger.info(String.format("Found git repository root at: %s", repoRoot));
			Git git = new Git(repo);
	        Iterable<RevCommit> logs = null;
			try {
				logs = git.log().call();
			} catch (GitAPIException e) {
				logger.error("Problem while running git log to retrieve newest and oldest commit on branch.", e);
			} finally {
				git.close();
			}
			RevCommit newest = null;
			RevCommit oldest = null;
			RevCommit potentiallyOldest = null;
			if (logs != null) {
				for (RevCommit rev : logs) {
					if (newest == null) {
						newest = rev;
					}
					potentiallyOldest = rev;
				}
			}
	        oldest = potentiallyOldest;
			if (oldest != null) {
				txtOldVersion.setText(oldest.getId().getName());
			}
			if (newest != null) {
				txtNewVersion.setText(newest.getId().getName());
			}
			validate();
		} else {
			setMessage("Project is not part of git repo!", IMessageProvider.ERROR);
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			logger.error("Project does not seem to be a part of a git repo.");
		}
		
		
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		createOldVersionField(container);
		createNewVersionField(container);
		createReplaySpeedSlider(container);
		createManualControlSwitch(container);
		createIgnorePathsField(container);
		createEnableJaMoPPValidationSwitch(container);
		createCleanupSection(container);
		return area;
	}
	
	private void createIgnorePathsField(Composite container) {
		Label ignorePathsLabel = new Label(container, SWT.NONE);
		ignorePathsLabel.setText("Ignore paths (comma sep.)");

		GridData dataIgnorePaths = new GridData();
		dataIgnorePaths.grabExcessHorizontalSpace = true;
		dataIgnorePaths.horizontalAlignment = GridData.FILL;

		txtIgnorePaths = new Text(container, SWT.BORDER);
		txtIgnorePaths.setLayoutData(dataIgnorePaths);
	}
	
	private void createEnableJaMoPPValidationSwitch(Composite container) {
		enableJamoppValidation = true;
		Label jamoppValidationLabel = new Label(container, SWT.NONE);
		jamoppValidationLabel.setText("Enable JaMoPP Validation");
		
		Button jamoppValidationButton = new Button(container, SWT.CHECK);
		jamoppValidationButton.setSelection(enableJamoppValidation);
		jamoppValidationButton.addSelectionListener(new SelectionAdapter() {

	        @Override
	        public void widgetSelected(SelectionEvent event) {
	            Button btn = (Button) event.getSource();
	            enableJamoppValidation = btn.getSelection();
	        }
	    });
	}

	private void createManualControlSwitch(Composite container) {
		Label manualControl = new Label(container, SWT.NONE);
		manualControl.setText("Manual Control of Replay");
		
		Button manualControlButton = new Button(container, SWT.CHECK);
		manualControlButton.addSelectionListener(new SelectionAdapter() {

	        @Override
	        public void widgetSelected(SelectionEvent event) {
	            Button btn = (Button) event.getSource();
	            allowManualControl = btn.getSelection();
	            if (allowManualControl) {
	            	replaySpeedSlider.setEnabled(false);
	            } else {
	            	replaySpeedSlider.setEnabled(true);
	            }
	        }
	    });
	}

	private void createCleanupSection(Composite container) {
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		Label cleanupLabel = new Label(container, SWT.NONE);
		cleanupLabel.setText("Cleanup options:");
		cleanupLabel.setLayoutData(gridData1);
		
		//empty for grid
		new Label(container, SWT.NONE);
		
		Label checkoutLabel = new Label(container, SWT.NONE);
		checkoutLabel.setText("Checkout version(empty to disable):");
		
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.horizontalAlignment = GridData.FILL;

		txtCheckoutVersion = new Text(container, SWT.BORDER);
		txtCheckoutVersion.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		txtCheckoutVersion.setLayoutData(gridData2);
		
		
	}

	private void createReplaySpeedSlider(Composite container) {
		Label speedSliderLabel = new Label(container, SWT.NONE);
		speedSliderLabel.setText("Replay Speed");
		
		GridData gridData1 = new GridData();
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.horizontalAlignment = GridData.FILL;
		
		replaySpeedSlider = new Slider(container, SWT.NONE);
		replaySpeedSlider.setLayoutData(gridData1);
		replaySpeedSlider.setMinimum(100);
		replaySpeedSlider.setMaximum(5000);
		replaySpeedSlider.setIncrement(100);
		replaySpeedSlider.setPageIncrement(100);
		replaySpeedSlider.setSelection(1000);
		
		Label speedTextLabel = new Label(container, SWT.NONE);
		speedTextLabel.setText("Time per Change");
		
		Text speedText = new Text(container, SWT.NONE);
		speedText.setEditable(false);
		replaySpeedSlider.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				speedText.setText(replaySpeedSlider.getSelection() + " ms");
			}
		});
		speedText.setText(replaySpeedSlider.getSelection() + " ms");
	}

	private void createOldVersionField(Composite container) {
		Label oldVersion = new Label(container, SWT.NONE);
		oldVersion.setText("Old Version");

		GridData dataOldVersion = new GridData();
		dataOldVersion.grabExcessHorizontalSpace = true;
		dataOldVersion.horizontalAlignment = GridData.FILL;

		txtOldVersion = new Text(container, SWT.BORDER);
		txtOldVersion.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		txtOldVersion.setLayoutData(dataOldVersion);
	}

	private void createNewVersionField(Composite container) {
		Label newVersion = new Label(container, SWT.NONE);
		newVersion.setText("New Version");

		GridData dataNewVersion = new GridData();
		dataNewVersion.grabExcessHorizontalSpace = true;
		dataNewVersion.horizontalAlignment = GridData.FILL;

		txtNewVersion = new Text(container, SWT.BORDER);
		txtNewVersion.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		txtNewVersion.setLayoutData(dataNewVersion);
	}
	
	protected void validate() {
		if (!txtCheckoutVersion.getText().isEmpty() && !validate(txtCheckoutVersion, "Cleanup-Checkout")) {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
			return;
		}
		if (validate(txtNewVersion, "New") && validate(txtOldVersion, "Old")) {
			if (txtNewVersion.getText().equals(txtOldVersion.getText())) {
				setMessage("Versions are equal. No change possible.", IMessageProvider.ERROR);
				getButton(IDialogConstants.OK_ID).setEnabled(false);
			} else if (!areReachable(txtNewVersion, txtOldVersion)) {
				getButton(IDialogConstants.OK_ID).setEnabled(false);
			} else {
				setMessage("Valid versions entered.", IMessageProvider.INFORMATION);
				getButton(IDialogConstants.OK_ID).setEnabled(true);
			}
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
	}
	
	private ObjectId stringToRevId(String stringId) throws IOException {
		ObjectId rev = null;
		Ref ref = repo.findRef(stringId);
		if (ref != null) {
			final Ref repoPeeled = repo.peel(ref);
			if(repoPeeled.getPeeledObjectId() != null) {
				return repoPeeled.getPeeledObjectId();
			}
			return ref.getObjectId();
		} else {
			rev = repo.resolve(stringId);
		}
		return rev;
	}

	private boolean areReachable(Text txtNewVersion, Text txtOldVersion) {
		try (RevWalk revWalk = new RevWalk(repo)){
			ObjectId oldRev = stringToRevId(txtOldVersion.getText());
			ObjectId newRev = stringToRevId(txtNewVersion.getText());
			boolean isMerged = revWalk.isMergedInto(revWalk.parseCommit(oldRev), revWalk.parseCommit(newRev));
			if (isMerged) {
				return true;
			} else {
				setMessage("New Version cannot be reached from old version", IMessageProvider.ERROR);
				return false;
			}
		} catch (RevisionSyntaxException | IOException e) {
			setMessage("Something during version resolving.", IMessageProvider.ERROR);
			return false;
		}
	}

	protected boolean validate(Text txtField, String name) {
		if (txtField.getText().isEmpty()) {
			setMessage(name + " Version field cannot be empty.", IMessageProvider.ERROR);
			return false;
		} else {
			ObjectId rev;
			try {
				String idTxt = txtField.getText();
				rev = stringToRevId(idTxt);
				if (rev == null) {
					setMessage(name + " Version not found in repository.", IMessageProvider.ERROR);
					return false;
				}
			} catch (RevisionSyntaxException | IOException e) {
				setMessage(name + " Version field does not contain valid version id.", IMessageProvider.ERROR);
				return false;
			}
		}
		return true;
	}

	public ObjectId getNewVersion() {
		return newVersion;
	}

	public ObjectId getOldVersion() {
		return oldVersion;
	}

	@Override
	protected void okPressed() {
		try {
			newVersion = stringToRevId(txtNewVersion.getText());
			oldVersion = stringToRevId(txtOldVersion.getText());
		} catch (IOException e) {
			throw new RuntimeException("Should be avoided by validation. Please fix.");
		}
		replaySpeedInMs = replaySpeedSlider.getSelection();
		cleanupCheckoutVersion = txtCheckoutVersion.getText();
		ignorePaths = splitPaths(txtIgnorePaths.getText());
		super.okPressed();
	}

	private List<IPath> splitPaths(String text) {
		List<IPath> paths = new ArrayList<>();
		for (String pathString : text.split(",")) {
			paths.add(Path.fromOSString(pathString));
		}
		return paths;
	}

	public Repository getRepository() {
		return repo;
	}

	public int getReplaySpeed() {
		return replaySpeedInMs;
	}

	public String getCleanupCheckoutVersion() {
		return cleanupCheckoutVersion;
	}
	
	public boolean isManualControlEnabled() {
		return allowManualControl;
	}
	
	public List<IPath> getPathsToIgnore() {
		return ignorePaths;
	}
	
	public boolean isJamoppValidationEnabled() {
		return enableJamoppValidation;
	}

}
