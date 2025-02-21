   <extension point="org.eclipse.emf.ecore.generated_package">
      <!-- @generated model -->
      <package
            uri="${item.modelUri}"
            class="${item.packageName}.model.model.${item.modelNameCap}Package"
            genModel="src/main/ecore/${item.genmodelName}"/>
   </extension>