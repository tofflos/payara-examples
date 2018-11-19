/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.business.things;

import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 * @author Erik
 */
public class ThingResourceIT {

    private static final String THINGS_URL = "http://localhost:8080/mavenproject74-1.0-SNAPSHOT/api/v1/things";
    
    @Test
    public void getAll() {
        List<Thing> expected = Arrays.asList(new Thing(1L, "Thing 1"), new Thing(2L, "Thing 2"));

        Client client = ClientBuilder.newBuilder()
                .build();

        List<Thing> actual = client.target(THINGS_URL).request().get(new GenericType<List<Thing>>() {
        });

        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
