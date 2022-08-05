package com.vladislavgolovkin.taskfromnatlex.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "sections",schema = "natlex")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "section",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<GeologicalClasses> geologicalClasses;

}
