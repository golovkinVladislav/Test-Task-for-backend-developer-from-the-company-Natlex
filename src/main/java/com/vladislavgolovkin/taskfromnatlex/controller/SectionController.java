package com.vladislavgolovkin.taskfromnatlex.controller;


import com.vladislavgolovkin.taskfromnatlex.entity.Section;
import com.vladislavgolovkin.taskfromnatlex.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @GetMapping("/sections")
    public List<Section> getAllSections() {
        return sectionService.getAllSections();
    }

    @GetMapping("/sections/{id}")
    public Section getSectionById(@PathVariable Long id) {
        return sectionService.getSectionById(id);
    }

    @PostMapping("/sections")
    public Section saveSection(@RequestBody Section section) {
       return sectionService.saveSection(section);
    }

    @DeleteMapping("/sections/{id}")
    public String deleteSectionById(@PathVariable Long id) {
       return sectionService.deleteSectionById(id);
    }

    @PutMapping("/sections/{id}")
    public Section updateSection(@PathVariable Long id, @RequestBody Section section) {
        section.setId(id);
        return sectionService.updateSection(section);
    }

    @GetMapping("/sections/by-code")
    public List<Section> getAllSectionsByCode(@RequestParam String code) {
        return sectionService.getAllSectionsByCode(code);
    }
}

