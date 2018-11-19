/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.jaxrs;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.security.DeclareRoles;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author Erik
 */
@BasicAuthenticationMechanismDefinition(realmName = "My realm")

@ApplicationPath("/api/v1")
@DeclareRoles({"Administrator", "Employee", "Guest"})
public class MyApplication extends Application {

    @Override
    public Map<String, Object> getProperties() {

        Map<String, Object> map = new HashMap();

//        map.put("jersey.config.server.mediaTypeMappings", "html : text/html, json : application/json");
//        map.put("jersey.config.server.response.setStatusOverSendError", true);
        map.put("jersey.config.server.wadl.disableWadl", true);

        return map;
    }
}