package com.example.AbiturBot.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name="spezialization")
public class Spezialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name="name")
    String name;
}
