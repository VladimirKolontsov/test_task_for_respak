package ru.kolontsov.testtask.TestTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolontsov.testtask.TestTask.entities.Farmer;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {
}
