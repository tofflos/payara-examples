package com.example.business.things;

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

@Path("/things")
@RequestScoped
@Transactional(Transactional.TxType.REQUIRED)
public class ThingResource {

    private static final List<Thing> THINGS = Arrays.asList(new Thing(1L, "Thing 1"), new Thing(2L, "Thing 2"));

    public ThingResource() {
        // Required by RESTEasy
    }

    @GET
    @RolesAllowed("Administrator")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGet() {
        return Response.ok(THINGS).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doGetById(@PathParam("id") Long id) {
        return Response.ok(THINGS.stream().filter(thing -> id.equals(thing.getId())).findFirst().orElseThrow(NotFoundException::new)).build();
    }
}
