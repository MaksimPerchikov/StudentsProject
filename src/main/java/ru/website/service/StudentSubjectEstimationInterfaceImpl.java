package ru.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.website.dto.StudentSubjectEstimationDto;
import ru.website.model.Estimation;
import ru.website.model.Students;
import ru.website.model.Subject;
import ru.website.repository.EstimationRepository;
import ru.website.repository.StudentRepository;
import ru.website.repository.SubjectRepository;
import ru.website.service.interfaces.StudentSubjectEstimationInterface;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentSubjectEstimationInterfaceImpl implements StudentSubjectEstimationInterface {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final EstimationRepository estimationRepository;

    @Autowired
    public StudentSubjectEstimationInterfaceImpl(StudentRepository studentRepository,
                                                 SubjectRepository subjectRepository,
                                                 EstimationRepository estimationRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.estimationRepository = estimationRepository;
    }

    @Override
    public List<Students> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Students addStudent(Students students) {
        return studentRepository.save(students);
    }

    @Override
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Estimation addEstimation(Estimation estimation) {
        return estimationRepository.save(estimation);
    }

    @Override
    public Object converterDtoToEntity(StudentSubjectEstimationDto studentSubjectEstimationDto) {
            List<Students> listStudents = studentRepository.findAll();
            Optional<Students> studentsOptional =
                    listStudents.stream()
                            .filter(element -> element
                                    .getSurname()
                                    .equals(studentSubjectEstimationDto.getSurname()))
                            .findFirst();

        Students st = studentsOptional.get();
        Subject subject = new Subject();


        try{
            List<Subject> subjectsList = subjectRepository.findAll();
            Optional<Subject> subjectOptional =
                    subjectsList.stream()
                            .filter(element -> element.getNameSubject()
                                    .equals(studentSubjectEstimationDto.getSubjectName()))
                            .findFirst();

            subject.setNameSubject(subjectOptional.get().getNameSubject());
        }catch (Exception e) {
            String str = new ResponseEntity<>(HttpStatus.NOT_FOUND).toString();
            return str;
        }


        Estimation estimation = new Estimation();

        try{
            List<Estimation> estimationList = estimationRepository.findAll();
            Optional<Estimation> estimationOptional =
                    estimationList.stream()
                            .filter(elem -> elem.getEstimation()
                                    .equals(studentSubjectEstimationDto.getEstimation()))
                            .findFirst();

            estimation.setEstimation(estimationOptional.get().getEstimation());
            estimationRepository.save(estimation);
        }catch (Exception e) {
            String str = new ResponseEntity<>(HttpStatus.NOT_FOUND).toString();
            return str;
        }


        estimationRepository.save(estimation);

        subject.addEstimation(estimation);
        subjectRepository.save(subject);

        st.addSubject(subject);
        studentRepository.save(st);
        return st;
    }

    public List<Students> aLs(Students students){
        List<Students> a = new ArrayList<>();
        a.add(students);
        return a;
    }

    @Override
    public String deleteStudentById(Long id) {
        try{
            studentRepository.deleteById(id);
            String str = "Удаление прошло успешно!";
            return str;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND).toString();
        }
    }

    @Override
    public String deleteSubjectById(Long id) {
        try{
            subjectRepository.deleteById(id);
            String str = "Удаление прошло успешно!";
            return str;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND).toString();
        }
    }

    @Override
    public String deleteEstimationById(Long id) {
        try{
            estimationRepository.deleteById(id);
            String str = "Удаление прошло успешно!";
            return str;
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND).toString();
        }
    }

    @Override
    public List<Subject> findAllSubject() {
        return subjectRepository.findAll();
    }

    @Override
    public List<Estimation> findAllEstimation() {
        return estimationRepository.findAll();
    }


    @Override
    public Estimation updateEstimationById(Long id,Estimation estimation) {
        try {
          Optional<Estimation> estimationOp =  estimationRepository.findById(id);

          estimationOp.get().setEstimation(estimation.getEstimation());
          estimationRepository.save(estimationOp.get());
            return estimation;
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.NOT_FOUND));
        }
    }

    @Override
    public Subject updateSubjectById(Long id,Subject subject) {
        try {
            Optional<Subject> subjectOptional =  subjectRepository.findById(id);
            subjectOptional.get().setNameSubject(subject.getNameSubject());
            subjectRepository.save(subjectOptional.get());
            return subject;
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.NOT_FOUND));
        }
    }

    @Override
    public Students updateStudentsById(Long id,Students students) {
        try {
            Optional<Students> studentsOp =  studentRepository.findById(id);

            studentsOp.get().setSurname(students.getSurname());
            studentRepository.save(studentsOp.get());
            return students;
        }catch (Exception e){
            throw new RuntimeException(String.valueOf(HttpStatus.NOT_FOUND));
        }
    }

    @Override
    public List<Students> sortedStudentsBySurnameUp() {
        List<Students> studentsList = findAllStudents();
        List<Students> sortedListStudents =
                studentsList.stream().sorted(Comparator.comparing(Students::getSurname))
                        .collect(Collectors.toList());
        return sortedListStudents;
    }

    @Override
    public List<Subject> sortedSubjectByNameUp() {
        List<Subject> subjectList = findAllSubject();
        List<Subject> sortedSubjects =
                subjectList.stream()
                        .sorted(Comparator.comparing(Subject::getNameSubject))
                        .collect(Collectors.toList());
        return sortedSubjects;
    }
}
