package ma.fsr.eda.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("patient", r -> r.path("/api/patients/**")
                        .filters(f -> f.rewritePath("/api/patients(?<segment>/?.*)",
                                "/internal/api/v1/patients${segment}"))
                        .uri("http://patient-service:8082"))

                .route("medecin", r -> r.path("/api/medecins/**")
                        .filters(f -> f.rewritePath("/api/medecins(?<segment>/?.*)",
                                "/internal/api/v1/medecins${segment}"))
                        .uri("http://medecin-service:8083"))

                .route("rendezvous", r -> r.path("/api/rendezvous/**")
                        .filters(f -> f.rewritePath("/api/rendezvous(?<segment>/?.*)",
                                "/internal/api/v1/rendezvous${segment}"))
                        .uri("http://rendezvous-service:8084"))

                .route("consultation", r -> r.path("/api/consultations/**")
                        .filters(f -> f.rewritePath("/api/consultations(?<segment>/?.*)",
                                "/internal/api/v1/consultations${segment}"))
                        .uri("http://consultation-service:8085"))

                .route("billing", r -> r.path("/api/factures/**")
                        .filters(f -> f.rewritePath("/api/factures(?<segment>/?.*)",
                                "/internal/api/v1/factures${segment}"))
                        .uri("http://billing-service:8086"))

                .route("dossier", r -> r.path("/api/dossiers/**")
                        .filters(f -> f.rewritePath("/api/dossiers(?<segment>/?.*)",
                                "/internal/api/v1/dossiers${segment}"))
                        .uri("http://dossier-service:8089"))

                .build();
    }

}
