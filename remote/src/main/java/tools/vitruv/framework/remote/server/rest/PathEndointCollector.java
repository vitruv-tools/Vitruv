package tools.vitruv.framework.remote.server.rest;

public record PathEndointCollector(
	String path,
	GetEndpoint getEndpoint,
	PostEndpoint postEndpoint,
	PutEndpoint putEndpoint,
	PatchEndpoint patchEndpoint,
	DeleteEndpoint deleteEndpoint) {}
