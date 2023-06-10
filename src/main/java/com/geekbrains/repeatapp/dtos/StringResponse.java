package com.geekbrains.repeatapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Класс создан для того, чтобы иметь возможность ответ заворачивать в JSON
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StringResponse {
    private String value;
}
