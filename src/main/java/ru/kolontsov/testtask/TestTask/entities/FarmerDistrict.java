package ru.kolontsov.testtask.TestTask.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Класс фермер-район, со всей информацией по засевным полям по одному фермеру
 */
@Data
@Entity
@Table(name = "farmer_districts")
@Schema(example = "Farmer-District")
public class FarmerDistrict {

    /**
     * Поле id - уникальный идентификатор записи в таблице
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Unique identifier")
    private Long id;

    /**
     * Поле farmer_id - уникальный идентификатор фермера
     */
    @Column(name = "farmer_id")
    @Schema(name = "Farmer unique identifier")
    private Long farmer_id;

    /**
     * Поле district_id - уникальный идентификатор района
     */
    @Column(name = "district_id")
    @Schema(name = "District unique identifier")
    private Long district_id;

}
