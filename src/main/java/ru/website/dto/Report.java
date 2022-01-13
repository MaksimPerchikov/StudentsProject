package ru.website.dto;

import lombok.Data;
import ru.website.model.Students;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Data
public class Report {

    private String studentSurname;
    private Map<String, Double> averageMark;
}
