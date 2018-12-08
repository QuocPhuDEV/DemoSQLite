package com.example.hoangquocphu.demosqlite.Customer;

import java.io.Serializable;
import java.security.PrivateKey;

public class Customer implements Serializable {
    private int CusID;
    private String FullName;
    private String DateOfBirth;
    private String Email;
    private String Address;
    private String PhoneNumber;
    private int Gender;

    public Customer() {
    }

    public Customer(int cusID, String fullName, String dateOfBirth, String email, String address, String phoneNumber, int gender) {
        CusID = cusID;
        FullName = fullName;
        DateOfBirth = dateOfBirth;
        Email = email;
        Address = address;
        PhoneNumber = phoneNumber;
        Gender = gender;
    }

    public int getCusID() {
        return CusID;
    }

    public void setCusID(int cusID) {
        CusID = cusID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getGender() {
        return Gender;
    }

    public void setGender(int gender) {
        Gender = gender;
    }
}
