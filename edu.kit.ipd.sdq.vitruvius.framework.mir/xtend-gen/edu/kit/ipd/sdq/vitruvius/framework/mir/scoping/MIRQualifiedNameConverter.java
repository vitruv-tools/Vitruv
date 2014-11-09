package edu.kit.ipd.sdq.vitruvius.framework.mir.scoping;

import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.XbaseQualifiedNameConverter;

@SuppressWarnings("all")
public class MIRQualifiedNameConverter extends XbaseQualifiedNameConverter {
  public QualifiedName toQualifiedName(final String qualifiedNameAsString) {
    QualifiedName _xblockexpression = null;
    {
      boolean _startsWith = qualifiedNameAsString.startsWith("http://");
      if (_startsWith) {
        return QualifiedName.create(qualifiedNameAsString);
      }
      _xblockexpression = super.toQualifiedName(qualifiedNameAsString);
    }
    return _xblockexpression;
  }
}
