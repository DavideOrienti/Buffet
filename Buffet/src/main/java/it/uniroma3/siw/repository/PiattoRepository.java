package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto,Long> {

	public boolean existsByNome(String nome);
}
