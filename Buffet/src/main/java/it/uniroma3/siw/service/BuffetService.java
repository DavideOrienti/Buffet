package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.repository.BuffetRepository;

@Service
public class BuffetService {
	
	@Autowired  // autocarichi
	private BuffetRepository br;
	
	
	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void savePersona (Buffet buffet) {
		br.save(buffet);		
		
		
	}
	
	public Buffet FindById(Long id) {
	
	  return br.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	public List<Buffet> FindAll(){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Buffet> buffet = new ArrayList<Buffet>();
		
		for(Buffet p : br.findAll()) {
			buffet.add(p);
		}
		return buffet;
	}
	
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(Buffet buffet) {
		return br.existsByNome(buffet.getNome());
	}


}
