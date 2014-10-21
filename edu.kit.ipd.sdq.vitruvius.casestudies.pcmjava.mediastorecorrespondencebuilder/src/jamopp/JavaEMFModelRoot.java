package jamopp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.commons.Commentable;
import org.emftext.language.java.commons.NamedElement;
import org.emftext.language.java.containers.CompilationUnit;
import org.emftext.language.java.instantiations.ExplicitConstructorCall;
import org.emftext.language.java.instantiations.NewConstructorCall;
import org.emftext.language.java.members.Constructor;
import org.emftext.language.java.references.ElementReference;
import org.emftext.language.java.references.ReferenceableElement;
import org.emftext.language.java.types.Type;
import org.emftext.language.java.types.TypeReference;

import utils.EMFUtils;
import utils.JamoppModelUtils;

/**
 * @author Jonas Kunz
 * 
 */
public class JavaEMFModelRoot {

    private static final Logger LOGGER = Logger.getLogger(JavaEMFModelRoot.class);

    private final ArrayList<CompilationUnit> relevantUnits;

    private Map<ReferenceableElement, List<ElementReference>> elemReferenceTable;
    private Map<Type, List<TypeReference>> typeReferenceTable;

    public JavaEMFModelRoot(List<CompilationUnit> relevantUnits) {
        this.relevantUnits = new ArrayList<CompilationUnit>();
        this.relevantUnits.addAll(relevantUnits);

        buildReferencesTable();
    }

    /**
     * Scans for all references in the compilation units and maps them to their target elements
     */
    private void buildReferencesTable() {

        this.elemReferenceTable = new HashMap<ReferenceableElement, List<ElementReference>>();
        this.typeReferenceTable = new HashMap<Type, List<TypeReference>>();

        for (CompilationUnit unit : this.relevantUnits) {
            LOGGER.info("Building references table for " + unit.getName() + " ...");
            List<ElementReference> ereferences = EMFUtils.listDirectAndIndirectContents(unit, ElementReference.class);
            for (ElementReference ereference : ereferences) {
                ReferenceableElement target = ereference.getTarget();
                if (target != null) {
                    List<ElementReference> referencesToTarget = this.elemReferenceTable.get(target);
                    if (referencesToTarget == null) {
                        referencesToTarget = new ArrayList<ElementReference>();
                        this.elemReferenceTable.put(target, referencesToTarget);
                    }
                    referencesToTarget.add(ereference);
                }
            }

            List<TypeReference> treferences = EMFUtils.listDirectAndIndirectContents(unit, TypeReference.class);
            for (TypeReference treference : treferences) {
                Type target = treference.getTarget();
                if (target != null) {
                    List<TypeReference> referencesToTarget = this.typeReferenceTable.get(target);
                    if (referencesToTarget == null) {
                        referencesToTarget = new ArrayList<TypeReference>();
                        this.typeReferenceTable.put(target, referencesToTarget);
                    }

                    referencesToTarget.add(treference);
                }
            }
        }
    }

    /**
     * Checks if the given element is in the compilationunits of interest.
     * 
     * @param element
     *            the element to check
     * @return true if part of these units, false otherwise
     */
    public boolean isElementInScope(Commentable element) {
        return this.relevantUnits.contains(element.getContainingCompilationUnit());
    }

    /**
     * 
     * @return all classifiers in the compilation units of interest.
     */
    public List<Classifier> getAllClassifiers() {
        ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
        for (CompilationUnit unit : this.relevantUnits) {
            classifiers.addAll(unit.getClassifiers());
        }
        return classifiers;
    }

    public List<CompilationUnit> getAllCompilationUnits() {
        return Collections.unmodifiableList(this.relevantUnits);
    }

