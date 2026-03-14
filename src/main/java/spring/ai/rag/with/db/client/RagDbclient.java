package spring.ai.rag.with.db.client;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class RagDbclient {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    @Value("classpath:prompts/systemDataPrompt.st")
    private Resource resource;

    public RagDbclient(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public String askToAI(String username , String message) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(message)
                .topK(3) // Retrieve top 5 relevant documents
                .similarityThreshold(0.3) // Set a similarity threshold for relevance
                .build();

        // Step 1: Retrieve relevant information from the vector store
        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        List<String> relevantInfo = documents
                .stream()
                .map(Document::getText)
                .toList();

        // Step 3: Send the combined message to the chat client
        return chatClient
                .prompt()
                .system(promptSystemSpec ->
                        promptSystemSpec
                                .text(resource)
                                .param("documents", relevantInfo))
                .user(message)
                .advisors(adviceSpec ->
                        adviceSpec.param(CONVERSATION_ID, username))
                .call()
                .content();
    }
}
