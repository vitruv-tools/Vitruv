package edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors;

import com.google.common.base.Objects;
import edu.kit.ipd.sdq.vitruvius.codeintegration.scmchanges.extractors.IContentValidator;
import edu.kit.ipd.sdq.vitruvius.framework.code.jamopp.JaMoPPParser;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.emftext.language.java.containers.CompilationUnit;

@SuppressWarnings("all")
public class JaMoPPContentValidator implements IContentValidator {
  @Override
  public boolean isValid(final String content, final URI contentUri) {
    try {
      final JaMoPPParser parser = new JaMoPPParser();
      byte[] _bytes = content.getBytes(StandardCharsets.UTF_8);
      final ByteArrayInputStream inputStream = new ByteArrayInputStream(_bytes);
      final CompilationUnit cu = parser.parseCompilationUnitFromInputStream(contentUri, inputStream);
      boolean _notEquals = (!Objects.equal(cu, null));
      if (_notEquals) {
        return true;
      }
    } catch (final Throwable _t) {
      if (_t instanceof Exception) {
        final Exception e = (Exception)_t;
        return false;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return false;
  }
}
