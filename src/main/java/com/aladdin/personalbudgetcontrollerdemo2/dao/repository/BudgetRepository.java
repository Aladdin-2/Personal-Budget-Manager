package com.aladdin.personalbudgetcontrollerdemo2.dao.repository;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    int deleteByWalletChangeDateBetween(LocalDateTime start, LocalDateTime end);

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE budget AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}
