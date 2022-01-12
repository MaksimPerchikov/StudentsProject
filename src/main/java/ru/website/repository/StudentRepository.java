package ru.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.website.model.Students;

@Repository
public interface StudentRepository extends JpaRepository<Students,Long> {
}
