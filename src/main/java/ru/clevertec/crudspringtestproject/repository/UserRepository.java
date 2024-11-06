package ru.clevertec.crudspringtestproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.crudspringtestproject.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
