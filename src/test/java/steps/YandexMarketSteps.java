package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helpers.Assertions;
import helpers.Screenshoter;
import io.qameta.allure.Step;
import pages.YandexMarketCatalogPage;
import pages.YandexMarketHeader;

public class YandexMarketSteps {
	
	private static WebDriverWait webDriverWait;
    private static WebDriver webDriver;
    
    /**
     * Метод открывает сайт и инициализирует параметры драйвера
     * 
     * @param url ссылка на сайт
     * @param title Заголовок целевого сайта
     * @param webDriverParam параметр веб-драйвера
     * @param webDriverWaitParam параметр для явного ожидания
     * 
     * @author Бирюкова Ольга
     */
    @Step("Переходим на сайт: {url}")
	public static void openSite(String url, String title, WebDriver webDriverParam,WebDriverWait webDriverWaitParam){
        webDriver= webDriverParam;
        webDriver.get(url);
        webDriverWait = webDriverWaitParam;
        webDriverWait.until(ExpectedConditions.titleIs(title));
        Screenshoter.makeScreenshot(webDriver);
    }
	
    /**
     * Метод открывает каталог Яндекс Маркета
     * 
     * @param yandexMarketHeader объект класса веб-страницы
     * 
     * @author Бирюкова Ольга
     */
    @Step("Открываем каталог Яндекс Маркета")
	public static void openYandexMarketCatalog(YandexMarketHeader yandexMarketHeader){
		//closeLoginPopup();
		WebElement catalogButton=yandexMarketHeader.getCatalogButton();
		catalogButton.click();
		Screenshoter.makeScreenshot(webDriver);
	}
    
    
    /**
     * Метод перемещает курсор на категорию товаров
     * 
     * @param yandexMarketHeader объект класса веб-страницы
     * @param productsCategoryName название категории
     * 
     * @author Бирюкова Ольга
     */
    @Step("Поместить курсор на категорию каталога:\"{productsCategoryName}\"")
    public static void openProductsCategory(YandexMarketHeader yandexMarketHeader, String productsCategoryName) {
    	WebElement categoryName=yandexMarketHeader.getCategoryWithName(productsCategoryName);
    	Assertions.assertTrue(!(categoryName==null),"Категория \""+productsCategoryName+"\" не найдена");
    	new Actions(webDriver)
        .moveToElement(categoryName)
        .perform();
    	webDriverWait.until(_->yandexMarketHeader.getOpenedCategoryName().equals(productsCategoryName));
    	Screenshoter.makeScreenshot(webDriver);
    }
    
    /**
     * Метод открывает подкатегорию товаров
     * 
     * @param yandexMarketHeader объект класса веб-страницы
     * @param productsSubcategoryName название подкатегории
     * 
     * @author Бирюкова Ольга
     */
    @Step("Открыть подкатегорию товаров \"{productsSubcategotyName}\"")
    public static void openProductsSubcategory(YandexMarketHeader yandexMarketHeader, String productsSubcategoryName) {
    	WebElement productsSubcategory = yandexMarketHeader.getSubcategoryWithName(productsSubcategoryName);
    	Assertions.assertTrue(!productsSubcategory.equals(null),"Категория \""+productsSubcategoryName+"\" не найдена");
    	productsSubcategory.click();
    	Screenshoter.makeScreenshot(webDriver);
    }
    
    /**
     * Метод закрывает popup с предложением войти в учетную запись
     * 
     * @author Бирюкова Ольга
     */
    public static void closeLoginPopup() {
    	webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-zone-name=\"loginPopup\"]")));
        webDriver.navigate().refresh();
    }
    
    /**
     * Метод устанавливает фильтр на ценовой диапазон товаров
     * 
     * @param yandexMarketCatalogPage объект класса веб-страницы
     * @param lowPriceBoundary нижняя граница цены
     * @param upperPriceBoundary верхняя граница цены
     */
    @Step("Установить фильтр на ценовой диапазон от {lowPriceBoundary} до {upperPriceBoundary}")
	public static void setPriceRange(YandexMarketCatalogPage yandexMarketCatalogPage,int lowPriceBoundary, int upperPriceBoundary) {
		Assertions.assertTrue(yandexMarketCatalogPage.setPriceRange(lowPriceBoundary, upperPriceBoundary),"Ценовой фильтр не был применен");
		Screenshoter.makeScreenshot(webDriver);
		webDriver.navigate().refresh();
	}
    
    /**
     * Метод устанавливает фильтр на бренд товаров
     * 
     * @param yandexMarketCatalogPage объект класса веб-страницы
     * @param brands названия брендов
     * 
     * @author Бирюкова Ольга
     */
    @Step("Установить фильтр на бренды: {brands}")
    public static void setBrandFilter(YandexMarketCatalogPage yandexMarketCatalogPage, String [] brands) {
    	yandexMarketCatalogPage.setBrandFilter(brands);
    	webDriver.navigate().refresh();
    }
    
    /**
     *  Метод проверяет, что количество товаров на странице больше, чем требуемое
     *  
     * @param yandexMarketCatalogPage объект класса веб-страницы
     * @param requiredProdutsCount требуемое количество товаров
     * 
     * @author Бирюкова Ольга
     */
    @Step("Проверка количества карточек товаров на первой странице")
    public static void checkProductsCountOnPage(YandexMarketCatalogPage yandexMarketCatalogPage, int requiredProdutsCount) {
    	int count = yandexMarketCatalogPage.getProductsOnPageCount();
    	Assertions.assertTrue(count>requiredProdutsCount,"Количество товаров на первой странице менее "+requiredProdutsCount+". Количество товаров: "+count);
    	
    }
    /**
     * Метод проверяет соответствие товаров в каталоге фильтру по брендам
     * @param yandexMarketCatalogPage объект класса веб-страницы
     * @param brands названия брендов
     * 
     * @author Бирюкова Ольга
     */
    @Step("Проверка соответствия товаров со всех страниц на соответствие фильтру по бренду")
    public static void isProductsCorrectFilteredByBrand(YandexMarketCatalogPage yandexMarketCatalogPage, String[] brands) {
    	Assertions.assertTrue(yandexMarketCatalogPage.isProductsCorrectFilteredByBrand(brands), "Выборка не соответствует фильтру по брендам");
    }
    
    /**
     * Метод получает название первого продукта на странице
     * 
     * @param yandexMarketCatalogPage объект класса веб-страницы
     * @return название первого продукта
     * 
     * @author Бирюкова Ольга
     */
    @Step("Получение названия первого продукта каталога")
    public static String getNameOfFirstProduct(YandexMarketCatalogPage yandexMarketCatalogPage) {
    	String productName = yandexMarketCatalogPage.getNameOfFirstProduct();
    	return productName;
    }
    
    /**
     * Метод поиска товаров в поисковой строке по названию
     * 
     * @param yandexMarketCatalogPage объект класса веб-страницы
     * @param productName название искомого продукта
     * 
     * @author Бирюкова Ольга
     */
    @Step("Поиск товара с названием \"{productName}\"")
    public static void searchProductWithName(YandexMarketCatalogPage yandexMarketCatalogPage, String productName) {
    	yandexMarketCatalogPage.searchProductByName(productName);
    	Assertions.assertTrue(yandexMarketCatalogPage.isProductWithNamePresent(productName),"Продукт с именем " + productName + " не найден");
    	
    }
    
}
