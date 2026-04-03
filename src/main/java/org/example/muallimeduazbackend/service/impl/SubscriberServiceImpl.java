package org.example.muallimeduazbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.muallimeduazbackend.dto.request.CreateSubsRequestDto;
import org.example.muallimeduazbackend.entity.Subscriber;
import org.example.muallimeduazbackend.repository.SubscriberRepository;
import org.example.muallimeduazbackend.service.SubscriberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {
    private final SubscriberRepository subsRepo;

    @Override
    public void createSubs(CreateSubsRequestDto body){
        subsRepo.save(Subscriber.builder().email(body.getEmail()).build());
    }

    @Override
    public List<Subscriber> getSubs(){
        return subsRepo.findAll();
    }
}
