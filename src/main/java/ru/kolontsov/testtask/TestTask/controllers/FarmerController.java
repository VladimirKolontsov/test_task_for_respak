package ru.kolontsov.testtask.TestTask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kolontsov.testtask.TestTask.entities.Farmer;
import ru.kolontsov.testtask.TestTask.servicies.DistrictsService;
import ru.kolontsov.testtask.TestTask.servicies.FarmerService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 Класс контроллер по работе с реестром фермеров - вывод их полный список, делает фильтрацию по требуемым параметрам,
  можно добавить нового фермера, изменить его данные, а также поменять статус архивности.
 */
@RestController
@RequestMapping("/")
@Tag(name = "Farmer", description = "Methods for working with farmers")
public class FarmerController {
    private final FarmerService farmerService;


    @Autowired
    public FarmerController(FarmerService farmerService) {
        this.farmerService = farmerService;
    }

    /**
     * Метод показать весь список - возвращает полный список неархивных фермеров
     *
     * @Return - возвращает список фермеров
     */
    @GetMapping("/all-farm")
    @Operation(summary = "Show all farmers")
    public List<Farmer> showAllFarmers() {
        List<Farmer> fullList = farmerService.showAll();

        return fullList;
    }

    /**
     * Метод вывести фермеров по названию организации - выводит всю информацию по фермерам по названию организации
     *
     * @Param name - название организации
     * @Return - возвращает список фермеров
     */
    @GetMapping("/all-farm-nameorg")
    @Operation(summary = "Show farmers by organization name")
    public List<Farmer> showAllFarmersByNameOrg(@Parameter(description = "Organization name")
                                                    @RequestParam("name") String name) {
        List<Farmer> fullList = farmerService.showAllFarmerByOrgName(name);

        return fullList;
    }

    /**
     * Метод вывести фермеров по ИНН - выводит всю информацию по фермерам по ИНН
     *
     * @Param inn - ИНН
     * @Return - возвращает список фермеров
     */
    @GetMapping("/all-farm-inn")
    @Operation(summary = "Show farmers by INN")
    public List<Farmer> showAllByInn(@Parameter(description = "INN")
                                         @RequestParam("inn") String inn) {
        List<Farmer> fullList = farmerService.showAllFarmerByInn(inn);

        return fullList;
    }

    /**
     * Метод вывести фермеров по названию района регистрации - выводит всю информацию по фермерам по названию района регистрации
     *
     * @Param nameDistrict - название района регистрации
     * @Return - возвращает список фермеров
     */
    @GetMapping("/all-farm-distname")
    @Operation(summary = "Show farmers by district registration name")
    public List<Farmer> showAllByRegistryNameOrg(@Parameter(description = "Name of registration district")
                                              @RequestParam("name")String nameDistrict) {
        List<Farmer> fullList = farmerService.showAllFarmerByDistrictReg(nameDistrict);

        return fullList;
    }

    /**
     * Метод вывести фермеров по дате регистрации - выводит всю информацию по фермерам по дате регистрации
     *
     * @Param dateRegistration - дата регистрации
     * @Return - возвращает список фермеров
     */
    @GetMapping("/all-farm-datereg")
    @Operation(summary = "Show farmers by date registration")
    public List<Farmer> showAllByDateRegistration(@Parameter(description = "Date of farmers registration")
                                             @RequestParam("date") LocalDate dateRegistration) {
        List<Farmer> fullList = farmerService.showAllFarmersByDateRegistration(dateRegistration);

        return fullList;
    }

    /**
     * Метод вывести фермеров по статусу архивности - выводит всю информацию по фермерам по статусу архивности
     *
     * @Param state - статус
     * @Return - возвращает список фермеров
     */
    @GetMapping("/all-farm-state")
    @Operation(summary = "Show all farmers by state")
    public List<Farmer> showAllByStateOfFarmer(@Parameter(description = "State of farmer")
                                                   @RequestParam("state") boolean state) {
        List<Farmer> newList = new ArrayList<>();

        if (state) {
            newList = farmerService.showAllArchived();
        } else {
            newList = farmerService.showAllNotArchived();
        }

        return newList;
    }

    /**
     * Метод по добавлению нового фермера - добавляет нового фермера в зависимости от переданных данных
     *
     * @RequestBody farmer - тело запроса по добавлению фермера, plantingDistId - уникальный идентификатор посевного района
     * @Return - ничего не возвращает, происходит запись в БД
     */
    @PostMapping("/add-farmer")
    @Operation(summary = "Adding new farmer to database")
    public void addNewFarmer(@Parameter(description = "Body with farmer attributes")
                               @RequestBody Farmer farmer,
                               @Parameter(description = "Planting district unique identifier")
                                @RequestParam("plant-id") Long plantingDistId) {
        farmerService.addNewFarmer(farmer.getOrganizationName(),
                                    farmer.getLegalForm(),
                                    farmer.getInn(),
                                    farmer.getKpp(),
                                    farmer.getOgrn(),
                                    farmer.getRegistrationDistrictId(),
                                    plantingDistId);
    }

    /**
     * Метод по изменеию данных о фермере -
     * должен по переданному уникальному идентификатору изменить текущие данные о фермере, на переданные
     *
     * @PathVariable id - уникальный идентификатор фермера
     * @RequestBody farmer - тело запроса на изменение данных о фермере
     * @Return - ничего не позвращает, изменяет текущую запись в БД
     */
    @PutMapping("/update-farmer/{id}")
    @Operation(summary = "Changing information about exact district")
    public void changingFarmerInformation(@Parameter(description = "Unique identifier of district")
                                @PathVariable Long id,
                                @Parameter(description = "Body with farmer attributes")
                                @RequestBody Farmer farmer) {
        farmerService.updateFarmer(id,
                farmer.getOrganizationName(),
                farmer.getLegalForm(),
                farmer.getInn(),
                farmer.getKpp(),
                farmer.getOgrn(),
                farmer.getRegistrationDistrictId());
    }

    @PutMapping("/farm-ar/{id}")
    public Farmer archiveFarmer(@Parameter(description = "Unique identifier of farmer")
                                     @PathVariable Long id) {
        return farmerService.archiveFarmer(id);
    }
}
