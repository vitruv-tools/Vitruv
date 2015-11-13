package edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.helper.java.shadowcopy;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.members.ClassMethod;
import org.emftext.language.java.members.Member;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.statements.Statement;

import com.google.common.collect.BiMap;

import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLExpressionHaving;
import edu.kit.ipd.sdq.vitruvius.casestudies.jml.language.jML.JMLSpecifiedElement;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JMLTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.casestudies.jmljava.metamodels.JaMoPPTUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.ModelInstance;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUID;
import edu.kit.ipd.sdq.vitruvius.framework.meta.correspondence.datatypes.TUIDCalculatorAndResolver;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EcoreResourceBridge;

public class ShadowCopyCorrespondencesImpl implements ShadowCopyCorrespondencesWritable {

	private static final Logger LOGGER = Logger.getLogger(ShadowCopyCorrespondencesImpl.class);
    private static final TUIDCalculatorAndResolver JAMOPP_TUID_RESOLVER = new JaMoPPTUIDCalculatorAndResolver();
    private static final JMLTUIDCalculatorAndResolver JML_TUID_RESOLVER = new JMLTUIDCalculatorAndResolver();
    private final CachedModelInstanceLoader modelInstanceHelperJMLCopies = new CachedModelInstanceLoader();
    private final CachedModelInstanceLoader modelInstanceHelperShadow = new CachedModelInstanceLoader();
    private final BiMap<JMLSpecifiedElement, Member> jmlElementToShadowMember = IdentityBiMap.create();
    private final BiMap<NamedElement, NamedElement> javaToShadowElement = IdentityBiMap.create();
    private final BiMap<JMLExpressionHaving, Statement> jmlExpression2ShadowStatement = IdentityBiMap.create();
    private final BiMap<Member, ClassMethod> jmlShadowMemberToSpecContainingMethod = IdentityBiMap.create();
    private final CorrespondenceInstance ci;
    private final Collection<CompilationUnit> javaCUs;
    private final boolean useJMLCopies;

    public ShadowCopyCorrespondencesImpl(CorrespondenceInstance ci, Collection<CompilationUnit> javaCUs,
            boolean useJMLCopies) {
        this.ci = ci;
        this.javaCUs = javaCUs;
        this.useJMLCopies = useJMLCopies;
    }

    public void addCorrespondence(JMLSpecifiedElement jml, Member java) {
            jmlElementToShadowMember.put(jml, java);
    }

    @Override
    public void addCorrespondence(JMLExpressionHaving jml, Statement java) {
            jmlExpression2ShadowStatement.put(jml, java);
    }

    @Override
    public Statement get(JMLExpressionHaving jml) {
            return jmlExpression2ShadowStatement.get(jml);
    }

    @Override
    public JMLExpressionHaving get(Statement stmt) {
            return jmlExpression2ShadowStatement.inverse().get(stmt);
    }

    @Override
    public JMLSpecifiedElement get(Member javaMember) {
            return jmlElementToShadowMember.inverse().get(javaMember);
    }

