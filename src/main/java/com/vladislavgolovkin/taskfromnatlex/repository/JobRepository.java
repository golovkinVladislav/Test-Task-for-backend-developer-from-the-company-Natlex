package com.vladislavgolovkin.taskfromnatlex.repository;


import com.vladislavgolovkin.taskfromnatlex.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    @Query(value = "SELECT j FROM Job j where j.id=:id and j.jobType='EXPORT'")
    Job getJobTypeEqualExport(Long id);

    @Query(value = "SELECT j FROM Job j where j.id=:id and j.jobType='IMPORT'")
    Job getJobTypeEqualImport(Long id);
}
