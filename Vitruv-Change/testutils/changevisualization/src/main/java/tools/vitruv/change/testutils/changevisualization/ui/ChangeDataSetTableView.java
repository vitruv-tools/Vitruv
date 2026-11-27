package tools.vitruv.change.testutils.changevisualization.ui;

import java.util.List;

import javax.swing.border.Border;
import javax.swing.event.ListSelectionListener;

import tools.vitruv.change.testutils.changevisualization.common.ChangeDataSet;

public interface ChangeDataSetTableView {
    void setBorder(Border border);

    void addListSelectionListener(ListSelectionListener listener);

    int getSelectedRow();

    void setHighlightedCdsIDs(List<String> ids);

    void appendCds(ChangeDataSet cds);

    void repaint();
}
