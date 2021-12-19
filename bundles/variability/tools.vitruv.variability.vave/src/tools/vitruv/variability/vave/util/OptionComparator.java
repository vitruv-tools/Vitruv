package tools.vitruv.variability.vave.util;

import java.util.Objects;

import org.eclipse.emf.ecore.EObject;

import vavemodel.Feature;
import vavemodel.FeatureRevision;
import vavemodel.Option;
import vavemodel.SystemRevision;
import vavemodel.util.VavemodelSwitch;

/**
 * Compares two options.
 */
public class OptionComparator extends VavemodelSwitch<Boolean> {

	private Option o1;

	private OptionComparator(Option o1) {
		super();
		this.o1 = o1;
	}

	public static Boolean equals(EObject o1, EObject o2) {
		return o1 instanceof Option && o2 instanceof Option && o1.eClass().equals(o2.eClass()) && new OptionComparator((Option) o1).doSwitch((Option) o2);
	}

	@Override
	public Boolean caseFeature(Feature feature) {
		return Objects.equals(feature.getName(), ((Feature) o1).getName());
	}

	@Override
	public Boolean caseFeatureRevision(FeatureRevision featureRevision) {
		return featureRevision.getRevisionID() == ((FeatureRevision) o1).getRevisionID() && OptionComparator.equals(o1.eContainer(), featureRevision.eContainer());
	}

	@Override
	public Boolean caseSystemRevision(SystemRevision systemRevision) {
		return systemRevision.getRevisionID() == ((SystemRevision) o1).getRevisionID();
	}

}
