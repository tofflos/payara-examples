/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.business.items;

import com.example.jaxrs.ErrorMessage;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import static org.assertj.core.api.Assertions.assertThat;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.Test;

/**
 *
 * @author Erik
 */
public class ItemResourceIT {

    private static final String ITEMS_URL = "http://localhost:8080/mavenproject74-1.0-SNAPSHOT/api/v1/items";
    
    @Test
    public void getAsAllUnauthenticatedShouldFail() {
        ErrorMessage expected = new ErrorMessage(401, "This request requires successful authentication.");

        Client client = ClientBuilder.newBuilder()
                // No authentication
                .build();

        Response response = client.target(ITEMS_URL).request().get();
        
        assertThat(response.getStatus()).isEqualTo(401);
        assertThat(response.readEntity(ErrorMessage.class)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void getAllAsUnauthorizedShouldFail() {
        ErrorMessage expected = new ErrorMessage(403, "You are not authorized for this request.");

        Client client = ClientBuilder.newBuilder()
                .register(HttpAuthenticationFeature.basic("employee", "employeePassword"))
                .build();

        Response response = client.target(ITEMS_URL).request().get();

        assertThat(response.getStatus()).isEqualTo(403);
        assertThat(response.readEntity(ErrorMessage.class)).isEqualToComparingFieldByField(expected);
    }

    @Test
    public void getAll() {
        List<Item> expected = Arrays.asList(new Item(1L, "Item 1"), new Item(2L, "Item 2"));

        Client client = ClientBuilder.newBuilder()
                .register(HttpAuthenticationFeature.basic("administrator", "administratorPassword"))
                .build();

        List<Item> actual = client.target(ITEMS_URL).request().get(new GenericType<List<Item>>() {
        });

        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
