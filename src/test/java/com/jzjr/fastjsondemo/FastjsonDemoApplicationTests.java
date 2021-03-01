package com.jzjr.fastjsondemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.*;
import com.jzjr.fastjsondemo.bean.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversions.toUpperCase;

@SpringBootTest
class FastjsonDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    private List<Person> listOfPersons = new ArrayList<Person>();

    @BeforeEach
    public void setUp() {
        listOfPersons.add(new Person(15, "John Doe", new Date()));
        listOfPersons.add(new Person(20, "Janette Doe", new Date()));
    }

    @Test
    public void whenJavaList_thanConvertToJsonCorrect() {
        String jsonOutput = JSON.toJSONString(listOfPersons);
        System.out.println(jsonOutput);
    }

    @Test
    public void whenGenerateJson_thanGenerationCorrect() throws ParseException {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 2; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AGE", 10);
            jsonObject.put("FULL NAME", "Doe " + i);
            jsonObject.put("DATE OF BIRTH", "2016/12/12 12:12:12");
            jsonArray.add(jsonObject);
        }
        String jsonOutput = jsonArray.toJSONString();
        System.out.println(jsonOutput);
    }

    @Test
    public void whenJson_thanConvertToObjectCorrect() {
        Person person = new Person(0, "史蒂芬 霍金", new Date());
        String jsonObject = JSON.toJSONString(person);
        Person newPerson = JSON.parseObject(jsonObject, Person.class);
        System.out.println(newPerson);
        assertEquals(newPerson.getAge(), 0); // 如果我们设置系列化为 false
        assertEquals(newPerson.getFullName(), listOfPersons.get(0).getFullName());
    }

    @Test
    public void givenContextFilter_whenJavaObject_thanJsonCorrect() {
        ContextValueFilter valueFilter = new ContextValueFilter() {
            @Override
            public Object process(BeanContext beanContext, Object o, String s, Object o1) {
                if (s.equals("DATE OF BIRTH")) {
                    return "NOT TO DISCLOSE";
                }
                if (o1.equals("John Doe")) {
                    return ((String) o1).toUpperCase();
                } else {
                    return null;
                }
            }
        };
        String jsonOutput = JSON.toJSONString(listOfPersons, valueFilter);
        System.out.println(jsonOutput);
    }

    @Test
    public void givenSerializeConfig_whenJavaObject_thanJsonCorrect(){
        NameFilter nameFilter = new NameFilter() {
            @Override
            public String process(Object o, String s, Object o1) {
                return s.toUpperCase().replace(" ","-");
            }
        };
        SerializeConfig.getGlobalInstance().addFilter(Person.class,nameFilter);
        String person = JSON.toJSONStringWithDateFormat(listOfPersons, "yyyy/MM/dd");
        System.out.println(person);
    }

}
