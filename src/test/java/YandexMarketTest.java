import static helpers.Properties.testsProperties;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import io.qameta.allure.Description;
import pages.YandexMarketCatalogPage;
import pages.YandexMarketHeader;
import steps.YandexMarketSteps;

public class YandexMarketTest extends BaseTest{
	/**
	 * Тестовый метод, выполняющий следующие шаги:
	 * 1. Переход на сайт Яндекс Маркета
	 * 2. Открытие каталога
	 * 3. Выбор категории и переход в подкатегорию товаров
	 * 4. Установка фильтра на товары по бренду и ценовому диапазону
	 * 5. Проверка соответствия товаров фильтру по брендам
	 * 6. Запомнить название первого товара и найти его в поиске
	 * 
	 * @param siteTitle заголовок сайта
	 * @param productsCategory категория товаров
	 * @param productsSubcategory подкатегория товаров
	 * @param brands бренды товаров
	 * @param expectedProductsOnPageCount нижняя граница количества товаров на странице
	 * @param lowPriceBoundary нижняя граница ценового диапазона
	 * @param upperPriceBoundary верняя граница ценового диапазона
	 * 
	 * @author Бирюкова Ольга
	 */
	@DisplayName("Тест-кейс 1.4")
    @Description("Тест тестирует навигацию, каталог и фильтрацию товаров на Яндекс Маркете")
	@ParameterizedTest
	@MethodSource("helpers.DataProvider#providerCheckMarket")
	public void checkMarket(String siteTitle, String productsCategory, String productsSubcategory, String[] brands,int expectedProductsOnPageCount, int lowPriceBoundary, int upperPriceBoundary){
		YandexMarketSteps.openSite(testsProperties.yandexMarketUrl(), siteTitle , webDriver, webDriverWait);
		YandexMarketHeader yandexMarketHeader=new YandexMarketHeader(webDriver,webDriverWait);
		YandexMarketSteps.openYandexMarketCatalog(yandexMarketHeader);
		YandexMarketSteps.openProductsCategory(yandexMarketHeader, productsCategory);
		YandexMarketSteps.openProductsSubcategory(yandexMarketHeader, productsSubcategory);
		
		YandexMarketCatalogPage yandexMarketCatalogPage=new YandexMarketCatalogPage(webDriver,webDriverWait);
		YandexMarketSteps.setPriceRange(yandexMarketCatalogPage, lowPriceBoundary, upperPriceBoundary);
		YandexMarketSteps.setBrandFilter(yandexMarketCatalogPage, brands);
		String firstProductOnPage=YandexMarketSteps.getNameOfFirstProduct(yandexMarketCatalogPage);
		YandexMarketSteps.checkProductsCountOnPage(yandexMarketCatalogPage,expectedProductsOnPageCount);
		YandexMarketSteps.isProductsCorrectFilteredByBrand(yandexMarketCatalogPage,brands);
		YandexMarketSteps.searchProductWithName(yandexMarketCatalogPage, firstProductOnPage);
	}

}
