package ru.website.controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.website.dto.StudentSubjectEstimationDto;
import ru.website.model.Estimation;
import ru.website.model.Students;
import ru.website.model.Subject;
import ru.website.service.StudentSubjectEstimationInterfaceImpl;

import javax.persistence.GeneratedValue;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MainRestController {

    private final StudentSubjectEstimationInterfaceImpl studentSubjectEstimationInterfaceImpd;
    @Autowired
    public MainRestController(StudentSubjectEstimationInterfaceImpl studentSubjectEstimationInterfaceImpd) {
        this.studentSubjectEstimationInterfaceImpd = studentSubjectEstimationInterfaceImpd;
    }
    //поставить КОНКРЕТНОМУ студенту ДОСТУПНУЮ оценку в СУЩЕСТВУЮЩИЙ предмет
    @PostMapping("/enter")
    public Object testMethod(@RequestBody StudentSubjectEstimationDto studentSubjectEstimationDto){
        return studentSubjectEstimationInterfaceImpd.converterDtoToEntity(studentSubjectEstimationDto);
    }


    ///////////////////создание сущностей/////////////////////
    //Внести студентов
    @PostMapping("/stud")
    public Object addStudent(@RequestBody Students students){
        return studentSubjectEstimationInterfaceImpd.addStudent(students);
    }
    //внести предметы,которые будут
    @PostMapping("/sub")
    public Object addSubject(@RequestBody Subject subject){
        return studentSubjectEstimationInterfaceImpd.addSubject(subject);
    }
    //внести , какие оценки могут быть
    @PostMapping("/estim")
    public Object addEstim(@RequestBody Estimation estimation){
        return studentSubjectEstimationInterfaceImpd.addEstimation(estimation);
    }


    //////////////////вывод списков///////////////////////////////
    @GetMapping("/stud/all")
    public List<Students> findAllStudents(){
      return   studentSubjectEstimationInterfaceImpd.findAllStudents();
    }

    @GetMapping("/sub/all")
    public List<Subject> findAllSubject(){
        return studentSubjectEstimationInterfaceImpd.findAllSubject();
    }

    @GetMapping("/estim/all")
    public List<Estimation> findAllEstimation(){
        return studentSubjectEstimationInterfaceImpd.findAllEstimation();
    }


    //////////////////////////редактирование///////////////////////
    @PutMapping("/estim/up/{id}")
    public Estimation updateEstimationById(@PathVariable("id") Long id,
                                           @RequestBody Estimation estimation){

            return studentSubjectEstimationInterfaceImpd.updateEstimationById(id,estimation);
    }
    @PutMapping("/sub/up/{id}")
    public Subject updateSubjectById(@PathVariable("id") Long id,
                                           @RequestBody Subject subject){

        return studentSubjectEstimationInterfaceImpd.updateSubjectById(id,subject);
    }
    @PutMapping("/stud/up/{id}")
    public Students updateEstimationById(@PathVariable("id") Long id,
                                           @RequestBody Students students){

        return studentSubjectEstimationInterfaceImpd.updateStudentsById(id,students);
    }


    ////////////////////////Удаление по айди/////////////////////////
    @DeleteMapping("/stud/{id}")
    public String deleteStudentById(@PathVariable("id")Long id){
       return studentSubjectEstimationInterfaceImpd.deleteStudentById(id);
    }
    @DeleteMapping("/sub/{id}")
    public String deleteSubjectById(@PathVariable("id")Long id){
        return studentSubjectEstimationInterfaceImpd.deleteSubjectById(id);

    }@DeleteMapping("/estim/{id}")
    public String deleteEstimById(@PathVariable("id")Long id){
        return studentSubjectEstimationInterfaceImpd.deleteEstimationById(id);
    }

    /////////////////////сортировки //////////////////////
    //студентов по фамилии
    @GetMapping("/stud/sortbs")
    public List<Students> sortedStudentsBySurname(){
        return studentSubjectEstimationInterfaceImpd.sortedStudentsBySurnameUp();
    }

    //Предметов по наименованию
    @GetMapping("/sub/sortbs")
    public List<Subject> sortedSubjectBySurname(){
        return studentSubjectEstimationInterfaceImpd.sortedSubjectByNameUp();
    }

    ////////////////////формирование отчета////////////////////

    @GetMapping("/rep")
    public Object testMeth(){
        return studentSubjectEstimationInterfaceImpd.report();
    }

    //////////////////найти студента по айди///////////////////
    @GetMapping("stud/find/{id}")
    public Optional<Students> findStudentById(@PathVariable("id") Long id){
       return studentSubjectEstimationInterfaceImpd.findByIdStudent(id);
    }

    //показать
}
