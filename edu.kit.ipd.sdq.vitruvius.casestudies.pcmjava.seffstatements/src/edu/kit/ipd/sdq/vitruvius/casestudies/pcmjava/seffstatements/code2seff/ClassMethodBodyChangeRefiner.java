//package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.seffstatements.code2seff;
//
//import org.emftext.language.java.members.ClassMethod;
//import org.somox.gast2seff.visitors.InterfaceOfExternalCallFinding;
//
//import de.uka.ipd.sdq.pcm.repository.BasicComponent;
//import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.CompositeChangeRefinerResultTransformation;
//import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.synchronizers.java.compositerefiners.JavaMethodBodyChangedChangeRefiner;
//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CompositeChange;
//import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
//import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.EFeatureChange;
//
//public class ClassMethodBodyChangeRefiner extends JavaMethodBodyChangedChangeRefiner {
//
//    public ClassMethodBodyChangeRefiner() {
//        super(null);
//    }
//
//    @Override
//    public CompositeChangeRefinerResultTransformation refine(final CompositeChange change) {
//        if (!this.match(change)) {
//            throw new IllegalArgumentException();
//        }
//        final EMFModelChange emfChange = (EMFModelChange)change.getChanges().get(0);
//
//        final EFeatureChange eFeatureChange = (EFeatureChange)emfChange.getEChange();
//
//        final ClassMethod oldMethod = (ClassMethod) eFeatureChange.getOldAffectedEObject();
//        final ClassMethod newMethod = (ClassMethod) eFeatureChange.getNewAffectedEObject();
//
//        return new CompositeChangeRefinerResultTransformation(methodBodyChanged):
//    }
// }
