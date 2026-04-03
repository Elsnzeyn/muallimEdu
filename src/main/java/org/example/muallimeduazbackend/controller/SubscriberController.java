package org.example.muallimeduazbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.CreateSubsRequestDto;
import org.example.muallimeduazbackend.dto.response.GeneralResponseDto;
import org.example.muallimeduazbackend.entity.Subscriber;
import org.example.muallimeduazbackend.service.SubscriberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscribers")
public class SubscriberController {
    private final SubscriberService subscriberService;

    @PostMapping()
    public ResponseEntity<GeneralResponseDto<?>> createSubscriber(@RequestBody CreateSubsRequestDto body){
        subscriberService.createSubs(body);
        var response = new GeneralResponseDto<>("Sub successfully added", null);
        return ResponseEntity.ok(response);
    }
    @GetMapping()
    public ResponseEntity<GeneralResponseDto<List<Subscriber>>> getSubscribers(){
        var response = new GeneralResponseDto<>("all subscribers", subscriberService.getSubs());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
