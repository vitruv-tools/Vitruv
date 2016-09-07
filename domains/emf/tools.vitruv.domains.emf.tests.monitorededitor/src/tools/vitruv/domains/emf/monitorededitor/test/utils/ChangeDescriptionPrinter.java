/*******************************************************************************
 * Copyright (c) 2014 Felix Kutzner.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Felix Kutzner - initial implementation.
 ******************************************************************************/

package tools.vitruv.domains.emf.monitorededitor.test.utils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.ecore.change.ChangeKind;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;

public class ChangeDescriptionPrinter {
	public static void printChangeDescription(ChangeDescription cd) {
		System.err.println("* Saved, detected a change.");
		System.err.println("* Saved, got change desc: " + cd.toString());
		System.err.println("* CD Class: " + cd.getClass().getCanonicalName());

		EMap<EObject, EList<FeatureChange>> changes = cd.getObjectChanges();

		for (EObject obj : changes.keySet()) {
			if (obj == null) {
				System.err.println("Got null object");
				continue;
			}
			System.err.println("Object: " + obj.toString());
			EList<FeatureChange> fcList = changes.get(obj);
			for (FeatureChange fc : fcList) {
				processFeatureChange(obj, fc, "  ");
			}
		}
	}

	protected static void processFeatureChange(EObject affectedObj, FeatureChange fc, String prefix) {
		
	    System.err.println(prefix + "Feature change on feature " + fc.getFeatureName());
	    System.err.println("\t  Target value: " + fc.getValue());
	    System.err.println("\tRollback value: " + affectedObj.eGet(fc.getFeature()));
		for (ListChange lc : fc.getListChanges()) {
			if (lc.getKind() == ChangeKind.ADD_LITERAL) {
				System.err.println(prefix + "\t - Adding Children: " + lc.getValues());
			}
			else if (lc.getKind() == ChangeKind.REMOVE_LITERAL) {
				System.err.println(prefix + "\t - Deleting Children: " + lc.getValues());
			}
		}
	}
}
