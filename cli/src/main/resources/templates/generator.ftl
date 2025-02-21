module tools.vitruv.framework.cli

import org.eclipse.emf.mwe2.ecore.EcoreGenerator
import org.eclipse.emf.mwe.utils.StandaloneSetup

var workspaceRoot = ".."

Workflow {

    bean = StandaloneSetup {
        scanClassPath = true
        platformUri = workspaceRoot
    }
    <#list items as item>
        <#include "modelComponent.ftl">
    </#list>

}