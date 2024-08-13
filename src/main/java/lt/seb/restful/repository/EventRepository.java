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
            "type, message, userId, transactionId ) " +
            "VALUES (#{type}, #{message}, #{userId}, #{transactionId});")
    Event createEvent(Event event);

    @Update("UPDATE events SET type = #{type}, message = #{message}," +
            " userId = #{userId}, transactionId = #{transactionId} WHERE id = #{id}")
    Event updateEvent(Event event);

    @Delete("DELETE from events WHERE ID = #{id}")
    void deleteEvent(int id);

}
