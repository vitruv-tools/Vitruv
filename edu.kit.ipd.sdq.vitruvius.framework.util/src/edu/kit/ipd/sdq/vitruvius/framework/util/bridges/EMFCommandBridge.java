package edu.kit.ipd.sdq.vitruvius.framework.util.bridges;

import org.eclipse.emf.common.command.Command;

// if a blackboard parameter is needed this class should be move to contracts.util.bridges
public interface EMFCommandBridge {

    Command createCommand(Runnable run);
}
