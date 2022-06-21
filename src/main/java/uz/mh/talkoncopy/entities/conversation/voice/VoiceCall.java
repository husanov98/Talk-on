package uz.mh.talkoncopy.entities.conversation.voice;


import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.user.Mentee;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "conversation")
@Entity(name = "voice_calls")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoiceCall extends Auditable {

    Integer duration;

    @ManyToOne
    @JoinColumn(name = "mentee1_id")
    Mentee mentee1;

    @ManyToOne
    @JoinColumn(name = "mentee2_id")
    Mentee mentee2;
}
