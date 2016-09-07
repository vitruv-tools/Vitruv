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

package tools.vitruv.domains.emf.monitorededitor.test.mocking;

import static org.easymock.EasyMock.createNiceMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class EclipseResourceDeltaMockFactory {
    public static IResourceDelta createResourceDeltaMock(final int kind, final String resourcePath) {
        final IPath path = new org.eclipse.core.runtime.Path(resourcePath);
        final IResource resource = createNiceMock(IResource.class);
        reset(resource);
        expect(resource.getFileExtension()).andReturn(path.getFileExtension()).anyTimes();
        expect(resource.getFullPath()).andReturn(path).anyTimes();
        replay(resource);

        return new IResourceDelta() {

            @SuppressWarnings("unchecked")
            @Override
            public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
                throw new UnsupportedOperationException();
            }

            @Override
            public IResource getResource() {
                return resource;
            }

            @Override
            public IPath getProjectRelativePath() {
                throw new UnsupportedOperationException();
            }

            @Override
            public IPath getMovedToPath() {
                throw new UnsupportedOperationException();
            }

            @Override
            public IPath getMovedFromPath() {
                throw new UnsupportedOperationException();
            }

            @Override
            public IMarkerDelta[] getMarkerDeltas() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getKind() {
                return kind;
            }

            @Override
            public IPath getFullPath() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int getFlags() {
                throw new UnsupportedOperationException();
            }

            @Override
            public IResourceDelta[] getAffectedChildren(int kindMask, int memberFlags) {
                throw new UnsupportedOperationException();
            }

            @Override
            public IResourceDelta[] getAffectedChildren(int kindMask) {
                throw new UnsupportedOperationException();
            }

            @Override
            public IResourceDelta[] getAffectedChildren() {
                throw new UnsupportedOperationException();
            }

            @Override
            public IResourceDelta findMember(IPath path) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void accept(IResourceDeltaVisitor visitor, int memberFlags) throws CoreException {
                visitor.visit(this);
            }

            @Override
            public void accept(IResourceDeltaVisitor visitor, boolean includePhantoms) throws CoreException {
                visitor.visit(this);
            }

            @Override
            public void accept(IResourceDeltaVisitor visitor) throws CoreException {
                visitor.visit(this);
            }
        };
    }
}
