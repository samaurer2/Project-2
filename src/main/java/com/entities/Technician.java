package com.entities;


import javax.persistence.*;

@Entity
@Table(name="technician")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "TECH")
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer techId;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", updatable = false, insertable = false)
    private String type;



    public Technician() {
    }

    public Technician(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.type = "TECH";
    }

    public Integer getId() {
        return techId;
    }

    public void setId(Integer id) {
        this.techId = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Technician{" +
                "id=" + techId +
                ", displayName='" + displayName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
