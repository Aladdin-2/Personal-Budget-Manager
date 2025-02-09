package com.aladdin.personalbudgetcontrollerdemo2.services;

import com.aladdin.personalbudgetcontrollerdemo2.config.BudgetMapper;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Expense;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Income;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.BudgetRepository;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.IncomeRepository;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseBudgetDto;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.SourceOfIncome;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget getBudgetById(Integer id) {
        return budgetRepository.findById(id).orElseThrow();
    }

    public List<ResponseBudgetDto> getBudgets() {
        List<Budget> all = budgetRepository.findAll();
        return all.stream().map(budgetMapper::toTdo).toList();
    }


    public Map<SourceOfIncome, Double> getIncomeGroup() {
        List<Budget> all = budgetRepository.findAll();
        return all.stream()
                .flatMap(budget -> budget.getIncomes().stream())
                .collect(Collectors.groupingBy(Income::getSource, Collectors.summingDouble(Income::getAmount)));
    }

    public Map<TypeOfExpense, Double> getExpense() {
        List<Budget> all = budgetRepository.findAll();
        return all.stream()
                .flatMap(budget -> budget.getExpenses().stream())
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));
    }


    public void deleteAllBudget() {
        budgetRepository.deleteAll();
        budgetRepository.resetAutoIncrement();
    }
}