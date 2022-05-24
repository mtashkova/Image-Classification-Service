package com.vmware.talentboost.imageclassificationservice.repository;

import com.vmware.talentboost.imageclassificationservice.data.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DescriptionRepository extends JpaRepository<Description, Integer> {

    List<Description> findDescriptionByTag(String tag);

    Description findDescriptionByTagAndConfidence(String tag, double confidence);

    Description findDescriptionByConfidence(Double number);

    @Query(value = "SELECT image_id FROM description_image WHERE description_id =:descriptionId",nativeQuery = true)
    public List<Integer>findAllImagesIdFromDescriptionId(int descriptionId);

    @Query(value = "SELECT * FROM descriptions WHERE tag LIKE :tag% ",nativeQuery = true)
    public List<Description>findAllByTagStartsWithm(String tag);
    public List<Description>findAllByTagStartsWith(String tag);

}
