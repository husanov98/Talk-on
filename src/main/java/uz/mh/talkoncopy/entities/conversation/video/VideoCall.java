package uz.mh.talkoncopy.entities.conversation.video;


import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.user.Mentee;
import uz.mh.talkoncopy.entities.user.Mentor;
import uz.mh.talkoncopy.enums.VideoCallStatus;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "conversation")
@Entity(name = "video_calls")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoCall extends Auditable {


    @Column(nullable = false)
    String videoLink;

    LocalDateTime startDateTime;

    LocalDateTime endDateTime;

    Integer duration;

    @Enumerated(EnumType.STRING)
    VideoCallStatus videoCallStatus;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    Mentee mentee;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    Mentor mentor;
}
