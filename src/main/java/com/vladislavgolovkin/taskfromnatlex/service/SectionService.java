package com.vladislavgolovkin.taskfromnatlex.service;

import com.vladislavgolovkin.taskfromnatlex.entity.Section;
import com.vladislavgolovkin.taskfromnatlex.exception_handling.NoSuchSectionException;
import com.vladislavgolovkin.taskfromnatlex.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAll();
    }

    public Section getSectionById(Long id) {
        try {
            return sectionRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new NoSuchSectionException("There is no section with ID " + id + " in Database");
        }
    }

    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public String deleteSectionById(Long id) {
        try {
            sectionRepository.deleteById(id);
            return "Section with ID = " + id + " was deleted.";
        } catch (EmptyResultDataAccessException exception) {
            throw new NoSuchSectionException("It is impossible to delete a section, there is no section with ID " + id + " in Database");
        }
    }

    public Section updateSection(Section section) {
        sectionRepository.findById(section.getId());
        return sectionRepository.save(section);
    }

    public List<Section> getAllSectionsByCode(String code) {
       if(sectionRepository.findAllSectionsByCode(code).isEmpty()){
                throw new NoSuchSectionException("There are no sections with this code = " + code + " in Database");
            }
        return sectionRepository.findAllSectionsByCode(code);
    }
}
