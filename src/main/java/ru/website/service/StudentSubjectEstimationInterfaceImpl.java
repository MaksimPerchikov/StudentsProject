package ru.website.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.website.dto.Report;
import ru.website.dto.StudentSubjectEstimationDto;
import ru.website.model.Estimation;
import ru.website.model.Students;
import ru.website.model.Subject;
import ru.website.repository.EstimationRepository;
import ru.website.repository.StudentRepository;
import ru.website.repository.SubjectRepository;
import ru.website.service.interfaces.StudentSubjectEstimationInterface;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public Object addStudent(Students students) {
        try {
            return studentRepository.save(students);

        }catch (Exception e){
            String str = new ResponseEntity<>(HttpStatus.NOT_FOUND)+e.toString();
            return str;
        }

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

        try {
            List<Students> listStudents = studentRepository.findAll();
            Optional<Students> studentsOptional =
                    listStudents.stream()
                            .filter(element -> element
                                    .getSurname()
                                    .equals(studentSubjectEstimationDto.getSurname()))
                            .findFirst();

            Students st = studentsOptional.get();



            Subject subject = new Subject();
            List<Subject> subjectsList = subjectRepository.findAll();
            Optional<Subject> subjectOptional =
                    subjectsList.stream()
                            .filter(element -> element.getNameSubject()
                                    .equals(studentSubjectEstimationDto.getSubjectName()))
                            .findFirst();

            subject.setNameSubject(subjectOptional.get().getNameSubject());


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
                String str = new ResponseEntity<>(HttpStatus.NOT_FOUND)+e.toString();
                return str;
            }


            estimationRepository.save(estimation);

            subject.addEstimation(estimation);
            subjectRepository.save(subject);

            st.addSubject(subject);
            studentRepository.save(st);


            // Subject subject = new Subject();
            return st;
        }catch (Exception e){
           String str  =  new RuntimeException(String.valueOf(HttpStatus.BAD_REQUEST))+e.toString();
           return str;
        }

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

    @Override
    public Object report() {

    try {
        List<Report> reportList = new ArrayList<>();

        List<Students> studentsList =
                findAllStudents();

        for (Students stud: studentsList) {
            Report report = new Report();
            report.setStudentSurname(stud.getSurname());

            Map<String, Double> map = new HashMap<>();
            Stream<Subject> subjectList = stud.getSubjectList().stream();
            Set<Subject> subjectSet = subjectList.collect(Collectors.toSet());

            for (Subject subFromSet: subjectSet) {
                String nameSubjectFromSet = subFromSet.getNameSubject();

                int estimationList =  stud.getSubjectList()
                        .stream()
                        .filter(e->e.getNameSubject().equals(nameSubjectFromSet))
                        .map(q->q.getEstimationList()
                                .stream()
                                .map(Estimation::getEstimation)
                                .mapToInt(Integer::intValue).sum()).mapToInt(r->r).sum();


                List<Object> size =  stud.getSubjectList().stream()
                        .filter(e->e.getNameSubject().equals(nameSubjectFromSet))
                        .map(q->q.getEstimationList()
                                .stream()
                                .map(Estimation::getEstimation)
                                .mapToInt(Integer::intValue).sum()).collect(Collectors.toList());

                int sizeFinal = size.size();
                double result = estimationList/sizeFinal;


                map.put(nameSubjectFromSet, result);
                report.setAverageMark(map);
            }

            reportList.add(report);
        }


        return reportList;
    }catch (Exception e){
        String str =  new RuntimeException(String.valueOf(HttpStatus.BAD_REQUEST))+e.toString();
        return str;
    }

    }




    @Override
    public Optional<Students> findByIdStudent(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Subject> sortedSubjectByNameByParamName(String name) {
        return null;
    }
}



/*  @Override
    public Object report() {

    try {
        List<Report> reportList = new ArrayList<>();

        List<Students> studentsList =
                findAllStudents();

        for (Students stud: studentsList) {
            Report report = new Report();
            report.setStudentSurname(stud.getSurname());

            Map<String, Double> map = new HashMap<>();
            Stream<Subject> subjectList = stud.getSubjectList().stream();
            Set<Subject> subjectSet = subjectList.collect(Collectors.toSet());

            for (Subject subFromSet: subjectSet) {
                int i = 0;
                String nameSubjectFromSet = subFromSet.getNameSubject();

                int estimationList =  stud.getSubjectList().stream()
                        .filter(e->e.getNameSubject().equals(nameSubjectFromSet))
                        .map(q->q.getEstimationList()
                                .stream()
                                .map(Estimation::getEstimation)
                                .mapToInt(Integer::intValue).sum()).mapToInt(r->r).sum();


                List<Object> size =  stud.getSubjectList().stream()
                        .filter(e->e.getNameSubject().equals(nameSubjectFromSet))
                        .map(q->q.getEstimationList()
                                .stream()
                                .map(Estimation::getEstimation)
                                .mapToInt(Integer::intValue).sum()).collect(Collectors.toList());

                int sizeFinal = size.size();
                double result = estimationList/sizeFinal;


                map.put(nameSubjectFromSet, result);
                report.setAverageMark(map);
            }

            reportList.add(report);
        }


        return reportList;
    }catch (Exception e){
        String str =  new RuntimeException(String.valueOf(HttpStatus.BAD_REQUEST))+e.toString();
        return str;
    }

    }*/








/*for (int i = 0; i < students.size(); i++) {
            Students stud = students.get(i);
            report.setStudentSurname(stud.getSurname());

            q.put(i,stud.getSubjectList().stream().toString());





               *//* Integer sumNum = subj.getEstimationList()
                        .stream()
                        .map(Estimation::getEstimation)
                        .mapToInt(g ->g )
                        .sum();//получил сумму оценок одного предмета*//*

 *//*String str = subj.getNameSubject();



                Map<Integer,String> q = new HashMap<>();
                q.put(sumNum,str);
                report.setAverageMark(q);
                reportsList.add(report);*//*
            }*/