package ru.alexey_ten.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexey_ten.user_service.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
