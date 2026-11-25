package com.globits.da.rest;

import com.globits.da.dto.MyFirstApiDto;
import com.globits.da.service.MyFirstApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public/my-first-api")
public class MyFirstApiController {
    @Autowired
    private MyFirstApiService myFirstApiService;

    // #4
    @GetMapping("/get-string")
    public String myFirstApi() {
        return "MyFirstApi";
    }

    //#5
    @GetMapping("/get-service-string")
    public String getServiceString() {
        return this.myFirstApiService.getMyFirstApi();
    }

    //#6
    @PostMapping("/post-dto")
    public MyFirstApiDto getMyFirstApiDto(@RequestBody MyFirstApiDto myFirstApiDto) {
        return myFirstApiDto;
    }

    //#9
    @PostMapping("/post-by-param")
    public MyFirstApiDto postByParam(
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "age", required = false) int age
    ) {
        MyFirstApiDto dto = new MyFirstApiDto();
        dto.setCode(code);
        dto.setName(name);
        dto.setAge(age);
        return dto;
    }

    //#10
    @PostMapping("/get-by-path/{code}")
    public MyFirstApiDto getByPath(@PathVariable("code") String code) {
        MyFirstApiDto dto = new MyFirstApiDto();
        dto.setCode(code);
        dto.setName("Nguyen Van A");
        dto.setAge(20);
        return dto;
    }

    // #11
    @PostMapping("/post-no-annotation")
    public MyFirstApiDto postNoAnnotation(MyFirstApiDto dto) {
        return dto;
    }

    // #12
    @PostMapping("/upload-file")
    public Map<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        if (file.isEmpty()) {
            response.put("message", "File rỗng!");
            return response;
        }
        response.put("fileName", file.getOriginalFilename());
        response.put("fileSize", file.getSize() + " bytes");
        response.put("contentType", file.getContentType());
        response.put("status", "Upload file thành công!");
        return response;
    }

    //#13
    @PostMapping("/post-no-requestbody")
    public MyFirstApiDto postNoRequestBody(MyFirstApiDto dto) {
        return dto;
    }

}
