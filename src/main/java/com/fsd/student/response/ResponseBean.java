package com.fsd.student.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean<T> {
    private String status;     // "success", "error"
    private String message;    // human-readable message
    private T data;            // actual payload (can be object, list, etc.)

    public static <T> ResponseBean<T> success(T data, String message) {
        return new ResponseBean<>("success", message, data);
    }

    public static <T> ResponseBean<T> error(String message) {
        return new ResponseBean<>("error", message, null);
    }
}
