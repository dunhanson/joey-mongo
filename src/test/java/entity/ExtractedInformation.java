package entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

/**
 * @author dunhanson
 * @version 1.0
 * @date 2019/5/17
 * @description
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ExtractedInformation {
    /**
     * 业主单位
     */
    private String tenderee;
    /**
     * 代理单位
     */
    private String agency;
    /**
     * 中标单位
     */
    private String winTenderer;
}
