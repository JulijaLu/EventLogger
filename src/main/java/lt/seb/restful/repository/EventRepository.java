package lt.seb.restful.repository;

import lt.seb.restful.model.Event;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EventRepository {

    @Select("SELECT * FROM events")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "transactionId", column = "transaction_id")
    })
    List<Event> findAll();

    @Select("SELECT * from events WHERE id=#{id}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "transactionId", column = "transaction_id")
    })
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

    @Select("SELECT * from events WHERE type=#{fieldName} OR message " +
            "LIKE #{fieldValue} OR userId = #{fieldValue, jdbcType=INTEGER} " +
            "OR transactionId = #{fieldValue, jdbcType=INTEGER}")
    List<Event> filterEvents(@Param("fieldName") String fieldName);
}
