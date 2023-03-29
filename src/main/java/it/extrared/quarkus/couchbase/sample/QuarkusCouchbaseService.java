package it.extrared.quarkus.couchbase.sample;

import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/quarkus/sample")
public class QuarkusCouchbaseService {

    @Inject
    Cluster cluster;

    @ConfigProperty(name = "couchbase.bucketName")
    String couchbaseBucketName;

    @GET
    @Path("/getFile/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Gets a file", description = "Retrieves a json file by id")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Success",
                    content = @Content(mediaType = "application/json")),
            @APIResponse(responseCode = "404", description="File not found",
                    content = @Content(mediaType = "application/json"))
    })
    public Response getFile(@PathParam("id") String id) {

        var bucket = cluster.bucket(couchbaseBucketName);
        var collection = bucket.defaultCollection();

        try {
            return Response.ok(collection.get(id).contentAsObject().toString()).build();
        } catch (DocumentNotFoundException e){

            return Response.status(Response.Status.NOT_FOUND.getStatusCode(),"{\"error\":\"Not Found\"}").build();
        }

    }

    @POST
    @Path("/sendFile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Store File", description = "Store a json file into database, return the id of the new element")
    @APIResponses(value = { @APIResponse(responseCode = "200", description = "Success",
            content = @Content(mediaType = "application/json")),
        @APIResponse(responseCode = "404", description="File not found",
            content = @Content(mediaType = "application/json"))
    })
    public Response storeFile(String body) {

        var bucket = cluster.bucket(couchbaseBucketName);
        var collection = bucket.defaultCollection();

        String id = UUID.randomUUID().toString();

        JsonObject jsonObj = JsonObject.fromJson(body);
        MutationResult upsertResult = collection.upsert(id,jsonObj);

        return Response.ok("{\"id\":\"" + id +"\"}").build();
    }

}
