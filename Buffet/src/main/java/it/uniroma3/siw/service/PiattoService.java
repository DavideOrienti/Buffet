package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Buffet;
import it.uniroma3.siw.model.Ingredienti;
import it.uniroma3.siw.model.Piatto;
import it.uniroma3.siw.repository.IngredientiRepository;
import it.uniroma3.siw.repository.PiattoRepository;


@Service
public class PiattoService {
	
	@Autowired  // autocarichi
	private PiattoRepository pr;
	
	@Autowired  // autocarichi
	private IngredientiRepository ir;
	
	@Autowired
	private BuffetService bs;
	
	@Autowired
	private IngredientiService is;
	

	
	
	@Transactional // ci pensa Springboot ad apreire e chiude la transazione
	public void savePiatto (Piatto piatto) {
		pr.save(piatto);		
		
		
	}
	
	
	@Transactional
	public Piatto FindById(Long id) {
	
	  return pr.findById(id).get();  // senza get non mi ritornava una persona ma un messaggio java optional
	}
	
	@Transactional
    public Piatto piattoPerId(Long id) {
        Optional<Piatto> optional = pr.findById(id);
        if (optional.isPresent())
            return optional.get();
        else 
            return null;
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
	public List<Ingredienti> FindAllIngredientiById(Long id){
		/* attenzine il metodo pr.findAll() non ritorna un alista ma un iteratore quindi
		 * devo far un modo di copiare ogni valore in un lista che poi faccio ritornare
		 */

		List<Ingredienti> ingredienti = new ArrayList<Ingredienti>();
		List<Piatto> piatti = new ArrayList<Piatto>();
		
		
		int contatore=0;
		
		for(Piatto p : pr.findAll()) {
			piatti.add(p);	
		}
		
		for(Ingredienti i : ir.findAll()) {
			ingredienti.add(i);	
		}
		
		for(int i=0;i<piatti.size();i++) {
				if(piatti.get(i).getId()== id) {
					contatore =i;
					
			}
		}
		
		return piatti.get(contatore).getIngredienti();
	}

	
	@Transactional
	//Creato per verificare l esistenza di un duplicato
	public boolean alreadyExist(Piatto piatto) {
//		return pr.existsById(piatto.getId());
//	}
//	public boolean alreadyExist(Piatto piatto) {
		return pr.existsByNome(piatto.getNome());
	}
	
	public BuffetService getBuffetService() {
		return bs;
	}
	
	public IngredientiService getIngredientiService() {
		return is;
	}
	

	
	@Transactional
	public List<Piatto> PiattiPerBuffet(Buffet buffet) {
		return (List<Piatto>) pr.findByBuffet(buffet);
	}
	
	@Transactional
	public void rimuovi(Piatto piatto) {
		pr.delete(piatto);
		
	}
	
	public boolean BuffetNonEsistente(Piatto piatto) {
		if(piatto.getBuffet()==null) {
			return true;
		}else {
			return false;
		}
	}



	public void update(Piatto piatto) {
		pr.save(piatto);
		
	}


	public Piatto update(Piatto piatto, Long id) {
		
		Piatto p = FindById(id);
	
		Buffet b = bs.FindById(piatto.getBuffet().getId());
		if(p.getId().equals(piatto.getId())) {
		p.setBuffet(piatto.getBuffet());
		p.setId(piatto.getId());
		p.setDescrizione(piatto.getDescrizione());
		p.setIngredienti(piatto.getIngredienti());
		p.setImmagine(piatto.getImmagine());
		p.setOrigine(piatto.getOrigine());
		p.setNome(piatto.getNome());
		this.rimuovi(piatto);
		
		
		pr.save(p);
		}
		return p;

	}
	
	

		
	}


	
	
	
	
	




