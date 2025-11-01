package ru.zaitsev.Service1.service;

import org.springframework.stereotype.Service;
import ru.zaitsev.Service1.model.Request;


@Service
public interface ModifyRequestService {

    void modify(Request request);
}
