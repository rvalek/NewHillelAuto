package hillelauto.jira;

import hillelauto.Tools;

public interface JiraVars {
    public static final String baseURL = "http://jira.hillel.it:8080/";
    public static final String username = "autorob";
    public static final String password = "forautotests";

    public static final String newIssueSummary = "Robert AutoTest " + Tools.timestamp();
    public static final String attachmentFileLocation = "/Users/Robert/Downloads/";
    public static final String attachmentFileName = "Test.png";
}