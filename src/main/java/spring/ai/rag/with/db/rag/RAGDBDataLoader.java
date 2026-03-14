package spring.ai.rag.with.db.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spring.ai.rag.with.db.entity.Product;
import spring.ai.rag.with.db.repository.ProductRepository;

import java.util.List;
import java.util.Map;

@Component
public class RAGDBDataLoader {

    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private ProductRepository productRepository;

    @PostConstruct
    public void dataLoader() {

       List<Product> products  = productRepository.findAll();

        List<Document> documents = products.stream()
                .map(product -> new Document(
                        product.getName() + " : " + product.getDescription(),
                        Map.of("id", product.getId())
                ))
                .toList();

        TextSplitter textSplitter = TokenTextSplitter
                .builder()
                .withChunkSize(20)
                .withMaxNumChunks(200)
                .build(); // create tokenTextSplitter with chunk size of 20 and max number of chunks as 200

        List<Document> splitDocs = textSplitter.split(documents);

       vectorStore.add(splitDocs);
    }

}
