package com.healthbank.classes;

public class Test {
    String TestId="";
    String TestName="";

    public String getTestId() {
        return TestId;
    }

    public void setTestId(String testId) {
        TestId = testId;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    @Override
    public String toString() {
        return TestName;
    }
}
