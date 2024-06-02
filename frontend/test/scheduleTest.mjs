import { Builder, By, Key, until } from 'selenium-webdriver';
import { assert } from 'chai';

describe('Schedule Test', function() {
    this.timeout(30000);
    let driver;
    let vars;

    const screen = {
        width: 1485,
        height: 886 
    };


    before(async function() {
        driver = await new Builder().forBrowser('chrome').setChromeOptions(new chrome.Options().addArguments('--headless').windowSize(screen)).build();
        vars = {};
    });

    after(async function() {
        await driver.quit();
    });

    it('should perform the specified actions', async function() {
        await driver.get('http://localhost:8100/home');
        await driver.manage().deleteAllCookies();
        //await driver.manage().window().setRect({ width: 1485, height: 886 })
        await driver.findElement(By.xpath("//ion-menu-button")).click()
        await driver.findElement(By.xpath("//ion-label")).click()
        await driver.findElement(By.id("ion-input-0")).click()
        await driver.findElement(By.id("ion-input-0")).sendKeys("exampl2@gmail.com")
        await driver.findElement(By.id("ion-input-1")).click()
        await driver.findElement(By.id("ion-input-1")).sendKeys("12341234")
        await driver.findElement(By.xpath("//ion-button")).click()
        await driver.findElement(By.xpath("//ion-button")).click()
        await driver.sleep(3000);
        await driver.findElement(By.xpath("//ion-menu-button")).click()
        await driver.findElement(By.xpath("//ion-item")).click()
        await driver.sleep(3000);
        await driver.findElement(By.id("ion-input-0")).click()
        await driver.findElement(By.id("ion-input-0")).sendKeys("2024-06-13")
        await driver.findElement(By.xpath("//ion-select[@value=\'\']")).click()
        await driver.findElement(By.xpath("//button/div/div[2]")).click()
        await driver.findElement(By.xpath("//button[2]/span")).click()
        await driver.findElement(By.id("ion-input-1")).click()
        await driver.findElement(By.id("ion-input-1")).sendKeys("AVEIRO")
        await driver.sleep(1000);
        await driver.findElement(By.xpath("//ion-button")).click()
        await driver.sleep(2000);
        assert(await driver.findElement(By.xpath("//div/ion-label")).getText() == "Services")
    });
});
