package com.vmware.talentboost.imageclassificationservice.service.impl;
import com.vmware.talentboost.imageclassificationservice.data.Description;
import com.vmware.talentboost.imageclassificationservice.data.Image;
import com.vmware.talentboost.imageclassificationservice.security.AuthorizationConfiguration;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ImageDetailsServiceImpl {
    private final AuthorizationConfiguration authorizationConfiguration;
    /*private final static int startOfUploadedTime = 35;
    private final static int endOfUploadedTime = 54;*/
    private final static String imageServiceName = "imagga";

    public ImageDetailsServiceImpl(AuthorizationConfiguration authorizationConfiguration) {
        this.authorizationConfiguration = authorizationConfiguration;
    }
    public  String getJSONResponse(String queryURL) throws IOException {
        String body = "";
        try {
            URL url = new URL(queryURL);
            HttpURLConnection connection = authorizationConfiguration.isAuthorized(url);
            if(connection==null) {
                throw new IOException("Not a valid URL " + url);
            }
            InputStream in = connection.getInputStream();
            String encoding = connection.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            body = IOUtils.toString(in, encoding);
        } catch(IOException e) {
           throw new IOException("Input stream is not valid");
        }
        return body;
    }

    /*public LocalDateTime getUploadedDateOfImageInfo(URL url) throws IOException, ImageProcessingException {
        HttpURLConnection connection = null;
        LocalDateTime dateTime = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            Metadata metadata = ImageMetadataReader.readMetadata(in);
            System.out.println(metadata);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    String stringTag = tag.toString();
                    System.out.println(stringTag);
                    if(stringTag.contains("Date/Time")) {
                        String[] str = stringTag.split("-", 2);
                        System.out.println(str[1].trim());
                        //String str = stringTag.substring(startOfUploadedTime, endOfUploadedTime);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss");
                        dateTime = LocalDateTime.parse(str[1].trim(), formatter);
                    }
                }
            }
        } catch(IOException io) {
            throw new IOException("Connection with url " + url + " was not successful!");
        } catch (ImageProcessingException e) {
            throw new ImageProcessingException("Metadata for image is not correct");
        }
        return dateTime;
    }*/

    public Image setImageDetails(String url) {
        URL urlOfImage = null;
        Image image;
        String[] imageUrls = url.split("=", 2);
        String imageUrl = imageUrls[1];
        try {
            urlOfImage = new URL(imageUrl);
            /*LocalDateTime uploadedDate = getUploadedDateOfImageInfo(urlOfImage);
            if(uploadedDate == null) {
                uploadedDate = LocalDateTime.now();
            }*/
            BufferedImage bimg = ImageIO.read(urlOfImage);
            int width          = bimg.getWidth();
            int height         = bimg.getHeight();
            image = new Image(url, LocalDateTime.now(), imageServiceName, width, height);
        }catch(IOException e) {
            throw new IllegalArgumentException("Image url is null " + urlOfImage);
        }
        return image;
    }
}
