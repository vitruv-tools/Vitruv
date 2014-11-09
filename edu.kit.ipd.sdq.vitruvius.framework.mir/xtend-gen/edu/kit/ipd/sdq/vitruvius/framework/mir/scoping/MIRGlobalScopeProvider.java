package edu.kit.ipd.sdq.vitruvius.framework.mir.scoping;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.inject.Inject;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScope;
import org.eclipse.xtext.scoping.impl.AbstractGlobalScopeProvider;
import org.eclipse.xtext.scoping.impl.SimpleScope;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class MIRGlobalScopeProvider extends AbstractGlobalScopeProvider {
  @Inject
  private IQualifiedNameConverter qualifiedNameConverter;
  
  private IScope packageScope = null;
  
  public IScope getScope(final Resource resource, final EReference reference) {
    return super.getScope(resource, reference);
  }
  
  private IScope getPackageScope() {
    boolean _equals = Objects.equal(this.packageScope, null);
    if (_equals) {
      Set<String> _keySet = EPackage.Registry.INSTANCE.keySet();
      final Function1<String, IEObjectDescription> _function = new Function1<String, IEObjectDescription>() {
        public IEObjectDescription apply(final String from) {
          EPackage _createEPackage = EcoreFactory.eINSTANCE.createEPackage();
          final InternalEObject proxyPackage = ((InternalEObject) _createEPackage);
          URI _createURI = URI.createURI(from);
          proxyPackage.eSetProxyURI(_createURI);
          QualifiedName _qualifiedName = MIRGlobalScopeProvider.this.qualifiedNameConverter.toQualifiedName(from);
          Map<String, String> _singletonMap = Collections.<String, String>singletonMap("nsURI", "true");
          return EObjectDescription.create(_qualifiedName, proxyPackage, _singletonMap);
        }
      };
      Iterable<IEObjectDescription> _map = IterableExtensions.<String, IEObjectDescription>map(_keySet, _function);
      SimpleScope _simpleScope = new SimpleScope(IScope.NULLSCOPE, _map);
      this.packageScope = _simpleScope;
    }
    return this.packageScope;
  }
  
  public IScope getScope(final Resource resource, final EReference ref, final Predicate<IEObjectDescription> filter) {
    IScope _xblockexpression = null;
    {
      EReference _import_Package = MIRPackage.eINSTANCE.getImport_Package();
      boolean _equals = Objects.equal(ref, _import_Package);
      if (_equals) {
        return this.getPackageScope();
      }
      _xblockexpression = super.getScope(resource, ref, filter);
    }
    return _xblockexpression;
  }
}
