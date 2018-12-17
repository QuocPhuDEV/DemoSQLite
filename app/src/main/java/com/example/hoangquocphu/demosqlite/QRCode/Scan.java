package com.example.hoangquocphu.demosqlite.QRCode;

import java.io.Serializable;

public class Scan implements Serializable {
    private int idScan;
    private String maHang;
    private String soID;
    private String scanTime;

    public Scan() {
    }

    public Scan(int idScan, String maHang, String soID, String scanTime) {
        this.idScan = idScan;
        this.maHang = maHang;
        this.soID = soID;
        this.scanTime = scanTime;
    }

    public Scan(String maHang, String soID, String scanTime) {
        this.maHang = maHang;
        this.soID = soID;
        this.scanTime = scanTime;
    }

    public int getIdScan() {
        return idScan;
    }

    public void setIdScan(int idScan) {
        this.idScan = idScan;
    }

    public String getMaHang() {
        return maHang;
    }

    public void setMaHang(String maHang) {
        this.maHang = maHang;
    }

    public String getSoID() {
        return soID;
    }

    public void setSoID(String soID) {
        this.soID = soID;
    }

    public String getScanTime() {
        return scanTime;
    }

    public void setScanTime(String scanTime) {
        this.scanTime = scanTime;
    }

    @Override
    public String toString() {
        return "Scan{" +
                "idScan=" + idScan +
                ", maHang='" + maHang + '\'' +
                ", soID='" + soID + '\'' +
                ", scanTime='" + scanTime + '\'' +
                '}';
    }
}
