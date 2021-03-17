package com.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ADMIN")
public class Admin extends Technician{

    @Column(name="role")
    private String type;

    public Admin(String uname, String password){
        super(uname, password);
        this.type="ADMIN";
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "type='" + type + '\'' +
                '}';
    }
}
