package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.pcmjamoppenforcer;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;

import de.uka.ipd.sdq.pcm.repository.Interface;
import de.uka.ipd.sdq.pcm.repository.OperationInterface;
import de.uka.ipd.sdq.pcm.repository.OperationSignature;
import de.uka.ipd.sdq.pcm.repository.ProvidedRole;
import de.uka.ipd.sdq.pcm.repository.RepositoryComponent;
import edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers.PCMRepositorytoJaMoPPInvariantEnforcer;

/**
 * Structural Conflict! Conflict arises when two different interfaces offer the same method and a
 * component implements both. This is permitted in PCM and in Java, but might lead to errors like
 * unwanted ambiguity, wrong expression of semantics in Java Code or alike. To solve this, this
 * class splits a single component as long any ambiguous method signatures are present.
 *
 * @author Johannes Hoor
 *
 */
public class PCMtoJaMoPPComponentInterfaceImplementsAmbiguity extends PCMRepositorytoJaMoPPInvariantEnforcer {
    private final ArrayList<Interface[]> candidates; // OLD

    private int ctr = 0;

    /**
     * Instantiates a new PC mto ja mo pp component interface implements ambiguity.
     */
    public PCMtoJaMoPPComponentInterfaceImplementsAmbiguity() {
        this.candidates = new ArrayList<>();

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.FixedInvariantEnforcer#enforceInvariant
     * ()
     */
    @Override
    public void enforceInvariant() {
        if (!this.identifyInterfaceCandidates()) {
            return;
        }
        // identifyConflictingComponents();
        this.renameConflictingInterfaces();

    }

    /**
     * Trivial algorithm that renames ALL signatures of a interface, if corresponding interface
     * exists that has at least one identical signature. Does not take overloading into account!
     */
    private void renameConflictingInterfaces() {
        final ArrayList<Interface> visitedInterfaces = new ArrayList<>();
        final Iterator<Interface[]> iter = this.candidates.iterator();
        while (iter.hasNext()) {
            final Interface[] ifaces = iter.next();
            final Interface target = ifaces[1];

            if (visitedInterfaces.contains(target)) {
                System.out.println("already in");
                break;
            }

            final EStructuralFeature sig = target.eClass().getEAllStructuralFeatures().get(6);
            final EObjectContainmentWithInverseEList list = (EObjectContainmentWithInverseEList) target.eGet(sig);
            for (int i = 0; i < list.size(); i++) {
                final OperationSignature opsig = (OperationSignature) list.get(i);
                final String mname = opsig.getEntityName();

                opsig.setEntityName("RNDUPLICATE_" + this.ctr + opsig.getEntityName());
                this.ctr++;

            }
            visitedInterfaces.add(target);
            iter.remove();
        }

    }

    /**
     *
     * CURRENTLY NOT IN USE as of 01.11.14
     *
     * Check if two (or more) interface candidates are provided by the same component. If no
     * component in this model has a conflict, the invariant cannot be violated.
     *
     *
     * @return t/f
     */
    private boolean identifyConflictingComponents() {
        final ArrayList<Interface> providingInterfaces = new ArrayList<>();
        for (final RepositoryComponent s : this.root.getComponents__Repository()) {

            final EList<ProvidedRole> list = s.getProvidedRoles_InterfaceProvidingEntity();

            // get InterfaceImpl
            final EStructuralFeature feature = list.get(0).eClass().getEAllStructuralFeatures().get(3);
            for (final ProvidedRole r : list) {

                final OperationInterface impl = (OperationInterface) r.eGet(feature);
                System.out.println("-->" + impl.getEntityName());
                providingInterfaces.add(impl);
            }
        }

        return false;
    }

    /**
     * Two (or more) interfaces must have at least 1 common method signature. If not, no conflict
     * can occur. An interface candidate is an interface that has at least one corresponding
     * interface with at least on identical method signature.
     *
     * @return true, if successful
     */
    private boolean identifyInterfaceCandidates() {
        final ArrayList<Interface> allInterfaces = new ArrayList<>();
        for (final Interface s : this.root.getInterfaces__Repository()) {
            allInterfaces.add(s);
        }

        if (allInterfaces.size() == 0) {
            return false;
        }

        final EStructuralFeature sig = allInterfaces.get(0).eClass().getEAllStructuralFeatures().get(6);
        for (int j = 0; j < allInterfaces.size(); j++) {
            final Interface s = allInterfaces.get(j);
            final EObjectContainmentWithInverseEList list = (EObjectContainmentWithInverseEList) s.eGet(sig);
            for (int i = 0; i < list.size(); i++) {
                final OperationSignature opsig = (OperationSignature) list.get(i);
                final String mname = opsig.getEntityName();

                for (int k = j + 1; k < allInterfaces.size(); k++) {
                    final Interface tmp = allInterfaces.get(k);
                    final EObjectContainmentWithInverseEList tmplist = (EObjectContainmentWithInverseEList) tmp
                            .eGet(sig);
                    for (int m = 0; m < tmplist.size(); m++) {
                        final OperationSignature tmpopsig = (OperationSignature) tmplist.get(m);
                        final String tmpmname = tmpopsig.getEntityName();
                        if (mname.equals(tmpmname)) {
                            // this.logger.info("-identified@interface " + tmp.getEntityName());
                            // System.out.println("MATCHING SIGNATURES " + s.getEntityName() + ":" +
                            // mname + " -AND- " + tmp.getEntityName() + ":" + tmpmname);
                            final Interface[] conflictPair = { s, tmp };
                            this.candidates.add(conflictPair);
                        }
                    }
                }

            }
        }
        if (this.candidates.size() > 0) {
            return true;
        }
        return false;
    }

}
