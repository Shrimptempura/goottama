<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 게시글 상세보기</title>
    <c:url var="cssUrl" value="/css/interior/interior-post-detail.css"/>
    <link rel="stylesheet" href="${cssUrl}">
</head>
<body>


<jsp:include page="/WEB-INF/views/common/header_navigation_bar.jsp"/>

<!-- 썸네일 선택: thumbnail=true 우선, 없으면 첫 번째 -->
<c:set var="thumbUrl" value=""/>
<c:if test="${not empty detail.images}">
    <c:forEach var="img" items="${detail.images}">
        <c:if test="${empty thumbUrl and img.thumbnail}">
            <c:set var="thumbUrl" value="${img.url}"/>
        </c:if>
    </c:forEach>

    <c:if test="${empty thumbUrl}">
        <c:set var="thumbUrl" value="${detail.images[0].url}"/>
    </c:if>
</c:if>

<!-- 썸네일 1장 풀사이즈 -->
<c:if test="${not empty thumbUrl}">
    <img class="hero-img" src="${thumbUrl}" alt="image"/>
    <div class="divider"></div>
</c:if>

<div class="page-wrap">


    <!-- 제목 + (수정일, 작성일, 조회수) -->
    <div class="head-row">
        <div class="title">${detail.post.companyPostTitle}</div>
        <div class="right-meta">
            <span>작성일: ${createdFmt}</span>
            <c:if test="${isUpdated}"><span class="dot">•</span><span>수정일: ${updatedFmt}</span></c:if>
            <span class="dot">•</span><span>조회수: ${detail.post.companyPostCount}</span>
        </div>
    </div>
    <div class="subtitle">${detail.company.companyName}</div>


    <!-- 공간/스타일/평수/시공상세 (한 줄) -->
    <div class="chip-row">
        <c:if test="${not empty detail.post.spaceType}">
            <span class="chip">${detail.post.spaceType}</span>
        </c:if>
        <c:if test="${not empty detail.post.style}">
            <span class="chip">${detail.post.style}</span>
        </c:if>
        <c:if test="${not empty detail.post.areaPyeong}">
            <span class="chip">${detail.post.areaPyeong}</span>
        </c:if>
        <c:if test="${not empty detail.post.constructionDetail}">
            <span class="chip">${detail.post.constructionDetail}</span>
        </c:if>
    </div>

    <!-- 업체 소개 -->
    <c:if test="${not empty detail.company.companyIntro}">
        <div class="divider"></div>
        <div class="subtitle" style="color:#444;">${detail.company.companyIntro}</div>
    </c:if>

    <div class="divider"></div>

    <!-- 썸네일 제외 이미지 -->
    <c:forEach var="img" items="${detail.images}">
        <c:if test="${img.url ne thumbUrl}">
            <img class="hero-img" src="${img.url}" alt="image"/>
        </c:if>
    </c:forEach>

    <div class="divider"></div>

    <!-- 본문 내용) -->
    <div class="post-content">
        ${detail.post.companyPostContent}
    </div>

    <!-- 모든 사진 히어로 처리 -->
    <c:if test="${not empty detail.images}">
        <div class="divider"></div>
        <div class="gallery-small">
            <c:forEach var="img" items="${detail.images}">
                <img src="${img.url}" alt="image"/>
            </c:forEach>
        </div>
    </c:if>

    <div class="divider"></div>

    <!-- 뒤로가기 / 수정 / 삭제 -->
    <c:set var="companyId" value="${detail.post.companyId}"/>
    <c:url var="backPostUrl" value="/interior/myhome/${companyId}">
        <c:param name="type" value="posts"/>
    </c:url>

    <div class="action-row">
        <div>
            <a class="btn btn-outline" href="${backPostUrl}">← 목록으로</a>
        </div>
        <div class="action-right">
            <c:if test="${isOwner}">
                <a class="btn" href="<c:url value='/interior/posts/${detail.post.companyPostId}/edit'/>">수정하기</a>
                <form action="<c:url value='/interior/posts/${detail.post.companyPostId}/delete'/>"
                      method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="companyId" value="${detail.post.companyId}"/>
                    <button type="submit" class="btn btn-danger">삭제하기</button>
                </form>
            </c:if>
        </div>
    </div>

    <div class="divider"></div>


    <hr id="comments"/>

    <!-- add플래시 추후 수정 필요 -->
    <c:if test="${not empty msg}">
        <div style="color:green">${msg}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div style="color:red">${error}</div>
    </c:if>


    <!-- 루트 댓글 작성 -->
    <div class="comment-section">

        <h3 class="comment-title">댓글</h3>

        <!-- add 플래시 -->
        <c:if test="${not empty msg}">
            <div class="alert">${msg}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <!-- 루트 댓글 작성 -->
        <form class="comment-box"
              action="${pageContext.request.contextPath}/interior/posts/${detail.post.companyPostId}/comments"
              method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <textarea name="content" rows="3" required placeholder="댓글 내용을 입력하세요"></textarea>
            <div class="comment-actions">
                <button type="submit" class="btn-sm">등록</button>
            </div>
        </form>

        <!-- 댓글/대댓글 목록 -->
        <c:forEach var="cmt" items="${comments}">
            <c:if test="${cmt.parentCommentId == null}">
                <div class="comment" id="cmt-${cmt.commentId}">
                    <div class="meta">#${cmt.commentId} · ${cmt.createdAt}</div>

                    <div class="comment-body-${cmt.commentId}" id="cmt-body-${cmt.commentId}">
                        <c:choose>
                            <c:when test="${cmt.deleted eq true}">
                                <em>삭제된 댓글입니다.</em>
                            </c:when>
                            <c:otherwise>
                                ${fn:escapeXml(cmt.commentContent)}
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="actions">
                        <button type="button" class="action-link" onclick="openReply(${cmt.commentId})">답글</button>
                        <c:if test="${cmt.deleted ne true}">
                            <button type="button" class="action-link" onclick="openEdit(${cmt.commentId})">수정</button>
                            <form action="${pageContext.request.contextPath}/interior/comments/${cmt.commentId}/delete"
                                  method="post" onsubmit="return confirm('이 댓글을 삭제할까요?');">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                <input type="hidden" name="companyPostId" value="${detail.post.companyPostId}">
                                <button type="submit" class="action-link">삭제</button>
                            </form>
                        </c:if>
                    </div>

                    <!-- 대댓글 목록 -->
                    <div id="children-${cmt.commentId}">
                        <c:forEach var="rep" items="${comments}">
                            <c:if test="${rep.parentCommentId == cmt.commentId}">
                                <div class="reply" id="cmt-${rep.commentId}">
                                    <div class="meta">↳ #${rep.commentId} · ${rep.createdAt}</div>

                                    <div class="reply-body" id="cmt-body-${rep.commentId}">
                                        <c:choose>
                                            <c:when test="${rep.deleted eq true}">
                                                <em>삭제된 댓글입니다.</em>
                                            </c:when>
                                            <c:otherwise>
                                                ${fn:escapeXml(rep.commentContent)}
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="actions">
                                        <button type="button" class="action-link" onclick="openReply(${rep.commentId})">답글</button>
                                        <c:if test="${rep.deleted ne true}">
                                            <button type="button" class="action-link" onclick="openEdit(${rep.commentId})">수정</button>
                                            <form action="${pageContext.request.contextPath}/interior/comments/${rep.commentId}/delete"
                                                  method="post" onsubmit="return confirm('이 댓글을 삭제할까요?');">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                                <input type="hidden" name="companyPostId" value="${detail.post.companyPostId}">
                                                <button type="submit" class="action-link">삭제</button>
                                            </form>
                                        </c:if>
                                    </div>

                                    <div id="children-${rep.commentId}"></div>

                                </div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
        </c:forEach>

        <!-- 답글 폼 (동적으로 위치 바뀜) -->
        <form id="replyForm" class="comment-box inline-form"
              action="${pageContext.request.contextPath}/interior/posts/${detail.post.companyPostId}/comments"
              method="post" style="display:none;">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="hidden" name="parentCommentId" id="replyParentId">
            <textarea name="content" id="replyContent" rows="3" required placeholder="답글 내용을 입력하세요"></textarea>
            <div class="comment-actions">
                <button type="submit" class="btn-sm">답글 등록</button>
                <button type="button" class="btn-sm btn-ghost" onclick="hideReply()">취소</button>
            </div>
        </form>

        <!-- 수정 폼 (동적으로 위치 바뀜) -->
        <form id="editForm" class="comment-box inline-form" action="" method="post" style="display:none;">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <input type="hidden" name="companyPostId" value="${detail.post.companyPostId}">
            <textarea name="content" id="editContent" rows="3" required></textarea>
            <div class="comment-actions">
                <button type="submit" class="btn-sm">수정 완료</button>
                <button type="button" class="btn-sm btn-ghost" onclick="hideEdit()">취소</button>
            </div>
        </form>

    </div>

    <script>
        const CTX = '${pageContext.request.contextPath}';
        const replyForm = document.getElementById('replyForm');
        const editForm = document.getElementById('editForm');
        const replyParentId = document.getElementById('replyParentId');
        const replyContent = document.getElementById('replyContent');
        const editContent = document.getElementById('editContent');

        function hideReply() {
            replyParentId.value = '';
            replyContent.value = '';
            replyForm.style.display = 'none';
        }

        function hideEdit() {
            editContent.value = '';
            editForm.style.display = 'none';
        }


        function openReply(parentId) {
            hideEdit();

            let slot = document.getElementById('children-' + parentId);
            if (!slot) {
                // 부모 카드 밑에 children 슬롯이 없을 때 생성해서 붙임
                const parent = document.getElementById('cmt-' + parentId) || document.body;
                slot = document.createElement('div');
                slot.id = 'children-' + parentId;
                parent.appendChild(slot);
            }

            slot.appendChild(replyForm);
            replyParentId.value = parentId;
            replyForm.style.display = 'block';
            replyForm.scrollIntoView({ behavior: 'smooth', block: 'center' });
        }

        function openEdit(commentId) {
            hideReply();
            const body = document.getElementById('cmt-body-' + commentId);
            editContent.value = body ? body.textContent.trim() : '';
            editForm.action = CTX + '/interior/comments/' + commentId + '/edit';
            const block = document.getElementById('cmt-' + commentId) || document.body;
            block.appendChild(editForm);
            editForm.style.display = 'block';
            editForm.scrollIntoView({behavior: 'smooth', block: 'center'});
        }

        if (window.location.hash === '#comments') {
            const anchor = document.getElementById('comments');
            if (anchor) anchor.scrollIntoView({behavior: 'auto', block: 'start'});
        }
    </script>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
