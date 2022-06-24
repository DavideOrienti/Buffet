package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Chef;
import it.uniroma3.siw.repository.BuffetRepository;
import it.uniroma3.siw.repository.ChefRepository;

@Service
public class ChefService {
	
	@Autowired  // autocarichi
	private ChefRepository cr;
	
	
	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void savePersona (Chef chef) {
		cr.save(chef);		
		
		
	}
	
	public Chef FindById(Long id) {
	
	  return cr.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	public List<Chef> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Chef> chef = new ArrayList<Chef>();
		
		for(Chef p : cr.findAll()) {
			chef.add(p);
		}
		return chef;
	}
	
//	@Transactional
//	public List<Chef> FindAll() {
//		return (List<Chef>) cr.findAll();
//	}
	
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(Chef chef) {
		return cr.existsByNomeAndCognomeAndNazionalita(chef.getNome(),chef.getCognome(),chef.getNazionalita());
	}



}
