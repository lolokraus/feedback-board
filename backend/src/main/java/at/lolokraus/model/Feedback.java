package at.lolokraus.model;

import javax.persistence.*;

@Entity
@Table(name = "FEEDBACK")
public class Feedback {

    public Feedback() {
    }

    public Feedback(Long id, String name, String description, byte[] picByte) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.picByte = picByte;
     }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "picByte", columnDefinition = "LONGBLOB")
    private byte[] picByte;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }
}
