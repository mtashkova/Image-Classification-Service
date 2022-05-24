package com.vmware.talentboost.imageclassificationservice.service.impl;

import com.vmware.talentboost.imageclassificationservice.data.Description;
import com.vmware.talentboost.imageclassificationservice.data.Image;
import com.vmware.talentboost.imageclassificationservice.repository.ImageRepository;
import com.vmware.talentboost.imageclassificationservice.service.ImageService;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private  final ImageRepository imageRepository;
    private final DescriptionServiceImpl descriptionService;

    private final ImageDetailsServiceImpl imageDetailsService;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, DescriptionServiceImpl descriptionService, ImageDetailsServiceImpl imageDetailsService) {
        this.imageRepository = imageRepository;
        this.descriptionService = descriptionService;
        this.imageDetailsService = imageDetailsService;
    }

    public Description addDescriptions(Image image, Description description) {
        return descriptionService.addDescription(description);
    }
    public void deleteImage(Integer id) {
        imageRepository.deleteById(id);
    }

    public Image updateImages(Image images) {
        Image newImage = imageRepository.findImageByUrl(images.getUrl());
        if(newImage==null) {
            imageRepository.save(images);
            return images;
        }
        return imageRepository.saveAndFlush(images);
    }
    public void deleteAllImages() {
        imageRepository.deleteAll();
    }
    public List<Image> findAllImages() {
        return imageRepository.findAll();
    }

    public Image findImageById(int id) {
        return imageRepository.findImageById(id);
    }

    public Image findImageByUrl(String url) {
        return imageRepository.findImageByUrl(url);
    }

    Page<Image> findAllByPage(org.springframework.data.domain.Pageable pageable){
        return imageRepository.findAll(pageable);
    }

    public Page<Image> findAllPages(int pageNumber, int numberOfImages, String order){
        org.springframework.data.domain.Pageable firstPageWithTwoElements;
        if(order.equals("reverse")) {
            firstPageWithTwoElements = PageRequest.of(pageNumber, numberOfImages, Sort.by("analysedTime").descending());
        } else {
            firstPageWithTwoElements = PageRequest.of(pageNumber, numberOfImages, Sort.by("analysedTime").ascending());
        }
        return this.findAllByPage(firstPageWithTwoElements);
    }


    public Image addImage(String url) throws IOException {
        String imaggaUrl = "https://api.imagga.com/v2/tags?image_url=" + url;
        System.out.println(imaggaUrl);
        Image existingImage = this.findImageByUrl(imaggaUrl);
        if(existingImage != null) {
            return existingImage;
        }
        String json = imageDetailsService.getJSONResponse(imaggaUrl);
        if(json.isEmpty()) {
            throw new IOException("Not a valid URL " + url);
        }
        JSONObject unformattedJSONResponse = new JSONObject(json);
        JSONObject result = (JSONObject) unformattedJSONResponse.get("result");
        JSONArray tags = (JSONArray) result.get("tags");
        Image newImage = imageDetailsService.setImageDetails(imaggaUrl);
        if(newImage==null) {
            throw new NoSuchObjectException("Image is not initialized");
        }
        for (int i = 0; i < tags.length(); i++) {
            JSONObject c = tags.getJSONObject(i);
            Double confidence = c.getDouble("confidence");
            JSONObject tag = (JSONObject) c.get("tag");
            String name = tag.getString("en");
            Description newDesc = new Description(confidence, name);
            newImage.addDescription(newDesc);
            this.addDescriptions(newImage, newDesc);
        }
        newImage.setDescriptions(newImage.getDescriptions());
        return imageRepository.save(newImage);
    }

}
