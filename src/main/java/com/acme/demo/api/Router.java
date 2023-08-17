package com.acme.demo.api;

import com.acme.builder.Builder;

public class Router {

    interface Prototype extends Builder.Prototype {

    }

    Router(Prototype prototype) {
    }
}
