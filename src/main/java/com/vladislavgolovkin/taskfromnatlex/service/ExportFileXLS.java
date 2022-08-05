package com.vladislavgolovkin.taskfromnatlex.service;


import com.vladislavgolovkin.taskfromnatlex.entity.GeologicalClasses;
import com.vladislavgolovkin.taskfromnatlex.entity.Job;
import com.vladislavgolovkin.taskfromnatlex.entity.Section;
import com.vladislavgolovkin.taskfromnatlex.enums.JobResultStatus;
import com.vladislavgolovkin.taskfromnatlex.repository.JobRepository;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ExportFileXLS {
    @Autowired
    private SectionService sectionService;
    @Autowired
    private JobRepository jobRepository;

    @Value("${path.toExport}")
    private String thePathForTheFileToExport;

    @Async
    public CompletableFuture<String> exportFileXLS(Job job) throws IOException {
        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            HSSFSheet sheet = hssfWorkbook.createSheet("Sections");

            Row headerRow = sheet.createRow(0);
            Cell section_cell = headerRow.createCell(0);
            section_cell.setCellValue("Section name");

            List<Section> sectionList = sectionService.getAllSections();
            int columnsCount = 0;
            for (Section s : sectionList) {
                int geoClassLength = s.getGeologicalClasses().size();
                if (columnsCount < geoClassLength) {
                    columnsCount = geoClassLength;
                }
            }
            columnsCount *= 2;

            int j = 1;
            for (int i = 1; i <= columnsCount; i += 1) {
                Cell cell = headerRow.createCell(i);
                if (i % 2 == 0) {
                    cell.setCellValue("Code name " + j);
                    j++;
                } else {
                    cell.setCellValue("Class name " + j);
                }
            }
            int rowNumber = 1;
            for (Section section : sectionList) {
                int cell = 0;
                Row row = sheet.createRow(rowNumber++);

                row.createCell(cell).setCellValue(section.getName());

                List<GeologicalClasses> geologicalClasses = section.getGeologicalClasses();
                if (geologicalClasses == null) {
                    continue;
                }
                cell++;
                for (GeologicalClasses geologicalClasses1 : geologicalClasses) {
                    row.createCell(cell).setCellValue(geologicalClasses1.getName());
                    cell++;

                    row.createCell(cell).setCellValue(geologicalClasses1.getCode());
                    cell++;
                }
            }

            String path = thePathForTheFileToExport + job.getId() + ".xls";
            FileOutputStream fos = new FileOutputStream(path);
            hssfWorkbook.write(fos);
            fos.close();
            job.setJobResultStatus(JobResultStatus.DONE);
            jobRepository.save(job);
            hssfWorkbook.close();
        } catch (Exception e) {
            job.setJobResultStatus(JobResultStatus.ERROR);
            jobRepository.save(job);
            return CompletableFuture.completedFuture("Status: " + JobResultStatus.ERROR.toString() + " jobId: " + job.getId());
        }
        return CompletableFuture.completedFuture("Status: " + JobResultStatus.DONE.toString() + " jobId: " + job.getId());
    }
}
