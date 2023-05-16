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
@Entity(name="university")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="name")
    String name;

    @Column(name="info")
    String info;

    @Column(name="email")
    String email;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "faculties_univers",
            joinColumns = {@JoinColumn(name = "id_university", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_faculty", referencedColumnName = "id")})
    List<Faculty> faculties;
}
