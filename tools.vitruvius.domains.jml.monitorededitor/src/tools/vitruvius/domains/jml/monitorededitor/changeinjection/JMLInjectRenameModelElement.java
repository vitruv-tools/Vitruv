package tools.vitruvius.domains.jml.monitorededitor.changeinjection;

import java.util.ArrayList;
import java.util.List;

import tools.vitruvius.domains.jml.language.jML.CompilationUnit;
import tools.vitruvius.domains.jml.language.jML.JMLModelElement;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration;
import tools.vitruvius.domains.jml.monitorededitor.ModelUtilities;
import tools.vitruvius.domains.jml.monitorededitor.changeinjection.Choice.EObjectToString;

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
