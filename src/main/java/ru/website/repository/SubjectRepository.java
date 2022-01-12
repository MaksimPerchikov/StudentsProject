package ru.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.website.model.Subject;
@Repository
public interface SubjectRepository extends JpaRepository<Subject,Long> {
}
