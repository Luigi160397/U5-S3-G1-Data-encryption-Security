package epicode.u5s3g1.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import epicode.u5s3g1.entities.User;
import epicode.u5s3g1.services.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {
	@Autowired
	UsersService usersService;

	@GetMapping("")
	public Page<User> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sortBy) {
		return usersService.find(page, size, sortBy);
	}

	@GetMapping("/{id}")
	public User getById(@PathVariable UUID id) throws Exception {
		return usersService.findById(id);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable UUID id) {
		usersService.findByIdAndDelete(id);
	}

}
