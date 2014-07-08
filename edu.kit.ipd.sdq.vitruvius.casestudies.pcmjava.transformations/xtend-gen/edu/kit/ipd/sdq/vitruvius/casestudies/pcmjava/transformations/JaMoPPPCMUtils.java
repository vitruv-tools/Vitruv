package edu.kit.ipd.sdq.vitruvius.casestudies.pcmjava.transformations;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceInstance;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.emftext.language.java.classifiers.Classifier;
import org.emftext.language.java.containers.CompilationUnit;

@SuppressWarnings("all")
public class JaMoPPPCMUtils {
  private JaMoPPPCMUtils() {
  }
  
  public static org.emftext.language.java.containers.Package getContainingPackageFromCorrespondenceInstance(final Classifier classifier, final CorrespondenceInstance correspondenceInstance) {
    CompilationUnit _containingCompilationUnit = classifier.getContainingCompilationUnit();
    String namespace = _containingCompilationUnit.getNamespacesAsString();
    boolean _endsWith = namespace.endsWith("$");
    if (_endsWith) {
      int _length = namespace.length();
      int _minus = (_length - 1);
      String _substring = namespace.substring(0, _minus);
      namespace = _substring;
    }
    boolean _endsWith_1 = namespace.endsWith(".");
    boolean _not = (!_endsWith_1);
    if (_not) {
      namespace = (namespace + ".");
    }
    final String finalNamespace = namespace;
    Set<org.emftext.language.java.containers.Package> packagesWithCorrespondences = correspondenceInstance.<org.emftext.language.java.containers.Package>getAllEObjectsInCorrespondencesWithType(org.emftext.language.java.containers.Package.class);
    final Function1<org.emftext.language.java.containers.Package, Boolean> _function = new Function1<org.emftext.language.java.containers.Package, Boolean>() {
      public Boolean apply(final org.emftext.language.java.containers.Package pack) {
        String _namespacesAsString = pack.getNamespacesAsString();
        return Boolean.valueOf(finalNamespace.equals(_namespacesAsString));
      }
    };
    final Iterable<org.emftext.language.java.containers.Package> packagesWithNamespace = IterableExtensions.<org.emftext.language.java.containers.Package>filter(packagesWithCorrespondences, _function);
    boolean _and = false;
    boolean _and_1 = false;
    boolean _notEquals = (!Objects.equal(null, packagesWithNamespace));
    if (!_notEquals) {
      _and_1 = false;
    } else {
      int _size = IterableExtensions.size(packagesWithNamespace);
      boolean _lessThan = (0 < _size);
      _and_1 = _lessThan;
    }
    if (!_and_1) {
      _and = false;
    } else {
      Iterator<org.emftext.language.java.containers.Package> _iterator = packagesWithNamespace.iterator();
      org.emftext.language.java.containers.Package _next = _iterator.next();
      boolean _notEquals_1 = (!Objects.equal(null, _next));
      _and = _notEquals_1;
    }
    if (_and) {
      Iterator<org.emftext.language.java.containers.Package> _iterator_1 = packagesWithNamespace.iterator();
      return _iterator_1.next();
    }
    return null;
  }
  
  public static EAttribute getAttributeByNameFromEObject(final String attributeName, final EObject eObject) {
    EClass _eClass = eObject.eClass();
    EList<EAttribute> _eAllAttributes = _eClass.getEAllAttributes();
    final Function1<EAttribute, Boolean> _function = new Function1<EAttribute, Boolean>() {
      public Boolean apply(final EAttribute attribute) {
        String _name = attribute.getName();
        return Boolean.valueOf(_name.equalsIgnoreCase(attributeName));
      }
    };
    Iterable<EAttribute> _filter = IterableExtensions.<EAttribute>filter(_eAllAttributes, _function);
    Iterator<EAttribute> _iterator = _filter.iterator();
    return _iterator.next();
  }
}
