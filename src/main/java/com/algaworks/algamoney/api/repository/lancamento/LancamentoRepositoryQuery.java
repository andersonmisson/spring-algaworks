package com.algaworks.algamoney.api.repository.lancamento;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);

}
