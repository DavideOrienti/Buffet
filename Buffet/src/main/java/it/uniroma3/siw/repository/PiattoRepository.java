package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingredienti;
import it.uniroma3.siw.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto,Long> {

	public boolean existsByNome(String nome);
	public List<Piatto> findByBuffet(Buffet buffet);
	
//	  @Query(value = "SELECT * FROM Ingredienti i JOIN Piatto p on i.nome = p.id", nativeQuery = true)// JOIN Author a on a.id_author = s.id_author", nativeQuery = true)
//	    public List<Ingredienti> findIngredientiBy();
	}
	

