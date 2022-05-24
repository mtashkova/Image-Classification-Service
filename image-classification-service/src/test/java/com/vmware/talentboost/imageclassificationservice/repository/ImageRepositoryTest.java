package com.vmware.talentboost.imageclassificationservice.repository;

import com.vmware.talentboost.imageclassificationservice.data.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private ImageRepository imageRepository;

    @Test
    public void findNoImagesIfRepositoryIsEmpty() {
        Iterable images = imageRepository.findAll();
        assertThat(images).isEmpty();
    }

    @Test
    public void testIfImageIsAddedToRepository() {
        LocalDateTime analysedDate = LocalDateTime.now();
        Image image = imageRepository.save(new Image("URL", analysedDate, "imagga", 620, 350));
        assertThat(image).hasFieldOrPropertyWithValue("url", "URL");
        assertThat(image).hasFieldOrPropertyWithValue("analysedTime", analysedDate);
        assertThat(image).hasFieldOrPropertyWithValue("service", "imagga");
        assertThat(image).hasFieldOrPropertyWithValue("width", 620);
        assertThat(image).hasFieldOrPropertyWithValue("height", 350);
    }

    @Test
    public void testToFindAllImages() {
        LocalDateTime analysedDate = LocalDateTime.now();
        Image image = imageRepository.save(new Image("URL", analysedDate, "imagga", 620, 350));
        entityManager.persist(image);
        Image image2 = imageRepository.save(new Image("URL1", analysedDate, "imagga", 650, 900));
        entityManager.persist(image2);
        Image image3 = imageRepository.save(new Image("URL2", analysedDate, "imagga", 670, 950));
        entityManager.persist(image3);
        Iterable images = imageRepository.findAll();
        assertThat(images).hasSize(3).contains(image, image2, image3);
    }

    @Test
    public void testFindImageById() {
        LocalDateTime analysedDate = LocalDateTime.now();
        Image image = imageRepository.save(new Image("URL",  analysedDate, "imagga", 620, 350));
        entityManager.persist(image);
        Image image2 = imageRepository.save(new Image("URL1",  analysedDate, "imagga", 650, 900));
        entityManager.persist(image2);
        Image foundImage = imageRepository.findById(image2.getId()).get();
        assertThat(foundImage).isEqualTo(image2);
    }
}
