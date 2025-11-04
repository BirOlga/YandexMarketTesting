package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class YandexMarketHeader {
	
	protected WebDriver webDriver;
	
	protected WebDriverWait webDriverWait;
	
	protected String catalogButtonSelector= "//button//*[contains(text(), 'Каталог')]";
	
	protected WebElement catalogButton;
	
	protected String productsCategoriesSelector = "//ul[contains(@role,'tablist')]//li/a";
	
	protected List<WebElement> productsCategories;
	
	protected String productsCategoryTitleSelector="//div[contains(@role, 'heading')]/a";
	
	protected WebElement productsCategoryTitle;
	
	protected String productsSubcategoriesListSelector="//div[contains(@role, 'tabpanel')]//ul//li//a";
	
	protected List<WebElement> productsSubcategoriesList;
	
	
	protected String searchFieldSelector = "//form[contains(@id,'HeaderSearch')]//input[@id=\"header-search\"]";
	
	protected WebElement searchField;
	
	protected String searchSubmitButtonSelector = "//form[contains(@id,'HeaderSearch')]//button[@type=\"submit\"]";
	
	protected WebElement searchSubmitButton;
	
	
	
	
	public YandexMarketHeader(WebDriver webDriver, WebDriverWait webDriverWait) {
		this.webDriver=webDriver;
		this.webDriverWait=webDriverWait;
	}
	
	/**
	 * Метод ищет кнопку каталога на странице
	 * 
	 * @return веб-элемент кнопка каталога
	 * 
	 * @author Бирюкова Ольга
	 */
	public WebElement getCatalogButton(){
		catalogButton = webDriver.findElement(By.xpath(catalogButtonSelector));
		return catalogButton;
	}
	
	/**
	 * Метод ищет категорию с определенным названием в каталоге 
	 * 
	 * @param categoryName название категории
	 * @return найденный веб-элемент категории или null
	 * 
	 * @author Бирюкова Ольга
	 */
	public WebElement getCategoryWithName(String categoryName){
		productsCategories=webDriver.findElements(By.xpath(productsCategoriesSelector));
		for(WebElement c :productsCategories) {
			if(c.getText().equals(categoryName)) {
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Метод ищет подкатегорию с определенным названием в каталоге 
	 * 
	 * @param subcategoryName название подкатегории
	 * @return найденный веб-элемент подкатегории или null
	 * 
	 * @author Бирюкова Ольга
	 */
	public WebElement getSubcategoryWithName(String subcategoryName){
		productsSubcategoriesList=webDriver.findElements(By.xpath(productsSubcategoriesListSelector));
		for(WebElement s:productsSubcategoriesList) {
			if(s.getText().equals(subcategoryName)) {
				return s;
			}
		}
		return null;
	}
	
	/**
	 * Метод получения названия открытой категории
	 * 
	 * @return название открытой категории
	 * 
	 * @author Бирюкова Ольга
	 */
	public String getOpenedCategoryName() {
		productsCategoryTitle=webDriver.findElement(By.xpath(productsCategoryTitleSelector));
		return productsCategoryTitle.getText();
	}
	
	/**
	 * Метод поиска товара по названию
	 * 
	 * @param productName название товара
	 * 
	 * @author Бирюкова Ольга
	 */
	public void searchProductByName(String productName) {
		searchField = webDriver.findElement(By.xpath(searchFieldSelector));
		searchField.click();
		searchField.sendKeys(productName);
		searchSubmitButton = webDriver.findElement(By.xpath(searchSubmitButtonSelector));
		searchSubmitButton.click();	
	}

}
