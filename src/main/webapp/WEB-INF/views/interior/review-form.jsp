<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 리뷰 폼</title>
    <c:url var="cssUrl" value="/css/interior/interior-review-form.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<div class="page-wrap">
    <div class="title">업체 리뷰 작성</div>

    <div class="card">
        <form action="/interior/myhome/${form.companyId}/review-form" method="post" enctype="multipart/form-data">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="hidden" name="companyId" value="${form.companyId}">

            <!-- 별점 -->
            <div class="rate-grid">
                <div class="rate-field">
                    <label class="rate-label">소통 점수</label>
                    <div class="stars" tabindex="0" role="slider"
                         aria-valuemin="1" aria-valuemax="5"
                         data-name="communicationRate"
                         data-initial="${form.communicationRate}">
                        <span class="star" data-value="1"></span>
                        <span class="star" data-value="2"></span>
                        <span class="star" data-value="3"></span>
                        <span class="star" data-value="4"></span>
                        <span class="star" data-value="5"></span>
                        <input type="hidden" name="communicationRate" value="${form.communicationRate}"/>
                        <span class="rate-note"></span>
                    </div>
                </div>

                <div class="rate-field">
                    <label class="rate-label">가격 점수</label>
                    <div class="stars" tabindex="0" role="slider"
                         aria-valuemin="1" aria-valuemax="5"
                         data-name="priceRate"
                         data-initial="${form.priceRate}">
                        <span class="star" data-value="1"></span>
                        <span class="star" data-value="2"></span>
                        <span class="star" data-value="3"></span>
                        <span class="star" data-value="4"></span>
                        <span class="star" data-value="5"></span>
                        <input type="hidden" name="priceRate" value="${form.priceRate}"/>
                        <span class="rate-note"></span>
                    </div>
                </div>

                <div class="rate-field">
                    <label class="rate-label">결과 점수</label>
                    <div class="stars" tabindex="0" role="slider"
                         aria-valuemin="1" aria-valuemax="5"
                         data-name="resultRate"
                         data-initial="${form.resultRate}">
                        <span class="star" data-value="1"></span>
                        <span class="star" data-value="2"></span>
                        <span class="star" data-value="3"></span>
                        <span class="star" data-value="4"></span>
                        <span class="star" data-value="5"></span>
                        <input type="hidden" name="resultRate" value="${form.resultRate}"/>
                        <span class="rate-note"></span>
                    </div>
                </div>

                <div class="rate-field">
                    <label class="rate-label">일정 점수</label>
                    <div class="stars" tabindex="0" role="slider"
                         aria-valuemin="1" aria-valuemax="5"
                         data-name="scheduleRate"
                         data-initial="${form.scheduleRate}">
                        <span class="star" data-value="1"></span>
                        <span class="star" data-value="2"></span>
                        <span class="star" data-value="3"></span>
                        <span class="star" data-value="4"></span>
                        <span class="star" data-value="5"></span>
                        <input type="hidden" name="scheduleRate" value="${form.scheduleRate}"/>
                        <span class="rate-note"></span>
                    </div>
                </div>
            </div>

            <div class="divider"></div>

            <!-- 옵션 느낌으로 변경된 필드들 -->
            <div class="form-grid">
                <!-- 건물유형: 단일 선택 -->
                <div class="field">
                    <label for="structureType">건물유형 <span class="note"></span></label>
                    <select id="structureType" name="structureType" required>
                        <option value="" disabled ${empty form.structureType ? 'selected' : ''}>선택하세요</option>
                        <option value="아파트" <c:if test="${form.structureType eq '아파트'}">selected</c:if>>아파트</option>
                        <option value="주택" <c:if test="${form.structureType eq '주택'}">selected</c:if>>주택</option>
                        <option value="오피스텔" <c:if test="${form.structureType eq '오피스텔'}">selected</c:if>>오피스텔</option>
                        <option value="원룸 / 투룸" <c:if test="${form.structureType eq '원룸 / 투룸'}">selected</c:if>>원룸 / 투룸</option>
                        <option value="빌라" <c:if test="${form.structureType eq '빌라'}">selected</c:if>>빌라</option>
                        <option value="상가 / 사무실" <c:if test="${form.structureType eq '상가 / 사무실'}">selected</c:if>>상가 / 사무실</option>
                    </select>
                </div>

                <!-- 평수 범위 -->
                <div class="field">
                    <label for="areaPyeong">평수 <span class="note"></span></label>
                    <select id="areaPyeong" name="areaPyeong" required>
                        <option value="" disabled ${empty form.areaPyeong ? 'selected' : ''}>선택하세요</option>
                        <option value="10평 미만" <c:if test="${form.areaPyeong eq '10평 미만'}">selected</c:if>>10평 미만</option>
                        <option value="10~20평" <c:if test="${form.areaPyeong eq '10~20평'}">selected</c:if>>10~20평</option>
                        <option value="20~30평" <c:if test="${form.areaPyeong eq '20~30평'}">selected</c:if>>20~30평</option>
                        <option value="30~40평" <c:if test="${form.areaPyeong eq '30~40평'}">selected</c:if>>30~40평</option>
                        <option value="40평 이상" <c:if test="${form.areaPyeong eq '40평 이상'}">selected</c:if>>40평 이상</option>
                    </select>
                </div>

                <!-- 시공 -->
                <div class="field">
                    <label for="constructionField">시공분야 <span class="note"></span></label>
                    <select id="constructionField" name="constructionField" required>
                        <option value="" disabled ${empty form.constructionField ? 'selected' : ''}>선택하세요</option>
                        <option value="주방 리모델링" <c:if test="${form.constructionField eq '주방 리모델링'}">selected</c:if>>주방 리모델링</option>
                        <option value="욕실 리모델링" <c:if test="${form.constructionField eq '욕실 리모델링'}">selected</c:if>>욕실 리모델링</option>
                        <option value="거실 인테리어" <c:if test="${form.constructionField eq '거실 인테리어'}">selected</c:if>>거실 인테리어</option>
                        <option value="발코니 확장" <c:if test="${form.constructionField eq '발코니 확장'}">selected</c:if>>발코니 확장</option>
                        <option value="도배 / 장판" <c:if test="${form.constructionField eq '도배 / 장판'}">selected</c:if>>도배 / 장판</option>
                        <option value="방 인테리어" <c:if test="${form.constructionField eq '방 인테리어'}">selected</c:if>>방 인테리어</option>
                    </select>
                </div>

                <div class="divider"></div>

                <!-- 내용, 사진 -->
                <div class="field">
                    <label>내용 <span class="note"></span></label>
                    <textarea name="reviewContent" required>${form.reviewContent}</textarea>
                </div>

                <div class="field">
                    <label>사진 첨부 <span class="note">(여러 장 가능, 필수)</span></label>
                    <input type="file" name="files" accept="image/*" multiple required>
                </div>
            </div>

            <div class="actions">
                <button type="submit" class="btn">작성</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

