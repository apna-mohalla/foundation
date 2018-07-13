package apna.Maholla.controller;

import apna.Maholla.model.Features;
import apna.Maholla.repository.FeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FeatureController {

    private FeaturesRepository featuresRepository;

    @Autowired
    public FeatureController(FeaturesRepository featuresRepository) {
        this.featuresRepository = featuresRepository;
    }

    @GetMapping(path = "/getFeatures", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<Features> getAllAvailableRoles() {
        return featuresRepository.findAll();
    }
}
