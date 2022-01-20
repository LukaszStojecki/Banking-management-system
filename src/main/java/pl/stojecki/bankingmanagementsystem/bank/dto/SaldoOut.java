package pl.stojecki.bankingmanagementsystem.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaldoOut {

    private Long id;
    private BigDecimal value;
}
