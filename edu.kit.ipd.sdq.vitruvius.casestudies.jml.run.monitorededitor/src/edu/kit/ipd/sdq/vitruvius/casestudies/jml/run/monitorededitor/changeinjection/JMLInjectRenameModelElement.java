package edu.kit.ipd.sdq.vitruvius.casestudies.jml.run.monitorededitor.changeinjection;

import java.util.ArrayList;
import java.util.List;

import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLModelElement;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclWithModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.run.monitorededitor.ModelUtilities;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.run.monitorededitor.changeinjection.Choice.EObjectToString;

/**
 * JML change injector for rename operations on model members.
 */
public class JMLInjectRenameModelElement extends JMLInjectRenameMemberBase {

    @Override
    protected MemberDeclWithModifier getMemberToRename(CompilationUnit cu) {
        List<JMLModelElement> elements = new ArrayList<JMLModelElement>(ModelUtilities.getChildrenOfType(cu,
                JMLModelElement.class, true));

        JMLModelElement element = Choice.chooseFromEObjects(elements, new EObjectToString<JMLModelElement>() {
            @Override
            public String getTitle(JMLModelElement obj) {
                MemberDeclaration tmp = ((MemberDeclaration) obj.getElement().getMemberdecl());
                if (tmp.getField() != null) {
                    return tmp.getField().getVariabledeclarator().get(0).getIdentifier();
                } else {
                    return tmp.getMethod().getIdentifier();
                }
            }
        });
        
        if (element == null) {
            return null;
        }
        return element.getElement();
    }

}
