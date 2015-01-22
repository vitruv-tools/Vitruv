package edu.kit.ipd.sdq.seifermann.thesis.monitorededitor.java.events;

import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.events.ChangeClassifyingEventExtension;
import edu.kit.ipd.sdq.vitruvius.framework.model.monitor.jamopputil.CompilationUnitAdapter;

/**
 * Base interface for change events, which are extensions for the
 * MonitoredEditor and require an original and changed state.
 */
public interface OriginalAndOldChangeClassifiyingEventExtension extends
		ChangeClassifyingEventExtension {

	/**
	 * 
	 * @return The compilation unit for the original state.
	 */
	CompilationUnitAdapter getOriginalCompilationUnit();

	/**
	 * @return The compilation unit for the changed state.
	 */
	CompilationUnitAdapter getChangedCompilationUnit();

}
