<%--
  Created by IntelliJ IDEA.
  User: goott4
  Date: 2025-08-14
  Time: 오전 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>업체 게시글 상세보기</title>
</head>
<body>
    <h2>post-detail</h2>

    <div>
        작성일: ${detail.post.postDate}
        조회수: ${detail.post.companyPostCount} <br/>
        <c:if test="${not empty detail.post.updatedAt}">
            수정일: ${detail.post.updatedAt}
        </c:if>
    </div>

    <div>
        제목: ${detail.post.companyPostTitle} <br/>
        내용: ${detail.post.companyPostContent} <br/>
    </div>

    <div>
        업체 정보 <br/>
        업체 이름: ${detail.company.companyName} <br/>
        소개: ${detail.company.companyIntro} <br/>
        공간 타입: ${detail.post.spaceType} <br/>
        평수: ${detail.post.areaPyeong} <br/>
        스타일: ${detail.post.style} <br/>
        시공상세: ${detail.post.constructionDetail} <br/>
    </div>

    <c:if test="${not empty detail.images}">
        <div>
            <c:forEach items="${detail.images}" var="img">
                <img src="${img.url}" style="width: 200px; height: 200px;"/>
            </c:forEach>
        </div>
    </c:if>

    <c:set var="companyId" value="${detail.post.companyId}"/>
    <c:url var="backPostUrl" value="/interior/myhome/${companyId}">
        <c:param name="type" value="posts"/>
        <%--<c:param name="posts" value="${detail.post.companyPostId}"/>--%>
    </c:url>

    <a href="${backPostUrl}">뒤로가기</a>

    <c:if test="${isOwner}">
        <c:url var="editUrl" value="/interior/posts/${detail.post.companyPostId}/edit"/>
        <a href="${editUrl}">수정하기</a>
    </c:if>

    <c:if test="${isOwner}">
        <form action="<c:url value='/interior/posts/${detail.post.companyPostId}/delete'/>"
              method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="companyId" value="${detail.post.companyId}"/>
            <button type="submit">삭제하기</button>
        </form>
    </c:if>

    <hr id="comments"/>

    <!-- add플래시 추후 수정 필요 -->
    <c:if test="${not empty msg}">
        <div style="color:green">${msg}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div style="color:red">${error}</div>
    </c:if>

    <h3>댓글</h3>

    <!-- 루트 댓글 작성 -->
    <form class="comment-box"
          action="${pageContext.request.contextPath}/interior/posts/${detail.post.companyPostId}/comments"
          method="post" style="margin-bottom:16px;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <textarea name="content" rows="3" cols="80" required placeholder="댓글 내용을 입력하세요"></textarea>
        <button type="submit">등록</button>
    </form>

    <!-- 댓글/대댓글 -->
    <c:forEach var="cmt" items="${comments}">
        <c:if test="${cmt.parentCommentId == null}">
            <div class="comment" id="cmt-${cmt.commentId}" style="border:1px solid #eee; padding:8px; margin-bottom:8px;">
                <div class="meta">#${cmt.commentId} · ${cmt.createdAt}</div>
                <div id="cmt-body-${cmt.commentId}">
                    <c:choose>
                        <c:when test="${cmt.deleted eq true}">
                            <em>삭제된 댓글입니다.</em>
                        </c:when>
                        <c:otherwise>
                            ${fn:escapeXml(cmt.commentContent)}
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="actions" style="margin-top:6px;">
                    <button type="button" onclick="openReply(${cmt.commentId})">답글</button>
                    <c:if test="${cmt.deleted ne true}">
                        <button type="button" onclick="openEdit(${cmt.commentId})">수정</button>
                        <form action="${pageContext.request.contextPath}/interior/comments/${cmt.commentId}/delete"
                              method="post" style="display:inline"
                              onsubmit="return confirm('이 댓글을 삭제할까요?');">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <input type="hidden" name="companyPostId" value="${detail.post.companyPostId}">
                            <button type="submit">삭제</button>
                        </form>
                    </c:if>
                </div>

                <!-- 대댓글 폼 삽입 슬롯 -->
                <div id="children-${cmt.commentId}" style="margin-top:6px; margin-left:24px;"></div>

                <!-- 대댓글 목록 -->
                <c:forEach var="rep" items="${comments}">
                    <c:if test="${rep.parentCommentId == cmt.commentId}">
                        <div class="reply" id="cmt-${rep.commentId}" style="border-left:2px solid #ddd; padding-left:8px; margin-top:8px;">
                            <div class="meta">↳ #${rep.commentId} · ${rep.createdAt}</div>
                            <div id="cmt-body-${rep.commentId}">
                                <c:choose>
                                    <c:when test="${rep.deleted eq true}">
                                        <em>삭제된 댓글입니다.</em>
                                    </c:when>
                                    <c:otherwise>
                                        ${fn:escapeXml(rep.commentContent)}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="actions" style="margin-top:6px;">
                                <button type="button" onclick="openReply(${rep.commentId})">답글</button>
                                <c:if test="${rep.deleted ne true}">
                                    <button type="button" onclick="openEdit(${rep.commentId})">수정</button>
                                    <form action="${pageContext.request.contextPath}/interior/comments/${rep.commentId}/delete"
                                          method="post" style="display:inline"
                                          onsubmit="return confirm('이 댓글을 삭제할까요?');">
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                        <input type="hidden" name="companyPostId" value="${detail.post.companyPostId}">
                                        <button type="submit">삭제</button>
                                    </form>
                                </c:if>
                            </div>

                            <!-- 대댓글 아래 폼 삽입 슬롯 -->
                            <div id="children-${rep.commentId}" style="margin-top:6px; margin-left:16px;"></div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:if>
    </c:forEach>

    <!-- 답글 폼 -->
    <form id="replyForm" class="comment-box"
          action="${pageContext.request.contextPath}/interior/posts/${detail.post.companyPostId}/comments"
          method="post" style="display:none; margin-top:8px;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="hidden" name="parentCommentId" id="replyParentId">
        <textarea name="content" id="replyContent" rows="3" cols="80" required placeholder="답글 내용을 입력하세요"></textarea>
        <button type="submit">답글 등록</button>
        <button type="button" onclick="hideReply()">취소</button>
    </form>

    <!-- 수정 폼 -->
    <form id="editForm" class="comment-box" action="" method="post"
          style="display:none; margin-top:8px;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input type="hidden" name="companyPostId" value="${detail.post.companyPostId}">
        <textarea name="content" id="editContent" rows="3" cols="80" required></textarea>
        <button type="submit">수정 완료</button>
        <button type="button" onclick="hideEdit()">취소</button>
    </form>

    <script>
        const CTX = '${pageContext.request.contextPath}';
        const replyForm = document.getElementById('replyForm');
        const editForm  = document.getElementById('editForm');
        const replyParentId = document.getElementById('replyParentId');
        const replyContent  = document.getElementById('replyContent');
        const editContent   = document.getElementById('editContent');

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
            const slot = document.getElementById('children-' + parentId) || document.body;
            slot.appendChild(replyForm);
            replyParentId.value = parentId;
            replyForm.style.display = 'block';
            replyForm.scrollIntoView({ behavior:'smooth', block:'center' });
        }

        function openEdit(commentId) {
            hideReply();
            const body = document.getElementById('cmt-body-' + commentId);
            editContent.value = body ? body.textContent.trim() : '';
            editForm.action = CTX + '/interior/comments/' + commentId + '/edit';
            const block = document.getElementById('cmt-' + commentId) || document.body;
            block.appendChild(editForm);
            editForm.style.display = 'block';
            editForm.scrollIntoView({ behavior:'smooth', block:'center' });
        }

        if (window.location.hash === '#comments') {
            const anchor = document.getElementById('comments');
            if (anchor) anchor.scrollIntoView({ behavior:'auto', block:'start' });
        }
    </script>



</body>
</html>
