package org.lsi.metier;

import org.lsi.dao.OperationRepository;
import org.lsi.entities.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationMetierImpl {
    @Autowired
    private OperationRepository operationRepository;

    public List<Operation> getOperationsByCompteId(String codeCompte) {
        return operationRepository.findByCompteId(codeCompte);
    }
}
