package com.jzjr.fastjsondemo.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @JSONField(name = "AGE",serialize = false)
    private int age;

    @JSONField(name = "FULL NAME",ordinal = 2)
    private String fullName;

    @JSONField(name = "DATE OF BIRTH",deserialize = false)
    private Date dateOfBirth;
}
