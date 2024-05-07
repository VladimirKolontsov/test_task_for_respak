package ru.kolontsov.testtask.TestTask.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "districts")
//@Schema(example = "Model overview")
public class Districts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Schema(name = "Unique identifier")
    private Long id;

//    @Schema(name = "Country producer")
    private String name;

    private Integer code;

//    @Schema(name = "availability to buy in credit")
    private Boolean is_archive;

}
