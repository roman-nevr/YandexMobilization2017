package org.berendeev.roma.yandexmobilization2017;

import org.berendeev.roma.yandexmobilization2017.domain.entity.LanguageMap;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Example locale unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("ru", "Russian");
        map.put("en", "English");

        LanguageMap languageMap = LanguageMap.builder()
                .map(map)
                .locale(Locale.getDefault())
                .build();

        Set<Map.Entry<String, String>> entries = languageMap.map().entrySet();

        ArrayList<Map.Entry<String, String>> list = new ArrayList<>(entries);

        System.out.println(list);
    }
}