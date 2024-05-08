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
import ru.kolontsov.testtask.TestTask.entities.Districts;
import ru.kolontsov.testtask.TestTask.servicies.DistrictsService;

import java.util.List;
//TODO заменить все русские комментарии, сделать метод по отправки в архив
/**
 Класс контроллер по работе с реестром районов - вывод их полный список, делает фильтрацию отдельно по названию,
 коду, одновременно и по названию и по коду, можно добавить новый район, изменить его данные, а также отправить в архив.
 */
@RestController
@RequestMapping("/")
@Tag(name = "Districts", description = "Methods for working with districts")
public class Controller {

    private final DistrictsService districtsService;

    @Autowired
    public Controller(DistrictsService districtsService) {
        this.districtsService = districtsService;
    }

    /**
     * Метод показать весь список - возвращает полный список неархивных районов.
     *
     * @Return - возвращает список районов
     */
    @GetMapping("/all")
    @Operation(summary = "Show all districts")
    public List<Districts> showAll() {
        return districtsService.showAll();
    }

    /**
     * Метод вывести район по названию - выводит всю информацию по району по его названию.
     *
     * @Param name - название района
     * @Return - возвращает район
     */
    @GetMapping("/district-name")
    @Operation(summary = "Show district information by name")
    public Districts getDistrictsByName(@Parameter(description = "Name of district")
                                            @RequestParam("name") String name) {
        return districtsService.showByName(name);
    }

    /**
     * Метод вывести районы по коду - выводит всю информацию по районам по его коду.
     *
     * @Param code - код района
     * @Return - возвращает список районов
     */
    @GetMapping("/district-code")
    @Operation(summary = "Show list of districts with its information by code")
    public List<Districts> getDistrictsByCode(@Parameter(description = "Code of district")
                                                  @RequestParam("code") Integer code) {
        return districtsService.showByCode(code);
    }

    /**
     * Метод вывести районы по названию и коду - выводит всю информацию по районам по названию и коду.
     *
     * @Param name - название района, code - код района
     * @Return - возвращает список районов
     */
    @GetMapping("/district-namecode")
    @Operation(summary = "Show list of districts with its information by name and by code")
    public List<Districts> getDistrictsByNameAndCode(@Parameter(description = "Name of district")
                                                @RequestParam("name") String name,
                                              @Parameter(description = "Code of district")
                                                @RequestParam("code") Integer code) {
        return districtsService.showByNameAndCode(name, code);
    }

    /**
     * Метод по добавлению нового района - добавляет новый район в зависимости от переданных данных
     *
     * @RequestBody districts - тело запроса по добавлению района
     * @Return - ничего не возвращает, происходит запись в БД
     */
    @PostMapping("/add")
    @Operation(summary = "Adding new district to database")
    public void addNewDistrict(@Parameter(description = "Body with district attributes")
                                    @RequestBody Districts districts) {
        districtsService.addNewDistrict(districts.getName(), districts.getCode(), districts.getIs_archive());
    }

    /**
     * Метод по изменеию данных о районе -
     * должен по переданному уникальному идентификатору изменить текущие данные о районе, на переданные
     *
     * @PathVariable id - уникальный идентификатор района
     * @RequestBody request - тело запроса на изменение данных о районе
     * @Return - ничего не позвращает, изменяет текущую запись в БД
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Changing information about exact district")
    public void addNewDisctrict(@Parameter(description = "Unique identifier of district")
                                    @PathVariable Long id,
                                @Parameter(description = "Body with district attributes")
                                    @RequestBody Districts districts) {
        districtsService.updateDistrict(id, districts.getName(), districts.getCode(), districts.getIs_archive());
    }

    /**
     * Метод по отправке района в архив -
     * должен по переданному уникальному идентификатору отправить район в архив, если он в нем не находится
     *
     * @PathVariable id - уникальный идентификатор района
     * @Return - ничего не позвращает, изменяет статус архивности для района в БД
     */
    @PutMapping("/dist-ar/{id}")
    public Districts archiveDistrict(@PathVariable Long id) {
        return districtsService.archiveDistrict(id);
    }

}
