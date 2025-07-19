package de.iubh.webanwendungen.config;

import de.iubh.webanwendungen.model.GhostNet;
import de.iubh.webanwendungen.model.Person;
import de.iubh.webanwendungen.model.Status;
import de.iubh.webanwendungen.repository.GhostNetRepository;
import de.iubh.webanwendungen.repository.PersonRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner loadData(GhostNetRepository ghostNetRepository, PersonRepository personRepository) {
        return args -> {
            // Netz 1: Gemeldet
            GhostNet net1 = new GhostNet();
            net1.setLatitude(53.0);
            net1.setLongitude(8.0);
            net1.setArea(12.0);
            net1.setStatus(Status.GEMELDET);
            ghostNetRepository.save(net1);

            // Netz 2: Bergung bevorstehend
            Person p1 = new Person();
            p1.setName("Hannah Hafen");
            p1.setPhone("0123456789");
            p1.setAnonymous(false);
            personRepository.save(p1);

            GhostNet net2 = new GhostNet();
            net2.setLatitude(54.0);
            net2.setLongitude(9.0);
            net2.setArea(24.0);
            net2.setStatus(Status.BERGUNG_BEVORSTEHEND);
            net2.setSalvagingPerson(p1);
            ghostNetRepository.save(net2);
        };
    }
}