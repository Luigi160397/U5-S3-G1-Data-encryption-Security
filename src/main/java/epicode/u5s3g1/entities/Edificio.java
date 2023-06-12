package epicode.u5s3g1.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "edifici")
@JsonIgnoreProperties({ "codice" })
public class Edificio {

	@Id
	@GeneratedValue
	private UUID id;
	private String nome;
	private String indirizzo;
	@Convert(converter = CodiceEdificioConverter.class)
	private String codice;

	@ManyToOne
	private Citta citta;

	public Edificio(String nome, String indirizzo, Citta citta, String codice) {
		super();
		this.nome = nome;
		this.indirizzo = indirizzo;
		this.codice = codice;
		this.citta = citta;
	}

}
