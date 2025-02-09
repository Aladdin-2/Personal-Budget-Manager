package com.aladdin.personalbudgetcontrollerdemo2.model.dto;

import com.aladdin.personalbudgetcontrollerdemo2.model.enums.SourceOfIncome;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseIncomeDto {
    private SourceOfIncome source;
    private Double amount;
    private LocalDateTime incomeDate;
    private String note;
    private String budgetName;
    private Double wallet;

}
