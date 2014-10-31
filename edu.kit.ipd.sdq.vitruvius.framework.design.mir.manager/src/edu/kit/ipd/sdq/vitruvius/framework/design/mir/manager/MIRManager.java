package edu.kit.ipd.sdq.vitruvius.framework.design.mir.manager;

import java.util.Collection;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Invariants;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Mapping;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Metamodel;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Responses;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.MIRManaging;

/**
 * Manages all mappings, invariants and response actions that can be added, changed and deleted by
 * the methodologist.
 * 
 * @author kramerm
 * 
 */
public class MIRManager implements MIRManaging {

    public MIRManager() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void addMapping(final Mapping mapping) {
        // TODO Auto-generated method stub

    }

    @Override
    public Mapping getMapping(final VURI... metamodelURIs) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mapping getMapping(final Metamodel... metamodels) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Mapping> getAllMappings(final Metamodel metamodel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addInvariants(final Invariants invariants) {
        // TODO Auto-generated method stub

    }

    @Override
    public Invariants getInvariants(final VURI mmURI) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addResponses(final Responses responses) {
        // TODO Auto-generated method stub

    }

    @Override
    public Responses getResponses(final VURI mmURI) {
        // TODO Auto-generated method stub
        return null;
    }
}
