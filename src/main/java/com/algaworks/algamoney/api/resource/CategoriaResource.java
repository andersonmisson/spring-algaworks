package com.algaworks.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	/*
	Esta sugestão é bem parecida com a resolução da própria aula, porém ao invés de ficarmos checando 
	manualmente de objeto é null ou não, o Optional nos dá algumas facilidades.

	Neste caso utilizamos o método isPresent, que nada mais é que uma comparação “obj != null”, 
	e finalizamos com um ternário, igual a resolução da aula.
	*/
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
	    Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
	    return categoria.isPresent() ? 
	            ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}
	
	// RequestBody serve para criar uma nova categoria, caso ainda não tenha.
	@PostMapping
	// @ResponseStatus(HttpStatus.CREATED) // STATUS 201: CREATED, como eu coloquei ResponseEntity, ele ja avisa o 201
	public ResponseEntity<Categoria> criar(@RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		// ServletUriComponentsBuilder é um helper do Spring
		// fromCurrentRequestUri = pegando na posição atual de categorias, adicionaro "codigo e colocar na URI"
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(categoriaSalva); // Com isso já sai CODIGO e o NOME na resposta
	}
}
