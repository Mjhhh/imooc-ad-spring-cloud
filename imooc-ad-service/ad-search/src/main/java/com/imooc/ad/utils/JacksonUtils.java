package com.imooc.ad.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JacksonUtils {

    public static void main(String[] args) throws IOException {
        Writer writer = new Writer("马骏泓", 23, new Date());
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(StdDateFormat.getDateTimeInstance());
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(writer);
        System.out.println(json);
    }

}

@JsonIgnoreProperties(value = {"age", "name"})
class Writer {
    private String name;
    private int age;
    private Date brithday;

    public Writer() {
    }

    public Writer(String name, int age, Date brithday) {
        this.name = name;
        this.age = age;
        this.brithday = brithday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }

    @Override
    public String toString() {
        return "Writer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
