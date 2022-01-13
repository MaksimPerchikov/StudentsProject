package ru.website.service.interfaces;

import ru.website.dto.StudentSubjectEstimationDto;
import ru.website.model.Estimation;
import ru.website.model.Students;
import ru.website.model.Subject;

import java.util.List;
import java.util.Optional;

public interface StudentSubjectEstimationInterface {


    Students addStudent(Students students);
    Subject addSubject(Subject subject);
    Estimation addEstimation(Estimation estimation);

    String deleteStudentById(Long id);
    String deleteSubjectById(Long id);
    String deleteEstimationById(Long id);

    List<Students> findAllStudents();
    List<Subject> findAllSubject();
    List<Estimation> findAllEstimation();

    Estimation updateEstimationById(Long id,Estimation estimation);
    Subject updateSubjectById(Long id,Subject subject);
    Students updateStudentsById(Long id,Students students);

    Object converterDtoToEntity(StudentSubjectEstimationDto studentSubjectEstimationDto);

    List<Students> sortedStudentsBySurnameUp();
    List<Subject> sortedSubjectByNameUp();

    Optional<Students> findByIdStudent(Long id);

    Object report();
}
