package ru.kolontsov.testtask.TestTask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolontsov.testtask.TestTask.entities.Districts;

import java.util.List;


@Repository
public interface DistrictsRepository extends JpaRepository<Districts, Long> {

//    Districts findById(Long id);
    Districts findByName(String name);

    List<Districts> findByCode(Integer code);

    List<Districts> findByNameAndCode(String name, Integer code);
}
