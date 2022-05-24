package com.vmware.talentboost.imageclassificationservice.controller;

import com.vmware.talentboost.imageclassificationservice.data.Description;
import com.vmware.talentboost.imageclassificationservice.data.Image;
import com.vmware.talentboost.imageclassificationservice.service.impl.DescriptionServiceImpl;
import com.vmware.talentboost.imageclassificationservice.service.impl.ImageServiceImpl;
import org.h2.util.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

@RestController
public class ImageController {
    private final ImageServiceImpl imageService;
    private final DescriptionServiceImpl descriptionService;

    public ImageController(ImageServiceImpl imageService, DescriptionServiceImpl descriptionService) {
        this.imageService = imageService;
        this.descriptionService = descriptionService;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.findAllImages();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Image> getImageById(@PathVariable int id) {
        Image image = imageService.findImageById(id);
        if(image==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    /*@PostMapping
    public ResponseEntity<Image> addImage(@RequestBody Image image) {
        if (!StringUtils.hasText(image.getUrl())) {
            throw new IllegalArgumentException("Image url must be specified.");
        }
        Image newImage = imageService.addImage(image);
        return new ResponseEntity<>(newImage, HttpStatus.OK);
    }*/

    @PostMapping
    public ResponseEntity<Image> addImage(@RequestBody String url) throws IOException {
        if (!StringUtils.hasText(url)) {
            throw new IllegalArgumentException("Image url must be specified.");
        }
        Image image = null;
        try {
            image = imageService.addImage(url);
        } catch(IOException e) {
            throw  new IOException("Couldn't download image");
        }
        return new ResponseEntity<Image>(image, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            imageService.deleteImage(id);
        } catch (final EmptyResultDataAccessException e) {
            throw new IllegalArgumentException(
                    String.format("No user with ID %d found.", id));
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> delete() {
        imageService.deleteAllImages();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value ="images", method = GET)
    public ResponseEntity<List<Image>> getImagesByTag(@RequestParam(name="tags") String[] tags) {
        List<Image> images = new ArrayList<>();
        List<List<Integer>> list = new ArrayList<>();
        for(String tag : tags) {
            List<Description> descr = descriptionService.findDescriptionByTags(tag);
            for (Description desc : descr) list.add(descriptionService.findAllImagesIdFromDescriptionId(desc.getId()));
            if (list != null) {
                for (List<Integer> l : list) {
                    for (Integer i : l) {
                        Image image = imageService.findImageById(i);
                        if(!images.contains(image))
                            images.add(image);
                    }
                }
            }
        }
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @RequestMapping(value ="noCache", method = PUT)
    public ResponseEntity<List<Image>> update(@RequestParam("noCache") boolean cache) {
        if(cache == true) {
            List<Image> newImageList = new ArrayList<>();
            List<Image> imageList = imageService.findAllImages();
            for(Image image: imageList) {
                newImageList.add(imageService.updateImages(image));
            }
            return new ResponseEntity<>(newImageList, HttpStatus.OK);
        }
        return new ResponseEntity<>(imageService.findAllImages(), HttpStatus.OK);
    }

    @RequestMapping(value ="pagination",  method = GET)
    public ResponseEntity<Page<Image>> getSorted(@RequestParam(name="sort", required=false) String order,
                                                 @RequestParam(name="pagenumber") int pageNumber,
                                                 @RequestParam(name="numimages") int numberOfImages) {
        if(order==null) order = "bydefault";
        Page<Image> page = imageService.findAllPages(pageNumber, numberOfImages, order);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
