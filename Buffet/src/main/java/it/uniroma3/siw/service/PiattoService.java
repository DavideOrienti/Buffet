package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.BuffetRepository;
import it.uniroma3.siw.repository.PiattoRepository;


@Service
public class PiattoService {
	
	@Autowired  // autocarichi
	private PiattoRepository pr;
	
	@Autowired
	private BuffetService bs;
	
	
	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void savePersona (Piatto piatto) {
		pr.save(piatto);		
		
		
	}
	@Transactional
	public Piatto FindById(Long id) {
	
	  return pr.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	@Transactional
	public List<Piatto> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Piatto> persone = new ArrayList<Piatto>();
		
		for(Piatto p : pr.findAll()) {
			persone.add(p);
		}
		return persone;
	}
	@Transactional
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(Piatto piatto) {
		return pr.existsByNome(piatto.getNome());
	}
	
	public BuffetService getBuffetService() {
		return bs;
	}
	
	
	



}
