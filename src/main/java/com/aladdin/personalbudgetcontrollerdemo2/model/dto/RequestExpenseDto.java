package com.aladdin.personalbudgetcontrollerdemo2.model.dto;

import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestExpenseDto {

    private TypeOfExpense category;
    private Double amount;
    private String note;

}
