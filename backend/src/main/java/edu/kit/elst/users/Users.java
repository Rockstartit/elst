package edu.kit.elst.users;

import edu.kit.elst.core.shared.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Users extends JpaRepository<User, UserId> {
}
