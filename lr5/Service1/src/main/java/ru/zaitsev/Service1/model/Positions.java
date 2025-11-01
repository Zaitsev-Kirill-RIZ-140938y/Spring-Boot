package ru.zaitsev.Service1.model;

import lombok.Getter;

@Getter
public enum Positions {

    DEV(2.2, false),

    HR(1.2, false),

    TL(2.6, true),

    INTERN(1.5, false),

    DIRECTOR(3.5, true),

    ANALYST(2.1, false);



    private final double positionCoefficient;
    private  final boolean isManager;

    Positions(double positionCoefficient, boolean isManager) {
        this.positionCoefficient = positionCoefficient;
        this.isManager = isManager;
    }

}
