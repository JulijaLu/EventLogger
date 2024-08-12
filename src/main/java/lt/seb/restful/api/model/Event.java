package lt.seb.restful.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lt.seb.restful.api.model.enums.MessageType;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CreationTimestamp
    private LocalDateTime time;
    @Getter @Setter private MessageType type;
    @Size(min = 10, max = 1024)
    private String message;
    private int userId;
    private int transactionId;

    public Event(MessageType type, String message, int userId, int transactionId) {
        this.type = type;
        this.message = message;
        this.userId = userId;
        this.transactionId = transactionId;
    }

}
