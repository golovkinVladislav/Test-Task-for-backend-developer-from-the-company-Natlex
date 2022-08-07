package com.vladislavgolovkin.taskfromnatlex.service;


import com.vladislavgolovkin.taskfromnatlex.entity.Job;
import com.vladislavgolovkin.taskfromnatlex.enums.JobResultStatus;
import com.vladislavgolovkin.taskfromnatlex.enums.JobType;
import com.vladislavgolovkin.taskfromnatlex.exception_handling.JobResultException;
import com.vladislavgolovkin.taskfromnatlex.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;
    private Path path = Path.of("C:/Natlex/Download/export/");

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job startNewJob(JobType jobType) {
        Job newJob = new Job(jobType);
        newJob.setJobResultStatus(JobResultStatus.IN_PROGRESS);
        newJob.setCreatedAt(LocalDateTime.now());
        jobRepository.save(newJob);
        return newJob;
    }

    public Resource load(String filename) {
        try {
            Resource resource = new UrlResource(path.resolve(filename).normalize().toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("File is not found.");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error:" + e.getMessage());
        }
    }

    public Resource downloadXLSUrl(Long jobId) {
        if (jobRepository.getJobTypeEqualExport(jobId).getJobResultStatus().equals(JobResultStatus.DONE) &&
                jobRepository.getJobTypeEqualExport(jobId).getJobType().equals(JobType.EXPORT)) {
            return load(jobId.toString() + ".xls");
        }
        if(jobRepository.getJobTypeEqualExport(jobId).getJobResultStatus().equals(JobResultStatus.ERROR) &&
                jobRepository.getJobTypeEqualExport(jobId).getJobType().equals(JobType.EXPORT)) {
            throw new JobResultException("Job result status for jobId " +jobId + " = ERROR ");
        }
        if(jobRepository.getJobTypeEqualImport(jobId).getJobType().equals(JobType.IMPORT)){
            throw new JobResultException("The ID for the export will not find");
        }
        throw new JobResultException("The export is in progress");
    }
}
