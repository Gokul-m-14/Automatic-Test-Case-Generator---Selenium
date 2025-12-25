package com.autotest;

public class TestCase {
    public String id;
    public String module;
    public String scenario;
    public String description;
    public String steps;
    public String expected;
    public String priority;

    public TestCase(String module, String scenario, String description, String steps, String expected, String priority) {
        this.id = TestCaseGenerator.genId(module);
        this.module = module;
        this.scenario = scenario;
        this.description = description;
        this.steps = steps;
        this.expected = expected;
        this.priority = priority;
    }
}
