    package com.aladdin.personalbudgetcontrollerdemo2.config;

    import com.aladdin.personalbudgetcontrollerdemo2.dao.entity.Budget;
    import com.aladdin.personalbudgetcontrollerdemo2.model.dto.ResponseBudgetDto;
    import org.springframework.stereotype.Component;

    @Component
    public class BudgetMapper {

        public ResponseBudgetDto toTdo(Budget budget) {
            return new ResponseBudgetDto(
                    budget.getId(),
                    budget.getWallet(),
                    budget.getBudgetName(),
                    budget.getWalletChangeDate(),
                    budget.getExpenses(),
                    budget.getIncomes()
            );
        }
    }
