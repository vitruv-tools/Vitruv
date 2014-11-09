package edu.kit.ipd.sdq.vitruvius.framework.mir.linking;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.Import;
import edu.kit.ipd.sdq.vitruvius.framework.mir.mIR.MIRPackage;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.conversion.ValueConverterException;
import org.eclipse.xtext.linking.impl.DefaultLinkingService;
import org.eclipse.xtext.linking.impl.IllegalNodeException;
import org.eclipse.xtext.linking.impl.LinkingHelper;
import org.eclipse.xtext.nodemodel.ILeafNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;

@SuppressWarnings("all")
public class MIRLinkingService extends DefaultLinkingService {
  private final static Logger log = Logger.getLogger(MIRLinkingService.class);
  
  @Inject
  private IValueConverterService valueConverterService;
  
  public List<EObject> getLinkedObjects(final EObject context, final EReference ref, final INode node) throws IllegalNodeException {
    List<EObject> _xblockexpression = null;
    {
      boolean _and = false;
      EReference _import_Package = MIRPackage.eINSTANCE.getImport_Package();
      boolean _equals = Objects.equal(ref, _import_Package);
      if (!_equals) {
        _and = false;
      } else {
        _and = (context instanceof Import);
      }
      if (_and) {
        return this.getEPackage(((Import) context), ((ILeafNode) node));
      }
      _xblockexpression = super.getLinkedObjects(context, ref, node);
    }
    return _xblockexpression;
  }
  
  /**
   * from org.eclipse.xtext.xtext.XtextLinkingService.getPackage(ReferencedMetamodel, ILeafNode)
   */
  public List<EObject> getEPackage(final Import context, final ILeafNode text) {
    final String nsUri = this.getMetamodelNsURI(text);
    boolean _equals = Objects.equal(nsUri, null);
    if (_equals) {
      return Collections.<EObject>emptyList();
    }
    EPackage _ePackage = EPackage.Registry.INSTANCE.getEPackage(nsUri);
    final EObject resolvedEPackage = ((EObject) _ePackage);
    boolean _equals_1 = Objects.equal(resolvedEPackage, null);
    if (_equals_1) {
      return Collections.<EObject>emptyList();
    }
    return Collections.<EObject>unmodifiableList(CollectionLiterals.<EObject>newArrayList(resolvedEPackage));
  }
  
  /**
   * from org.eclipse.xtext.xtext.XtextLinkingService.getMetamodelNsURI(ILeafNode)
   */
  private String getMetamodelNsURI(final ILeafNode text) {
    try {
      String _text = text.getText();
      LinkingHelper _linkingHelper = this.getLinkingHelper();
      EObject _grammarElement = text.getGrammarElement();
      String _ruleNameFrom = _linkingHelper.getRuleNameFrom(_grammarElement);
      Object _value = this.valueConverterService.toValue(_text, _ruleNameFrom, text);
      return ((String) _value);
    } catch (final Throwable _t) {
      if (_t instanceof ValueConverterException) {
        final ValueConverterException e = (ValueConverterException)_t;
        String _text_1 = text.getText();
        String _plus = ("Exception on leaf \'" + _text_1);
        String _plus_1 = (_plus + "\'");
        MIRLinkingService.log.debug(_plus_1, e);
        return null;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
  }
}
