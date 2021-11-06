package tools.vitruv.variability.vave.tests.comparator;

/*******************************************************************************
 * Copyright (c) 2013
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Benjamin Klatt - initial API and implementation and/or initial documentation
 *******************************************************************************/

import org.eclipse.emf.compare.match.IMatchEngine;
import org.eclipse.emf.compare.match.impl.MatchEngineFactoryImpl;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.compare.utils.IEqualityHelper;

/**
 * Factory specific for hierarchical match engines.
 */
public class HierarchicalMatchEngineFactory extends MatchEngineFactoryImpl {

	/** The equality helper to wire with the comparison model. */
	private IEqualityHelper equalityHelper;
	/** The equality strategy to use for element matching. */
	private SimilarityChecker similarityChecker;

	/**
	 * Constructor to set the required match engine dependencies.
	 * 
	 * @param equalityHelper   The equality helper to wire with the comparison model.
	 * @param equalityStrategy The equality strategy to use for element matching.
	 * @param ignoreStrategy   The strategy to use to ignore elements.
	 * @param resourceMatcher  The resource matcher to identify matching resources.
	 */
	public HierarchicalMatchEngineFactory(IEqualityHelper equalityHelper, SimilarityChecker similarityChecker) {
		this.equalityHelper = equalityHelper;
		this.similarityChecker = similarityChecker;
	}

	@Override
	public boolean isMatchEngineFactoryFor(IComparisonScope scope) {
		return true;
	}

	@Override
	public IMatchEngine getMatchEngine() {
		return new HierarchicalMatchEngine(equalityHelper, similarityChecker);
	}

}
