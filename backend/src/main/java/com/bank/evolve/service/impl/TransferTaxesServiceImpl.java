package com.bank.evolve.service.impl;

import com.bank.evolve.dto.request.TransferTaxesRequest;
import com.bank.evolve.dto.request.TransferTaxesUpdateRequest;
import com.bank.evolve.entity.TransferTaxes;
import com.bank.evolve.service.TransferTaxesService;
import com.bank.evolve.repository.TransferTaxesRepository;
import com.bank.evolve.util.AppError;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferTaxesServiceImpl implements TransferTaxesService {

    private final TransferTaxesRepository transferTaxesRepository;

    public TransferTaxesServiceImpl(TransferTaxesRepository transferTaxesRepository) {
        this.transferTaxesRepository = transferTaxesRepository;
    }

    public List<TransferTaxes> findAll(){
        return transferTaxesRepository.findAll();
    }

    public TransferTaxes findById(Long id){
        return transferTaxesRepository.findById(id).orElseThrow(() -> new AppError("Taxa de transferência não encontrada", HttpStatus.NOT_FOUND));
    }

    public TransferTaxes createNew(TransferTaxesRequest transferTaxes){
        TransferTaxes newTransferTaxes = new TransferTaxes();
        newTransferTaxes.setAmountDays(transferTaxes.getAmountDays());
        newTransferTaxes.setFixedTax(transferTaxes.getFixedTax());
        newTransferTaxes.setTaxPercentage(transferTaxes.getTaxPercentage());
        return transferTaxesRepository.save(newTransferTaxes);
    }

    public TransferTaxes update(Long id, TransferTaxesUpdateRequest transferTaxes){
        TransferTaxes foundTax = transferTaxesRepository.findById(id).orElseThrow(
                () -> new AppError("Taxa de transferência não encontrada", HttpStatus.NOT_FOUND));

        foundTax.setAmountDays(transferTaxes.getAmountDays());
        foundTax.setFixedTax(transferTaxes.getFixedTax());
        foundTax.setTaxPercentage(transferTaxes.getTaxPercentage());
        return transferTaxesRepository.save(foundTax);
    }

    public void deleteById(Long id){
        TransferTaxes foundTax = transferTaxesRepository.findById(id).orElseThrow(
                () -> new AppError("Taxa de transferência não encontrada", HttpStatus.NOT_FOUND));
        transferTaxesRepository.delete(foundTax);
    }

    public Double calculateTax(Double amount, Long days){
        List<TransferTaxes> taxes = transferTaxesRepository.findAll();

        TransferTaxes applicableTax = taxes.stream()
                .filter(tax -> tax.getAmountDays() <= days)
                .max((tax1, tax2) -> Integer.compare(tax1.getAmountDays(), tax2.getAmountDays()))
                .orElseThrow(() -> new AppError("Nenhuma taxa aplicável encontrada para a quantidade de dias informada", HttpStatus.NOT_FOUND));

        Double taxAmount = applicableTax.getFixedTax() + (amount * applicableTax.getTaxPercentage() / 100);

        return amount + taxAmount;
    }

}
