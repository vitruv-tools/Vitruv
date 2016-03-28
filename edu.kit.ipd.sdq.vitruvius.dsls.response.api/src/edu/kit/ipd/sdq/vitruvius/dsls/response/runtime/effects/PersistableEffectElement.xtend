package edu.kit.ipd.sdq.vitruvius.dsls.response.runtime.effects

import org.eclipse.xtext.xbase.lib.Functions.Function0

interface PersistableEffectElement {
	public static class PersistenceInformation {
		public final boolean pathIsSourceRelative;
		public final Function0<String> pathSupplier; 
		
		public new(boolean pathIsSourceRelative, Function0<String> pathSupplier) {
			this.pathIsSourceRelative = pathIsSourceRelative;
			this.pathSupplier = pathSupplier;
		}
	}
	
	public def void setPersistenceInformation(PersistenceInformation persistenceInformation);
}