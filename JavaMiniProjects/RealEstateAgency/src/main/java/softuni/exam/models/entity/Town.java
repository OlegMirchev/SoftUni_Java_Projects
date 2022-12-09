package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "towns")
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "town_name", nullable = false, unique = true)
    private String townName;

    @Column(nullable = false)
    private int population;

    @OneToMany(mappedBy = "town", targetEntity = Agent.class)
    private Set<Agent> agents;

    @OneToMany(mappedBy = "town", targetEntity = Apartment.class)
    private Set<Apartment> apartments;

    public Town() {
        this.agents = new LinkedHashSet<>();
        this.apartments = new LinkedHashSet<>();
    }

    public Town(String townName, int population) {
        this();
        this.townName = townName;
        this.population = population;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Set<Agent> getAgents() {
        return agents;
    }

    public void setAgents(Set<Agent> agents) {
        this.agents = agents;
    }


    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Town town = (Town) o;
        return id == town.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Successfully imported town %s - %d", this.townName, this.population);
    }
}
