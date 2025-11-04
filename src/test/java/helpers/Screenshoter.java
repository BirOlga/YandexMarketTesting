package helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Attachment;

public class Screenshoter {
	
	protected static String outputDirectory="";
	
	protected static int counter=1;
	/**
	 * Метод делает снимок экрана и сохраняет в директорию
	 * @param webDriver веб=драйвер
	 * @return снимок экрана в последовательности байт
	 * 
	 * @author Бирюкова Ольга
	 */
	@Attachment()
	public static byte[] makeScreenshot(WebDriver webDriver) {
		File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
            FileUtils.copyFile(screenshot, new File("src/test/resources/"+outputDirectory+"/screenshot"+counter+".png"));
            counter++;
            return Files.readAllBytes(Paths.get("src/test/resources/"+outputDirectory, "screenshot"+(counter-1)+".png"));
        } catch (Exception e){
            e.printStackTrace();
            Assertions.fail("Не удалось сохранить файл на диск");
        }
        return new byte[0];
	}
	
	/**
	 * Метод инициализирует название директории, в которую будут помещены скриншоты с результатами тестирования
	 * 
	 * @author Бирюкова Ольга
	 */
	protected static void setOutputDirectory() {
		outputDirectory="test_"+String.valueOf(LocalDateTime.now()).replace(":", "");
	}
	
	/**
	 * Метод создает директорию для скриншотов с результатом
	 * 
	 * @author Бирюкова Ольга
	 */
	protected static void createOutputDirectory() {
		try {
			Files.createDirectory(Path.of("src/main/resources/"+outputDirectory));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Метод выполняет настройку директории для скриншотирования результатов
	 * 
	 * @author Бирюкова Ольга
	 */
	public static void configureScreenshoter() {
		setOutputDirectory();
		createOutputDirectory();
	}
	
	

}
