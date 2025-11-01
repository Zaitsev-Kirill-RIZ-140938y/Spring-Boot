package ru.zaitsev.Service1.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.zaitsev.Service1.model.Request;
import ru.zaitsev.Service1.model.Systems;
import ru.zaitsev.Service1.util.DateTimeUtil;

import java.util.Date;


@Service
public class ModifySystemNameRequestService implements ModifyRequestService {

    @Override
    public void modify(Request request) {
        // Изменяем поле SystemName
        request.setSystemName(Systems.CRM);
        // Изменяем поле Source
        request.setSource("Service 1");
        request.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));


        HttpEntity<Request> httpEntity = new HttpEntity<>(request);

        new RestTemplate().exchange("http://localhost:8082/feedback",
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<>() {
                });
    }
}
