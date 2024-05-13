package ru.kolontsov.testtask.TestTask.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "farmer_districts")
public class FarmerDistrict {

    /**
     * Поле id - уникальный идентификатор фермера
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Unique identifier")
    private Long id;

    @Column(name = "farmer_id")
    private Long farmer_id;

    @Column(name = "district_id")
    private Long district_id;

}
