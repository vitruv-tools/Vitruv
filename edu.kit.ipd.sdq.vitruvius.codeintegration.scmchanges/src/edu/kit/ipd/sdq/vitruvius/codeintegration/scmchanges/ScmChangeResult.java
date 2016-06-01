package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

import org.eclipse.core.runtime.IPath;

public class ScmChangeResult {
	
	private String newContent;
	private String oldContent;
	private IPath oldFileWithOffset;
	private IPath newFileWithOffset;
	private String newVersionId;
	private String oldVersionId;

	public ScmChangeResult(IPath repoProjectOffset, IPath newFile, String newContent, String newVersionId, IPath oldFile, String oldContent, String oldVersionId) {
		this.newFileWithOffset = applyOffset(newFile, repoProjectOffset);
		this.newContent = newContent;
		this.newVersionId = newVersionId;
		this.oldFileWithOffset = applyOffset(oldFile, repoProjectOffset);
		this.oldContent = oldContent;
		this.oldVersionId = oldVersionId;
	}

	private IPath applyOffset(IPath relativeToRepo, IPath offset) {
		if (relativeToRepo == null) {
			return null;
		}
		int matching = relativeToRepo.matchingFirstSegments(offset);
		IPath fileWithOffset = relativeToRepo.removeFirstSegments(matching);
		return fileWithOffset;
	}

	public IPath getNewFileWithOffset() {
		return newFileWithOffset;
	}

	public String getNewContent() {
		return newContent;
	}

	public String getOldContent() {
		return oldContent;
	}

	public IPath getOldFileWithOffset() {
		return oldFileWithOffset;
	}

	public String getNewVersionId() {
		return newVersionId;
	}

	public String getOldVersionId() {
		return oldVersionId;
	}
}
