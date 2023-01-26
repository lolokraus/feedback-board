package at.lolokraus.service.impl;

import at.lolokraus.model.Feedback;
import at.lolokraus.model.FeedbackRequest;
import at.lolokraus.repository.FeedbackRepository;
import at.lolokraus.service.AuthorityService;
import at.lolokraus.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    private final AuthorityService authService;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, AuthorityService authService) {
        this.feedbackRepository = feedbackRepository;
        this.authService = authService;
    }

    @Override
    public List<Feedback> findAll() {
        final List<Feedback> feedbacks =  feedbackRepository.findAll();
        for (Feedback feedback: feedbacks) {
            if(feedback.getPicByte() != null){
                feedback.setPicByte(decompressBytes(feedback.getPicByte()));
            }
        }
        return feedbacks;
    }

    @Override
    public Feedback findById(Long id) {
        final Feedback compressedFeedback = feedbackRepository.getOne(id);
        Feedback feedback = null;
        if(compressedFeedback.getPicByte() != null) {
           feedback = new Feedback(compressedFeedback.getId(), compressedFeedback.getName(), compressedFeedback.getDescription(),
                    decompressBytes(compressedFeedback.getPicByte()));
        }else{
            feedback = new Feedback(compressedFeedback.getId(), compressedFeedback.getName(), compressedFeedback.getDescription(),
                    null);
        }
        return feedback;
    }

    @Override
    public Feedback save(FeedbackRequest feedbackRequest) throws IOException {
        Feedback feedback = new Feedback();
        feedback.setName(feedbackRequest.getName());
        feedback.setDescription(feedbackRequest.getDescription());
        feedback.setPicByte(compressBytes(feedbackRequest.getPicByte().getBytes()));
        return feedbackRepository.save(feedback);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(Long id){
        feedbackRepository.deleteById(id);
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
