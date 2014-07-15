package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.test.mocking;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import org.eclipse.core.resources.IProject;

public class EclipseProjectDummyFactory {
    public static final IProject DUMMY = createDummyIProjectImpl();

    public static IProject createDummyIProjectImpl() {
        IProject result = createNiceMock(IProject.class);
        reset(result);
        replay(result);
        return result;
    }
}
