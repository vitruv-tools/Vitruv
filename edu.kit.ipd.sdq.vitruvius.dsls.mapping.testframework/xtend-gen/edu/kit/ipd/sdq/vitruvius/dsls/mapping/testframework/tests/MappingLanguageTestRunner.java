package edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests;

import com.google.common.base.Objects;
import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.api.AbstractMappingChange2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests.AbstractMappingTestBase;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests.MappingLanguageTestEnvironment;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.tests.MappingLanguageTestUserInteracting;
import edu.kit.ipd.sdq.vitruvius.dsls.mapping.testframework.util.MappingLanguageTestUtil;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.interfaces.Change2CommandTransforming;
import edu.kit.ipd.sdq.vitruvius.framework.util.VitruviusConstants;
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.JavaHelper;
import java.util.function.Consumer;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

@SuppressWarnings("all")
public class MappingLanguageTestRunner extends BlockJUnit4ClassRunner {
  @Inject
  private MappingLanguageTestEnvironment mlte;
  
  public MappingLanguageTestRunner(final Class<?> klass) throws InitializationError {
    super(klass);
  }
  
  @Override
  protected Object createTest() throws Exception {
    AbstractMappingTestBase _xblockexpression = null;
    {
      final Object newTest = super.createTest();
      if ((!(newTest instanceof AbstractMappingTestBase))) {
        StringConcatenation _builder = new StringConcatenation();
        _builder.append("The class under test ");
        TestClass _testClass = this.getTestClass();
        String _name = _testClass.getName();
        _builder.append(_name, "");
        _builder.append(" does not extend ");
        String _name_1 = AbstractMappingTestBase.class.getName();
        _builder.append(_name_1, "");
        _builder.append(".");
        throw new InitializationError(_builder.toString());
      }
      final AbstractMappingTestBase test = ((AbstractMappingTestBase) newTest);
      final String pluginName = test.getPluginName();
      final AbstractMappingChange2CommandTransforming c2cTransforming = MappingLanguageTestRunner.getChange2CommandTransforming(pluginName);
      boolean _equals = Objects.equal(c2cTransforming, null);
      if (_equals) {
        StringConcatenation _builder_1 = new StringConcatenation();
        _builder_1.append("Could not find ");
        String _name_2 = Change2CommandTransforming.class.getName();
        _builder_1.append(_name_2, "");
        _builder_1.append(" for plugin \"");
        _builder_1.append(pluginName, "");
        _builder_1.append("\" for extension point \"");
        _builder_1.append(Change2CommandTransforming.ID, "");
        _builder_1.append("\".");
        _builder_1.newLineIfNotEmpty();
        _builder_1.append("Please make sure that the plugin project has been generated and loaded correctly in the plugin test configuration.");
        _builder_1.newLine();
        throw new InitializationError(_builder_1.toString());
      }
      Class<? extends AbstractMappingChange2CommandTransforming> _change2CommandTransformingClass = test.getChange2CommandTransformingClass();
      boolean _isInstance = _change2CommandTransformingClass.isInstance(c2cTransforming);
      boolean _not = (!_isInstance);
      if (_not) {
        StringConcatenation _builder_2 = new StringConcatenation();
        _builder_2.append("The loaded ");
        String _name_3 = Change2CommandTransforming.class.getName();
        _builder_2.append(_name_3, "");
        _builder_2.append(" for the plugin \"");
        _builder_2.append(pluginName, "");
        _builder_2.append("\" (");
        String _string = c2cTransforming.toString();
        _builder_2.append(_string, "");
        _builder_2.append(") is not");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("an instance of ");
        Class<? extends AbstractMappingChange2CommandTransforming> _change2CommandTransformingClass_1 = test.getChange2CommandTransformingClass();
        String _name_4 = _change2CommandTransformingClass_1.getName();
        _builder_2.append(_name_4, "");
        _builder_2.append(" as expected.");
        _builder_2.newLineIfNotEmpty();
        _builder_2.append("Please make sure that the plugin project has been generated and loaded correctly in the plugin test configuration.");
        _builder_2.newLine();
        throw new InitializationError(_builder_2.toString());
      }
      final Consumer<Binder> _function = (Binder it) -> {
        AnnotatedBindingBuilder<Change2CommandTransforming> _bind = it.<Change2CommandTransforming>bind(Change2CommandTransforming.class);
        _bind.toInstance(c2cTransforming);
        AnnotatedBindingBuilder<AbstractMappingTestBase> _bind_1 = it.<AbstractMappingTestBase>bind(AbstractMappingTestBase.class);
        _bind_1.toInstance(test);
        AnnotatedBindingBuilder<MappingLanguageTestEnvironment> _bind_2 = it.<MappingLanguageTestEnvironment>bind(MappingLanguageTestEnvironment.class);
        _bind_2.in(Singleton.class);
        AnnotatedBindingBuilder<MappingLanguageTestUserInteracting> _bind_3 = it.<MappingLanguageTestUserInteracting>bind(MappingLanguageTestUserInteracting.class);
        _bind_3.in(Singleton.class);
      };
      Injector _injector = MappingLanguageTestUtil.injector(_function);
      final Consumer<Injector> _function_1 = (Injector it) -> {
        it.injectMembers(test);
        it.injectMembers(this);
      };
      JavaHelper.<Injector>with(_injector, _function_1);
      _xblockexpression = test;
    }
    return _xblockexpression;
  }
  
  /**
   * Add magic to move and delete correspondence / vsum before/after each test
   */
  @Override
  protected Statement methodBlock(final FrameworkMethod method) {
    final Statement signature = super.methodBlock(method);
    final Procedure1<Object> _function = (Object it) -> {
      try {
        this.mlte.setup(method);
        try {
          signature.evaluate();
        } finally {
          this.mlte.after();
        }
      } catch (Throwable _e) {
        throw Exceptions.sneakyThrow(_e);
      }
    };
    final Statement result = ((Statement) new Statement() {
        public void evaluate() {
          _function.apply(null);
        }
    });
    return result;
  }
  
  private static AbstractMappingChange2CommandTransforming getChange2CommandTransforming(final String contributorName) throws CoreException {
    IExtensionRegistry _extensionRegistry = Platform.getExtensionRegistry();
    final IConfigurationElement[] config = _extensionRegistry.getConfigurationElementsFor(Change2CommandTransforming.ID);
    for (final IConfigurationElement extConfElem : config) {
      IContributor _contributor = extConfElem.getContributor();
      String _name = _contributor.getName();
      boolean _equals = contributorName.equals(_name);
      if (_equals) {
        String _extensionPropertyName = VitruviusConstants.getExtensionPropertyName();
        Object _createExecutableExtension = extConfElem.createExecutableExtension(_extensionPropertyName);
        return ((AbstractMappingChange2CommandTransforming) _createExecutableExtension);
      }
    }
    return null;
  }
}
