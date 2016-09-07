package tools.vitruvius.domains.jml.monitorededitor.changeinjection;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import tools.vitruvius.domains.jml.language.jML.CompilationUnit;
import tools.vitruvius.domains.jml.language.jML.JMLFactory;
import tools.vitruvius.domains.jml.language.jML.JMLMemberModifier;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.language.jML.JMLSpecMemberModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclWithModifier;
import tools.vitruvius.domains.jml.language.jML.MemberDeclaration;
import tools.vitruvius.domains.jml.monitorededitor.ModelUtilities;
import tools.vitruvius.domains.jml.monitorededitor.changeinjection.Choice.EObjectToString;
import tools.vitruvius.domains.java.echange.feature.JavaFeatureEChange;
import tools.vitruvius.domains.java.echange.feature.reference.JavaInsertEReference;
import tools.vitruvius.domains.java.echange.feature.reference.JavaRemoveEReference;
import tools.vitruvius.domains.java.echange.feature.reference.ReferenceFactory;
import tools.vitruvius.framework.change.description.EMFModelChange;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.change.description.VitruviusChangeFactory;
import tools.vitruvius.framework.change.echange.feature.FeatureEChange;
import tools.vitruvius.framework.util.datatypes.VURI;

/**
 * Base class for modifier changes on JML methods. The user can choose the method, which shall be
 * changed. If the method already has the modifier it will be removed and vice versa.
 * 
 * @author Stephan Seifermann
 *
 */
public abstract class JMLInjectJMLModifierBase extends JMLInjectionHandler {

    /**
     * @return The relevant modifier.
     */
    protected abstract JMLSpecMemberModifier getWantedModifier();

    @Override
    protected boolean performChangeOn(CompilationUnit cu) {
        List<MemberDeclWithModifier> memberDecls = new ArrayList<MemberDeclWithModifier>(
                ModelUtilities.getChildrenOfType(cu, MemberDeclWithModifier.class, true));

        MemberDeclWithModifier chosenMemberDecl = chooseMemberDeclaration(memberDecls);
        if (chosenMemberDecl == null) {
            return false;
        }

        final boolean addMofifier = !containsModifier(chosenMemberDecl);

        int result = JOptionPane.showConfirmDialog(null, "The " + getWantedModifier().getLiteral()
                + " modifier will be " + (addMofifier ? "added to" : "removed from") + " the element.");
        if (result != JOptionPane.YES_OPTION) {
            return false;
        }

        GeneralChange change = createChange(chosenMemberDecl, addMofifier);

        submitChange(change);

        return true;
    }

    /**
     * Displays a choice dialog for the user, where he can select a method.
     * 
     * @param elements
     *            All possible methods.
     * @return The selected method.s
     */
    private static MemberDeclWithModifier chooseMemberDeclaration(List<MemberDeclWithModifier> elements) {
        MemberDeclWithModifier element = Choice.chooseFromEObjects(elements,
                new EObjectToString<MemberDeclWithModifier>() {
                    @Override
                    public String getTitle(MemberDeclWithModifier obj) {
                        MemberDeclaration md = (MemberDeclaration) obj.getMemberdecl();
                        if (md.getField() != null) {
                            return md.getField().getVariabledeclarator().get(0).getIdentifier();
                        }
                        if (md.getMethod() != null) {
                            return md.getMethod().getIdentifier();
                        }
                        return "INVALID ENTRY";
                    }
                });

        if (element == null) {
            return null;
        }
        return element;
    }

    /**
     * Checks if the given method has a wanted modifier.
     * 
     * @param memberDecl
     *            The method.
     * @return True if there is a wanted modifier.
     */
    private boolean containsModifier(MemberDeclWithModifier memberDecl) {
        return findModifier(memberDecl) != null;
    }

    /**
     * Finds the wanted modifier for the given method.
     * 
     * @param memberDecl
     *            The method.
     * @return The modifier or null if there is no such modifier.
     */
    private JMLMemberModifier findModifier(MemberDeclWithModifier memberDecl) {
        for (JMLMemberModifier modifier : memberDecl.getJmlModifiers()) {
            if (modifier.getModifier() == getWantedModifier()) {
                return modifier;
            }
        }
        return null;
    }

    /**
     * Creates the modifier change for the given method.
     * 
     * @param chosenMemberDecl
     *            The method.
     * @param addModifier
     *            Flag to indicate whether the modifier shall be added (true) or deleted (false).
     * @return The constructed change.
     */
    private GeneralChange createChange(MemberDeclWithModifier chosenMemberDecl, boolean addModifier) {
        MemberDeclWithModifier oldMemberDecl = ModelUtilities.clone(chosenMemberDecl);
        MemberDeclWithModifier newMemberDecl = ModelUtilities.clone(chosenMemberDecl);

        JavaFeatureEChange<EObject, EReference> change = null;
        if (addModifier) {
            JavaInsertEReference<EObject, EObject> tmp = ReferenceFactory.eINSTANCE.createJavaInsertEReference();
            tmp.setIsCreate(true);
            JMLMemberModifier newModifier = JMLFactory.eINSTANCE.createJMLMemberModifier();
            newModifier.setModifier(getWantedModifier());
            newMemberDecl.getJmlModifiers().add(0, newModifier);
            tmp.setIndex(0);
            tmp.setNewValue(newModifier);
            change = tmp;
        } else {
            JavaRemoveEReference<EObject, EObject> tmp = ReferenceFactory.eINSTANCE.createJavaRemoveEReference();
            tmp.setIsDelete(true);
            JMLMemberModifier oldModifier = findModifier(oldMemberDecl);
            int index = newMemberDecl.getJmlModifiers().indexOf(findModifier(newMemberDecl));
            newMemberDecl.getJmlModifiers().remove(index);
            tmp.setIndex(index);
            tmp.setOldValue(oldModifier);
            change = tmp;
        }

        change.setAffectedFeature(JMLPackage.eINSTANCE.getMemberDeclWithModifier_JmlModifiers());
        change.setOldAffectedEObject(oldMemberDecl);
        change.setAffectedEObject(newMemberDecl);

        return VitruviusChangeFactory.getInstance().createGeneralChange(change, VURI.getInstance(oldMemberDecl.eResource()));
    }

}
