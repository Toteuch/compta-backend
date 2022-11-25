package com.toteuch.compta.backend.endpoint;

import com.toteuch.compta.backend.dto.UserDTO;
import com.toteuch.compta.backend.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserEndpoint {

    @Inject
    UserService service;

    @GET
    @Path("/{id}")
    public UserDTO getById(@PathParam("id") Long id) {
        return service.get(id);
    }

    @GET
    public List<UserDTO> getAll() {
        return service.getAll();
    }

    @POST
    public UserDTO create(UserDTO dto) {
        return service.create(dto);
    }

    @PUT
    public UserDTO update(UserDTO dto) {
        return service.update(dto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
