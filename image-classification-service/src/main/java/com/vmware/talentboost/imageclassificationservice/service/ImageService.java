package com.vmware.talentboost.imageclassificationservice.service;

import com.vmware.talentboost.imageclassificationservice.data.Image;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    public Image addImage(String url) throws IOException;
    public List<Image> findAllImages();
}
