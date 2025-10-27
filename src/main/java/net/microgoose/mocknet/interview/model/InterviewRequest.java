package net.microgoose.mocknet.interview.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interview_request")
public class InterviewRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private InterviewRequestStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private InterviewUser creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private InterviewRole role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "interview_request_grade",
        joinColumns = @JoinColumn(name = "request_id"),
        inverseJoinColumns = @JoinColumn(name = "grade_id")
    )
    private Set<Grade> grades;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "interview_request_skill",
        joinColumns = @JoinColumn(name = "request_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @OneToMany(mappedBy = "request", fetch = FetchType.LAZY)
    private Set<InterviewSlot> slots;
}
