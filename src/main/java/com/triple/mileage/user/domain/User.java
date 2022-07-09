package com.triple.mileage.user.domain;

import java.util.UUID;
import javax.persistence.*;

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    private int point;

    protected User() {
    }

    public User(int point) {
        this(null, point);
    }

    public User(UUID uuid, int point) {
        this.uuid = uuid;
        this.point = point;
    }

    public void increasePoint(int point) {
        this.point += point;
    }

    public void decreasePoint(int point) {
        this.point -= point;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getPoint() {
        return point;
    }
}
