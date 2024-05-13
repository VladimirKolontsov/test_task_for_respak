package ru.kolontsov.testtask.TestTask.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Класс фермер, со всей информацией по фермеру
 */
@Data
@Entity
@Table(name = "farmers")
@Schema(example = "Farmer")
public class Farmer {

    /**
     * Поле id - уникальный идентификатор фермера
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "Unique identifier")
    private Long id;

    /**
     * Поле organizationName - наименование организации
     */
    @Column(name = "organization_name")
    @Schema(name = "Organization name")
    private String organizationName;

    /**
     * Поле legalForm - организационно-правовая форма (ИП, ЮЛ, ФЛ)
     */
    @Column(name = "legal_form")
    @Schema(name = "Legal form")
    private String legalForm;

    /**
     * Поле inn - ИНН
     */
    @Column(name = "inn")
    @Schema(name = "INN")
    private String inn;

    /**
     * Поле kpp - КПП
     */
    @Column(name = "kpp")
    @Schema(name = "KPP")
    private String kpp;

    /**
     * Поле ogrn - ОГРН
     */
    @Column(name = "ogrn")
    @Schema(name = "OGRN")
    private String ogrn;

    /**
     * Поле registrationDistrictId - уникальный идентификатор района регистрации фермера
     */
    @Column(name = "registration_district_id")
    @Schema(name = "Registration district id")
    private Long registrationDistrictId;

    /**
     * Поле registrationDate - дата регистрации
     */
    @Column(name = "registration_date")
    @Schema(name = "Registration date")
    private LocalDate registrationDate;

    /**
     * Поле isArchived - статус архивности фермера
     */
    @Column(name = "is_archived")
    @Schema(name = "State of archived")
    private boolean isArchived;

    /**
     * Поле plantingAreas - список районов посевных полей фермера
     */
    @ManyToMany
    @JoinTable(
            name = "farmer_districts",
            joinColumns = @JoinColumn(name = "farmer_id"),
            inverseJoinColumns = @JoinColumn(name = "district_id")
    )
    @Schema(name = "Planting areas")
    private List<Districts> plantingAreas;
}
