package com.example.hoangquocphu.demosqlite.Customer;

import java.io.Serializable;
import java.security.PrivateKey;

public class Customer implements Serializable {
    private int CusID;
    private String FullName;
    private String DateOfBirth;
    private String Gender;
    private String Email;
    private String PhoneNumber;
    private String Address;


    public Customer() {
    }

    public Customer(String fullName, String dateOfBirth, String gender, String email, String phoneNumber, String address) {
        FullName = fullName;
        DateOfBirth = dateOfBirth;
        Gender = gender;
        Email = email;
        PhoneNumber = phoneNumber;
        Address = address;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return this.FullName + " - " + this.Address;
    }
}
