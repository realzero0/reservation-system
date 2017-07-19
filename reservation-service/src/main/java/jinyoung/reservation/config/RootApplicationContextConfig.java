package jinyoung.reservation.config;

import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(basePackages = { "jinyoung.reservation.dao", "jinyoung.reservation.service" })
@Import({ DbConfig.class })
public class RootApplicationContextConfig {
}
