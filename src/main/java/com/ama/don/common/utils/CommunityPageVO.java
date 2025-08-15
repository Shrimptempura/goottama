package com.ama.don.common.utils;

/**
 * 동적 Pagination을 위한 코드를 제공한다.
 * <p>
 * - pageCalculate을 통해 페이지를 계산한다. <br>
 * - pageCalculate는 전체 데이터 개수를 받는다. <br>
 * - displayRowCount는 화면에 출력 될 열의 수이다. <br>
 * - pageGrpCnt는 페이지 번호의 수이다.
 * </p>
 */
public class CommunityPageVO {
    private Integer displayRowCount=10;  //출력할 데이터 갯수
    private Integer rowStart;  			//시작행 번호
    private Integer rowEnd;  			//종료행 번호
    private Integer totPage; 			 //전체페이지 수
    private Integer totRow=0; 			 //전체 데이터 수
    private Integer page; 			 	//현재페이지
    private Integer pageStart; 			 //시작페이지
    private Integer pageEnd; 			 //종료페이지
    private Integer pageGrpCnt=10; 			 //페이지그룹의 페이지갯수

    /**
     * 전체데이터 개수를 이용해서 페이지를 동적으로 계산
     * @param total 전체 데이터 개수. 각 DAO의 count 메서드로 계산한다.
     */
    public void pageCalculate(Integer total) {
        getPage();
        totRow = total;

        // 전체 페이지 수 계산
        totPage = (int)(total / displayRowCount);
        if (total % displayRowCount > 0) {
            totPage++;
        }

        // 현재 페이지에 따른 시작/끝 row 번호
        rowStart = ((page - 1) * displayRowCount);
        rowEnd = rowStart + displayRowCount - 1;
        setRowStart(rowStart);
        setRowEnd(rowEnd);
        // 동적 페이지네이션 시작
        int pageCount = pageGrpCnt; // 보여줄 최대 페이지 번호 개수 (예: 10)
        int half = pageCount / 2;

        // 페이지가 충분히 앞쪽이면 고정 시작
        if (page <= half) {
            pageStart = 1;
            pageEnd = Math.min(totPage, pageCount);
        }
        // 페이지가 뒤쪽 끝 근처면 끝에 고정
        else if (page >= (totPage - half)) {
            pageEnd = totPage;
            pageStart = Math.max(1, totPage - pageCount + 1);
        }
        // 중간 구간이면 현재 페이지 중심으로 계산
        else {
            pageStart = page - half;
            pageEnd = pageStart + pageCount - 1;
        }
    }

    /**
     * 현제 페이지 번호를 반환한다.
     * @return 현재 페이지 번호
     */
    public Integer getPage() {
        if (page==null || page==0) {
            page=1;
        }
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getDisplayRowCount() {
        return displayRowCount;
    }

    public void setDisplayRowCount(Integer displayRowCount) {
        this.displayRowCount = displayRowCount;
    }

    public Integer getRowStart() {
        // 이 메서드가 호출될 때 rowStart가 null이라면 문제가 있는 것.
        // pageCalculate가 실행되지 않았거나, 실행되었어도 뭔가 잘못된 것.
        if (rowStart == null) {
            System.err.println("CRITICAL ERROR: getRowStart() called but rowStart is null. This indicates pageCalculate was not run or failed.");
            // 임시 방편으로 재계산하거나 기본값 반환 (근본적인 해결책은 아님)
            return (getPage() - 1) * displayRowCount; // page가 1 이상이라고 가정
        }
        return rowStart;
    }

    public void setRowStart(Integer rowStart) { this.rowStart = rowStart; }

    public Integer getRowEnd() {
        return rowEnd;
    }

    public void setRowEnd(Integer rowEnd) {
        this.rowEnd = rowEnd;
    }

    public Integer getTotPage() {
        return totPage;
    }

    public void setTotPage(Integer totPage) {
        this.totPage = totPage;
    }

    public Integer getTotRow() {
        return totRow;
    }

    public void setTotRow(Integer totRow) {
        this.totRow = totRow;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(Integer pageEnd) {
        this.pageEnd = pageEnd;
    }

}
