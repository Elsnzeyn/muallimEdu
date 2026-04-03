package org.example.muallimeduazbackend.service;

import org.example.muallimeduazbackend.dto.request.CreateSubsRequestDto;
import org.example.muallimeduazbackend.entity.Subscriber;

import java.util.List;

public interface SubscriberService {
    void createSubs(CreateSubsRequestDto body);
    List<Subscriber> getSubs();
}
