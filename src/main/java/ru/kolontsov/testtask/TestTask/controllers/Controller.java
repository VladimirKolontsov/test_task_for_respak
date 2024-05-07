package ru.kolontsov.testtask.TestTask.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kolontsov.testtask.TestTask.dto.DistrictsDto;
import ru.kolontsov.testtask.TestTask.entities.Districts;
import ru.kolontsov.testtask.TestTask.servicies.DistrictsService;

import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    private final DistrictsService districtsService;

    @Autowired
    public Controller(DistrictsService districtsService) {
        this.districtsService = districtsService;
    }

    @GetMapping("/all")
    public List<Districts> showAll() {
        return districtsService.showAll();
    }

    @GetMapping("/district-name")
    public Districts getDistrictsByName(@RequestParam("name") String name) {
        return districtsService.showByName(name);
    }

    @GetMapping("/district-code")
    public List<Districts> getDistrictsByName(@RequestParam("code") Integer code) {
        return districtsService.showByCode(code);
    }

    @GetMapping("/district-namecode")
    public List<Districts> getDistrictsByName(@RequestParam("name") String name,
                                              @RequestParam("code") Integer code) {
        return districtsService.showByNameAndCode(name, code);
    }

    @PostMapping("/add")
    public void addNewDisctrict(@RequestBody Districts districts) {
        districtsService.addNewDistrict(districts.getName(), districts.getCode(), districts.getIs_archive());
//        districtsService.addNewDistrict(districts);
    }

    @PutMapping("/update/{id}")
    public void addNewDisctrict(@PathVariable Long id, @RequestBody Districts districts) {
        districtsService.updateDistrict(id, districts.getName(), districts.getCode(), districts.getIs_archive());
//        districtsService.addNewDistrict(districts);
    }

}
