package ru.zaitsev.Service1.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.Service1.model.Response;

@Service
public interface ModifyResponseService {

    Response modify(Response response);
}
