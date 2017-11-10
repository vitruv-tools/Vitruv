concept test

commonality Identified {

	with AllElementTypes:(Root in Resource)
	with AllElementTypes2:(Root2 in Resource)
	with PcmMockup:(Repository in Resource)
	with UmlMockup:(UPackage in Resource)
	
	has id {
		= AllElementTypes:Root.id
		= AllElementTypes2:Root2.id2
		= PcmMockup:Repository.id
		= UmlMockup:UPackage.id
		-> AllElementTypes:Resource.name
		-> AllElementTypes2:Resource.name
		-> PcmMockup:Resource.name
		-> UmlMockup:Resource.name
	}
	
	has number {
		= AllElementTypes:Root.singleValuedEAttribute
		= AllElementTypes2:Root2.singleValuedEAttribute2
	}
	
	has numberList {
		= AllElementTypes:Root.multiValuedEAttribute
		= AllElementTypes2:Root2.multiValuedEAttribute2
	}
	
	has sub referencing test:Sub {
		= AllElementTypes:Root.multiValuedContainmentEReference
		= AllElementTypes2:Root2.multiValuedContainmentEReference2
		= PcmMockup:Repository.components
		= UmlMockup:UPackage.classes
	}
}