package com.apollocare.backend.SeleniumTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.apollocare.backend.models.Consultation;
import com.apollocare.backend.service.ConsultationService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestAdmin {

    @LocalServerPort
    private int port;

    WebDriver driver;

    @MockBean
    private ConsultationService consultationService;

    @BeforeEach
    void setUp() {
        driver = new HtmlUnitDriver();
        List<Consultation> mockConsultations = Arrays.asList(
                new Consultation(1L, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), 0, 0, 30, "12345", "1",
                        "SCHEDULED", "Cardiology", "Clinic A"),
                new Consultation(2L, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), 0, 0, 30, "12345", "2",
                        "SCHEDULED", "Cardiology", "Clinic B"));
        when(consultationService.findConsultationsByPatientId(anyString())).thenReturn(mockConsultations);
        when(consultationService.findAllConsultations()).thenReturn(mockConsultations);

    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testAddConsultation() {
        String baseUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/admin/v1/form");

        WebElement scheduledDateInput = driver.findElement(By.id("scheduledDateInput"));
        scheduledDateInput.sendKeys("2024-06-01T08:00");

        WebElement patientIdInput = driver.findElement(By.id("patientId"));
        patientIdInput.sendKeys("12345");

        WebElement doctorIdDropdown = driver.findElement(By.id("doctorId"));
        doctorIdDropdown.sendKeys("Dr. John Doe (Cardiology, Clinic A)");

        WebElement specialtyDropdown = driver.findElement(By.id("specialty"));
        specialtyDropdown.sendKeys("Cardiology");

        WebElement locationDropdown = driver.findElement(By.id("location"));
        locationDropdown.sendKeys("Clinic A");

        WebElement submitButton = driver.findElement(By.tagName("button"));
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/admin/v1/add"));
    }

    @Test
    void testGetAllConsultations() {
        String baseUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/admin/v1/all");

        String pageTitle = driver.getTitle();
        assertEquals("Consultations", pageTitle);

        List<WebElement> consultationRows = driver.findElements(By.tagName("tr"));
        assertTrue(consultationRows.size() > 1);

        List<String> expectedHeaders = Arrays.asList("ID", "Scheduled Date", "Check-In Date", "Reception Date",
                "Duration", "Patient ID", "Doctor ID", "State", "Specialty", "Location", "Actions");
        WebElement headerRow = consultationRows.get(0);
        List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
        List<String> actualHeaders = headerCells.stream().map(WebElement::getText).collect(Collectors.toList());
        assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    void testGetConsultationsByPatient() {
        String baseUrl = "http://localhost:" + port;
        String patientId = "12345";
        driver.get(baseUrl + "/admin/v1/patient_consultations?patientId=" + patientId);

        String pageTitle = driver.getTitle();
        assertEquals("Consultations", pageTitle);

        List<WebElement> consultationRows = driver.findElements(By.tagName("tr"));
        assertTrue(consultationRows.size() > 1);

        List<String> expectedHeaders = Arrays.asList("ID", "Scheduled Date", "Check-In Date", "Reception Date",
                "Duration", "Patient ID", "Doctor ID", "State", "Specialty", "Location", "Actions");
        WebElement headerRow = consultationRows.get(0);
        List<WebElement> headerCells = headerRow.findElements(By.tagName("th"));
        List<String> actualHeaders = headerCells.stream().map(WebElement::getText).collect(Collectors.toList());
        assertEquals(expectedHeaders, actualHeaders);

        for (int i = 1; i < consultationRows.size(); i++) {
            WebElement row = consultationRows.get(i);
            String patientIdInRow = row.findElement(By.xpath("./td[6]")).getText();
            assertEquals(patientId, patientIdInRow);
        }
    }

    @Test
    void testConsultationButtons() {
        Consultation scheduledConsultation = new Consultation(1L, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), 0, 0, 30, "12345", "1", "SCHEDULED", "Cardiology", "Clinic A");
        Consultation checkedInConsultation = new Consultation(2L, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC), 0, 0, 30, "12345", "2", "CHECKED_IN", "Cardiology", "Clinic A");
        when(consultationService.findConsultationsByPatientId(anyString())).thenReturn(Arrays.asList(scheduledConsultation, checkedInConsultation));
        when(consultationService.findAllConsultations()).thenReturn(Arrays.asList(scheduledConsultation, checkedInConsultation));
    
        String baseUrl = "http://localhost:" + port;
        driver.get(baseUrl + "/admin/v1/all");
        List<WebElement> consultationRows = driver.findElements(By.xpath("//tr[contains(@class, 'consultation-row')]"));
    
        for (WebElement row : consultationRows) {
            String state = row.findElement(By.xpath(".//td[8]")).getText();
    
            if (state.equals("SCHEDULED")) {
                WebElement checkInButton = row.findElement(By.xpath(".//button[text()='Check-In']"));
                assertTrue(checkInButton.isDisplayed());
                assertEquals("Check-In", checkInButton.getText());
            }
            if (state.equals("CHECKED_IN")) {
                WebElement checkOutButton = row.findElement(By.xpath(".//button[text()='Check-Out']"));
                assertTrue(checkOutButton.isDisplayed());
                assertEquals("Check-Out", checkOutButton.getText());
            }
        }
    }
}
