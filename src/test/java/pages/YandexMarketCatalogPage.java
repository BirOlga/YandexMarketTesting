package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import helpers.Screenshoter;

public class YandexMarketCatalogPage extends YandexMarketHeader{

	
	public YandexMarketCatalogPage(WebDriver webDriver, WebDriverWait webDriverWait) {
		super(webDriver,webDriverWait);
	}
	
	protected String priceFilterSectionSelector="//div[contains(@id,'searchFilters')]//div[contains(@data-filter-id, 'price')]";
	
	protected String lowPriceBoundaryInputFieldSelector=priceFilterSectionSelector+"//input[contains(@id,'min')]";
	
	protected WebElement lowPriceBoundaryInputField;
	
	protected String upperPriceBoundaryInputFieldSelector=priceFilterSectionSelector+"//input[contains(@id,'max')]";
	
	protected WebElement upperPriceBoundaryInputField;
	
	protected String brandFilterSectionSelector="//legend//*[text()=\"Бренд\"]/../../..";
	
//	protected WebElement brandFilterSection;
	
	protected String moreBrandsButtonSelector=brandFilterSectionSelector+"//div[contains(@data-zone-name,'showMoreFilters')]//button";
	
	protected WebElement moreBrandsButton;
	
	protected String brandSearchInputSelector = brandFilterSectionSelector+"//input[contains(@placeholder,'Найти')]";
	
	protected WebElement brandSearchInput;
	
	protected String productsCardsSelector = "//div[contains(@data-auto-themename,'listDetailed')]//a/span";
	
	protected List<WebElement> productsCards;
	
	protected String catalogPagesSelector ="//div[contains(@data-auto,'pagination-page')]/a";
	
	protected List<WebElement> catalogPages;
	
	protected String appliedFiltersSelector="//div[@data-zone-name=\"quickFilters\"]//div[@data-zone-name=\"filter\"]/div/div/span";
	
	
	
