package com.aladdin.personalbudgetcontrollerdemo2.dao.repository;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Expense;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Income;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.SourceOfIncome;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Integer> {
    List<Income> findByIncomeDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT i FROM Income i WHERE i.source=:source")
    List<Income> findBySourceEqualsIgnoreCase(SourceOfIncome source);

    @Modifying
    @Query(value = "ALTER TABLE income AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();

}

