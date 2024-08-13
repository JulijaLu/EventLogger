package lt.seb.restful.api;

import lt.seb.restful.model.Event;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("lt.seb.restful.api.mapper")
@MappedTypes(Event.class)
public class EventMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventMonitorApplication.class, args);

	}
}