	/** 
	 * Метод устанавливает фильтр товаров по цене
	 * 
	 * @param lowPriceBoundary нижняя граница ценового диапазона
	 * @param upperPriceBoundary верхняя граница ценового диапазона
	 * @return true если фильтр отобразился на панели быстрых фильтров (рядом с сортировкой), false если фильтр не отобразился
	 * 
	 * @author Бирюкова Ольга
	 */
	public boolean setPriceRange(int lowPriceBoundary, int upperPriceBoundary) {
		lowPriceBoundaryInputField=webDriver.findElement(By.xpath(lowPriceBoundaryInputFieldSelector));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(lowPriceBoundaryInputField));
		upperPriceBoundaryInputField=webDriver.findElement(By.xpath(upperPriceBoundaryInputFieldSelector));
		lowPriceBoundaryInputField.sendKeys(String.valueOf(lowPriceBoundary));
		upperPriceBoundaryInputField.sendKeys(String.valueOf(upperPriceBoundary));
		webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(appliedFiltersSelector)));
		List<WebElement> appliedFilters=webDriver.findElements(By.xpath(appliedFiltersSelector));
		return appliedFilters.stream().anyMatch(c -> c.getText().replace("\u202F", "").contains(lowPriceBoundary+"–"+upperPriceBoundary));
	}
	
	
	/**
	 * Метод устанавливает фильтр товаров по брендам
	 * 
	 * @param brands массив строк, содержащий названия брендов
	 * 
	 * @author Бирюкова Ольга
	 */
	public void setBrandFilter(String[] brands) {
		moreBrandsButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(moreBrandsButtonSelector)));
		moreBrandsButton.click();
		WebElement brandSearchInput=webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(brandSearchInputSelector)));
		for(String s :brands) {
			webDriverWait.until(ExpectedConditions.elementToBeClickable(brandSearchInput));
			brandSearchInput.click();
			brandSearchInput.sendKeys(s);
			WebElement brand=webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(brandFilterSectionSelector+"//span[contains(text(),'"+s+"')]")));
			brand.click();
			Screenshoter.makeScreenshot(webDriver);
			brandSearchInput=webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(brandSearchInputSelector)));
			brandSearchInput.clear();
		}
		webDriverWait.until(_ -> webDriver.findElements(By.xpath(appliedFiltersSelector)).stream().anyMatch(d -> d.getText().contains(brands[0])));
	}
	
	/**
	 * Метод получает количество товаров на текущей странице каталога
	 * 
	 * @return количество товаров на текущей странице
	 * 
	 * @author Бирюкова Ольга
	 */
	public int getProductsOnPageCount() {
		productsCards=webDriver.findElements(By.xpath(productsCardsSelector));
		return productsCards.size();
	}
	
	/**
	 * Метод проверяет соответствие товаров фильтру по брендам
	 * 
	 * @param brands массив строк, содержащий список брендов
	 * @return true - если все элементы соответствуют фильтру, false - если хотя бы 1 товар не соответствует фильтру
	 * 
	 * @author Бирюкова Ольга
	 */
	public boolean isProductsCorrectFilteredByBrand(String[] brands) {
		boolean isProductsCorrectFilteredByBrand=true;
		productsCards=webDriver.findElements(By.xpath(productsCardsSelector));
		catalogPages = webDriver.findElements(By.xpath(catalogPagesSelector));
		int lastPage=Integer.valueOf(catalogPages.getLast().getText());
		isProductsCorrectFilteredByBrand&=checkProductsBrands(brands,productsCards);
		for(int i=1;i<lastPage;) {
			if(!isProductsCorrectFilteredByBrand)
				return isProductsCorrectFilteredByBrand;
			i++;
			WebElement nextPage=webDriver.findElement(By.xpath("//div[@data-zone-name=\"next\"]"));
			nextPage.click();
			productsCards=(webDriver.findElements(By.xpath(productsCardsSelector)));
			isProductsCorrectFilteredByBrand&=checkProductsBrands(brands,productsCards);
			catalogPages = webDriver.findElements(By.xpath(catalogPagesSelector));
			lastPage=Integer.valueOf(catalogPages.getLast().getText());
		}
		return isProductsCorrectFilteredByBrand;
	}
	
	/**
	 * Метод проверки товаров на текущей странице на соответствие фильтру по брендам
	 * 
	 * @param brands массив строк, содержащий список брендов
	 * @param productsCards список веб-элементов с заголовком товаров
	 * @return true если все заголовки товаров содержат бренд из списка, false - если хотя бы 1 заголовок товаров не содержит бренда из списка
	 */
	protected boolean checkProductsBrands(String[] brands, List<WebElement> productsCards) {
		boolean result=true;
		for(WebElement p : productsCards) {
			boolean tmp=false;
			for(String b: brands) {
			if(p.getText().toLowerCase().contains(b.toLowerCase())) {
				tmp=true;
				break;
			}
							
			}
			result&=tmp;
			if(!result) {
				System.out.println("Бренд следующего товара не соответствует фильтрам: " + p.getText());
				return result;
			}
		}
		return result;
	}
	
	/**
	 * Метод проверяет присутствует ли товар с конкретным названием на странице
	 * 
	 * @param productName название товара
	 * @return true - товар с названием присутствует на странице, false - товара с название на странице нет
	 * 
	 * @author Бирюкова Ольга
	 */
	public boolean isProductWithNamePresent(String productName) {
		productsCards=webDriver.findElements(By.xpath(productsCardsSelector));
		return productsCards.stream().anyMatch(p -> p.getText().equals(productName));
	}
	
	/**
	 * Метод ищет заголовок первого товара на текущей странице каталога
	 * 
	 * @return заголовок первого товара на текущей странице
	 * 
	 * @author Бирюкова Ольга
	 */
	public String getNameOfFirstProduct() {
		WebElement firstProduct = webDriver.findElement(By.xpath(productsCardsSelector));
		return firstProduct.getText();
	}
		
}
