package com.vladislavgolovkin.taskfromnatlex.controller;


import com.vladislavgolovkin.taskfromnatlex.entity.Job;
import com.vladislavgolovkin.taskfromnatlex.enums.JobResultStatus;
import com.vladislavgolovkin.taskfromnatlex.enums.JobType;
import com.vladislavgolovkin.taskfromnatlex.exception_handling.JobResultException;
import com.vladislavgolovkin.taskfromnatlex.repository.JobRepository;
import com.vladislavgolovkin.taskfromnatlex.service.ExportFileXLS;
import com.vladislavgolovkin.taskfromnatlex.service.ImportFileXLS;
import com.vladislavgolovkin.taskfromnatlex.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
@RequestMapping("/api")
public class JobController {
    @Autowired
    private ExportFileXLS exportFileXLS;
    @Autowired
    private JobService jobService;
    @Autowired
    private ImportFileXLS importFileXLS;
    @Autowired
    private JobRepository jobRepository;


    @GetMapping("/jobs")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/jobs/export")
    public CompletableFuture<String> exportFileXLS() throws IOException {
        Job newExportJob = jobService.startNewJob(JobType.EXPORT);
        return exportFileXLS.exportFileXLS(newExportJob);
    }

    @GetMapping("/jobs/export/{id}")
    public JobResultStatus getJobResultStatusByExportById(@PathVariable Long id) {
        if (jobRepository.getJobTypeEqualExport(id) == null) {
            throw new JobResultException("There is no result for this ID");
        }
        return jobRepository.getJobTypeEqualExport(id).getJobResultStatus();
    }

    @GetMapping(value = "/jobs/export/{id}/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getXLSFileByJobId(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Resource resource = jobService.downloadXLSUrl(id);
        Date current = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm");
        String s = format.format(current);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + s + " idFile-" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/jobs/import/{id}")
    public JobResultStatus getJobResultStatusByImportById(@PathVariable Long id) {
        if (jobRepository.getJobTypeEqualImport(id) == null) {
            throw new JobResultException("There is no result for this ID");
        }
        return jobRepository.getJobTypeEqualImport(id).getJobResultStatus();
    }

    @PostMapping(value = "/jobs/import")
    public CompletableFuture<String> importFileXLS() {
        Job newImportJob = jobService.startNewJob(JobType.IMPORT);
        return importFileXLS.importFileXLS(newImportJob);

    }
}


