package helpers;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/main/resources/tests.properties"
})
/**
 * Класс описывает зависимости
 */
public interface TestsProperties extends Config{

	@Config.Key("yandex.market.base-url")
	String yandexMarketUrl();
	
	@Config.Key("default.implicity.timeout")
	int defaultImplicityTimeout();
	
	@Config.Key("default.explicity.timeout")
	int defaultExplicityTimeout();
	
}
