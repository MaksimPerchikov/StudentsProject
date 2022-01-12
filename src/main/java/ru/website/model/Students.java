package ru.website.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Students {

    @Id
    @GeneratedValue
    @Column(name = "id_student")
    private Long idStudent;
    private String surname;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Subject> subjectList = new ArrayList<>();

    //рекурсия
    public void addSubject(Subject subject){
        subjectList.add(subject);
    }
    public void removeSubject(Subject subject){
        subjectList.remove(subject);
    }
}
