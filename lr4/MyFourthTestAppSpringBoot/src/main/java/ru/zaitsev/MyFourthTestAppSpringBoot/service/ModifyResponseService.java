package ru.zaitsev.MyFourthTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.MyFourthTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);
}
