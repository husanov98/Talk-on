package uz.mh.talkoncopy.entities.conversation.chat;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.user.Mentee;
import uz.mh.talkoncopy.entities.user.Mentor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chats", uniqueConstraints = {@UniqueConstraint(columnNames = {"mentor_id", "mentee_id"})}, schema = "conversation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Chat extends Auditable {

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    Mentor mentor;

    @ManyToOne
    Mentee mentee;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "chat")
    List<Message> messages = new ArrayList<>();
}