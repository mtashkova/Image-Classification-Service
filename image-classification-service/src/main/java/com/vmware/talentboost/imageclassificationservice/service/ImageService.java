package com.vmware.talentboost.imageclassificationservice.service;

import com.vmware.talentboost.imageclassificationservice.data.Image;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    Image addImage(String url) throws IOException;
    List<Image> findAllImages();
    void deleteImage(Integer id);
    Image updateImages(Image images);
    void deleteAllImages();
    Image findImageById(int id);
}
