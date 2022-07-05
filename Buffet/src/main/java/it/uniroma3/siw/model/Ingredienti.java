package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;


@Entity
public class Ingredienti {
	
	@Id
	private String nome;
	
	@NotBlank
	private String origine;
	
	@NotBlank
	private String descrizione;
	
	@ManyToMany
	private List<Piatto> piatti ;
	
	public Ingredienti() {
		// TODO Auto-generated constructor stub
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}
	
	public void removePiatto(Piatto p) {
		this.piatti.remove(p);
		
	}
	
	
	
	
	
	
	
	

}
