package org.lsi.metier;

import org.lsi.entities.Operation;

import java.util.List;

public interface OperationMetier {

    public List<Operation> getOperationsByCompteId(String codeCompte);

    }
