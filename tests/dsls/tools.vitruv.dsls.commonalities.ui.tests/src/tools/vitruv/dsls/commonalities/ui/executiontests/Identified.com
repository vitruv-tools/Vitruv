concept test

commonality Identified {

	with AllElementTypes:Root
	with AllElementTypes2:Root2
	with PcmMockup:Repository
	with UmlMockup:UPackage
	
	has id {
		= AllElementTypes:Root.id
		= AllElementTypes2:Root2.id2
		= PcmMockup:Repository.id
		= UmlMockup:UPackage.id
	}
}