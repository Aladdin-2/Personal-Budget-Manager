package com.aladdin.personalbudgetcontrollerdemo2.config;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Income;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseIncomeDto;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {

    public ResponseIncomeDto toDto(Income income, Budget budget) {
        return new ResponseIncomeDto(
                income.getSource(),
                income.getAmount(),
                income.getIncomeDate(),
                income.getNote(),
                budget.getBudgetName(),
                budget.getWallet()
        );
    }

    public Income fromDto(ResponseIncomeDto incomeDto, Budget budget) {
        return new Income(
                null,
                incomeDto.getSource(),
                incomeDto.getAmount(),
                incomeDto.getIncomeDate(),
                incomeDto.getNote(),
                budget
        );
    }
}