package tools.vitruv.framework.remote.server.rest;

/** Collects all REST endpoints for a specific path. */
public record PathEndointCollector(
    String path,
    GetEndpoint getEndpoint,
    PostEndpoint postEndpoint,
    PutEndpoint putEndpoint,
    PatchEndpoint patchEndpoint,
    DeleteEndpoint deleteEndpoint) {}
