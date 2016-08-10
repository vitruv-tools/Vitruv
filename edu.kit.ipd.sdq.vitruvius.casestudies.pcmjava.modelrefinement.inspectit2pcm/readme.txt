This plugin allows us to enrich the co-evolved models with resource demands.
To do so, it reuses the II2PCMJob from EJBmox.  
To allow the reuse it generates a minimal SourceCodeDecoratorModel, which can be used as in put for the II2PCMJob, 
from a Vitruvius Correspondence Model.
Furthermore, the II2PCMJob  needs to have a running CMR server that contains the information about the method response times and the innvocation sequences. 
The class InspectIt2PCMHandler can be configured via the config file InspectIt2PCMHandler.ini.
This ini file needs to be contained in the project, which also contains the source code as well as the repository file (aka. the project under investigation).
Within this configuration for the II2PCMConfiguration can be done.

The following requirements need to be fulfilled to be able to run the project on a project:
1) The project needs to contain a repository file that is consistent with the source code.
2) The Workspace needs to have a Vitruvius Correspondence Model Instance in the vitruvius.meta project
3) To be able to run the II2PCM Job the address for a running CMR server needs to specified within the InspectIt2PCMHandler.ini file.

In the following an example for the configuration file InspectIt2PCMHandler.ini is listed.

org.somox.ejbmox.inspectit2pcm.cmr_api_url=http://141.3.52.137:8182/rest/
org.somox.ejbmox.inspectit2pcm.ensureInternalActionsBeforeSTOPAction=true
org.somox.ejbmox.inspectit2pcm.warmup=10