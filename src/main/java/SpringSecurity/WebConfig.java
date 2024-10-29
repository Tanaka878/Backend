package SpringSecurity;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://distinguished-happiness-production.up.railway.app")  // Replace with your frontend's URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowCredentials(true);  // If you need to allow credentials (e.g., cookies)
    }
}
