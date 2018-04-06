/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mape
 */
public class Student {

    private int id;
    private String firstName;
    private String lastName;
    private String imageLink;
    private String email;
    private String status;
    private String fullName;
    private int classId;
    private int percentage;
    Button changeAttendanceButton;
    ImageView attendanceImage;
    ImageView photo;

    public Student(int id, String firstName, String lastName, String imageLink, String email, int classId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageLink = imageLink;
        this.email = email;
        this.classId = classId;
        this.fullName = firstName + " " + lastName;
        this.changeAttendanceButton = new Button("Change");

        this.attendanceImage = new ImageView();
        this.photo = new ImageView();

    }

    public Button getChangeAttendanceButton() {
        return changeAttendanceButton;
    }

    public void setChangeAttendanceButton(Button changeAttendanceButton) {
        this.changeAttendanceButton = changeAttendanceButton;
    }

    public ImageView getAttendanceImage() {
        return attendanceImage;
    }

    public void setAttendanceImage(ImageView attendanceImage) {
        this.attendanceImage = attendanceImage;
    }

    public ImageView getPhoto() {
        return photo;
    }

    public void setPhoto(ImageView photo) {
        this.photo = photo;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student";
    }
}
