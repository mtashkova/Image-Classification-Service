package com.vmware.talentboost.imageclassificationservice;

import com.google.common.collect.ImmutableList;
import com.vmware.talentboost.imageclassificationservice.data.Description;
import com.vmware.talentboost.imageclassificationservice.data.Image;
import com.vmware.talentboost.imageclassificationservice.repository.DescriptionRepository;
import com.vmware.talentboost.imageclassificationservice.repository.ImageRepository;
import org.apache.catalina.filters.CorsFilter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ImageClassificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageClassificationServiceApplication.class, args);

	}

}
