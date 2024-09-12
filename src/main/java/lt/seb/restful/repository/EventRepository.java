package lt.seb.restful.repository;

import lt.seb.restful.model.Event;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EventRepository {

    @Select("SELECT * FROM events")
    List<Event> findAll();

    @Select("SELECT * from events WHERE id=#{id}")
    Optional<Event> findById(@Param("id") int id);

    @Insert("INSERT INTO events (" +
            "type, message, user_id, transaction_id, time ) " +
            "VALUES (#{type}, #{message}, #{userId}, #{transactionId}, now());")
    int createEvent(Event event);

    @Update("UPDATE events SET type = #{type}, message = #{message}," +
            " user_id = #{userId}, transaction_id = #{transactionId} WHERE id = #{id}")
    int updateEvent(Event event);

    @Delete("DELETE from events WHERE ID = #{id}")
    void deleteEvent(int id);

    @Delete("DELETE from events")
    void deleteAllEvents();

    @Select("SELECT * from events WHERE ((#{type} IS NOT NULL AND type = #{type}) OR (#{type} IS NULL)) " +
            "AND ((#{message} IS NOT NULL AND message LIKE \'%#{message}%\') OR (#{message} IS NULL))")
    List<Event> filterEvents(@Param("type") String type, @Param("message") String message);
}
