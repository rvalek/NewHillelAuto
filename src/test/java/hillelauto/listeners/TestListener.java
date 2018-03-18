package hillelauto.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
    }

    public void onTestStart(ITestResult result) {
        // result.getTestContext().getSkippedTests().removeResult(result.getMethod());
    }

    public void onTestSuccess(ITestResult result) {
    }

    public void onTestFailure(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }
}