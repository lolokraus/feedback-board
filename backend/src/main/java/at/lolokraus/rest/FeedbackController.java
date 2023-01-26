package at.lolokraus.rest;

import at.lolokraus.model.Feedback;
import at.lolokraus.model.FeedbackRequest;
import at.lolokraus.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @RequestMapping(method = GET, value = "/feedback/all")
    public List<Feedback> findAll() {
        return this.feedbackService.findAll();
    }

    @RequestMapping(method = GET, value = "/feedback/{feedbackId}")
    public Feedback loadById(@PathVariable Long feedbackId) {
        return this.feedbackService.findById(feedbackId);
    }


    @RequestMapping(method = POST, value = "/feedback/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<?>  uploadImage(@RequestParam("file") MultipartFile file, @RequestParam(value = "name") String name, @RequestParam(value = "description") String description) throws IOException {
        FeedbackRequest feedbackRequest = new FeedbackRequest();
        feedbackRequest.setName(name);
        feedbackRequest.setDescription(description);
        feedbackRequest.setPicByte(file);
        Feedback feedback = this.feedbackService.save(feedbackRequest);
        return new ResponseEntity<Feedback>(feedback, HttpStatus.CREATED);
    }

    @RequestMapping(method = DELETE,  value = "/feedback/delete/{id}")
    public void deleteById(@PathVariable("id") Long id){
        this.feedbackService.deleteById(id);
    }


}
