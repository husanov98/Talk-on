package uz.mh.talkoncopy.entities.user;


import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "users")
@Entity(name = "account")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account extends Auditable {
   @OneToOne
   User user;
   Integer talkCoins;
}


