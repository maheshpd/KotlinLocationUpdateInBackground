package com.healthbank.model;

import org.json.JSONException;
import org.json.JSONObject;

public class SelectedTest {
    int TestId = 0;
    String TestName;
    String TestType;
    int advised;

    public SelectedTest() {
    }

    public SelectedTest(JSONObject obj) {

        try {
            if (obj.has("TestId")) {
                this.TestId = obj.getInt("TestId");
            } if (obj.has("TestName")) {
                this.TestName = obj.getString("TestName");
            } if (obj.has("TestType")) {
                this.TestType = obj.getString("TestType");
            } if (obj.has("advised")) {
                this.advised = obj.getInt("advised");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public int getTestId() {
        return TestId;
    }

    public void setTestId(int testId) {
        TestId = testId;
    }

    public String getTestName() {
        return TestName;
    }

    public void setTestName(String testName) {
        TestName = testName;
    }

    public String getTestType() {
        return TestType;
    }

    public void setTestType(String testType) {
        TestType = testType;
    }

    public int getAdvised() {
        return advised;
    }

    public void setAdvised(int advised) {
        this.advised = advised;
    }
}
