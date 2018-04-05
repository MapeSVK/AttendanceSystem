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
public class TodayStudents {
    
    private String firstName;
    private String lastName;
    private String status;
    private String imageLink;
    Button changeAttendanceButton;
    ImageView attendanceImage;
    ImageView photo;
    private String fullName;
    private int studentId;

    public TodayStudents(int studentId, String firstName, String lastName, String status, String imageLink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.imageLink = imageLink;
        this.fullName=firstName+" "+lastName;
        this.changeAttendanceButton = new Button("EDIT");
        this.changeAttendanceButton.setVisible(false);
        this.attendanceImage = new ImageView();
        this.photo = new ImageView();
        this.studentId=studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
