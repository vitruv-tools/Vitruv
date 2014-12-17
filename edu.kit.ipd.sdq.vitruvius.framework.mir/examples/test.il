<?xml version="1.0" encoding="ASCII"?>
<MIRintermediate:MIR xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:MIRintermediate="http://www.kit.edu/ipd/sdq/vitruvius/framework/mir/MIRintermediate" xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore">
  <classMappings predicates="//@predicates.1">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Operation"/>
  </classMappings>
  <classMappings predicates="//@predicates.2">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/DataType"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//DataType"/>
    <initializer xsi:type="MIRintermediate:JavaInitializer" callStatement="(uiooop.direction = org.eclipse.uml2.uml.ParameterDirectionKind.RETURN)"/>
  </classMappings>
  <classMappings predicates="//@predicates.3">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Parameter"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Parameter"/>
    <initializer xsi:type="MIRintermediate:JavaInitializer" callStatement="(uioop.direction != org.eclipse.uml2.uml.ParameterDirectionKind.RETURN)"/>
  </classMappings>
  <classMappings predicates="//@predicates.0">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface"/>
    <initializer xsi:type="MIRintermediate:JavaInitializer" callStatement="(initializer test)"/>
  </classMappings>
  <classMappings predicates="//@predicates.4">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Interface"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//NamedElement"/>
  </classMappings>
  <classMappings>
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Repository"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Package"/>
  </classMappings>
  <featureMappings>
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface/signatures__OperationInterface"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Operation"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface/ownedOperation"/>
    </right>
  </featureMappings>
  <featureMappings>
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/DataType"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature/returnType__OperationSignature"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//DataType"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//TypedElement/type"/>
    </right>
  </featureMappings>
  <featureMappings>
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Parameter"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature/parameters__OperationSignature"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Parameter"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//BehavioralFeature/ownedParameter"/>
    </right>
  </featureMappings>
  <featureMappings>
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Interface"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Repository/interfaces__Repository"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//NamedElement"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Namespace/member"/>
    </right>
  </featureMappings>
  <predicates xsi:type="MIRintermediate:JavaPredicate" checkStatement="(predicate test)"/>
  <predicates xsi:type="MIRintermediate:ReverseFeaturesCorrespondWithEClassifiers">
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface/signatures__OperationInterface"/>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface"/>
    </correspondences>
  </predicates>
  <predicates xsi:type="MIRintermediate:ReverseFeaturesCorrespondWithEClassifiers">
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature/returnType__OperationSignature"/>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Operation"/>
    </correspondences>
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface/signatures__OperationInterface"/>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface"/>
    </correspondences>
  </predicates>
  <predicates xsi:type="MIRintermediate:ReverseFeaturesCorrespondWithEClassifiers">
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature/parameters__OperationSignature"/>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Operation"/>
    </correspondences>
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface/signatures__OperationInterface"/>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface"/>
    </correspondences>
  </predicates>
  <predicates xsi:type="MIRintermediate:ReverseFeaturesCorrespondWithEClassifiers">
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Repository/interfaces__Repository"/>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Package"/>
    </correspondences>
  </predicates>
  <configuration package="mir.pcm2uml" type="PCM2UML"/>
</MIRintermediate:MIR>
