package tools.vitruv.variability.vave.util;

import vavemodel.Conjunction;
import vavemodel.Disjunction;
import vavemodel.False;
import vavemodel.Feature;
import vavemodel.FeatureRevision;
import vavemodel.Not;
import vavemodel.Option;
import vavemodel.SystemRevision;
import vavemodel.True;
import vavemodel.Variable;
import vavemodel.util.VavemodelSwitch;

public class ExpressionPrinter extends VavemodelSwitch<String> {

	@Override
	public <T extends Option> String caseConjunction(Conjunction<T> object) {
		return doSwitch(object.getTerm().get(0)) + " AND " + doSwitch(object.getTerm().get(1));
	}

	@Override
	public <T extends Option> String caseDisjunction(Disjunction<T> object) {
		return doSwitch(object.getTerm().get(0)) + " OR " + doSwitch(object.getTerm().get(1));
	}

	@Override
	public <T extends Option> String caseNot(Not<T> object) {
		return "!" + doSwitch(object.getTerm());
	}

	@Override
	public <T extends Option> String caseVariable(Variable<T> object) {
		Option o = object.getOption();
		if (o instanceof Feature)
			return ((Feature) o).getName();
		else if (o instanceof FeatureRevision) {
			return ((Feature) ((FeatureRevision) o).eContainer()).getName() + "." + ((FeatureRevision) o).getRevisionID();
		} else if (o instanceof SystemRevision) {
			return ((SystemRevision) o).getRevisionID() + "";
		}
		return object.getOption().toString();
	}

	@Override
	public <T extends Option> String caseTrue(True<T> object) {
		return "TRUE";
	}

	@Override
	public <T extends Option> String caseFalse(False<T> object) {
		return "FALSE";
	}

}
