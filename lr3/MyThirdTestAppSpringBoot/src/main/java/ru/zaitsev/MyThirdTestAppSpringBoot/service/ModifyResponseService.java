package ru.zaitsev.MyThirdTestAppSpringBoot.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.MyThirdTestAppSpringBoot.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);
}
