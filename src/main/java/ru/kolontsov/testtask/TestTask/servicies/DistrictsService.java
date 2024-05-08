package ru.kolontsov.testtask.TestTask.servicies;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolontsov.testtask.TestTask.entities.Districts;
import ru.kolontsov.testtask.TestTask.repositories.DistrictsRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 Класс сервис района - включает логику взаимодействия с базой данных реестра
 и использование данных из таблицы с информацией о районах
 */
@Service
public class DistrictsService {
    private final DistrictsRepository districtsRepository;

    public DistrictsService(DistrictsRepository districtsRepository) {
        this.districtsRepository = districtsRepository;
    }

    /**
     * Метод показать все районы - возвращает все данные о всех районах из БД, которые являются не архивными
     * @Return возвращает данные о всех районах
     */
    public List<Districts> showAll() {
        List<Districts> allDistricts = districtsRepository.findAll();

        List<Districts> activeDistricts = allDistricts.stream()
                .filter(districts -> !districts.getIs_archive())
                .collect(Collectors.toList());

        return activeDistricts;
    }

    /**
     * Метод показать район по названию - возвращает все данные о районе из БД, который являются не архивными
     * @Param name - название района
     * @Return возвращает данные района по названию
     */
    public Districts showByName(String name) {
        Districts districts = districtsRepository.findByName(name);

        if (!districts.getIs_archive()) {
            return districts;
        } else {
            return null;
        }
    }

    /**
     * Метод показать районы по коду - возвращает все данные о районах из БД, которые являются не архивными
     * @Param code - код района
     * @Return возвращает данные районов по коду
     */
    public List<Districts> showByCode(Integer code) {
        List<Districts> allDistricts = districtsRepository.findByCode(code);

        List<Districts> activeDistricts = allDistricts.stream()
                .filter(districts -> !districts.getIs_archive())
                .collect(Collectors.toList());

        return activeDistricts;
    }

    /**
     * Метод показать районы по наименованию и коду - возвращает все данные о районах из БД, которые являются не архивными
     * @Param name - название района, code - код района
     * @Return возвращает данные районов по наименованию и коду
     */
    public List<Districts> showByNameAndCode(String name, Integer code) {
        List<Districts> allDistricts = districtsRepository.findByNameAndCode(name, code);

        List<Districts> activeDistricts = allDistricts.stream()
                .filter(districts -> !districts.getIs_archive())
                .collect(Collectors.toList());

        return activeDistricts;
    }

    /**
     * Метод добавить новый район - добавляет новый район в БД в соответствие с теми данными, которые были переданы
     * @Param name - название района, code - код района, is_archive - статус архивности
     * @Return сохраняет новый район с его данными в БД
     */
    @Transactional
    public Districts addNewDistrict(String name, Integer code, Boolean is_archive) {
        Districts districts = new Districts();

        districts.setName(name);
        districts.setCode(code);
        districts.setIs_archive(is_archive);

        return districtsRepository.save(districts);
    }

    /**
     * Метод изменить данные района - добавляет новый район в БД в соответствие с теми данными, которые были переданы
     * @Param id - уникальный идентификатор района, name - название района, code - код района, is_archive - статус архивности
     * @Return изменяет данные переданного района в БД
     */
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

    /**
     * Метод изменить статус архивности района - изменяет статус архивности переданного района, если требуется
     * @Param id - уникальный идентификатор района
     * @Return изменяет данные переданного района в БД
     */
    public Districts archiveDistrict(Long id) {
        Districts existingDistrict = districtsRepository.findById(id).orElse(null);
        if (existingDistrict != null && !existingDistrict.getIs_archive()) {
            existingDistrict.setIs_archive(true);

            return districtsRepository.save(existingDistrict);
        }
        return null; // или можно бросить исключение, если район не найден
    }

}
