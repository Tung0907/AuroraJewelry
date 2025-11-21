package org.example.aurorajewelry.model;

import java.util.Date;

public class Customer {
    private int customerId;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private Date createdAt;
    private String password;

    public String getPassword() { return password; }   // ✅ thêm
    public void setPassword(String password) { this.password = password; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
