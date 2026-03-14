package spring.ai.rag.with.db.controller;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.web.bind.annotation.*;
import spring.ai.rag.with.db.client.RagDbclient;

@RestController
@RequestMapping("/api/rag")
public class RagDbController {

    private final RagDbclient ragDbclient;

    public RagDbController(RagDbclient ragDbclient) {
        this.ragDbclient = ragDbclient;
    }

    @GetMapping("/askAI")
    public String askAI(@RequestHeader String useName, @RequestParam String message){
        return ragDbclient.askToAI(useName, message);
    }
}

