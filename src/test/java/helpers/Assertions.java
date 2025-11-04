package helpers;

import io.qameta.allure.Step;

public class Assertions {
	/**
	 * Метод переопределяет Assertions.assertTrue junit
	 * 
	 * @param condition условие
	 * @param message сообщение, которое выдается, если условие ложно
	 * 
	 * @author Бирюкова Ольга
	 */
	@Step("Проверяем что нет ошибки: '{message}'")
    public static void assertTrue(boolean condition, String message){
        org.junit.jupiter.api.Assertions.assertTrue(condition, message);
    }

}
