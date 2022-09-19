package com.banking.core.controller.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public final class ControllerUtilMethods {
    private ControllerUtilMethods(){}
    public static URI createUriToAnEntity(String uniqueIdentifier, String path) {

        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path(path)
                .buildAndExpand(uniqueIdentifier).toUri();
    }
}
