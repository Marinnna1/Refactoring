package ru.javaschool.JavaSchoolBackend2.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class MainPageTest {

    private static ChromeDriver chromeDriver;

    @BeforeAll
    public static void init() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\marus\\Downloads\\chromedriver\\chromedriver.exe");
        chromeDriver = new ChromeDriver();
        chromeDriver.get("http://localhost:3000");
    }


    @AfterAll
    static void close() {
        chromeDriver.quit();
    }


    @Test
    @DisplayName("Add patient test")
    void addPatientTest() {
        SignInPageTest signInPageTest = new SignInPageTest();
        signInPageTest.correctUserDataForDoctor();
        fillAddPatientForm("test", "test@gmail.com", "Cold",
                22, "doctor");
        String answer = chromeDriver.findElement(By.xpath("/html/body/div/div/div/form/p[1]")).getText();
        assertEquals("Add patient successfully", answer);
    }


    @Test
    @DisplayName("Delete patient test")
    void deletePatientTest() {
        SignInPageTest signInPageTest = new SignInPageTest();
        signInPageTest.correctUserDataForDoctor();
        fillDeletePatientForm("test");
        String answer = chromeDriver.findElement(By.xpath("/html/body/div/div/div/form/p[1]")).getText();
        assertEquals("Patient deleted", answer);
    }





    private void fillAddPatientForm(String name, String email, String diagnosis,
                                    Integer insurance, String doctorName) {

        WebDriverWait wait1 = new WebDriverWait(chromeDriver, 10);
        WebElement patientName = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"patientName\"]")));
        patientName.sendKeys(name);
        WebElement patientEmail = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"patientEmail\"]")));
        patientEmail.sendKeys(email);
        WebElement patientDiagnosis = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"patientDiagnosis\"]")));
        patientDiagnosis.sendKeys(diagnosis);
        WebElement patientInsurance = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"patientInsurance\"]")));
        patientInsurance.sendKeys(String.valueOf(insurance));
        WebElement doctorNameElement = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"doctorName\"]")));
        doctorNameElement.sendKeys(doctorName);
    }


    private void fillDeletePatientForm(String name) {
        WebDriverWait wait1 = new WebDriverWait(chromeDriver, 10);
        WebElement patientName = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"patientName\"]")));
        patientName.sendKeys(name);
    }

}
