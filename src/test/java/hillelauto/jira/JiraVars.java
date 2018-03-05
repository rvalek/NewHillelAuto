package hillelauto.jira;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface JiraVars {
	static final String baseURL = "http://jira.hillel.it:8080/";
	static final String username = "autorob";
	static final String password = "forautotests";

	static final String newIssueSummary = "AutoTest " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	static final String attachmentFileLocation = "/Users/Robert/Downloads/";
	static final String attachmentFileName = "TEST1.pdf";
}