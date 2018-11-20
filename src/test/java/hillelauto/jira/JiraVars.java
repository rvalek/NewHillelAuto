package hillelauto.jira;

import hillelauto.Helper;

public interface JiraVars {
    String baseURL = "http://jira.hillel.it:8080/";
    String username = "autorob";
    String password = "forautotests";

    String newIssueSummary = "AutoTest " + Helper.timeStamp();
    String attachmentFileLocation = "/Users/Robert/Downloads/";
    String attachmentFileName = "TEST1.pdf";
}