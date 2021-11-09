package tools.vitruv.variability.vave.tests.umlcomp;

import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.IEqualityHelper;

public class UMLMatchEngineFactory extends MatchEngineFactoryImpl {

	private IEqualityHelper equalityHelper;

	public UMLMatchEngineFactory(IEqualityHelper equalityHelper) {
		this.equalityHelper = equalityHelper;
	}

	@Override
	public boolean isMatchEngineFactoryFor(IComparisonScope scope) {
		return true;
	}

	@Override
	public IMatchEngine getMatchEngine() {
		return new UMLMatchEngine(equalityHelper);
	}

}
