package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.ui;

import java.io.IOException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ApplyScmChangesDialog extends TitleAreaDialog {

	private static final Logger logger = Logger.getLogger(ApplyScmChangesDialog.class.getName());

	private IProject project;

	private Text txtOldVersion;

	private Text txtNewVersion;

	private String newVersion;

	private String oldVersion;

	private Repository repo;

	public ApplyScmChangesDialog(Shell parentShell, IProject project) {
		super(parentShell);
		this.project = project;
		logger.setLevel(Level.ALL);
	}

	@Override
	public void create() {
		super.create();
		setTitle("Apply SCM Changes");
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
		return area;
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
		if (validate(txtNewVersion, "New") && validate(txtOldVersion, "Old")) {
			setMessage("Valid versions entered.", IMessageProvider.INFORMATION);
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
	}

	protected boolean validate(Text txtField, String name) {
		if (txtField.getText().isEmpty()) {
			setMessage(name + " Version field cannot be empty.", IMessageProvider.ERROR);
			return false;
		} else {
			ObjectId rev;
			try {
				rev = repo.resolve(txtField.getText());
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

	public String getNewVersion() {
		return newVersion;
	}

	public String getOldVersion() {
		return oldVersion;
	}

	@Override
	protected void okPressed() {
		newVersion = txtNewVersion.getText();
		oldVersion = txtOldVersion.getText();
		super.okPressed();
	}

	public Repository getRepository() {
		return repo;
	}

}
