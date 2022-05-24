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
        /*Description newDescription = this.findDescriptionByTagAndConfidence(description.getTag(), description.getConfidence());
        if(newDescription!=null) {
            return description;
        }*/
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


    Description findDescriptionByTagAndConfidence(String tag, double confidence) {
        return descriptionRepository.findDescriptionByTagAndConfidence(tag, confidence);
    }
    public Description findDescriptionByConfidence(Double confidence) {
        return descriptionRepository.findDescriptionByConfidence(confidence);
    }

    public List<Description> findDescriptionByTags (String str){
        return descriptionRepository.findDescriptionByTag(str);
    }
    /*public Description findTags(String tags) {
        Description desc;
        List<Description> descriptions = new ArrayList<>();
        desc = this.findDescriptionByTag(tags);
        System.out.println(tags + " desc " + desc);
        //descriptions.add(desc);
        return desc;
    }*/

    public List<Description>findAllByTagStartsWith(String tag) {
        System.out.printf("nooco " + tag);
        //this.descriptionRepository.findAll();
        return descriptionRepository.findAllByTagStartsWithm(tag);
    }

    public List<Integer> findAllImagesIdFromDescriptionId(int descriptionId){
        return  descriptionRepository.findAllImagesIdFromDescriptionId(descriptionId);
    }


}
