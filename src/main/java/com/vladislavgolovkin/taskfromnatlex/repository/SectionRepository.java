package com.vladislavgolovkin.taskfromnatlex.repository;


import com.vladislavgolovkin.taskfromnatlex.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query(value = "SELECT s FROM Section s JOIN GeologicalClasses g ON s.id=g.section.id where g.code = :code")
    List<Section> findAllSectionsByCode(String code);
}
