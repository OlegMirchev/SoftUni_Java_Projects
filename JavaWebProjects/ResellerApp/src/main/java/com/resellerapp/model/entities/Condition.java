package com.resellerapp.model.entities;

import com.resellerapp.util.Enum.ConditionType;

import javax.persistence.*;

@Entity
@Table(name = "conditions")
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ConditionType name;

    @Column(nullable = false)
    private String description;

    public Condition() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ConditionType getName() {
        return name;
    }

    public void setName(ConditionType name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
