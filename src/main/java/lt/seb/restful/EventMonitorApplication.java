package lt.seb.restful;

import lt.seb.restful.model.Event;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("lt.seb.restful.repository")
@MappedTypes(Event.class)
public class EventMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventMonitorApplication.class, args);

	}
}