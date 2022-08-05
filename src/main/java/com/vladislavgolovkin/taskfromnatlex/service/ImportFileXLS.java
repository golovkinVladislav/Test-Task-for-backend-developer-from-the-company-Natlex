package com.vladislavgolovkin.taskfromnatlex.service;


import com.vladislavgolovkin.taskfromnatlex.entity.GeologicalClasses;
import com.vladislavgolovkin.taskfromnatlex.entity.Job;
import com.vladislavgolovkin.taskfromnatlex.entity.Section;
import com.vladislavgolovkin.taskfromnatlex.enums.JobResultStatus;
import com.vladislavgolovkin.taskfromnatlex.repository.JobRepository;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ImportFileXLS {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private JobRepository jobRepository;

    @Value("${path.toImport}")
    private String thePathForTheFileToImport;

    @Async
    public CompletableFuture<String> importFileXLS(Job job)  {
        try{
            File file = new File(thePathForTheFileToImport+"sections.xls");
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                HSSFRow row = sheet.getRow(i);

                Section section = new Section();
                section.setName(row.getCell(0).toString());

                List<GeologicalClasses> geologicalClassesList = new ArrayList<>();

                for (int j = 1; j < row.getLastCellNum() - 1; j++) {
                    GeologicalClasses geologicalClasses = new GeologicalClasses();
                    String name = row.getCell(j).toString();
                    if (name == null) {
                        continue;
                    }
                    geologicalClasses.setName(name);
                    String code = row.getCell(++j).toString();
                    if (code != null) {
                        geologicalClasses.setCode(code);
                    }

                    geologicalClasses.setSection(section);
                    geologicalClassesList.add(geologicalClasses);
                }
                section.setGeologicalClasses(geologicalClassesList);
                sectionService.saveSection(section);
        }
            job.setJobResultStatus(JobResultStatus.DONE);
            jobRepository.save(job);
        }  catch (Exception e){
                job.setJobResultStatus(JobResultStatus.ERROR);
                jobRepository.save(job);
            return CompletableFuture.completedFuture("Status: " + JobResultStatus.ERROR.toString()+" jobId: " + job.getId());
        }
        return CompletableFuture.completedFuture("Status: " + JobResultStatus.DONE.toString()+" jobId: " + job.getId());
    }
}
