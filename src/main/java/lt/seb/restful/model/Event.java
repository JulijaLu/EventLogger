package lt.seb.restful.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import lt.seb.restful.model.enums.MessageType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CreationTimestamp
    private LocalDateTime time;
    private MessageType type;
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
