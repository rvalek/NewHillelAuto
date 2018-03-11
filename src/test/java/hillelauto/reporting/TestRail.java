package hillelauto.reporting;

import java.util.HashMap;

import org.json.simple.JSONObject;

public class TestRail {
    APIClient client;
    Long runID;

    public TestRail(String baseURL) {
        client = new APIClient(baseURL);
    }

    public void setCreds(String username, String password) {
        client.setUser(username);
        client.setPassword(password);
    }

    public void startRun(Integer projectID, String runName) throws Exception {
        JSONObject r = (JSONObject) client.sendPost(String.format("add_run/%d", projectID),
                new HashMap<>().put("name", runName));
        this.runID = (Long) r.get("id");
    }

    public void endRun() throws Exception {
        client.sendPost(String.format("close_run/%d", this.runID), new HashMap<>());
    }

    public void setResult(Integer caseID, Integer testNGResult) throws Exception {
        client.sendPost(String.format("add_result_for_case/%d/%d", this.runID, caseID),
                new HashMap<>().put("status_id", convertResult(testNGResult)));
    }

    private Integer convertResult(Integer testNGResult) {
        switch (testNGResult) {
        case 1:
            return 1; // Success
        case 2:
            return 5; // Failure
        case 3:
            return 2; // Skip/Blocked
        default:
            return 4; //Retest
        }
    }
}
