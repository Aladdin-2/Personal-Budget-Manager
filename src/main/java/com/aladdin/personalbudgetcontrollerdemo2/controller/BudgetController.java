package com.aladdin.personalbudgetcontrollerdemo2.controller;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseBudgetDto;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.SourceOfIncome;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import com.aladdin.personalbudgetcontrollerdemo2.services.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "aladdin.com/budget/")
@RequiredArgsConstructor
public class BudgetController {
    private final BudgetService budgetService;

    @PostMapping(path = "new")
    public Budget saveBudget(@RequestBody Budget budget) {
        return budgetService.saveBudget(budget);
    }

    @GetMapping(path = "getAll")
    public List<ResponseBudgetDto> getBudgets() {
        return budgetService.getBudgets();
    }



    @GetMapping(path = "incomeGroup")
    public Map<SourceOfIncome, Double> getIncomeGroup() {
        return budgetService.getIncomeGroup();
    }

    @GetMapping(path = "expenseGroup")
    public Map<TypeOfExpense, Double> getExpense() {
        return budgetService.getExpense();
    }

    @GetMapping(path = "findById/{id}")
    public Budget getBudgetById(@PathVariable(name = "id") Integer id) {
        return budgetService.getBudgetById(id);
    }

    @DeleteMapping(path = "delete/All")
    public void deleteAllBudget() {
        budgetService.deleteAllBudget();
    }
}