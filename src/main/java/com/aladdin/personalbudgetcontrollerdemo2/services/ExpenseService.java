package com.aladdin.personalbudgetcontrollerdemo2.services;

import com.aladdin.personalbudgetcontrollerdemo2.config.ExpenseMapper;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Expense;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.BudgetRepository;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.ExpenseRepository;
import com.aladdin.personalbudgetcontrollerdemo2.exception.ResourceNotFoundException;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseExpenseDto;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import com.aladdin.personalbudgetcontrollerdemo2.utils.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final ExpenseMapper expenseMapper;
    private static final Logger logger = LoggerFactory.getLogger(ExpenseService.class);
    private final ModelMapper modelMapper;


    public Budget getExistingBudget(Integer budgetId) {
        return budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found!"));
    }

    public void updateBudgetWallet(Budget budget, Double expenseAmount) {
        Double updatedWallet = budget.getWallet() != null ? budget.getWallet() - expenseAmount : expenseAmount;
        budget.setWallet(updatedWallet);
        budgetRepository.save(budget);
    }

    private ResponseExpenseDto toResponseExpenseDto(Expense expense) {
        return expenseMapper.toTdo(expense, expense.getBudget());
    }

    public ResponseExpenseDto saveExpense(Expense expense) {
        Budget budget = getExistingBudget(expense.getBudget().getId());
        updateBudgetWallet(budget, expense.getAmount());
        expense.setBudget(budget);
        Expense save = expenseRepository.save(expense);
        return toResponseExpenseDto(expense);
    }

    public ResponseExpenseDto updateExpense(Integer id, String category, Double amount, String note) {
        Expense expenseFromDb = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No expense found with ID: " + id));
        Budget existingBudget = getExistingBudget(id);
        if (category != null && !category.trim().isEmpty()) {
            TypeOfExpense typeOfExpense = TypeOfExpense.valueOf(category);
            expenseFromDb.setCategory(typeOfExpense);
        }
        if (amount != null) {
            expenseFromDb.setAmount(amount);
        }

        if (note != null && !note.trim().isEmpty()) {
            expenseFromDb.setNote(note);
        }
        updateBudgetWallet(existingBudget, expenseFromDb.getAmount());
        expenseFromDb.setBudget(existingBudget);
        expenseRepository.save(expenseFromDb);
        return toResponseExpenseDto(expenseFromDb);
    }

    public ResponseExpenseDto getExpenseById(Integer id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No expense found with ID: " + id));
        return toResponseExpenseDto(expense);
    }

    public List<ResponseExpenseDto> findExpensesByDate(int year, int month, int day, Integer hour, Integer minute) {
        LocalDateTime startOfDay = DateTimeUtil.getStartOfDay(year, month, day, hour, minute);
        LocalDateTime endOfDay = DateTimeUtil.getEndOfDay(year, month, day);
        expenseRepository.findByExpenseDateBetween(startOfDay, endOfDay);
        List<Expense> byExpenseDateStartWith = expenseRepository.findByExpenseDateBetween(startOfDay, endOfDay);
        if (!byExpenseDateStartWith.isEmpty()) {
            return byExpenseDateStartWith.stream().map(this::toResponseExpenseDto).toList();
        }
        throw new ResourceNotFoundException("No expenses found for the given date and time range");
    }

    public List<ResponseExpenseDto> findExpenseByCategory(String category) {
        TypeOfExpense typeOfExpense = TypeOfExpense.valueOf(category);
        List<Expense> byCategoryEqualsIgnoreCase = expenseRepository.findByCategoryEqualsIgnoreCase(typeOfExpense);
        if (!byCategoryEqualsIgnoreCase.isEmpty()) {
            return byCategoryEqualsIgnoreCase.stream().map(this::toResponseExpenseDto).toList();
        }
        throw new ResourceNotFoundException("There are no expenses in this category!: " + category);
    }

    public List<ResponseExpenseDto> getExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        if (!expenses.isEmpty()) {
            return expenses.stream().map(this::toResponseExpenseDto).toList();
        }
        throw new ResourceNotFoundException("There is no expense in the budget!👌👌");
    }

    public void deleteById(Integer id) {
        expenseRepository.deleteById(id);
        logger.info("Expense deleted: {}", id);
    }

    public void deleteAllExpense() {
        expenseRepository.deleteAll();
        expenseRepository.resetAutoIncrement();

    }

}