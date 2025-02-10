package com.aladdin.personalbudgetcontrollerdemo2.services;

import com.aladdin.personalbudgetcontrollerdemo2.config.BudgetMapper;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Expense;
import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Income;
import com.aladdin.personalbudgetcontrollerdemo2.dao.repository.BudgetRepository;
import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseBudgetDto;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.SourceOfIncome;
import com.aladdin.personalbudgetcontrollerdemo2.model.enums.TypeOfExpense;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;
    private final EmailService emailService;
    private final Set<Integer> sentBudgetIds = new HashSet<>();

    @Value("${email.toEmail}")
    private String toEmail;

    public Budget saveBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget getBudgetById(Integer id) {
        return budgetRepository.findById(id).orElseThrow();
    }

    public List<ResponseBudgetDto> getBudgets() {
        List<Budget> all = budgetRepository.findAll();
        return all.stream().map(budgetMapper::toTdo).toList();
    }


    public Map<SourceOfIncome, Double> getIncomeGroup() {
        List<Budget> all = budgetRepository.findAll();
        return all.stream()
                .flatMap(budget -> budget.getIncomes().stream())
                .collect(Collectors.groupingBy(Income::getSource, Collectors.summingDouble(Income::getAmount)));
    }

    public Map<TypeOfExpense, Double> getExpense() {
        List<Budget> all = budgetRepository.findAll();
        return all.stream()
                .flatMap(budget -> budget.getExpenses().stream())
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));
    }

    @Scheduled(cron = "0 * * * * ?")
    public void checkBudgetAmount() {
        List<Budget> allBudgets = budgetRepository.findAll();
        List<Budget> list = allBudgets.stream().filter(budget -> (budget.getWallet() < 0 || budget.getWallet() == 0) && !sentBudgetIds.contains(budget.getId())).toList();
        for (Budget budget : list) {
            emailSender("There are " + budget.getWallet() + " manats in this budget: " + budget.getBudgetName());
            sentBudgetIds.add(budget.getId());
        }
    }

    public void emailSender(String text) {
        String subject = "WARNING!";
        FileSystemResource imageFile = new FileSystemResource(new File("C:/Users/Asus/Downloads/for budget management..jpg"));
        emailService.sendEmail(toEmail, subject, text, imageFile);
    }

    public void deleteAllBudget() {
        budgetRepository.deleteAll();
        budgetRepository.resetAutoIncrement();
    }
}