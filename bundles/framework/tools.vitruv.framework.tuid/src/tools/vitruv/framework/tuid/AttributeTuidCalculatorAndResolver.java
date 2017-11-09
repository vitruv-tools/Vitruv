package tools.vitruv.framework.tuid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.change.impl.ChangeDescriptionImpl;

import tools.vitruv.framework.util.bridges.EcoreBridge;

/**
 * A {@link HierarchicalTuidCalculatorAndResolver} that creates the Tuid for
 * each {@link EObject} by finding the first attribute from the list that the
 * EObject possesses and returning "{attribute name}={attribute value}".
 *
 * @see DefaultTuidCalculatorAndResolver
 * @author Dominik Werle
 */
public class AttributeTuidCalculatorAndResolver extends HierarchicalTuidCalculatorAndResolver<EObject> {
	protected final List<String> attributeNames;

	public AttributeTuidCalculatorAndResolver(final String tuidPrefix, final String... attributeNames) {
		super(tuidPrefix);
		this.attributeNames = Arrays.asList(attributeNames);
	}

	public AttributeTuidCalculatorAndResolver(final String tuidPrefix, final List<String> attributeNames) {
		super(tuidPrefix);
		this.attributeNames = new ArrayList<String>(attributeNames);
	}

	@Override
	protected Class<EObject> getRootObjectClass() {
		return EObject.class;
	}

	@Override
	protected boolean hasId(final EObject obj, final String indidivualId) throws IllegalArgumentException {
		return indidivualId.equals(calculateIndividualTuidDelegator(obj));
	}

	@Override
	protected String calculateIndividualTuidDelegator(final EObject obj) throws IllegalArgumentException {
		for (String attributeName : this.attributeNames) {
			final String attributeValue = EcoreBridge.getStringValueOfAttribute(obj, attributeName);
			if (null != attributeValue) {
				String subTuid = (obj.eContainingFeature() == null || obj.eContainer() instanceof ChangeDescriptionImpl ? "<root>"
						: obj.eContainingFeature().getName()) + SUBDIVIDER + obj.eClass().getName() + SUBDIVIDER
								+ attributeName + "=" + attributeValue;
				return subTuid;
			} else {
				EStructuralFeature idFeature = obj.eClass().getEStructuralFeature(attributeName);
				if (idFeature != null && !obj.eIsSet(idFeature)) {
					return attributeName;
				}
			}
		}

		throw new RuntimeException(
				"None of '" + String.join("', '", this.attributeNames) + "' found for eObject '" + obj + "'");
	}

}
