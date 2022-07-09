package com.triple.mileage.user.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private int point;

    protected User() {
    }

    public User(int point) {
        this(null, point);
    }

    public User(UUID id, int point) {
        this.id = id;
        this.point = point;
    }

    public void increasePoint(int point) {
        this.point += point;
    }

    public void initPoint() {
        this.point = 0;
    }

    public UUID getId() {
        return id;
    }

    public int getPoint() {
        return point;
    }
}
