package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingredienti;
import it.uniroma3.siw.repository.IngredientiRepository;


@Service
public class IngredientiService {
	
	@Autowired  // autocarichi
	private IngredientiRepository ir;
	
	
	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void savePersona (Ingredienti ingredienti) {
		ir.save(ingredienti);		
		
		
	}
	
	public Ingredienti FindById(Long id) {
	
	  return ir.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	public List<Ingredienti> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Ingredienti> persone = new ArrayList<Ingredienti>();
		
		for(Ingredienti p : ir.findAll()) {
			persone.add(p);
		}
		return persone;
	}
	
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(Ingredienti ing) {
		return ir.existsByNome(ing.getNome());
	}



}