    @Override
    public Member getMember(JMLSpecifiedElement jml) {
            return jmlElementToShadowMember.get(jml);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends NamedElement> T getShadow(T original) {
            if (!javaToShadowElement.containsKey(original)) {
                String originalTUID = ci.calculateTUIDFromEObject(original).toString();
                ModelInstance shadowModelInstance = modelInstanceHelperShadow.loadModelInstance(original.eResource()
                        .getURI());
                EObject shadowModelRoot = shadowModelInstance.getUniqueTypedRootEObject(CompilationUnit.class);
                EObject resolvedObject = JAMOPP_TUID_RESOLVER.resolveEObjectFromRootAndFullTUID(shadowModelRoot,
                        originalTUID);
                if (javaToShadowElement.containsValue(resolvedObject)) {
                    return (T) resolvedObject;
                }
                javaToShadowElement.put(original, (T) resolvedObject);
            }
            return (T) javaToShadowElement.get(original);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends NamedElement> T getOriginal(T shadow) {
            if (!javaToShadowElement.inverse().containsKey(shadow)) {
                String shadowTUID = ci.calculateTUIDFromEObject(shadow).toString();
                CompilationUnit originalRoot = null;
                for (CompilationUnit cu : javaCUs) {
                    if (cu.eResource().equals(shadow.eResource())) {
                        originalRoot = cu;
                    }
                }
                EObject resolvedObject = JAMOPP_TUID_RESOLVER.resolveEObjectFromRootAndFullTUID(originalRoot,
                        shadowTUID);
                javaToShadowElement.inverse().put(shadow, (T) resolvedObject);
            }
            return (T) javaToShadowElement.inverse().get(shadow);

    }

    @Override
    public CachedModelInstanceLoader getShadowModelInstanceLoader() {
        return this.modelInstanceHelperShadow;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends EObject> Set<T> getCorrespondingEObjectsByType(EObject eObject, Class<T> type) {
        Set<T> correspondingElements = ci.getCorrespondingEObjectsByType(eObject, type);
        if (correspondingElements == null || !useJMLCopies) {
            return correspondingElements;
        }

        Set<T> copies = new HashSet<T>();
        for (T correspondingElement : correspondingElements) {
            ModelInstance mi = modelInstanceHelperJMLCopies
                    .loadModelInstance(correspondingElement.eResource().getURI());
            T correspondingElementCopy = (T) ci.resolveEObjectFromRootAndFullTUID(mi.getUniqueRootEObject(),
                    ci.calculateTUIDFromEObject(correspondingElement).toString());
            copies.add(correspondingElementCopy);
        }
        return copies;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends EObject> T getJMLElement(T original) {
        TUID originalTUID = ci.calculateTUIDFromEObject(original);
        if (!useJMLCopies) {
            return (T) ci.resolveEObjectFromTUID(originalTUID);
        }

        ModelInstance mi = modelInstanceHelperJMLCopies.loadModelInstance(original.eResource().getURI());
        return (T) JML_TUID_RESOLVER.resolveEObjectFromRootAndFullTUID(mi.getUniqueRootEObject(),
                originalTUID.toString());
    }

    @Override
    public CachedModelInstanceLoader getShadowModelInstanceLoaderJML() {
        return modelInstanceHelperJMLCopies;
    }

    @Override
    public Collection<Setting> findReferencesToJavaObject(EObject obj) {
    	LOGGER.debug("Starting to find references to " + obj);
    	long timestamp1 = System.currentTimeMillis();
        Collection<Setting> tmp = modelInstanceHelperShadow.findObjectReferences(obj);
        LOGGER.debug("Finished finding references. Duration: " + (System.currentTimeMillis() - timestamp1)/1000 + " s.");
        return tmp;
    }

    @Override
    public void addCorrespondence(Member shadowMember, ClassMethod specContainingMember) {
        jmlShadowMemberToSpecContainingMethod.put(shadowMember, specContainingMember);
        //TODO this feature should be documented as well as the limitations
        if (shadowMember instanceof Method) {
            Method shadowMethod = (Method) shadowMember;
            for (int i = 0; i < shadowMethod.getParameters().size(); ++i) {
                shadowMethod.getParameters().get(i).eAdapters()
                        .add(new MethodParameterObserver(specContainingMember.getParameters().get(i)));
            }
        }
    }

    @Override
    public Set<Entry<Member, ClassMethod>> getSpecContainingMethods() {
        return jmlShadowMemberToSpecContainingMethod.entrySet();
    }

    @Override
    public ClassMethod getSpecContainingMethod(Member shadowMember) {
        return jmlShadowMemberToSpecContainingMethod.get(shadowMember);
    }
    
    
    
    
    /**
     * Observer for changes on method parameters. It contains a corresponding parameter to which all
     * changes shall be applied. At the moment not all changes are implemented.
     * 
     * @author Stephan Seifermann
     *
     */
    private static class MethodParameterObserver extends AdapterImpl {

        private static final Logger LOGGER = Logger.getLogger(MethodParameterObserver.class);
        private final Parameter correspondingParameter;
        private volatile boolean processingChange = false;

        /**
         * Constructor.
         * @param correspondingParameter The parameter on which the changes shall be applied.
         */
        public MethodParameterObserver(Parameter correspondingParameter) {
            this.correspondingParameter = correspondingParameter;
        }

        @Override
        public void notifyChanged(Notification msg) {
            if (processingChange) {
                return;
            }
            processingChange = true;
            switch (msg.getEventType()) {
            case Notification.SET:
                applySet((EStructuralFeature) msg.getFeature(), msg.getOldValue(), msg.getNewValue());
                break;
            case Notification.UNSET:
                applyUnset((EStructuralFeature) msg.getFeature());
                break;
            default:
                LOGGER.trace("Ignoring change on parameter " + ((Parameter) getTarget()).getName() + ".");
                // TODO implement more
            }
            processingChange = false;
        }

        /**
         * Applies a set change
         * @param feature The changed feature
         * @param oldValue The old value of the feature
         * @param newValue The new value of the feature
         */
        private void applySet(EStructuralFeature feature, Object oldValue, Object newValue) {
            LOGGER.debug("Updating feature " + feature.getName() + " from " + oldValue + " to " + newValue + ".");
            correspondingParameter.eSet(feature, newValue);
        }

        /**
         * Applies an unset change
         * @param feature The changed feature
         */
        private void applyUnset(EStructuralFeature feature) {
            LOGGER.debug("Unsetting feature " + feature.getName() + ".");
            correspondingParameter.eUnset(feature);
        }

    }




	@Override
	public void saveAll() throws IOException {
		for (ModelInstance mi : modelInstanceHelperShadow.getAllCachedModelInstances()) {
			EcoreResourceBridge.saveResource(mi.getResource());
		}
	}

}
