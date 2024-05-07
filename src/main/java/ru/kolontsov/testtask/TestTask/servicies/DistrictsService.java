package ru.kolontsov.testtask.TestTask.servicies;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolontsov.testtask.TestTask.dto.DistrictsDto;
import ru.kolontsov.testtask.TestTask.entities.Districts;
import ru.kolontsov.testtask.TestTask.repositories.DistrictsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DistrictsService {
    private final DistrictsRepository districtsRepository;

    public DistrictsService(DistrictsRepository districtsRepository) {
        this.districtsRepository = districtsRepository;
    }

    public List<Districts> showAll() {
        List<Districts> allDistricts = districtsRepository.findAll();

        List<Districts> activeDistricts = allDistricts.stream()
                .filter(districts -> !districts.getIs_archive())
                .collect(Collectors.toList());

        return activeDistricts;
    }

    public Districts showByName(String name) {
        Districts districts = districtsRepository.findByName(name);

        if (!districts.getIs_archive()) {
            return districts;
        } else {
            return null;
        }
    }

    public List<Districts> showByCode(Integer code) {
        List<Districts> allDistricts = districtsRepository.findByCode(code);

        List<Districts> activeDistricts = allDistricts.stream()
                .filter(districts -> !districts.getIs_archive())
                .collect(Collectors.toList());

        return activeDistricts;
    }

    public List<Districts> showByNameAndCode(String name, Integer code) {
        List<Districts> allDistricts = districtsRepository.findByNameAndCode(name, code);

        List<Districts> activeDistricts = allDistricts.stream()
                .filter(districts -> !districts.getIs_archive())
                .collect(Collectors.toList());

        return activeDistricts;
    }

    @Transactional
    public Districts addNewDistrict(String name, Integer code, Boolean is_archive) {
        Districts districts = new Districts();

        districts.setName(name);
        districts.setCode(code);
        districts.setIs_archive(is_archive);

        return districtsRepository.save(districts);
    }

    public Districts updateDistrict(Long id, String name, Integer code, Boolean isArchive) {

        Districts existingDistrict = districtsRepository.findById(id).orElse(null);
        if (existingDistrict != null) {
            existingDistrict.setName(name);
            existingDistrict.setCode(code);
            existingDistrict.setIs_archive(isArchive);

            return districtsRepository.save(existingDistrict);
        }
        return null; // или можно бросить исключение, если район не найден
    }

}
