/*
 * Copyright (c) 2019. Afaqy Company
 * Mohammed ElAdly (mohammed.eladly@afaqy.com)
 * All rights reserved.
 */

package com.afaqy.avl.webnotifier.api;


import com.afaqy.avl.webnotifier.context.WebNotifierContext;
import com.afaqy.avl.webnotifier.service.ConsumerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("positions")
@Api(value = "KPositionController")
public class KPositionController {
    private ConsumerService service;

    public KPositionController() {
        this.service = WebNotifierContext.getInstance().getkPositionService();
    }


    @PUT
    @Path("/consumers/increment")
    @ApiOperation(value = "increment consumers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Request Success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Not Found")})
    public Response incrementPositionConsumers() {
        try {
            service.startConsumer();

            return Response.status(Response.Status.OK).entity(service.getConsumersCount()).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }


    @PUT
    @Path("/consumers/decrement")
    @ApiOperation(value = "decrement consumers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Request Success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Not Found")})
    public Response decrementPositionConsumers() {
        try {
            service.stopRandomConsumer();

            return Response.status(Response.Status.OK).entity(service.getConsumersCount()).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }


    @GET
    @Path("/consumers/count")
    @ApiOperation(value = "decrement consumers")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Request Success"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 404, message = "Not Found")})
    public Response positionConsumersCount() {
        try {

            return Response.status(Response.Status.OK).entity(service.getConsumersCount()).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
}
