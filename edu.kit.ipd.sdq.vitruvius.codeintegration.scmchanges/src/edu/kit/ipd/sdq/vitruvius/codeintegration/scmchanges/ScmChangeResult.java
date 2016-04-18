package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

import org.eclipse.core.runtime.IPath;

public class ScmChangeResult {
	
	private IPath file;
	private String newContent;
	private String oldContent;

	public ScmChangeResult(IPath file, String newContent, String oldContent) {
		this.file = file;
		this.newContent = newContent;
		this.oldContent = oldContent;
	}

	public IPath getFile() {
		return file;
	}

	public String getNewContent() {
		return newContent;
	}

	public String getOldContent() {
		return oldContent;
	}
}
