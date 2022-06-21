package uz.mh.talkoncopy.entities.transaction;

import lombok.*;
import lombok.experimental.FieldDefaults;
import uz.mh.talkoncopy.entities.base.Auditable;
import uz.mh.talkoncopy.entities.user.Account;
import uz.mh.talkoncopy.enums.PayType;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "transaction")
@Entity(name = "transactions")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction extends Auditable {

    Double amount;

    @Enumerated(EnumType.STRING)
    PayType payType;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

}
