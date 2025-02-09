package com.aladdin.personalbudgetcontrollerdemo2.model.dto;

import com.aladdin.personalbudgetcontrollerdemo2.model.enums.SourceOfIncome;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseExpenseDto {
    private TypeOfExpense category;
    private Double amount;
    private LocalDateTime incomeDate;
    private String note;
    private String budgetName;
    private Double wallet;
}
