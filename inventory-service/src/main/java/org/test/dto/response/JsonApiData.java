package org.test.dto.response;

import lombok.Data;

@Data
public class JsonApiData<T> {

    private Long id;
    private String type;
    private T attributes;
}
