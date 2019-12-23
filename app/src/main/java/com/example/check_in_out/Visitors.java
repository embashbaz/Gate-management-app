package com.example.check_in_out;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Visitors_table")
public class Visitors {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String secondName;
    private boolean status;
    private String idNumber;
    private String dateIn;
    private String dateOut;
    private String timeOut;
    private String timeIn;
    private String comentout;
    private boolean flagIn;
    private boolean flagOut;
    private String flagReason;
    private String purpose;
    private long phone;
    private String plate;

    public Visitors( String name, String secondName,
                    boolean status, String idNumber, String dateIn,
                    String dateOut, String timeOut, String timeIn,
                    String comentout, boolean flagIn, boolean flagOut,
                    String flagReason, String purpose, long phone,
                    String plate) {

        this.name = name;
        this.secondName = secondName;
        this.status = status;
        this.idNumber = idNumber;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.timeOut = timeOut;
        this.timeIn = timeIn;
        this.comentout = comentout;
        this.flagIn = flagIn;
        this.flagOut = flagOut;
        this.flagReason = flagReason;
        this.purpose = purpose;
        this.phone = phone;
        this.plate = plate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public boolean isStatus() {
        return status;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getDateIn() {
        return dateIn;
    }

    public String getDateOut() {
        return dateOut;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public String getComentout() {
        return comentout;
    }

    public boolean isFlagIn() {
        return flagIn;
    }

    public boolean isFlagOut() {
        return flagOut;
    }

    public String getFlagReason() {
        return flagReason;
    }

    public String getPurpose() {
        return purpose;
    }

    public long getPhone() {
        return phone;
    }

    public String getPlate() {
        return plate;
    }
}
