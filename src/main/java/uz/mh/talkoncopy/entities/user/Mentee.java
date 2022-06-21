package uz.mh.talkoncopy.entities.user;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.schedule.Schedule;
import uz.mh.talkoncopy.enums.Level;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "users",indexes = {
        @Index(name = "level_index",columnList = "level"),
        @Index(name = "conversationCount_index",columnList = "conversationCount")
})
@Entity(name = "mentees")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mentee extends Auditable {
    public Mentee(Level level){
        this.setId(UUID.randomUUID().toString());
        this.level = level;
    }

    @Enumerated(EnumType.STRING)
    Level level;

    int conversationCount;

    @OneToOne(optional = false)
    User user;

    @OneToMany(mappedBy = "mentee")
    List<Schedule> schedules = new ArrayList<>();
}