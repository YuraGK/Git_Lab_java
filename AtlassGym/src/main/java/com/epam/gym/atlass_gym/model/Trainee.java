package com.epam.gym.atlass_gym.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Trainee")
@Table(name = "trainees")
@Inheritance(strategy = InheritanceType.JOINED)
public class Trainee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trainee_id")
    private Long id;
    @Column
    private LocalDate dateOfBirth;
    @Column
    private String address;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "foreign_user_id")
    private User foreign;

    @ManyToMany(mappedBy = "trainees")
    private List<Trainer> trainers;

    public Trainee() {
        super();
    }

    public Trainee(String firstName, String lastName, String username, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, username);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Trainee(String firstName, String lastName, String username, LocalDate dateOfBirth, String address, Long userId) {
        super(firstName, lastName, username);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.id = userId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " " + getUsername() + " " + getPassword() + " " + dateOfBirth.toString() + " " + address + " " + id;

    }

    @Override
    public boolean equals(Object obj) {

        return obj.toString().equals(toString());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((super.getFirstName() == null) ? 0 : super.getFirstName().hashCode());
        result = prime * result + ((super.getLastName() == null) ? 0 : super.getLastName().hashCode());
        result = prime * result + ((super.getUsername() == null) ? 0 : super.getUsername().hashCode());
        result = prime * result + ((super.getPassword() == null) ? 0 : super.getPassword().hashCode());
        result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + id.intValue();
        return result;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }
}
