package spring.ai.rag.with.db.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfiguration {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder){
        SimpleLoggerAdvisor loggerAdvisor = new SimpleLoggerAdvisor();
        return builder
                .defaultAdvisors(loggerAdvisor)
                .build();
    }
}
