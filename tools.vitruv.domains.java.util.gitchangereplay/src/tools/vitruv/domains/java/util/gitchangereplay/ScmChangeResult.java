package tools.vitruv.domains.java.util.gitchangereplay;

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
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Change:\n");
		if (newFileWithOffset != null && oldFileWithOffset != null) {
			if (newFileWithOffset.equals(oldFileWithOffset)) {
				buffer.append("Content Change in: ");
				buffer.append(newFileWithOffset);
				buffer.append("\n");
			} else {
				buffer.append("File moved from ");
				buffer.append(oldFileWithOffset);
				buffer.append(" to ");
				buffer.append(newFileWithOffset);
				buffer.append("\n");
				if (oldContent.equals(newContent)) {
					buffer.append("Content did not change\n");
				} else {
					buffer.append("Content changed\n");
				}
			}
		} else if (newFileWithOffset != null) {
			buffer.append("New file at ");
			buffer.append(newFileWithOffset);
			buffer.append("\n");
		} else if (oldFileWithOffset != null) {
			buffer.append("Removed file at ");
			buffer.append(oldFileWithOffset);
			buffer.append("\n");
		} else {
			buffer.append("Empty change. Should not occur.");
		}
		return buffer.toString();
	}
}
