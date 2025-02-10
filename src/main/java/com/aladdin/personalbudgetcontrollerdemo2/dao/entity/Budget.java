package com.aladdin.personalbudgetcontrollerdemo2.dao.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "wallet")
    private Double wallet;

    @Column(name = "budget_name")
    private String budgetName;

    @Column(name = "change_date")
    @UpdateTimestamp
    private LocalDateTime walletChangeDate;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "budget", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "budget", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<Income> incomes = new ArrayList<>();
}
