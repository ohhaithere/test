package ru.vtb.carrent.car.util;

import org.apache.commons.codec.binary.Base64;
import org.testng.annotations.Test;
import ru.vtb.carrent.car.domain.model.KeyValuePair;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Documentation template
 *
 * @author Tsimafei_Dynikau
 */
public class FilterUtilsTest {

    @Test
    public void testGetFilterList() throws IOException {
        KeyValuePair[] pairs = new KeyValuePair[]{
                new KeyValuePair("K1", "V1"),
                new KeyValuePair("K2", "V2"),
        };
        String testJson = JsonUtils.beanToJson(pairs);
        List<KeyValuePair> pairList = FilterUtils.getFilterList(testJson);

        assertEquals(pairList.size(), 2);
    }

    @Test
    public void testGetFilterListWithBase64() throws IOException {
        KeyValuePair[] pairs = new KeyValuePair[]{
                new KeyValuePair("K1", "V1"),
                new KeyValuePair("K2", "V2"),
        };
        String testJson = JsonUtils.beanToJson(pairs);
        List<KeyValuePair> pairList = FilterUtils.getFilterList(
                Base64.encodeBase64String(testJson.getBytes())
        );

        assertEquals(pairList.size(), 2);
    }
}