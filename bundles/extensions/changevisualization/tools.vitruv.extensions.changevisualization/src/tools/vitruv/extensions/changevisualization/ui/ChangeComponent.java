package tools.vitruv.extensions.changevisualization.ui;

import java.awt.LayoutManager;
import java.util.List;

import javax.swing.JPanel;

import tools.vitruv.extensions.changevisualization.ChangeDataSet;

/**
 * Base class for different visualization implementations. If additional implementation are needed,
 * they have to subclass this class to be usable by the change visualization framework.
 * 
 * @author Andreas Loeffler
 */
public abstract class ChangeComponent extends JPanel {

	/**
	 * Needed for eclipse to stop warning about serialVersionIds. This feature will never been used. 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Mandatory constructor to use for implementations
	 * 
	 * @param layout The layout manager to use
	 */
	public ChangeComponent(LayoutManager layout) {
		super(layout);
	}

	/**
	 * Visualizes a given ChangeDataSet
	 * 
	 * @param cds The ChangeDataSet to visualize
	 */
	public abstract void setData(ChangeDataSet cds);

	public abstract List<String> determineHighlightedCdsIds(String highlightID, List<ChangeDataSet> changeDataSets);

}
