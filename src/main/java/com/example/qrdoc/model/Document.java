package com.example.qrdoc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Document {
    @Id
    private String id;

    private String name;
    private String surname;
    private LocalDate expiryDate;
    private String issuedBy;
    private String details;
    private String qrCodeValue;
    private String secretCode;

    // Добавь поле для имени файла QR-кода
//    private String qrCodeImagePath;
//
//    // Геттер и сеттер:
//    public String getQrCodeImagePath() {
//        return qrCodeImagePath;
//    }
//
//    public void setQrCodeImagePath(String qrCodeImagePath) {
//        this.qrCodeImagePath = qrCodeImagePath;
//    }


// getters и setters для всех полей


    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getIssuedBy() { return issuedBy; }
    public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getQrCodeValue() { return qrCodeValue; }
    public void setQrCodeValue(String qrCodeValue) { this.qrCodeValue = qrCodeValue; }

    public String getSecretCode() { return secretCode; }
    public void setSecretCode(String secretCode) { this.secretCode = secretCode; }
}
