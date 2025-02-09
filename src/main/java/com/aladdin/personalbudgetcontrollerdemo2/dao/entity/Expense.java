package com.aladdin.personalbudgetcontrollerdemo2.dao.entity;

import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "expense")
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @Column(name = "expense_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeOfExpense category;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "expense_date", updatable = false)
    @UpdateTimestamp
    private LocalDateTime expenseDate;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    @JsonBackReference
    private Budget budget;
}