    /**
     * Searches for an element by its fully qualified name.
     * 
     * @param name
     *            the name of the object
     * @return the element foudn with the given name.
     * 
     * 
     */
    public NamedElement getElementByFullName(String name) {
        for (CompilationUnit unit : this.relevantUnits) {
            String packageStr = JamoppModelUtils.getCompilationUnitPackage(unit);
            if (name.startsWith(packageStr + ".")) {
                String suffix = name.substring((packageStr + ".").length());
                for (ConcreteClassifier class1 : unit.getClassifiers()) {
                    NamedElement found = JamoppModelUtils.findElementByName(class1, suffix);
                    if (found != null) {
                        return found;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Searches for all elements matching the given fully qualified name.
     * 
     * @param name
     *            the name to scan for.
     * @return a lsit of al lfound elements.
     */
    public List<NamedElement> getAllElementsByFullName(String name) {
        List<NamedElement> elements = new ArrayList<NamedElement>();
        for (CompilationUnit unit : this.relevantUnits) {
            String packageStr = JamoppModelUtils.getCompilationUnitPackage(unit);
            if (name.startsWith(packageStr + ".")) {
                String suffix = name.substring((packageStr + ".").length());
                for (ConcreteClassifier class1 : unit.getClassifiers()) {
                    JamoppModelUtils.findAllElementsByName(class1, suffix, elements);
                }
            }
        }
        return elements;
    }

    /**
     * Finds all elements of the given type containing references to the given target.
     * 
     * @param target
     *            The target
     * @param type
     *            the type of the elements to find
     * @return
     */
    public <T extends Commentable> Set<T> getAllReferencingElementsOfType(Commentable target, Class<T> type) {
        if (target instanceof Constructor) {
            return getReferencingElementsToConstructorOfType((Constructor) target, type);
        } else {
            Set<T> referencingObjs = new HashSet<T>();
            List<ElementReference> ereferences = this.elemReferenceTable.get(target);
            if (ereferences != null) {
                for (ElementReference ereference : ereferences) {
                    EMFUtils.putElementOrParentOfTypeInCollection(ereference, referencingObjs, type);
                }
            }

            List<TypeReference> treferences = this.typeReferenceTable.get(target);
            if (treferences != null) {
                for (TypeReference treference : treferences) {
                    EMFUtils.putElementOrParentOfTypeInCollection(treference, referencingObjs, type);
                }
            }
            return referencingObjs;
        }

    }

    private <T extends Commentable> Set<T> getReferencingElementsToConstructorOfType(Constructor target, Class<T> type) {

        Set<T> referencingObjs = new HashSet<T>();

        Classifier classOfConstructor = target.getParentByType(Classifier.class);
        List<TypeReference> treferences = this.typeReferenceTable.get(classOfConstructor);
        if (treferences != null) {
            for (TypeReference tref : treferences) {
                // we are only interested in new-cosntructor-calls to this class
                NewConstructorCall call = tref.getParentByType(NewConstructorCall.class);
                if (call != null) {
                    if (JamoppModelUtils.doesCallMatchSignature(target, call)) {
                        // add the cal lto the references list
                        EMFUtils.putElementOrParentOfTypeInCollection(call, referencingObjs, type);
                    }
                }
            }
        }
        // check for super() cosntructor calls
        if (EMFUtils.isClassSubclass(Constructor.class, type)) {
            // find all subclasses
            Set<org.emftext.language.java.classifiers.Class> subClasses = this.getAllReferencingElementsOfType(
                    classOfConstructor, org.emftext.language.java.classifiers.Class.class);
            for (org.emftext.language.java.classifiers.Class subClass : subClasses) {
                if (subClass.getSuperClass() == classOfConstructor) {
                    List<ExplicitConstructorCall> explicitCalls = EMFUtils.listDirectAndIndirectContents(subClass,
                            ExplicitConstructorCall.class);
                    for (ExplicitConstructorCall call : explicitCalls) {
                        if (JamoppModelUtils.doesCallMatchSignature(target, call)) {
                            EMFUtils.putElementOrParentOfTypeInCollection(call, referencingObjs, type);
                        }
                    }
                }
            }
        }

        return referencingObjs;
    }

    /**
     * Finds all elements of the given type who are referenced by the given source.
     * 
     * @param source
     *            The source (container of the references)
     * @param type
     *            the type of the elements to find
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Commentable> Set<T> getAllReferencedElementsOfType(Commentable source, Class<T> type) {

        Set<T> referencedObjs = new HashSet<T>();

        if (EMFUtils.isClassSubclass(Constructor.class, type)) {
            return (Set<T>) getAllReferencedElementsOfTypeConstructor(source, type.asSubclass(Constructor.class));
        } else {

            List<ElementReference> references = EMFUtils.listDirectAndIndirectContents(source, ElementReference.class);

            for (ElementReference reference : references) {
                ReferenceableElement target = reference.getTarget();
                if (type.isInstance(target) && isElementInScope(target)) {
                    referencedObjs.add((T) target);
                }
            }
            List<TypeReference> treferences = EMFUtils.listDirectAndIndirectContents(source, TypeReference.class);
            for (TypeReference reference : treferences) {
                Type target = reference.getTarget();
                if (type.isInstance(target) && isElementInScope(target)) {
                    referencedObjs.add((T) target);
                }
            }
        }

        return referencedObjs;
    }

    private <T extends Constructor> Set<T> getAllReferencedElementsOfTypeConstructor(Commentable source, Class<T> type) {
        Set<T> referencedObjs = new HashSet<T>();

        List<TypeReference> treferences = EMFUtils.listDirectAndIndirectContents(source, TypeReference.class);
        for (TypeReference reference : treferences) {
            Type targetClass = reference.getTarget();
            if (targetClass != null && isElementInScope(targetClass)) {
                NewConstructorCall newCall = reference.getParentByType(NewConstructorCall.class);
                if (newCall != null) {
                    // find the matching Constructor
                    List<T> constructors = EMFUtils.listDirectAndIndirectContents(targetClass, type);
                    // add the matching ones
                    for (T constr : constructors) {
                        if (JamoppModelUtils.doesCallMatchSignature(constr, newCall)) {
                            referencedObjs.add(constr);
                        }
                    }
                }
            }
        }
        List<ExplicitConstructorCall> expCalls = EMFUtils.listDirectAndIndirectContents(source,
                ExplicitConstructorCall.class);
        for (ExplicitConstructorCall expCall : expCalls) {
            org.emftext.language.java.classifiers.Class srcClass = expCall
                    .getParentByType(org.emftext.language.java.classifiers.Class.class);
            if (isElementInScope(srcClass)) {
                List<T> constructors = EMFUtils.listDirectAndIndirectContents(srcClass.getSuperClass(), type);
                // add the matching ones
                for (T constr : constructors) {
                    if (JamoppModelUtils.doesCallMatchSignature(constr, expCall)) {
                        referencedObjs.add(constr);
                    }
                }
            }
        }

        return referencedObjs;
    }

}
