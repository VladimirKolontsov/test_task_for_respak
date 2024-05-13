package ru.kolontsov.testtask.TestTask.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Класс района, со всей информацией по району
 */
@Getter
@Setter
@Entity
@Table(name = "districts")
@Schema(example = "District")
public class Districts {

    /**
     * Поле id - уникальный идентификатор района
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Unique identifier")
    private Long id;

    /**
     * Поле name - название района
     */
    @Column(name = "name")
    @Schema(name = "Name of district")
    private String name;

    /**
     * Поле code - код района
     */
    @Column(name = "code")
    @Schema(name = "Code of district")
    private Integer code;

    /**
     * Поле is_archive - статус архивности района
     */
    @Column(name = "is_archive")
    @Schema(name = "State of district")
    private Boolean is_archive;

    /**
     * Поле farmers - показывает связь с фермерами, для вывода списка района посевных полей у фермера
     */
    @ManyToMany(mappedBy = "plantingAreas")
    @JsonIgnore
    private List<Farmer> farmers;

}
