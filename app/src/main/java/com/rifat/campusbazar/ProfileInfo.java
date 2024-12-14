package com.rifat.campusbazar;

public class ProfileInfo {
    private String name;
    private String email;    // immutable key identifier
    private String mobileNo;
    private String university;
    private String hallName;
    private String address;

    public ProfileInfo() {
        // Required for Firebase
    }

    public ProfileInfo(String name, String email, String mobileNo, String university, String hallName, String address) {
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
        this.university = university;
        this.hallName = hallName;
        this.address = address;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobileNo() { return mobileNo; }
    public String getUniversity() { return university; }
    public String getHallName() { return hallName; }
    public String getAddress() { return address; }

    public void setName(String name) { this.name = name; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }
    public void setUniversity(String university) { this.university = university; }
    public void setHallName(String hallName) { this.hallName = hallName; }
    public void setAddress(String address) { this.address = address; }
}
