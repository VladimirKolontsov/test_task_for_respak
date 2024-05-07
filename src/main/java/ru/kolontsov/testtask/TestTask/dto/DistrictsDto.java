package ru.kolontsov.testtask.TestTask.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DistrictsDto {

    private Long id;

    private String name;

    private Integer code;

    private Boolean is_archive;
}
