package com.vmware.talentboost.imageclassificationservice.service.impl;

import com.vmware.talentboost.imageclassificationservice.data.Description;
import com.vmware.talentboost.imageclassificationservice.data.Image;
import com.vmware.talentboost.imageclassificationservice.repository.DescriptionRepository;
import com.vmware.talentboost.imageclassificationservice.service.DescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class DescriptionServiceImpl implements DescriptionService {
    private final DescriptionRepository descriptionRepository;

    @Autowired
    public DescriptionServiceImpl(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    public Description addDescription(Description description) {
        return descriptionRepository.save(description);
    }

    public List<Description> findAllDescription() {
        return descriptionRepository.findAll();
    }

    public void deleteAllDescriptions() {
        descriptionRepository.deleteAll();
    }

    public List<Description> findAllDescriptions() {
        return descriptionRepository.findAll();
    }


    public List<Description> findDescriptionByTags (String str){
        return descriptionRepository.findDescriptionByTag(str);
    }

    public List<Description>findAllByTagStartsWith(String tag) {
        return descriptionRepository.findAllByTagStartsWithLetter(tag);
    }

    public List<Integer> findAllImagesIdFromDescriptionId(int descriptionId){
        return  descriptionRepository.findAllImagesIdFromDescriptionId(descriptionId);
    }


}
