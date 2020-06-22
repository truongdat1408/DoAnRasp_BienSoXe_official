package com.example.doanrasp_biensoxe;

public class Post {
    String name;
    String code;
    String phone;

    @Override
    public String toString() {
        return "Post{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Post() {
    }
}
