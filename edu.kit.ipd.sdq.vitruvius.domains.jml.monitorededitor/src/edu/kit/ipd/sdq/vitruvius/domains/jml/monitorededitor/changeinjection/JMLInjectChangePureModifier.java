package edu.kit.ipd.sdq.vitruvius.domains.jml.monitorededitor.changeinjection;

import edu.kit.ipd.sdq.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier;

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
