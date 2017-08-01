package bicycle.reservation.config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = { "bicycle.reservation.dao", "bicycle.reservation.service" })
@Import({ DbConfig.class })
public class RootApplicationContextConfig {
}
