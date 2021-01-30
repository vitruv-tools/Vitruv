package tools.vitruv.testutils.domains

import edu.kit.ipd.sdq.activextendannotations.Utility
import static extension tools.vitruv.testutils.domains.DomainModelCreators.*
import static tools.vitruv.testutils.metamodels.AllElementTypesCreators.aet
import static tools.vitruv.testutils.metamodels.AllElementTypes2Creators.aet2
import static tools.vitruv.testutils.metamodels.PcmMockupCreators.pcm
import static tools.vitruv.testutils.metamodels.UmlMockupCreators.uml
import tools.vitruv.framework.domains.repository.VitruvDomainRepositoryImpl
import java.util.List

@Utility
class TestDomainsRepository {
	public static val INSTANCE = new VitruvDomainRepositoryImpl(List.of(aet.domain, aet2.domain, pcm.domain, uml.domain))
}