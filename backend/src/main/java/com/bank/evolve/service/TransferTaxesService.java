package com.bank.evolve.service;

import com.bank.evolve.dto.request.TransferTaxesRequest;
import com.bank.evolve.dto.request.TransferTaxesUpdateRequest;
import com.bank.evolve.entity.TransferTaxes;

import java.util.List;

public interface TransferTaxesService {

    List<TransferTaxes> findAll();
    TransferTaxes findById(Long id);
    TransferTaxes createNew(TransferTaxesRequest transferTaxes);
    TransferTaxes update(Long id, TransferTaxesUpdateRequest transferTaxes);
    void deleteById(Long id);

    Double calculateTax(Double amount, Long days);

}
