package tools.vitruvius.domains.jml.monitorededitor.changeinjection;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import tools.vitruvius.domains.jml.language.ConcreteSyntaxHelper;
import tools.vitruvius.domains.jml.language.jML.CompilationUnit;
import tools.vitruvius.domains.jml.language.jML.Expression;
import tools.vitruvius.domains.jml.language.jML.JMLInvariantExpression;
import tools.vitruvius.domains.jml.language.jML.JMLPackage;
import tools.vitruvius.domains.jml.monitorededitor.ModelUtilities;
import tools.vitruvius.domains.jml.monitorededitor.changeinjection.Choice.EObjectToString;
import tools.vitruvius.casestudies.jml.run.monitorededitor.ChangeBuilder;
import tools.vitruvius.framework.change.description.EMFModelChange;
import tools.vitruvius.framework.change.description.GeneralChange;
import tools.vitruvius.framework.userinteraction.UserInteractionType;
import tools.vitruvius.framework.userinteraction.impl.UserInteractor;

/**
 * A change injector for JML invariants. The user can choose an invariant to change and replace its
 * expression with a new one. The resulting change does not represent a diff of the expressions but
 * a replacement of them.
 * 
 * @author Stephan Seifermann
 *
 */
public class JMLInjectChangeInvariantExpression extends JMLInjectionHandler {

    @Override
    protected boolean performChangeOn(CompilationUnit cu) {
        List<JMLInvariantExpression> invariants = new ArrayList<JMLInvariantExpression>(
                ModelUtilities.getChildrenOfType(cu, JMLInvariantExpression.class, true));
        JMLInvariantExpression choosenInvariant = chooseInvariant(invariants);

        if (choosenInvariant == null) {
            return false;
        }

        String newExpressionString = JOptionPane.showInputDialog("Enter a new expression:", ConcreteSyntaxHelper.convertToConcreteSyntax(choosenInvariant.getExpr()));
        
        if (newExpressionString == null) {
            return false;
        }
        
        Expression newExpression = null;
        try {
            newExpression = ConcreteSyntaxHelper.convertFromConcreteSyntax(newExpressionString, Expression.class);
        } catch (Exception e) {
            new UserInteractor().showMessage(UserInteractionType.MODAL, "The given expression is not valid.");
            return false;
        }

        JMLInvariantExpression invariantOld = ModelUtilities.clone(choosenInvariant);
        JMLInvariantExpression invariantNew = ModelUtilities.clone(choosenInvariant);

        invariantNew.setExpr(newExpression);
        GeneralChange change = ChangeBuilder.createUpdateChange(invariantOld, invariantNew,
                JMLPackage.eINSTANCE.getJMLExpressionHaving_Expr());

        submitChange(change);

        return true;
    }

    /**
     * Displays a choice dialog for the invariant to change. The user can choose any of all
     * available invariants based on their expression.
     * 
     * @param elements
     *            The set of invariants to choose from.
     * @return The chosen invariant or null if the user aborted the dialog.
     */
    private JMLInvariantExpression chooseInvariant(List<JMLInvariantExpression> elements) {
        JMLInvariantExpression element = Choice.chooseFromEObjects(elements,
                new EObjectToString<JMLInvariantExpression>() {
                    @Override
                    public String getTitle(JMLInvariantExpression obj) {
                        return ConcreteSyntaxHelper.convertToConcreteSyntax(obj.getExpr());
                    }
                });

        if (element == null) {
            return null;
        }
        return element;
    }

}
