package com.vladislavgolovkin.taskfromnatlex.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "geologicalclasses",schema = "natlex")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeologicalClasses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "section_id")
    @JsonBackReference
    private Section section;

}
