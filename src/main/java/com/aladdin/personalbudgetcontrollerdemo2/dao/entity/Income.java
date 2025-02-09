package com.aladdin.personalbudgetcontrollerdemo2.dao.entity;

import com.aladdin.personalbudgetcontrollerdemo2.model.enums.SourceOfIncome;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "income")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "source", nullable = false)
    @Enumerated(EnumType.STRING)
    private SourceOfIncome source;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "income_date")
    @UpdateTimestamp
    private LocalDateTime incomeDate;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    @JsonBackReference
    private Budget budget;
}