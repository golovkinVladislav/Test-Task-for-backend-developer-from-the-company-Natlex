package com.vladislavgolovkin.taskfromnatlex.entity;


import com.vladislavgolovkin.taskfromnatlex.enums.JobResultStatus;
import com.vladislavgolovkin.taskfromnatlex.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jobs",schema = "natlex")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "job_type")
    private JobType jobType;
    @Enumerated(EnumType.STRING)
    @Column(name = "job_result")
    private JobResultStatus jobResultStatus;
    @Column(name = "date_time_create",updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public Job(JobType jobType) {
        this.jobType = jobType;
    }
}
