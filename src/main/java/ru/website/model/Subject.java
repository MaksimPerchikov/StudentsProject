package ru.website.model;

import lombok.*;
import org.apache.catalina.valves.StuckThreadDetectionValve;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Subject {

    @Id
    @GeneratedValue
    @Column(name = "id_subject")
    private Long idSubject;
    private String nameSubject;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Estimation> estimationList = new ArrayList<>();

    public void addEstimation(Estimation estimation){
        estimationList.add(estimation);
    }

}
