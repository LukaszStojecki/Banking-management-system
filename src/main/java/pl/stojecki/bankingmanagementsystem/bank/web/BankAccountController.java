package pl.stojecki.bankingmanagementsystem.bank.web;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.stojecki.bankingmanagementsystem.bank.dto.BankAccountRequest;
import pl.stojecki.bankingmanagementsystem.bank.service.BankAccountService;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;
import pl.stojecki.bankingmanagementsystem.exception.UnauthorizedException;

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
}
