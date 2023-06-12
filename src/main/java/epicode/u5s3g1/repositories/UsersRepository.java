package epicode.u5s3g1.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import epicode.u5s3g1.entities.User;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
	Optional<User> findByEmail(String email);
}
