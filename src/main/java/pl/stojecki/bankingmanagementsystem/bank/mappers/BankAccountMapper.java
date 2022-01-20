package pl.stojecki.bankingmanagementsystem.bank.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.stojecki.bankingmanagementsystem.bank.dto.BankAccountOut;
import pl.stojecki.bankingmanagementsystem.bank.model.BankAccount;

@Mapper(componentModel = "spring", uses = SaldoMapper.class)
public interface BankAccountMapper {

    @Mapping(source = "number", target = "number")
    @Mapping(source = "bankAccountType", target = "bankAccountType")
    @Mapping(source = "saldo", target = "saldo")
    BankAccountOut entityToDTO(BankAccount bankAccount);

}
