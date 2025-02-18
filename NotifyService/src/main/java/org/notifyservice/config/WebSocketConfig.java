package org.notifyservice.config;

import lombok.RequiredArgsConstructor;
import org.authservice.annotations.ImportFilterJWT;
import org.notifyservice.interceptor.JwtAuthChannelInterceptor;
import org.notifyservice.interceptor.JwtAuthHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@ImportFilterJWT
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtAuthHandshakeInterceptor jwtAuthHandshakeInterceptor;
    private final JwtAuthChannelInterceptor authChannelInterceptorAdapter;


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*")
                .addInterceptors(jwtAuthHandshakeInterceptor)
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.interceptors(authChannelInterceptorAdapter);
    }

}
