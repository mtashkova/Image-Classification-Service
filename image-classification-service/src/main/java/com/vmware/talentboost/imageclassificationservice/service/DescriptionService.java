package com.vmware.talentboost.imageclassificationservice.service;

import com.vmware.talentboost.imageclassificationservice.data.Description;

import java.util.List;

public interface DescriptionService {
    Description addDescription(Description description);
    List<Description> findAllDescriptions();
    void deleteAllDescriptions();

    List<Description> findDescriptionByTags(String str);
    List<Description>findAllByTagStartsWith(String tag);

    List<Integer> findAllImagesIdFromDescriptionId(int descriptionId);
}
