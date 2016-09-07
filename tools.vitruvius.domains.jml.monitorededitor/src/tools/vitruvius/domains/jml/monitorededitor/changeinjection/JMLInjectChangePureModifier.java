package tools.vitruvius.domains.jml.monitorededitor.changeinjection;

import tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier;

/**
 * Change injector for modifcations on the pure modifier of JML methods.
 * 
 * @author Stephan Seifermann
 *
 */
public class JMLInjectChangePureModifier extends JMLInjectJMLModifierBase {

    @Override
    protected JMLSpecMemberModifier getWantedModifier() {
        return JMLSpecMemberModifier.PURE;
    }

}
