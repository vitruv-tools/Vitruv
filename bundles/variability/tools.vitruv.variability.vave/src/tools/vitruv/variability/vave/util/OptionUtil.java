package tools.vitruv.variability.vave.util;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.variability.vave.model.expression.BinaryExpression;
import tools.vitruv.variability.vave.model.expression.Constant;
import tools.vitruv.variability.vave.model.expression.Expression;
import tools.vitruv.variability.vave.model.expression.NaryExpression;
import tools.vitruv.variability.vave.model.expression.UnaryExpression;
import tools.vitruv.variability.vave.model.expression.Variable;
import tools.vitruv.variability.vave.model.vave.Feature;
import tools.vitruv.variability.vave.model.vave.FeatureRevision;
import tools.vitruv.variability.vave.model.vave.Option;
import tools.vitruv.variability.vave.model.vave.SystemRevision;
import tools.vitruv.variability.vave.model.vave.util.VaveSwitch;
import tools.vitruv.variability.vave.util.old.OptionComparator;

/**
 * Utility class for options.
 */
public final class OptionUtil {

	private OptionUtil() {
	}

	public static boolean compare(EObject o1, EObject o2) {
		return o1.eClass().equals(o2.eClass()) && new VaveSwitch<Boolean>() {
			@Override
			public Boolean caseFeature(Feature o2) {
				return Objects.equals(o2.getName(), ((Feature) o1).getName());
			}

			@Override
			public Boolean caseFeatureRevision(FeatureRevision o2) {
				return o2.getRevisionID() == ((FeatureRevision) o1).getRevisionID() && OptionComparator.equals(o1.eContainer(), o2.eContainer());
			}

			@Override
			public Boolean caseSystemRevision(SystemRevision o2) {
				return o2.getRevisionID() == ((SystemRevision) o1).getRevisionID();
			}

			@Override
			public Boolean defaultCase(EObject o2) {
				return o1.equals(o2);
			}
		}.doSwitch((Option) o2);
	}

	public static <T> Set<T> collect(Expression<T> expression) {
		Set<T> literals = new HashSet<>();
		if (expression instanceof NaryExpression) {
			for (Expression<T> childExpression : ((NaryExpression<T>) expression).getExpressions())
				literals.addAll(collect(childExpression));
			return literals;
		} else if (expression instanceof BinaryExpression) {
			literals.addAll(collect(((BinaryExpression<T>) expression).getLeft()));
			literals.addAll(collect(((BinaryExpression<T>) expression).getRight()));
			return literals;
		} else if (expression instanceof UnaryExpression) {
			collect(((UnaryExpression<T>) expression).getExpression());
			return literals;
		} else if (expression instanceof Variable) {
			literals.add(((Variable<T>) expression).getValue());
			return literals;
		} else if (expression instanceof Constant) {
			return literals;
		} else
			throw new IllegalStateException("Expression contains unknown operations or literals.");
	}

}
