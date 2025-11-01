package ru.urfu.courseanimal.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "animal_expenses")
public class AnimalExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "animal_id", unique = true)
    private Animal animal;

    @Column(name = "monthly_food", precision = 12, scale = 2)
    private BigDecimal monthlyFood;

    @Column(name = "monthly_vet", precision = 12, scale = 2)
    private BigDecimal monthlyVet;

    @Column(name = "monthly_other", precision = 12, scale = 2)
    private BigDecimal monthlyOther;

    public AnimalExpense() { }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }

    public BigDecimal getMonthlyFood() { return monthlyFood; }
    public void setMonthlyFood(BigDecimal monthlyFood) { this.monthlyFood = monthlyFood; }

    public BigDecimal getMonthlyVet() { return monthlyVet; }
    public void setMonthlyVet(BigDecimal monthlyVet) { this.monthlyVet = monthlyVet; }

    public BigDecimal getMonthlyOther() { return monthlyOther; }
    public void setMonthlyOther(BigDecimal monthlyOther) { this.monthlyOther = monthlyOther; }
}