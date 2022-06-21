package uz.mh.talkoncopy.entities.notification;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.user.User;
import uz.mh.talkoncopy.enums.NotificationType;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(schema = "notification")
@Entity(name = "notifications")
public class Notification extends Auditable {

    @Column(nullable = false)
    String title;

    @Column(columnDefinition = "text", nullable = false)
    String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    NotificationType type;

    @ManyToOne
            @JoinColumn(name = "to_id")
    User to;
}
