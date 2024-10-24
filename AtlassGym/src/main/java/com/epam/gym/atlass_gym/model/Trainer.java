package com.epam.gym.atlass_gym.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "Trainer")
@Table(name = "trainers")
@Inheritance(strategy = InheritanceType.JOINED)
public class Trainer extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trainer_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Training_type specialisation;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "foreign_user_id")
    private User foreign;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private List<Trainee> trainees;
	
    public Trainer() {
    }

    public Trainer(String firstName, String lastName, String username, Training_type specialisation, Long userId) {
        super(firstName, lastName, username);
        this.specialisation = specialisation;
        this.id = userId;
    }

    public Long getId() {
        return id;
    }

    public Training_type getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(Training_type specialisation) {
        this.specialisation = specialisation;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " " + getUsername() + " " + getPassword() + " " + specialisation.getTraining_type() + " " + id;
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
        result = prime * result + ((specialisation == null) ? 0 : specialisation.hashCode());
        result = prime * result + id.intValue();
        return result;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }
}
