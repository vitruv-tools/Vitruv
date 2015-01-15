<?xml version="1.0" encoding="ASCII"?>
<MIRintermediate:MIR xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:MIRintermediate="http://www.kit.edu/ipd/sdq/vitruvius/framework/mir/MIRintermediate" xmlns:ecore="http://www.eclipse.org/emf/2002/Ecore">
  <classMappings predicates="//@predicates.0" featureMapping="//@featureMappings.0">
    <left xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Operation"/>
    <right xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature"/>
  </classMappings>
  <classMappings predicates="//@predicates.1" featureMapping="//@featureMappings.1">
    <left xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//DataType"/>
    <right xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/DataType"/>
  </classMappings>
  <classMappings predicates="//@predicates.2" featureMapping="//@featureMappings.2">
    <left xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Parameter"/>
    <right xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Parameter"/>
  </classMappings>
  <classMappings>
    <left xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface"/>
    <right xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface"/>
  </classMappings>
  <classMappings predicates="//@predicates.3" featureMapping="//@featureMappings.3">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Operation"/>
  </classMappings>
  <classMappings predicates="//@predicates.4" featureMapping="//@featureMappings.4">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/DataType"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//DataType"/>
  </classMappings>
  <classMappings predicates="//@predicates.5" featureMapping="//@featureMappings.5">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Parameter"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Parameter"/>
  </classMappings>
  <classMappings>
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface"/>
  </classMappings>
  <classMappings predicates="//@predicates.6" featureMapping="//@featureMappings.6">
    <left xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//NamedElement"/>
    <right xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Interface"/>
  </classMappings>
  <classMappings>
    <left xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Package"/>
    <right xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Repository"/>
  </classMappings>
  <classMappings predicates="//@predicates.7" featureMapping="//@featureMappings.7">
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Interface"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//NamedElement"/>
  </classMappings>
  <classMappings>
    <left xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Repository"/>
    <right xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Package"/>
  </classMappings>
  <featureMappings classifierMapping="//@classMappings.0">
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Operation"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface/ownedOperation"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface/signatures__OperationInterface"/>
    </right>
  </featureMappings>
  <featureMappings classifierMapping="//@classMappings.1">
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//DataType"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//TypedElement/type"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/DataType"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature/returnType__OperationSignature"/>
    </right>
  </featureMappings>
  <featureMappings classifierMapping="//@classMappings.2">
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Parameter"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//BehavioralFeature/ownedParameter"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Parameter"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature/parameters__OperationSignature"/>
    </right>
  </featureMappings>
  <featureMappings classifierMapping="//@classMappings.4">
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface/signatures__OperationInterface"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Operation"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface/ownedOperation"/>
    </right>
  </featureMappings>
  <featureMappings classifierMapping="//@classMappings.5">
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/DataType"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature/returnType__OperationSignature"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//DataType"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//TypedElement/type"/>
    </right>
  </featureMappings>
  <featureMappings classifierMapping="//@classMappings.6">
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Parameter"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature/parameters__OperationSignature"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//Parameter"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//BehavioralFeature/ownedParameter"/>
    </right>
  </featureMappings>
  <featureMappings classifierMapping="//@classMappings.8">
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//NamedElement"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Namespace/member"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Interface"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Repository/interfaces__Repository"/>
    </right>
  </featureMappings>
  <featureMappings classifierMapping="//@classMappings.10">
    <left>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Interface"/>
      <feature xsi:type="ecore:EReference" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Repository/interfaces__Repository"/>
    </left>
    <right>
      <eClassifier xsi:type="ecore:EClass" href="http://www.eclipse.org/uml2/5.0.0/UML#//NamedElement"/>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Namespace/member"/>
    </right>
  </featureMappings>
  <predicates xsi:type="MIRintermediate:ReverseFeaturesCorrespondWithEClassifiers">
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface/ownedOperation"/>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface"/>
    </correspondences>
  </predicates>
  <predicates xsi:type="MIRintermediate:ReverseFeaturesCorrespondWithEClassifiers">
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//TypedElement/type"/>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature"/>
    </correspondences>
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface/ownedOperation"/>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface"/>
    </correspondences>
  </predicates>
  <predicates xsi:type="MIRintermediate:ReverseFeaturesCorrespondWithEClassifiers">
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//BehavioralFeature/ownedParameter"/>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationSignature"/>
    </correspondences>
    <correspondences>
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Interface/ownedOperation"/>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/OperationInterface"/>
    </correspondences>
  </predicates>
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
      <feature xsi:type="ecore:EReference" href="http://www.eclipse.org/uml2/5.0.0/UML#//Namespace/member"/>
      <eClassifier xsi:type="ecore:EClass" href="http://sdq.ipd.uka.de/PalladioComponentModel/5.0#//repository/Repository"/>
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
