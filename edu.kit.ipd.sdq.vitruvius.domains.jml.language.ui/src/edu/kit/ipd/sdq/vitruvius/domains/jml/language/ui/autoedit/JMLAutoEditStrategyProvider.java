package edu.kit.ipd.sdq.vitruvius.domains.jml.language.ui.autoedit;

import org.eclipse.xtext.ui.editor.autoedit.DefaultAutoEditStrategyProvider;

public class JMLAutoEditStrategyProvider extends DefaultAutoEditStrategyProvider {

    @Override
    protected void configureMultilineComments(IEditStrategyAcceptor acceptor) {
        return;
    }

}
