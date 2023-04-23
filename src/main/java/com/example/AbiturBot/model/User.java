package com.example.AbiturBot.model;

import javax.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name="user")
public class User {

    @Id
    private Long chatId;
    
    private String firstName;

    private String lastName;

    private String userName;

    private Timestamp registeredAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return chatId != null && Objects.equals(chatId, user.chatId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
