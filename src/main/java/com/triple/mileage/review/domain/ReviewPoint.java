package com.triple.mileage.review.domain;

import javax.persistence.Embeddable;

@Embeddable
public class ReviewPoint {
    private int contentPoint;
    private int photoPoint;
    private int bonusPoint;

    public ReviewPoint() {
    }

    public ReviewPoint(int contentPoint, int photoPoint, int bonusPoint) {
        this.contentPoint = contentPoint;
        this.photoPoint = photoPoint;
        this.bonusPoint = bonusPoint;
    }

    public void increaseContentPoint() {
        this.contentPoint++;
    }

    public void increasePhotoPoint() {
        this.photoPoint++;
    }

    public void increaseBonusPoint() {
        this.bonusPoint++;
    }

    public void init() {
        this.contentPoint = 0;
        this.photoPoint = 0;
        this.bonusPoint = 0;
    }

    public void initBasicPoint() {
        this.contentPoint = 0;
        this.photoPoint = 0;
    }

    public int basicPoint() {
        return this.contentPoint + this.photoPoint;
    }

    public int bonusPoint() {
        return this.bonusPoint;
    }

}
