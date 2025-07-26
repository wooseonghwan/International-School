package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BoContentDTO extends CommonDTO implements Serializable {
    // t_category
    private String categoryId;
    private String categoryName;
    private String subCategoryName;
    private String useYn;
    private String useYnNm;
    private String fileId;
    private String createId;
    private String createDt;
    private String updateId;
    private String updateDt;
    private String delYn;

    // t_ad_product
    private String productId;
    private String amount;
    private String workDt;
    private String title;
    private String content;
    private String amountContent;
    private String etcContent;
    private String mainContent;
    private String media;
    private String mediaNm;
    private String mediaDetail;

    // tb_package
    private String packageId;
    private String packageName;
    private String mediaDetail2;
    private String price;

    // tb_ad_product_sub_content
    private String productSubContent;
    private List<String> productSubContents;

    @Builder.Default
    private String searchText = "";
    private MultipartFile file;
    private MultipartFile[] productFiles;

    private String errorCode;
    private String fileUrl;
    private String originFileId;
}