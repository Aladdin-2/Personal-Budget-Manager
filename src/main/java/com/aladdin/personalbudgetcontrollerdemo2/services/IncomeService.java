package com.aladdin.personalbudgetcontrollerdemo2.services;

import com.aladdin.personalbudgetcontrollerdemo2.config.IncomeMapper;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Income;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.BudgetRepository;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.IncomeRepository;
import com.aladdin.personalbudgetcontrollerdemo2.exception.ResourceNotFoundException;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseIncomeDto;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.SourceOfIncome;
import com.aladdin.personalbudgetcontrollerdemo2.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IncomeService {
    private final IncomeRepository incomeRepository;
    private final BudgetRepository budgetRepository;
    private final IncomeMapper incomeMapper;

    private Budget getExistingBudget(Integer budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found!"));
    }

    private void updateBudgetWallet(Budget budget, Double incomeAmount) {
        Double updatedWallet = budget.getWallet() != null ? budget.getWallet() + incomeAmount : incomeAmount;
        budget.setWallet(updatedWallet);
        budgetRepository.save(budget);
    }

    private ResponseIncomeDto toResponseIncomeDto(Income income) {
        return incomeMapper.toDto(income, income.getBudget());
    }

    public ResponseIncomeDto saveIncome(Income income) {
        Budget budget = getExistingBudget(income.getBudget().getId());
        updateBudgetWallet(budget, income.getAmount());
        income.setBudget(budget);
        income = incomeRepository.save(income);
        log.info("Expense added: {}", income);
        return toResponseIncomeDto(income);
    }

    public ResponseIncomeDto updateIncome(Integer id, String source, Double amount, String note) {
        Budget existingBudget = getExistingBudget(id);
        Income income = incomeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No income matching this ID was found: " + id));
        if (source != null && !source.trim().isEmpty()) {
            SourceOfIncome sourceOfIncome = SourceOfIncome.valueOf(source);
            income.setSource(sourceOfIncome);
        }
        if (amount != null) {
            income.setAmount(amount);
        }
        if (note != null && !note.trim().isEmpty()) {
            income.setNote(note);
        }
        updateBudgetWallet(existingBudget, income.getAmount());
        income.setBudget(existingBudget);
        incomeRepository.save(income);
        return toResponseIncomeDto(income);
    }

    public ResponseIncomeDto getIncomeById(Integer id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No income matching this ID was found: " + id));
        return toResponseIncomeDto(income);
    }

    public List<ResponseIncomeDto> findIncomeByDate(int year, int month, int day, Integer hour, Integer minute) {
        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(year, month, day, hour, minute);
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(year, day, hour);
        List<Income> byIncomeDateBetween = incomeRepository.findByIncomeDateBetween(startOfDay, endOfDay);
        if (!byIncomeDateBetween.isEmpty()) {
            return byIncomeDateBetween.stream().map(this::toResponseIncomeDto).toList();
        }
        throw new ResourceNotFoundException("No data in this time range: year-> {0} , month-> {1}, day-> {2} " + year, month, day);
    }

    public List<ResponseIncomeDto> findIncomeBySource(String source) {
        SourceOfIncome sourceType = SourceOfIncome.valueOf(source);
        List<Income> sourceEqualsIgnoreCase = incomeRepository.findBySourceEqualsIgnoreCase(sourceType);
        if (!sourceEqualsIgnoreCase.isEmpty()) {
            return sourceEqualsIgnoreCase.stream().map(this::toResponseIncomeDto).toList();
        }
        throw new ResourceNotFoundException("There are no incomes in this source!: " + source);
    }

    public List<ResponseIncomeDto> getIncomes() {
        List<Income> allIncomes = incomeRepository.findAll();
        if (!allIncomes.isEmpty()) {
            return allIncomes.stream().map(this::toResponseIncomeDto).toList();
        }
        throw new ResourceNotFoundException("There is no income in the database!👌👌");
    }

    public void deleteById(Integer id) {
        log.info("Income deleted: {}", id);
        incomeRepository.deleteById(id);
    }

    public void deleteAllIncome() {
        incomeRepository.deleteAll();
        incomeRepository.resetAutoIncrement();
    }


}