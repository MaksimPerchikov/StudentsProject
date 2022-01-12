package ru.website.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Estimation {

    @Id
    @GeneratedValue
    private Long idEstimation;

    private Integer estimation;



}
