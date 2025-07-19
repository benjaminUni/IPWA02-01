package de.iubh.webanwendungen.model;

import jakarta.persistence.*;

@Entity
public class GhostNet {
	
	@ManyToOne
	private Person salvagingPerson;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;
    private Double area;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Person getSalvagingPerson() {
        return salvagingPerson;
    }

    public void setSalvagingPerson(Person salvagingPerson) {
        this.salvagingPerson = salvagingPerson;
    }
}
