package de.iubh.webanwendungen.repository;

import de.iubh.webanwendungen.model.GhostNet;
import de.iubh.webanwendungen.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GhostNetRepository extends JpaRepository<GhostNet, Long> {
    List<GhostNet> findByStatus(Status status);
    List<GhostNet> findByStatusNot(Status status);
}