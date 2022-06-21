package uz.mh.talkoncopy.controller;

import uz.mh.talkoncopy.services.base.BaseGenericService;

public abstract class AbstractController<S extends BaseGenericService> {

    protected final S service;

    protected final String API = "/api";

    protected final String VERSION = "/v1";

    protected final String PATH = API + VERSION;

    protected AbstractController(S service) {
        this.service = service;
    }
}
