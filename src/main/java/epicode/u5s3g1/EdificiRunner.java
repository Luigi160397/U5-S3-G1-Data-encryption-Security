package epicode.u5s3g1;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import epicode.u5s3g1.entities.Citta;
import epicode.u5s3g1.entities.Edificio;
import epicode.u5s3g1.repositories.CittaRepository;
import epicode.u5s3g1.repositories.EdificiRepository;

@Component
public class EdificiRunner implements CommandLineRunner {
	@Autowired
	EdificiRepository edificiRepo;
	@Autowired
	CittaRepository cittaRepo;

	@Override
	public void run(String... args) throws Exception {
		Faker faker = new Faker(new Locale("it"));

		List<Edificio> edificiDB = edificiRepo.findAll();
		List<Citta> cittaDB = cittaRepo.findAll();

		if (edificiDB.size() == 0) {
			for (int i = 0; i < 10; i++) {
				try {
					String nome = faker.pokemon().name();
					String indirizzo = faker.address().fullAddress();
					int randomIndex = new Random().nextInt(cittaDB.size());
					Citta randomCitta = cittaDB.get(randomIndex);

					String randomCodice = faker.internet().password();

					Edificio edificio = new Edificio(nome, indirizzo, randomCitta, randomCodice);
					edificiRepo.save(edificio);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}

	}

}
