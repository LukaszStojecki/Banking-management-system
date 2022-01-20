package pl.stojecki.bankingmanagementsystem.bank.mappers;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.stojecki.bankingmanagementsystem.bank.dto.SaldoOut;
import pl.stojecki.bankingmanagementsystem.bank.model.Saldo;

@Mapper(componentModel = "spring")
public interface SaldoMapper {

    @Mapping(source = "value", target = "value")
    SaldoOut saldoToSaldoOut(Saldo saldo);
}
