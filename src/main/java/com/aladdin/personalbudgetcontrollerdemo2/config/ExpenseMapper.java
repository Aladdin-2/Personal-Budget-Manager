package com.aladdin.personalbudgetcontrollerdemo2.config;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Expense;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseExpenseDto;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ResponseExpenseDto toTdo(Expense expense, Budget budget) {
        return new ResponseExpenseDto(
                expense.getCategory(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getNote(),
                budget.getBudgetName(),
                budget.getWallet()
        );
    }
}
