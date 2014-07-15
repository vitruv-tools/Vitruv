package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.mocking;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import org.eclipse.core.runtime.IProgressMonitor;

public class EclipseProgressMonitorDummyFactory {
    public static final IProgressMonitor DUMMY = createProgressMonitorDummy();

    public static IProgressMonitor createProgressMonitorDummy() {
        IProgressMonitor result = createNiceMock(IProgressMonitor.class);
        reset(result);
        replay(result);
        return result;
    }
}
