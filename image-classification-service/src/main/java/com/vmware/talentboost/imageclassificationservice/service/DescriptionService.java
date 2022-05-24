package com.vmware.talentboost.imageclassificationservice.service;

import com.vmware.talentboost.imageclassificationservice.data.Description;

import java.util.List;

public interface DescriptionService {
    public Description addDescription(Description description);
    public List<Description> findAllDescription();
    public List<Description> findAllDescriptions();
    public void deleteAllDescriptions();
}
