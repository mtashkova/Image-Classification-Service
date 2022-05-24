package com.vmware.talentboost.imageclassificationservice.data;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="images")
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "analysed_time")
    private LocalDateTime analysedTime;

    @Column(name="service")
    private String service;

    @Column(name="width")
    private int width;

    @Column(name="height")
    private int height;
    @ManyToMany
    @JoinTable(name = "description_image", joinColumns = @JoinColumn(name = "image_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "description_id", referencedColumnName = "id"))
    private List<Description> descriptions = new ArrayList<>();

    public Image() {
    }

    public Image( String url, LocalDateTime analysedTime, String service, int width, int height) {
        this.url = url;
        this.analysedTime = analysedTime;
        this.service = service;
        this.width = width;
        this.height = height;
    }
    public void addDescription(Description description) {
        descriptions.add(description);
    }
    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void removeDescription(Description description) {
        descriptions.remove(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public LocalDateTime getAnalysedTime() {
        return analysedTime;
    }

    public void setAnalysedTime(LocalDateTime analysedTime) {
        this.analysedTime = analysedTime;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", analysedTime=" + analysedTime +
                ", service='" + service + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", descriptions=" + descriptions +
                '}';
    }
}