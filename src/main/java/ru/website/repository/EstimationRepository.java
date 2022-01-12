package ru.website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.website.model.Estimation;

@Repository
public interface EstimationRepository extends JpaRepository<Estimation,Long> {
}
