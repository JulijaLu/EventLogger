package lt.seb.restful.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import lt.seb.restful.api.dto.enums.MessageType;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Accessors(chain = true)
@Table(name = "events")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @CreationTimestamp
    private LocalDateTime time;
    private String type;
    @Size(min = 10, max = 1024)
    private String message;
    private int userId;
    private int transactionId;

    public Event(MessageType type, String message, int userId, int transactionId) {
        this.type = type.toString();
        this.message = message;
        this.userId = userId;
        this.transactionId = transactionId;
    }
}
