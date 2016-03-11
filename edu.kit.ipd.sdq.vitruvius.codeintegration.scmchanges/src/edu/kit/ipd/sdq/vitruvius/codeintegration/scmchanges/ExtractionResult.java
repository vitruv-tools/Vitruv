package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges;

import java.util.List;

import com.github.gumtreediff.actions.model.Action;
import com.github.gumtreediff.matchers.MappingStore;
import com.github.gumtreediff.tree.TreeContext;

public class ExtractionResult {

	private TreeContext oldContext;
	private TreeContext newContext;
	private MappingStore store;
	private List<Action> actions;

	public ExtractionResult(TreeContext oldContext, TreeContext newContext, MappingStore store, List<Action> actions) {
		this.oldContext = oldContext;
		this.newContext = newContext;
		this.store = store;
		this.actions = actions;
	}

	public TreeContext getOldContext() {
		return oldContext;
	}

	public TreeContext getNewContext() {
		return newContext;
	}

	public MappingStore getStore() {
		return store;
	}

	public List<Action> getActions() {
		return actions;
	}

}
