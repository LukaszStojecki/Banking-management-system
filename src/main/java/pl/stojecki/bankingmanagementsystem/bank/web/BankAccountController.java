package pl.stojecki.bankingmanagementsystem.bank.web;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.stojecki.bankingmanagementsystem.bank.dto.BankAccountOut;
import pl.stojecki.bankingmanagementsystem.bank.dto.BankAccountRequest;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccountType;
import pl.stojecki.bankingmanagementsystem.bank.service.BankAccountService;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;
import pl.stojecki.bankingmanagementsystem.exception.UnauthorizedException;

import java.util.List;

@RestController
@RequestMapping("api/bank/account")
@AllArgsConstructor
public class BankAccountController {

    private BankAccountService bankAccountService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody BankAccountRequest bankAccountRequest) throws NotFoundException, UnauthorizedException {
        bankAccountService.createBankAccount(bankAccountRequest);
        return new ResponseEntity<>("Bank account created successful", HttpStatus.OK);
    }

    @GetMapping("/byUser")
    public List<BankAccountOut> findByUser() {
        return bankAccountService.findByUser();
    }

    @GetMapping("/{id}")
    public BankAccountOut findById(@PathVariable("id") Long id) {
        return bankAccountService.findById(id);
    }

    @GetMapping("/byType")
    public List<BankAccountType> findByType() {
        return bankAccountService.findAllBankAccountType();
    }

}
