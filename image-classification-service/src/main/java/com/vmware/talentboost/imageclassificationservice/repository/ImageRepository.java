package com.vmware.talentboost.imageclassificationservice.repository;


import com.vmware.talentboost.imageclassificationservice.data.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.OrderBy;
import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>, PagingAndSortingRepository<Image, Integer> {
    Image findImageById(int id);
    Image findImageByUrl(String url);
    @Modifying
    @Query(value = "INSERT INTO description_image (image_id, description_id) values (:imageId, :descriptionId)",nativeQuery = true)
    void insertExistingDescriptionId(@Param("imageId")int imageId,@Param("descriptionId") int descriptionId);

    @Query(value = "SELECT image_id FROM description_image WHERE description_id =:descriptionId",nativeQuery = true)
    List<Integer> findIfImageIdAndDescriptionIdExists(int descriptionId);

    //List<Image> findAllByOrderByUploadedTimeDesc();

    //List<Image> findAllByOrderByUploadedTimeAsc();

    @Override
    Page<Image> findAll(org.springframework.data.domain.Pageable pageable);

}
