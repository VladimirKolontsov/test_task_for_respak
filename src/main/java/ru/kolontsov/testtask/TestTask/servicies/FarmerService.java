package ru.kolontsov.testtask.TestTask.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kolontsov.testtask.TestTask.entities.Districts;
import ru.kolontsov.testtask.TestTask.entities.Farmer;
import ru.kolontsov.testtask.TestTask.entities.FarmerDistrict;
import ru.kolontsov.testtask.TestTask.repositories.DistrictsRepository;
import ru.kolontsov.testtask.TestTask.repositories.FarmerDistrictRepository;
import ru.kolontsov.testtask.TestTask.repositories.FarmerRepository;

import java.time.LocalDate;
import java.util.List;

/**
 Класс сервис фермера - включает логику взаимодействия с базой данных реестра
 и использование данных из таблицы с информацией о фермерах
 */
@Service
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final DistrictsRepository districtsRepository;
    private final FarmerDistrictRepository farmerDistrictRepository;

    @Autowired
    public FarmerService(FarmerRepository farmerRepository, DistrictsRepository districtsRepository, FarmerDistrictRepository farmerDistrictRepository) {
        this.farmerRepository = farmerRepository;
        this.districtsRepository = districtsRepository;
        this.farmerDistrictRepository = farmerDistrictRepository;
    }

    /**
     * Метод показать всех фермеров - возвращает все данные о всех фермерах из БД, которые являются не архивными
     * @Return возвращает данные о всех фермерах
     */
    public List<Farmer> showAll() {
        return farmerRepository.findAll().stream()
                .filter(farmer -> !farmer.isArchived()).toList();
    }

    /**
     * Метод показать фермеров по названию организации - возвращает все данные о фермерах из БД, которые являются
     * не архивными
     * @Param orgName - название организации
     * @Return возвращает данные фермеров по названию организации
     */
    public List<Farmer> showAllFarmerByOrgName(String orgName) {
        List<Farmer> fullList = showAll();

        List<Farmer> newList = fullList.stream()
                .filter(farmer -> !farmer.isArchived())
                .filter(farmer -> farmer.getOrganizationName().equals(orgName)).toList();

        return newList;
    }

    /**
     * Метод показать фермеров по ИНН - возвращает все данные о фермерах из БД, которые являются не архивными
     * @Param inn - ИНН
     * @Return возвращает данные фермеров по ИНН
     */
    public List<Farmer> showAllFarmerByInn(String inn) {
        List<Farmer> fullList = showAll();

        List<Farmer> newList = fullList.stream()
                .filter(farmer -> !farmer.isArchived())
                .filter(farmer -> farmer.getInn().equals(inn)).toList();

        return newList;
    }

    /**
     * Метод показать фермеров по району регистрации - возвращает все данные о фермерах из БД, которые являются
     * не архивными
     * @Param nameDistrict - название района регистрации
     * @Return возвращает данные фермеров по району регистрации
     */
    public List<Farmer> showAllFarmerByDistrictReg(String nameDistrict) {
        Districts districtByName = districtsRepository.findByName(nameDistrict);

        Long districtId = districtByName.getId();

        List<Farmer> newList = showAll().stream()
                .filter(farmer -> !farmer.isArchived())
                .filter(farmer -> farmer.getId().equals(districtId)).toList();

        return newList;
    }

    /**
     * Метод показать фермеров по дате регистрации - возвращает все данные о фермерах из БД, которые являются
     * не архивными
     * @Param registrationDate - дата регистрации
     * @Return возвращает данные фермеров по дате регистрации
     */
    public List<Farmer> showAllFarmersByDateRegistration(LocalDate registrationDate){
        List<Farmer> newList = showAll().stream()
                .filter(farmer -> !farmer.isArchived())
                .filter(farmer -> farmer.getRegistrationDate().equals(registrationDate)).toList();

        return newList;
    }

    /**
     * Метод показать фермеров по статусу архивности - возвращает все данные о фермерах из БД, которые являются
     * архивными
     * @Return возвращает данные фермеров, которые находятся в статусе "архивный"
     */
    public List<Farmer> showAllArchived() {
        List<Farmer> newList = showAll().stream()
                .filter(Farmer::isArchived)
                .toList();

        return newList;
    }

    /**
     * Метод показать фермеров по статусу архивности - возвращает все данные о фермерах из БД, которые являются
     * не архивными
     * @Return возвращает данные фермеров, которые находятся в статусе "не архивный"
     */
    public List<Farmer> showAllNotArchived() {
        List<Farmer> newList = showAll().stream()
                .filter(farmer -> !farmer.isArchived())
                .toList();

        return newList;
    }

    /**
     * Метод добавить нового фермера - добавляет нового фермера в БД в соответствие с теми данными, которые были переданы
     * @Param nameOrganization - название организации, legalForm - организационно-правовая форма, inn - ИНН,
     * kpp - КПП, ogrn - ОГРН, distId - уникальный идентификатор района регистрации
     * @Return сохраняет новый район с его данными в БД, дата регистрации устанавливается - текущая, статус архивности,
     * по умлочанию - "не архивный"
     */
    @Transactional
    public void addNewFarmer(String nameOrganization,
                               String legalForm,
                               String inn,
                               String kpp,
                               String ogrn,
                               Long distId,
                               Long plantingDistId) {
        Farmer farmer = new Farmer();

        farmer.setOrganizationName(nameOrganization);
        farmer.setLegalForm(legalForm);
        farmer.setInn(inn);
        farmer.setKpp(kpp);
        farmer.setOgrn(ogrn);
        farmer.setRegistrationDistrictId(distId);
        farmer.setRegistrationDate(LocalDate.now());
        farmer.setArchived(false);
        farmerRepository.save(farmer);

        FarmerDistrict farmerDistrict = new FarmerDistrict();
        farmerDistrict.setFarmer_id(farmer.getId());
        farmerDistrict.setDistrict_id(plantingDistId);
        farmerDistrictRepository.save(farmerDistrict);
    }

    /**
     * Метод изменить данные фермера - изменяет данные фермера в БД в соответствие с теми данными, которые были переданы
     * @Param nameOrganization - название организации, legalForm - организационно-правовая форма, inn - ИНН,
     * kpp - КПП, ogrn - ОГРН, distId - уникальный идентификатор района регистрации
     * @Return изменяет данные переданного фермера в БД
     */
    @Transactional
    public Farmer updateFarmer(Long id,
                               String organizationName,
                               String legalForm,
                               String inn,
                               String kpp,
                               String ogrn,
                               Long distId) {

        Farmer existingFarmer = farmerRepository.findById(id).orElse(null);

        if (existingFarmer != null) {
            existingFarmer.setOrganizationName(organizationName);
            existingFarmer.setLegalForm(legalForm);
            existingFarmer.setInn(inn);
            existingFarmer.setKpp(kpp);
            existingFarmer.setOgrn(ogrn);
            existingFarmer.setRegistrationDistrictId(distId);

            return farmerRepository.save(existingFarmer);
        }
        return null; // или можно бросить исключение, если район не найден
    }

    /**
     * Метод изменить статус архивности фермера - изменяет статус архивности переданного фермера
     * @Param id - уникальный идентификатор фермера
     * @Return изменяет статус архивности переданного фермера в БД
     */
    public Farmer archiveFarmer(Long id) {
        Farmer existingFarmer = farmerRepository.findById(id).orElse(null);

        if (existingFarmer != null && !existingFarmer.isArchived()) {
            existingFarmer.setArchived(true);
            return farmerRepository.save(existingFarmer);
        } else if (existingFarmer != null && existingFarmer.isArchived()){
            existingFarmer.setArchived(false);
            return farmerRepository.save(existingFarmer);
        }

        return null; // или можно бросить исключение, если район не найден
    }

}
