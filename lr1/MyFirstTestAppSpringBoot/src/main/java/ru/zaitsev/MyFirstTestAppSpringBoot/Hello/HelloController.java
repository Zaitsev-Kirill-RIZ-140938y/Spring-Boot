package ru.zaitsev.MyFirstTestAppSpringBoot.Hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@RestController
public class HelloController {
    private List<String> storage;
    private Map<Integer, String> mapStorage;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name",
            defaultValue = "World") String name) {
        return String.format("Hello, %s!", name);
    }

    @GetMapping("/update-array")
    public List<String> updateArrayList(@RequestParam(name = "s", defaultValue = "") String s) {
        if (storage == null) {
            storage = new ArrayList<>();
        }
        if (!s.isBlank()) {
            storage.add(s);
        }
        return storage;
    }

    @GetMapping("/show-array")
    public String showArrayList() {
        if (storage == null) {
            return "Список ещё не создан. Сначала вызови /update-array?s=...";
        }
        return storage.toString();
    }

    @GetMapping("/update-map")
    public Map<Integer, String> updateHashMap(
            @RequestParam(name = "s", defaultValue = "") String s
    ) {
        if (mapStorage == null) {
            mapStorage = new HashMap<>();
        }
        if (!s.isBlank()) {
            int nextKey = mapStorage.size() + 1;
            mapStorage.put(nextKey, s);
        }
        return mapStorage;
    }

    @GetMapping("/show-map")
    public String showHashMap() {
        if (mapStorage == null) {
            return "Карта ещё не создана. Сначала вызови /update-map?s=...";
        }
        return mapStorage.toString();
    }

    @GetMapping("/show-all-lenght")
    public String showAllLenght() {
        if (storage == null && mapStorage == null) {
            return "Карта и список ещё не созданы";
        }
        int listCount = (storage == null) ? 0 : storage.size();
        int mapCount  = (mapStorage == null) ? 0 : mapStorage.size();
        return "ArrayList: " + listCount + ", HashMap: " + mapCount;
    }

}
