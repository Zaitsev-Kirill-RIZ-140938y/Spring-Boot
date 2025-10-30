package ru.zaitsev.MySecondTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.MySecondTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);
}
