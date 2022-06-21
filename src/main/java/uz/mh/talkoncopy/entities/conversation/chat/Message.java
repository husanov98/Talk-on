package uz.mh.talkoncopy.entities.conversation.chat;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.user.User;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "messages")
@Table(schema = "conversation")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message extends Auditable {

    @Column(columnDefinition = "text")
    String body;

    String filePath;

    String parent;

    boolean isRead;

    @ManyToOne
    User from;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    Chat chat;
}
