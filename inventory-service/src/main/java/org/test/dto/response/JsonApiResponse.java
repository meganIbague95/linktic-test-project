package org.test.dto.response;

import lombok.Data;

@Data
public class JsonApiResponse<T> {

    private JsonApiData<T> data;
}
