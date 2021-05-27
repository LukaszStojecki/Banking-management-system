package pl.stojecki.bankingmanagementsystem.bank.web;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.stojecki.bankingmanagementsystem.bank.dto.PaymentRequest;
import pl.stojecki.bankingmanagementsystem.bank.service.SaldoService;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;

@RestController
@RequestMapping("api/bank/saldo")
@AllArgsConstructor
public class SaldoController {

    private final SaldoService saldoService;

    @PostMapping("/payment")
    public void create(@RequestBody PaymentRequest paymentRequest) throws NotFoundException {
        saldoService.addPayment(paymentRequest);
    }
}
