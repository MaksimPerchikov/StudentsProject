package ru.website.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.website.model.Estimation;
import ru.website.model.Students;
import ru.website.model.Subject;
import ru.website.repository.EstimationRepository;
import ru.website.repository.StudentRepository;
import ru.website.repository.SubjectRepository;

@Configuration
public class LoadDataBase {

    private static final Logger log = LoggerFactory.getLogger(LoadDataBase.class);


    /*@Bean
    CommandLineRunner initStudents(StudentRepository studentRepository) {
        return args -> {
            log.info("Preloading " + studentRepository
                    .save(new Students(1L, "Pety",new Subject(1L,"Math"))));
        };
    }*/

    @Bean
    CommandLineRunner initEstimation(EstimationRepository estimationRepository) {
        return args -> {
            log.info("Preloading " + estimationRepository
                    .save(new Estimation(1L, 2)));
            log.info("Preloading " + estimationRepository
                    .save(new Estimation(2L, 3)));
            log.info("Preloading " + estimationRepository
                    .save(new Estimation(3L, 4)));
            log.info("Preloading " + estimationRepository
                    .save(new Estimation(4L, 5)));
        };
    }

   /* @Bean
    CommandLineRunner initSubject(SubjectRepository subjectRepository) {
        return args -> {
            log.info("Preloading " + subjectRepository
                    .save(new Subject(1L, "Mathematics")));
            log.info("Preloading " + subjectRepository
                    .save(new Subject(2L, "History")));
        };
    }*/

}