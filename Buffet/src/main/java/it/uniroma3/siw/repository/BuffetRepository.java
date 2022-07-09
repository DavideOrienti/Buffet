package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Piatto;

public interface BuffetRepository extends CrudRepository<Buffet,Long> {
	
	public boolean existsByNome(String nome);
	public List<Piatto> findByPiatti(Buffet buffet);
	public Buffet findByNome(String nome);
	
	


}
