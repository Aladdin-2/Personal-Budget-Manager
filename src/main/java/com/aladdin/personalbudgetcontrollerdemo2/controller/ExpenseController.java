package com.aladdin.personalbudgetcontrollerdemo2.controller;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Expense;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseExpenseDto;
import com.aladdin.personalbudgetcontrollerdemo2.services.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "aladdin.com/expense/")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping(path = "new")
    public ResponseExpenseDto saveExpense(@RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    @GetMapping(path = "get/{id}")
    public ResponseExpenseDto getExpenseById(@PathVariable(name = "id") Integer id) {
        return expenseService.getExpenseById(id);
    }

    @GetMapping(path = "getAllExpense")
    public List<ResponseExpenseDto> getExpenses() {
        return expenseService.getExpenses();
    }

    @PutMapping(path = "update/{id}")
    public ResponseExpenseDto updateExpense(@PathVariable(name = "id") Integer id,
                                            @RequestParam(name = "category", required = false) String category,
                                            @RequestParam(name = "amount", required = false) Double amount,
                                            @RequestParam(name = "note", required = false) String note) {
        return expenseService.updateExpense(id, category, amount, note);
    }

    @GetMapping(path = "filterDate")
    public List<ResponseExpenseDto> findIncomeByDate(
            @RequestParam(name = "year", required = false) int year,
            @RequestParam(name = "month", required = false) int month,
            @RequestParam(name = "day", required = false) int day,
            @RequestParam(name = "hour", required = false) Integer hour,
            @RequestParam(name = "minute", required = false) Integer minute) {
        return expenseService.findExpensesByDate(year, month, day, hour, minute);
    }

    @GetMapping(path = "category/{category}")
    public List<ResponseExpenseDto> findExpenseByCategory(@PathVariable(name = "category") String category) {
        return expenseService.findExpenseByCategory(category);
    }

    @DeleteMapping(path = "delete/{id}")
    public void deleteById(@PathVariable(name = "id") Integer id) {
        expenseService.deleteById(id);
    }

    @DeleteMapping()
    public void deleteAllExpense() {
        expenseService.deleteAllExpense();
    }
}