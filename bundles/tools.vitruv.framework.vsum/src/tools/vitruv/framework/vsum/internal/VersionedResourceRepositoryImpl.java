package tools.vitruv.framework.vsum.internal;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import tools.vitruv.change.composite.description.PropagatedChange;
import tools.vitruv.change.correspondence.model.PersistableCorrespondenceModel;
import tools.vitruv.framework.vsum.helper.VsumFileSystemLayout;

public class VersionedResourceRepositoryImpl {
	private List<HistoryNode> history = new LinkedList<>();
	private VsumFileSystemLayout fileSystemLayout;

	public VersionedResourceRepositoryImpl(VsumFileSystemLayout fileSystemLayout) {
		this.fileSystemLayout = fileSystemLayout;
	}
	
	public ModelResourcesInstance getLatestModelResourcesInstance() {
		if (history.isEmpty()) {
			return getModelResourcesInstance(null);
		}
		return getModelResourcesInstance(history.get(history.size() -1).versionIdentifier());
	}

	public ModelResourcesInstance getModelResourcesInstance(String versionIdentifier) {
		int versionIndex = getVersionIdentifiers().indexOf(versionIdentifier);
		if (versionIndex < 0) {
			return new ModelResourcesInstance(List.of(), null, fileSystemLayout);
		} else {
			List<HistoryNode> filteredHistory = history.subList(0, versionIndex + 1);
			return new ModelResourcesInstance(filteredHistory.stream().map(it -> it.changes()).toList(),
					versionIdentifier, fileSystemLayout);
		}
	}

	public List<String> getVersionIdentifiers() {
		return history.stream().map(it -> it.versionIdentifier()).toList();
	}

	public String commitChanges(List<PropagatedChange> changes, PersistableCorrespondenceModel correspondenceModel) {
		HistoryNode node = new HistoryNode(changes);
		history.add(node);
		correspondenceModel.save();
		return node.versionIdentifier();
	}

	private static class HistoryNode {
		private final String versionIdentifier;
		private final List<PropagatedChange> changes;

		public HistoryNode(List<PropagatedChange> changes) {
			this.versionIdentifier = UUID.randomUUID().toString();
			this.changes = changes.stream().map(change -> new PropagatedChange(change.getOriginalChange().unresolve(),
					change.getConsequentialChanges().unresolve())).toList();
		}

		public String versionIdentifier() {
			return versionIdentifier;
		}

		public List<PropagatedChange> changes() {
			return changes;
		}
	}
}
