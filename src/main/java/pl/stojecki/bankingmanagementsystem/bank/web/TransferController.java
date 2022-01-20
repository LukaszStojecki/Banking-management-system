package pl.stojecki.bankingmanagementsystem.bank.web;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.stojecki.bankingmanagementsystem.bank.dto.TransferRequest;
import pl.stojecki.bankingmanagementsystem.bank.service.TransferService;
import pl.stojecki.bankingmanagementsystem.exception.ConflictException;
import pl.stojecki.bankingmanagementsystem.exception.NotFoundException;

@RestController
@RequestMapping("api/bank/transfer")
@AllArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/create")
    public void create(@RequestBody TransferRequest transferRequest) throws NotFoundException, ConflictException {
        transferService.transferToAccount(transferRequest);

    }
}
