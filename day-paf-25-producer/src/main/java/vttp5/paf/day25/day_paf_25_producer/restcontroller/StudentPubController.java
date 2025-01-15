package vttp5.paf.day25.day_paf_25_producer.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp5.paf.day25.day_paf_25_producer.model.Student;
import vttp5.paf.day25.day_paf_25_producer.service.ProducerService;

@RestController
@RequestMapping("/api/students")
public class StudentPubController {

    @Autowired
    ProducerService producerService;

    @PostMapping("")
    public ResponseEntity<String> sendMessage(@RequestBody Student student) {
        producerService.sendMessage(student);
        return new ResponseEntity<>("Message Sent", HttpStatus.OK);
    }

    
}
