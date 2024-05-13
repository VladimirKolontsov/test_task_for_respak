package ru.kolontsov.testtask.TestTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kolontsov.testtask.TestTask.entities.FarmerDistrict;

public interface FarmerDistrictRepository extends JpaRepository<FarmerDistrict, Long> {
}
