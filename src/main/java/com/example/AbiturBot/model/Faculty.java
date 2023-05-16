package com.example.AbiturBot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name="faculties")
public class Faculty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="name_faculty")
    String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "spezializations_univer",
            joinColumns = {@JoinColumn(name = "id_faculty", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_spezoalization", referencedColumnName = "id")})
    List<Spezialization> spezializations;
}
