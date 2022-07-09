package it.uniroma3.siw.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Buffet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
    private String nome;
	
	//@Column(nullable = false)
	
	private String descrizione;
	
	@ManyToOne (cascade = {CascadeType.PERSIST})
	@NotNull(message = "{chef.nullo}")
	private Chef chef;
	
	
	
	@OneToMany (mappedBy = "buffet", cascade = {CascadeType.PERSIST , CascadeType.REMOVE})
	
	private List<Piatto> piatti;
	
	public Buffet() {
		
	}

	public Buffet(String nome) {
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<Piatto> getPiatti() {
		return piatti;
	}

	public void setPiatti(List<Piatto> piatti) {
		this.piatti = piatti;
	}
	
	public void removePitto(Piatto p) {
		for(int i=0;i<piatti.size();i++) {
		
		}
	}
	
	
	
	
	

}
