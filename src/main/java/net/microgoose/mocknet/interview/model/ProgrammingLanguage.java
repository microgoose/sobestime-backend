package net.microgoose.mocknet.interview.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "programming_languages")
public class ProgrammingLanguage {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
}
