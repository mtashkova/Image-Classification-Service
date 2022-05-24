package com.vmware.talentboost.imageclassificationservice.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="descriptions")
public class Description implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "confidence")
    private double confidence;

    @Column(name = "tag")
    private String tag;


    public Description(double confidence, String tag) {
        this.confidence = confidence;
        this.tag = tag;
    }

    public Description() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return confidence +
                "," + tag;
    }
}
