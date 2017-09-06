concept test

commonality Sub {
	
	with AllElementTypes:NonRoot
	with AllElementTypes2:NonRoot2
	with PcmMockup:Component
	with UmlMockup:UClass
	
	has name {
		= AllElementTypes:NonRoot.id
		= AllElementTypes2:NonRoot2.id2
		= PcmMockup:Component.name
		= UmlMockup:UClass.name
	}
}