package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

import org.eclipse.core.runtime.IPath;

public class ScmChangeResult {
	
	private IPath newFile;
	private String newContent;
	private String oldContent;
	private IPath oldFile;

	public ScmChangeResult(IPath newFile, String newContent, IPath oldFile, String oldContent) {
		this.newFile = newFile;
		this.newContent = newContent;
		this.oldFile = oldFile;
		this.oldContent = oldContent;
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
}
