package com.vmware.talentboost.imageclassificationservice.controller;

import com.vmware.talentboost.imageclassificationservice.data.Description;
import com.vmware.talentboost.imageclassificationservice.data.Image;
import com.vmware.talentboost.imageclassificationservice.service.DescriptionService;
import com.vmware.talentboost.imageclassificationservice.service.impl.DescriptionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/tags")
public class DescriptionController {
    DescriptionServiceImpl descriptionService;

    public DescriptionController(DescriptionServiceImpl descriptionService) {
        this.descriptionService = descriptionService;
    }

    @GetMapping
    public ResponseEntity<List<Description>> getAllDescriptions() {
        List<Description> descriptions = descriptionService.findAllDescriptions();
        return new ResponseEntity<>(descriptions, HttpStatus.OK);
    }
    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        descriptionService.deleteAllDescriptions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Description> addImage(@RequestBody Description description) {
        if (!StringUtils.hasText(description.getTag())) {
            throw new IllegalArgumentException("Description tag must be specified.");
        }
        Description description1 = descriptionService.addDescription(description);
        return new ResponseEntity<>(description1, HttpStatus.OK);
    }

    @RequestMapping(value ="tags", method = GET)
    public ResponseEntity<List<Description>> getAutocomplete(@RequestParam(name="autocomplete") String tag) {
        System.out.println("marcheto " + tag);
       // String str = "" + tag;
        if(tag.isEmpty())  System.out.println("empty" + tag);
        descriptionService.findAllDescriptions();
        List<Description> list = descriptionService.findAllByTagStartsWith(tag);
        System.out.println("marcheto " + list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
