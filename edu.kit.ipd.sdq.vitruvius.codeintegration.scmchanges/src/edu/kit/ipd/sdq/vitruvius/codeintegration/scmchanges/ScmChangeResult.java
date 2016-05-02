package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

import org.eclipse.core.runtime.IPath;

public class ScmChangeResult {
	
	private IPath newFile;
	private String newContent;
	private String oldContent;
	private IPath oldFile;
	private String newVersionId;
	private String oldVersionId;

	public ScmChangeResult(IPath newFile, String newContent, String newVersionId, IPath oldFile, String oldContent, String oldVersionId) {
		this.newFile = newFile;
		this.newContent = newContent;
		this.newVersionId = newVersionId;
		this.oldFile = oldFile;
		this.oldContent = oldContent;
		this.oldVersionId = oldVersionId;
	}

	public IPath getNewFile() {
		return newFile;
	}

	public String getNewContent() {
		return newContent;
	}

	public String getOldContent() {
		return oldContent;
	}

	public IPath getOldFile() {
		return oldFile;
	}

	public String getNewVersionId() {
		return newVersionId;
	}

	public String getOldVersionId() {
		return oldVersionId;
	}
}