<script>
    /* 별점 1~5: 클릭만 반영 */
    (function(){
        function paint(container, value){
            const stars = container.querySelectorAll('.star');
            stars.forEach(st => st.classList.toggle('filled', Number(st.dataset.value) <= value));
            const note = container.querySelector('.rate-note');
            if (note) note.textContent = value ? (value + '/5') : '';
            container.setAttribute('aria-valuenow', value || 0);
        }
        function commit(container, value){
            value = Math.max(1, Math.min(5, value||0));
            const hidden = container.querySelector('input[type="hidden"]');
            hidden.value = value;
            paint(container, value);
        }
        document.querySelectorAll('.stars').forEach(container=>{
            const initial = Number(container.dataset.initial || 0);
            if (initial) commit(container, initial);

            container.addEventListener('click', e=>{
                const star = e.target.closest('.star');
                if (!star) return;
                commit(container, Number(star.dataset.value));
            });

            // 호버 미리보기(클릭해야 확정)
            container.addEventListener('mousemove', e=>{
                const star = e.target.closest('.star');
                if (!star) return;
                paint(container, Number(star.dataset.value));
            });
            container.addEventListener('mouseleave', ()=>{
                const hidden = container.querySelector('input[type="hidden"]');
                paint(container, Number(hidden.value || 0));
            });
        });
    })();
</script>

</body>
</html>
