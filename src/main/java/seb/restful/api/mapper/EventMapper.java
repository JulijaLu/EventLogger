package seb.restful.api.mapper;

import org.apache.ibatis.annotations.*;
import seb.restful.api.model.Event;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface EventMapper {

    @Select("SELECT * FROM events")
    List<Event> findAll();

    @Select("SELECT * from events WHERE id=#{id}")
    Event findById(int id);

    @Insert("INSERT INTO events (" +
            "id, time, type, message, userId, transactionId ) " +
            "VALUES (#{id}, #{time}, #{type}, #{message}, #{userId}, #{transactionId});")
    void createEvent(Event event);

    @Update("UPDATE events SET time = #{time}, type = #{type}, message = #{message}," +
            " userId = #{userId}, transactionId = #{transactionId} WHERE id = #{id}")
    void updateEvent(Event event);

    @Delete("DELETE from events WHERE ID = #{id}")
    void deleteEvent(int id);

}
