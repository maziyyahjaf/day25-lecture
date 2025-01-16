package vttp5.paf.day25.day_paf_25_producer.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// import vttp5.paf.day25.day_paf_25_producer.model.Order;
import vttp5.paf.day25.day_paf_25_producer.model.Todo;
import vttp5.paf.day25.day_paf_25_producer.service.ProducerService;

@RestController
@RequestMapping("/api/messages")
public class ProducerController {
    
    @Autowired
    ProducerService producerService;

    @PostMapping("")
    public ResponseEntity<String> sendMessage(@RequestBody Todo todo) {
        producerService.sendMessage(todo);
        return new ResponseEntity<>("Message Sent", HttpStatus.OK);
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendOrder(@RequestBody String rawJsonString) {
        producerService.publish(rawJsonString);

        return new ResponseEntity<>("Order sent", HttpStatus.OK);
    }

    @PostMapping("/publish2")
    public ResponseEntity<String> sendOrder2(@RequestBody String rawJsonString) {
        producerService.publish2(rawJsonString);

        return new ResponseEntity<>("Order sent", HttpStatus.OK);
    }
}
