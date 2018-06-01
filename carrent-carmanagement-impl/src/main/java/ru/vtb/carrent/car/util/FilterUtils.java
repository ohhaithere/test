/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2017 VTB Group. All rights reserved.
 */

package ru.vtb.carrent.car.util;

import com.google.common.base.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import ru.vtb.carrent.car.domain.model.KeyValuePair;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to prepare filters.
 *
 * @author Valiantsin_Charkashy
 */
public final class FilterUtils {

    private FilterUtils(){}

    /**
     * Returns a filter for params.
     *
     * @param filter filter string
     * @return the list with results
     * @throws IOException problems of converting
     */
    @SuppressWarnings("unchecked")
    public static List<KeyValuePair> getFilterList(String filter) throws IOException {
        List<KeyValuePair> filterList = new LinkedList<>();
        if (StringUtils.isNotBlank(filter)) {
            KeyValuePair[] filterParams = Base64.isBase64(filter)
                    ? JsonUtils.jsonToBean(new String(Base64.decodeBase64(filter), Charsets.UTF_8), KeyValuePair[].class)
                    : JsonUtils.jsonToBean(filter, KeyValuePair[].class);
            if (ArrayUtils.isNotEmpty(filterParams)) {
                filterList.addAll(Arrays.asList(filterParams));
            }
        }

        return filterList;
    }
}
