/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be;

import java.util.Date;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mape
 */
public class Attendance {

    private int studentId;
    private Date date;
    private String status;
    Button changeAttendanceButton;
    ImageView attendanceImage;

    public Attendance(int studentId, Date date, String status) {
        this.studentId = studentId;
        this.date = date;
        this.status = status;
        this.changeAttendanceButton = new Button("EDIT");
        this.changeAttendanceButton.setVisible(false);
        this.attendanceImage = new ImageView();

    }

    public ImageView getAttendanceImage() {
        return attendanceImage;
    }

    public void setAttendanceImage(ImageView attendanceImage) {
        this.attendanceImage = attendanceImage;
    }

    public Button getChangeAttendanceButton() {
        return changeAttendanceButton;
    }

    public void setChangeAttendanceButton(Button changeAttendanceButton) {
        this.changeAttendanceButton = changeAttendanceButton;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    /*
    Callback<TableColumn<Attendance, String>, TableCell<Attendance, String>> changeAttendanceCallback
                = //
                new Callback<TableColumn<Attendance, String>, TableCell<Attendance, String>>() {
            @Override
            public TableCell call(final TableColumn<Attendance, String> param) {
                final TableCell<Attendance, String> cell = new TableCell<Attendance, String>() {
                    
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else { 
                            changeAttendanceButton.setOnAction(event -> {
                                Attendance attendance = getTableView().getItems().get(getIndex());
                                System.out.println(attendance);
                            });
                            setGraphic(changeAttendanceButton);
                            setText(null);
                        
                        }
                    }
                };
                return cell;
            }
        };
     */
}
