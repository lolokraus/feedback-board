package at.lolokraus.service;

import at.lolokraus.model.Feedback;
import at.lolokraus.model.FeedbackRequest;

import java.io.IOException;
import java.util.List;

public interface FeedbackService {

    List<Feedback> findAll();

    Feedback findById(Long id);

    Feedback save(FeedbackRequest feedbackRequest) throws IOException;

    void deleteById(Long id);
}
