package ru.geekbrains.rest;

import ru.geekbrains.service.CategoryRepr;

import javax.ejb.Local;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Local
@Path("/category")
public interface CategoryServiceRs {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void insert(CategoryRepr categoryRepr);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    void update(CategoryRepr categoryRepr);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void delete(@PathParam("id") long id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List <CategoryRepr> findAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    CategoryRepr findByIdRs(@PathParam("id") long id);

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Optional<CategoryRepr> findByName(@PathParam("name")String name);

}
