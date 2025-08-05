package com.ama.don.common.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommunitySearchVO extends  CommunityPageVO  {
    private String bgno;
    private String searchKeyword = "";
    private String searchType = "";
    private String[] searchTypeArr;

    public String[] getSearchTypeArr() {
        return searchType.split(",");
    }
}