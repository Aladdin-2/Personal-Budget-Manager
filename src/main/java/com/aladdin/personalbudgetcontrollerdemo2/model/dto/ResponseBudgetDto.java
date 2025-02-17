package com.aladdin.personalbudgetcontrollerdemo2.model.dto;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Expense;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Income;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBudgetDto {

    private Integer id;
    private Double wallet;
    private String budgetName;
    private LocalDateTime walletChangeDate;
    private List<Expense> expenses;
    private List<Income> incomes;
}
