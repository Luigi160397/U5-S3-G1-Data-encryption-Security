package epicode.u5s3g1.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import epicode.u5s3g1.entities.User;
import epicode.u5s3g1.entities.payloads.UserRegistrationPayload;
import epicode.u5s3g1.exceptions.BadRequestException;
import epicode.u5s3g1.exceptions.NotFoundException;
import epicode.u5s3g1.repositories.UsersRepository;

@Service
public class UsersService {
	@Autowired
	UsersRepository usersRepo;

	public Page<User> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		return usersRepo.findAll(pageable);
	}

	public User findById(UUID id) throws NotFoundException {
		return usersRepo.findById(id).orElseThrow(() -> new NotFoundException("Utente non trovato!"));

	}

	public User create(UserRegistrationPayload u) {

		usersRepo.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new BadRequestException("Email " + user.getEmail() + " already in use!");
		});
		User newUser = new User(u.getUsername(), u.getNome(), u.getEmail(), u.getPassword());
		return usersRepo.save(newUser);
	}

	public User findByEmail(String email) throws NotFoundException {
		return usersRepo.findByEmail(email)
				.orElseThrow(() -> new NotFoundException("Utente con questa mail: " + email + " non trovato!"));
	}

	public void findByIdAndDelete(UUID id) throws NotFoundException {
		User found = this.findById(id);
		usersRepo.delete(found);
	}
}
