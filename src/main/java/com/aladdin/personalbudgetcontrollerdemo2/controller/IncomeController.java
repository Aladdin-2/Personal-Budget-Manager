package com.aladdin.personalbudgetcontrollerdemo2.controller;

import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Income;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseExpenseDto;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseIncomeDto;
import com.aladdin.personalbudgetcontrollerdemo2.services.IncomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "aladdin.com/income/")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    @PostMapping(path = "new")
    public ResponseIncomeDto saveIncome(@RequestBody Income income) {
        return incomeService.saveIncome(income);
    }

    @PutMapping(path = "update/{id}")
    public ResponseIncomeDto updateExpense(@PathVariable(name = "id") Integer id,
                                            @RequestParam(name = "category", required = false) String category,
                                            @RequestParam(name = "amount", required = false) Double amount,
                                            @RequestParam(name = "note", required = false) String note) {
        return incomeService.updateIncome(id, category, amount, note);
    }

    @GetMapping(path = "get/{id}")
    public ResponseIncomeDto getIncomeById(@PathVariable(name = "id") Integer id) {
        return incomeService.getIncomeById(id);
    }

    @GetMapping(path = "allIncome")
    public List<ResponseIncomeDto> getIncomes() {
        return incomeService.getIncomes();
    }

    @GetMapping(path = "/filterIncome/")
    public List<ResponseIncomeDto> findIncomeByDate(@RequestParam(name = "year", required = false) int year,
                                                    @RequestParam(name = "month", required = false) int month,
                                                    @RequestParam(name = "day", required = false) int day,
                                                    @RequestParam(name = "hour", required = false) Integer hour,
                                                    @RequestParam(name = "minute", required = false) Integer minute) {
        return incomeService.findIncomeByDate(year, month, day, hour, minute);
    }

    @GetMapping(path = "source/{source}")
    public List<ResponseIncomeDto> findIncomeBySource(@PathVariable(name = "source") String source) {
        return incomeService.findIncomeBySource(source);
    }

    @DeleteMapping(path = "delete/{id}")
    public void deleteById(@PathVariable(name = "id") Integer id) {
        incomeService.deleteById(id);
    }

    @DeleteMapping()
    public void deleteAllIncome() {
        incomeService.deleteAllIncome();
    }

}
