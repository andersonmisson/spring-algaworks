package com.algaworks.algamoney.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algamoney.api.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	//findby - assinatura do metodo, usado para achar algo na sua lista, to buscando pelo codigo do estado
	List<Cidade> findByEstadoCodigo(Long estadoCodigo);

}