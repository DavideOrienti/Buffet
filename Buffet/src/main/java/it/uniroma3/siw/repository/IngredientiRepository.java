package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Ingredienti;

public interface IngredientiRepository extends CrudRepository<Ingredienti,Long> {

	public boolean existsByNome(String nome);
}
