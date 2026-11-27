package tools.vitruv.change.atomic.resolve;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static tools.vitruv.change.atomic.message.Error.UNKNOWN_CHANGE_OF_TYPE;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.junit.jupiter.api.Test;
import tools.vitruv.change.atomic.EChange;
import tools.vitruv.change.atomic.TypeInferringAtomicEChangeFactory;
import tools.vitruv.change.atomic.eobject.CreateEObject;
import tools.vitruv.change.atomic.eobject.DeleteEObject;
import tools.vitruv.change.atomic.eobject.EobjectFactory;
import tools.vitruv.change.atomic.feature.FeatureFactory;
import tools.vitruv.change.atomic.feature.UnsetFeature;
import tools.vitruv.change.atomic.feature.attribute.InsertEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.RemoveEAttributeValue;
import tools.vitruv.change.atomic.feature.attribute.ReplaceSingleValuedEAttribute;
import tools.vitruv.change.atomic.feature.reference.InsertEReference;
import tools.vitruv.change.atomic.feature.reference.RemoveEReference;
import tools.vitruv.change.atomic.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.change.atomic.feature.single.ReplaceSingleValuedFeatureEChange;
import tools.vitruv.change.atomic.root.InsertRootEObject;
import tools.vitruv.change.atomic.root.RemoveRootEObject;
import tools.vitruv.change.atomic.root.RootFactory;

class AtomicEChangeCopierUnitTest {

  @Test
  void constructorIsPrivate() throws Exception {
    var constructor = AtomicEChangeCopier.class.getDeclaredConstructor();
    assertThat(constructor.canAccess(null)).isFalse();

    constructor.setAccessible(true);
    var instance = constructor.newInstance();
    assertThat(instance).isNotNull();
  }

  @Test
  void testCopyInsertRootEObject() {
    var original = RootFactory.eINSTANCE.createInsertRootEObject();
    original.setUri("test://insert");
    original.setIndex(42);

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(InsertRootEObject.class);
    var insertCopy = (InsertRootEObject<?>) copy;
    assertThat(insertCopy.getUri()).isEqualTo("test://insert");
    assertThat(insertCopy.getIndex()).isEqualTo(42);
  }

  @Test
  void testCopyRemoveRootEObject() {
    var original = RootFactory.eINSTANCE.createRemoveRootEObject();
    original.setUri("test://remove");
    original.setIndex(24);

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(RemoveRootEObject.class);
    var removeCopy = (RemoveRootEObject<?>) copy;
    assertThat(removeCopy.getUri()).isEqualTo("test://remove");
    assertThat(removeCopy.getIndex()).isEqualTo(24);
  }

  @Test
  void testCopyCreateEObject() {
    var original = EobjectFactory.eINSTANCE.createCreateEObject();

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(CreateEObject.class);
  }

  @Test
  void testCopyDeleteEObject() {
    var original = EobjectFactory.eINSTANCE.createDeleteEObject();

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(DeleteEObject.class);
  }

  @Test
  void testCopyUnsetFeature() {
    var feature = mock(EStructuralFeature.class);

    var original = FeatureFactory.eINSTANCE.createUnsetFeature();
    original.setAffectedFeature(feature);

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(UnsetFeature.class);
    var unsetCopy = (UnsetFeature<?, ?>) copy;
    assertThat(unsetCopy.getAffectedFeature()).isEqualTo(feature);
  }

  @Test
  void testCopyInsertEAttributeValue() {
    var feature = mock(EAttribute.class);

    var original =
        TypeInferringAtomicEChangeFactory.getInstance()
            .createInsertAttributeChange(null, feature, 1, "newVal");

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(InsertEAttributeValue.class);
    var insertCopy = (InsertEAttributeValue<?, ?>) copy;
    assertThat(insertCopy.getIndex()).isEqualTo(1);
    assertThat(insertCopy.getNewValue()).isEqualTo("newVal");
  }

  @Test
  void testCopyReplaceSingleValuedEAttribute() {
    var feature = mock(EAttribute.class);

    var original =
        TypeInferringAtomicEChangeFactory.getInstance()
            .createReplaceSingleAttributeChange(null, feature, "oldVal", "newVal");
    original.setIsUnset(true);

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(ReplaceSingleValuedEAttribute.class);
    var replaceCopy = (ReplaceSingleValuedFeatureEChange<?, ?, ?>) copy;
    assertThat(replaceCopy.isIsUnset()).isTrue();
  }

  @Test
  void testCopyRemoveEAttributeValue() {
    var feature = mock(EAttribute.class);

    var original =
        TypeInferringAtomicEChangeFactory.getInstance()
            .createRemoveAttributeChange(null, feature, 2, "oldVal");

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(RemoveEAttributeValue.class);
    var removeCopy = (RemoveEAttributeValue<?, ?>) copy;
    assertThat(removeCopy.getIndex()).isEqualTo(2);
    assertThat(removeCopy.getOldValue()).isEqualTo("oldVal");
  }

  @Test
  void testCopyInsertEReference() {
    var feature = mock(EReference.class);

    var original =
        TypeInferringAtomicEChangeFactory.getInstance()
            .createInsertReferenceChange(null, feature, null, 3);

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(InsertEReference.class);
    var insertRefCopy = (InsertEReference<?>) copy;
    assertThat(insertRefCopy.getIndex()).isEqualTo(3);
  }

  @Test
  void testCopyReplaceSingleValuedEReference() {
    var feature = mock(EReference.class);

    var original =
        TypeInferringAtomicEChangeFactory.getInstance()
            .createReplaceSingleReferenceChange(null, feature, null, null);
    original.setIsUnset(true);

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(ReplaceSingleValuedEReference.class);
    var replaceCopy = (ReplaceSingleValuedFeatureEChange<?, ?, ?>) copy;
    assertThat(replaceCopy.isIsUnset()).isTrue();
  }

  @Test
  void testCopyRemoveEReference() {
    var feature = mock(EReference.class);

    var original =
        TypeInferringAtomicEChangeFactory.getInstance()
            .createRemoveReferenceChange(null, feature, null, 4);

    var copy = AtomicEChangeCopier.copy(original);

    assertThat(copy).isInstanceOf(RemoveEReference.class);
    var removeRefCopy = (RemoveEReference<?>) copy;
    assertThat(removeRefCopy.getIndex()).isEqualTo(4);
  }

  @Test
  void testCopyUnknownTypeThrowsException() {
    @SuppressWarnings("unchecked")
    EChange<Object> unknown = mock(EChange.class);

    assertThatThrownBy(() -> AtomicEChangeCopier.copy(unknown))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining(String.format(UNKNOWN_CHANGE_OF_TYPE, ""));
  }
}
