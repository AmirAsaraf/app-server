package com.app.resources;

import com.app.exceptions.UserNotExistsException;
import com.app.model.Image;
import com.app.model.ImageInfo;
import com.app.model.UserRecord;
import com.app.services.AuditService;
import com.app.services.UsersService;
import com.app.utils.CorsGenerator;

import javax.imageio.ImageIO;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by aa069w on 1/12/2017.
 */
@Path("/resource")
public class ImageResource {


    @OPTIONS
    public Response corsMyResource(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        return CorsGenerator.makeCORS(Response.ok(), requestH);
    }

    @OPTIONS
    @Path("{id}")
    public Response corsResourceId(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        return CorsGenerator.makeCORS(Response.ok(), requestH);
    }

    @OPTIONS
    @Path("{id}/images")
    public Response corsUpload(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        return CorsGenerator.makeCORS(Response.ok(), requestH);
    }


    @OPTIONS
    @Path("{id}/images/{imageUrl}")
    public Response corsImageUrl(@HeaderParam("Access-Control-Request-Headers") String requestH) {
        return CorsGenerator.makeCORS(Response.ok(), requestH);
    }



    @GET
    @Path("{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getSourceImages(
            @PathParam("id") String id) {
        List images = new ArrayList<Image>();

        Image image1 = new Image("http://i.lv3.hbo.com/assets/images/series/game-of-thrones/home/season-06/160223-s6-key-art-1920x700.jpg", 300, 400,123, "poster");
        Image image2 = new Image("http://www.auditionsfree.com/content/user/2016/09/dragon.png", 600, 300,123, "poster");

        images.add(image1);
        images.add(image2);

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(images).build();
    }

    @DELETE
    @Path("{id}/images/{imageUrl}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response deleteSourceImages(
            @PathParam("id") String id,
            @PathParam("imageUrl") String imageUrl,
            String body){
        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity(imageUrl + " deleted successfully!").build();
    }


    /*
     * Upload source image for a resource id (e.g. program, season, series,
     * etc.)
     * Uploaded image should be added to an existing resource id or in case the resource id doesn't exists,
     * create new one and add the image
     *
     * @param id - The ID of the resource
     * @param type - The image type - jpg/png
     * @param sourceType - Image source type - Manual,...
     * @param imageType - Image type - channel, iconic,...,...
     * @param imageBytes - Image content in byte array
     * @param vendor - Manual Default or Manual Override
     * @return
     */
    @POST
    @Path("{id}/images")
    @Consumes({"image/jpg", "image/jpeg","image/png"})
    public Response uploadImage(
            @PathParam("id") String id,
            @HeaderParam("Image-File-Type") String type,
            @HeaderParam("Image-Source-Type") String sourceType,
            @HeaderParam("Image-Type") String imageType,
            @HeaderParam("Image-Vendor") String vendor,
            byte[] imageBytes) throws IOException {

        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
            System.out.println(img.getWidth() + ":" + img.getHeight());

            String fileExtention = type.replace("image/", "");

            File outputfile = new File("image" +  new Date().getTime() + "." + fileExtention);
            ImageIO.write(img, fileExtention, outputfile);

            System.out.println(outputfile.getName() + " created!");


        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("image uploaded successfully!").build();
    }




}
