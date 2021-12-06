package com.fizzy.fileservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author FizzyElf
 * Date 2021/11/29 16:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Boolean success = true;

    private String errorCode;

    private String errorMsg;
}
