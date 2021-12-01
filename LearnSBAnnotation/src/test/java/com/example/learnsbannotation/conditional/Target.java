package com.example.learnsbannotation.conditional;

/**
 * @author liujd65
 * @date 2021/11/23 9:46
 **/
public class Target {
    private String id;
    private String name;

    public Target(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Target{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
