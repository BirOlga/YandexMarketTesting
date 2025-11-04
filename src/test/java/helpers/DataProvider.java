package helpers;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;

public class DataProvider {
	/**
	 * Метод с тестовыми данными для YandexMarketTests.checkMarket()
	 * 
	 * @return набор тестовых данных
	 *
	 *@author Бирюкова Ольга
	 */
	public static Stream<Arguments> providerCheckMarket(){
		String yandexMarketTitle="Яндекс Маркет — покупки с быстрой доставкой";
		String[] laptopsBrands= {"Acer","Ирбис"};
		String[] smartphonesBrands= {"Apple"};
		return Stream.of(
				Arguments.of(yandexMarketTitle, "Электроника", "Мобильные телефоны", smartphonesBrands,5, 10000, 20000),
				Arguments.of(yandexMarketTitle, "Электроника", "Ноутбуки",laptopsBrands,6, 10000, 20000)
				);
	}

}
