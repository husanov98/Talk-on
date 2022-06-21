package uz.mh.talkoncopy.entities.user;

import lombok.*;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.conversation.chat.Message;
import uz.mh.talkoncopy.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(schema = "users")

public class User extends Auditable {
    private String firstName;
    private String lastname;
    private String password;
    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @Email
    @Column(unique = true)
    private String email;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isOnline;

    private LocalDateTime lastSeen;

    private String code;

    private int tryCount;

    private boolean firstTime;

    @Column(nullable = false)
    private LocalDateTime expiry;

    private int timeZone;

    @Column(columnDefinition = "text")
    private String photoPath;

    @OneToMany(mappedBy = "from",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    List<Message> chatMessages = new ArrayList<>();

    @Builder(builderMethodName = "childBuilder")

    public User(String id, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, boolean deleted, short status, String firstName, String lastname, String password, String phoneNumber, String email, LocalDate dateOfBirth, Role role, boolean isOnline, LocalDateTime lastSeen, String code, int tryCount, boolean firstTime, LocalDateTime expiry, int timeZone, String photoPath, List<Message> chatMessages) {
        super(id, createdAt, updatedAt, createdBy, deleted, status);
        this.firstName = firstName;
        this.lastname = lastname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.role = role;
        this.isOnline = isOnline;
        this.lastSeen = lastSeen;
        this.code = code;
        this.tryCount = tryCount;
        this.firstTime = firstTime;
        this.expiry = expiry;
        this.timeZone = timeZone;
        this.photoPath = photoPath;
        this.chatMessages = chatMessages;
    }
}
