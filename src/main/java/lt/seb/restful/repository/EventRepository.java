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

    @Select("SELECT * from events WHERE user_id=#{user_id}")
    Optional<Event> findByUserId(@Param("user_id") Integer user_id);

    @Insert("INSERT INTO events (" +
            "type, message, user_id, transaction_id, time ) " +
            "VALUES (#{type}, #{message}, #{userId}, #{transactionId}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int createEvent(Event event);

    @Update("UPDATE events SET type = #{type}, message = #{message}," +
            " user_id = #{userId}, transaction_id = #{transactionId} WHERE id = #{id}")
    int updateEvent(Event event);

    @Delete("DELETE from events WHERE ID = #{id}")
    void deleteEvent(int id);

    @Delete("DELETE from events")
    void deleteAllEvents();

    @Select({
            "<script>" +
            "SELECT * FROM events " +
            "WHERE 1=1 " +
            "<if test='type != null'>AND type = #{type}</if> " +
            "<if test='message != null'>AND message LIKE CONCAT('%', #{message}, '%')</if> " +
                    "<if test='user_id != null'>AND user_id = #{user_id}</if> " +
                    "<if test='transaction_id != null'>AND transaction_id = #{transaction_id}</if>" +
            "</script>"
    })
    List<Event> filterEvents(@Param("type") String type, @Param("message") String message,
                             @Param("user_id") Integer user_id, @Param("transaction_id") Integer transaction_id);
}
