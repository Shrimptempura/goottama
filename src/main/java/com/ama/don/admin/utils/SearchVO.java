package com.ama.don.admin.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchVO extends  PageVO  {
    private String bgno;
    private String searchKeyword = "";
    private String searchType = "";
    private String[] searchTypeArr;

    public String[] getSearchTypeArr() {
        return searchType.split(",");
    }
}