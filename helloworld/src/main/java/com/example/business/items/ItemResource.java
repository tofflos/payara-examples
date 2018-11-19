package com.example.business.items;

import java.util.Arrays;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/items")
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class ItemResource {

    private static final List<Item> ITEMS = Arrays.asList(new Item(1L, "Item 1"), new Item(2L, "Item 2"));

    public ItemResource() {
        // Required by RESTEasy
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Administrator")
    public Response doGet() {
        return Response.ok(ITEMS).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Administrator")
    public Response doGetById(@PathParam("id") Long id) {
        return Response.ok(ITEMS.stream().filter(item -> id.equals(item.getId())).findFirst().orElseThrow(NotFoundException::new)).build();
    }
}
