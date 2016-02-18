package edu.kit.ipd.sdq.vitruvius.tests.casestudies.jmljava.unittests.utils;

import java.util.Set;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstanceDecorator;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.CorrespondenceProviding;

public class CorrespondingProvidingMock implements CorrespondenceProviding {

    @Override
    public CorrespondenceInstance getCorrespondenceInstanceCopy(VURI model1uri, VURI model2uri) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CorrespondenceInstance getCorrespondenceInstanceOriginal(VURI mmAVURI, VURI mmBVURI) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveCorrespondenceInstanceAndDecorators(CorrespondenceInstanceDecorator correspondenceInstance) {
        // TODO Auto-generated method stub

    }

    @Override
    public Set<CorrespondenceInstanceDecorator> getOrCreateAllCorrespondenceInstances(VURI mmVURI) {
        // TODO Auto-generated method stub
        return null;
    }

}
