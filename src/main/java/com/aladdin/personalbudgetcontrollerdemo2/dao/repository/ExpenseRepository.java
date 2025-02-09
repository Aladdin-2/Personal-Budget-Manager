package com.aladdin.personalbudgetcontrollerdemo2.dao.repository;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Expense;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    List<Expense> findByExpenseDateBetween(LocalDateTime start, LocalDateTime end);

    @Modifying
    @Transactional
    int deleteByExpenseDateBetween(LocalDateTime start, LocalDateTime end);

    @Modifying
    @Query(value = "ALTER TABLE expense AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

    @Query("SELECT e FROM Expense e WHERE e.category=:category")
    List<Expense> findByCategoryEqualsIgnoreCase(TypeOfExpense category);
}
