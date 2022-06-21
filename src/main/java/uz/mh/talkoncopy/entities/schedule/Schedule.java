package uz.mh.talkoncopy.entities.schedule;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.user.Mentee;
import uz.mh.talkoncopy.entities.user.Mentor;
import uz.mh.talkoncopy.enums.ScheduleStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

//@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "schedule")
@Entity(name = "schedules")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule extends Auditable {

    @Column(nullable = false)
    LocalDateTime startDateTime;

    Integer duration;

    @Enumerated(EnumType.STRING)
    ScheduleStatus status;

    Integer cost;

    @ManyToOne
    Mentor mentor;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    Mentee mentee;
}
