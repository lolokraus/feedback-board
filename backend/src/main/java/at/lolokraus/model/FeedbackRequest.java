package at.lolokraus.model;

import org.springframework.web.multipart.MultipartFile;

public class FeedbackRequest {

    private Long id;

    private String name;

    private String description;

    private MultipartFile picByte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getPicByte() {
        return picByte;
    }

    public void setPicByte(MultipartFile picByte) {
        this.picByte = picByte;
    }
}
