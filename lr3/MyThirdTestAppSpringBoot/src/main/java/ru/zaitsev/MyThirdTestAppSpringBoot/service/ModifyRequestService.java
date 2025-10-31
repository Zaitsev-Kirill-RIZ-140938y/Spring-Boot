package ru.zaitsev.MyThirdTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.MyThirdTestAppSpringBoot.model.Request;


@Service
public interface ModifyRequestService {

    void modify(Request request);
}
