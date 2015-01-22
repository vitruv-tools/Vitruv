package edu.kit.ipd.sdq.vitruvius.casestudies.jml.run.monitorededitor.changeinjection;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.CompilationUnit;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLFactory;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLMemberModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLPackage;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSpecMemberModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclWithModifier;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.MemberDeclaration;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.run.monitorededitor.changeinjection.Choice.EObjectToString;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.changesynchronizer.ModelUtilities;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentFactory;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.CreateNonRootEObjectInList;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.DeleteNonRootEObjectInList;

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

        EMFModelChange change = createChange(chosenMemberDecl, addMofifier);

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
    private EMFModelChange createChange(MemberDeclWithModifier chosenMemberDecl, boolean addModifier) {
        MemberDeclWithModifier oldMemberDecl = ModelUtilities.clone(chosenMemberDecl);
        MemberDeclWithModifier newMemberDecl = ModelUtilities.clone(chosenMemberDecl);

        EFeatureChange<EReference> change = null;
        if (addModifier) {
            CreateNonRootEObjectInList<EObject> tmp = ContainmentFactory.eINSTANCE.createCreateNonRootEObjectInList();
            JMLMemberModifier newModifier = JMLFactory.eINSTANCE.createJMLMemberModifier();
            newModifier.setModifier(getWantedModifier());
            newMemberDecl.getJmlModifiers().add(0, newModifier);
            tmp.setIndex(0);
            tmp.setNewValue(newModifier);
            change = tmp;
        } else {
            DeleteNonRootEObjectInList<EObject> tmp = ContainmentFactory.eINSTANCE.createDeleteNonRootEObjectInList();
            JMLMemberModifier oldModifier = findModifier(oldMemberDecl);
            int index = newMemberDecl.getJmlModifiers().indexOf(findModifier(newMemberDecl));
            newMemberDecl.getJmlModifiers().remove(index);
            tmp.setIndex(index);
            tmp.setOldValue(oldModifier);
            change = tmp;
        }

        change.setAffectedFeature(JMLPackage.eINSTANCE.getMemberDeclWithModifier_JmlModifiers());
        change.setOldAffectedEObject(oldMemberDecl);
        change.setNewAffectedEObject(newMemberDecl);

        return new EMFModelChange(change, VURI.getInstance(oldMemberDecl.eResource()));
    }

}
