package com.student.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lxy
 * @date 2024/6/2 17:49
 */
@Data
public class WordCloudVO implements Serializable {

    private String value;

    private String name;

}
