package com.ama.don.member.controller;


import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ama.don.admin.dto.postForAdminDTO.PostSearchForAdminDTO;
import com.ama.don.admin.service.postViewerForAdmin.GetPostListService;
import com.ama.don.admin.utils.SearchVO;
import com.ama.don.member.dto.MemberDto;
import com.ama.don.member.dto.MemberEditDto;
import com.ama.don.member.dto.ResetPwDto;
import com.ama.don.member.service.LoginMemberService;
import com.ama.don.member.service.MemberProfileService;
import com.ama.don.member.service.MemberUpdateService;
import com.ama.don.member.service.MypageDataService;
import com.ama.don.member.service.ProfileImgUploadService;
import com.ama.don.member.service.WithdrawalService;
import com.ama.don.shop.dao.ShopIDao;
import com.ama.don.shop.service.ShopServiceinter;
import com.ama.don.shop.service.orderservice.ShopOrderDetailService;
import com.ama.don.shop.service.productinquiry.ShopProductInquiryDetailService;
import com.ama.don.shop.service.productlike.ShopProductLikeDetailService;
import com.ama.don.shop.service.reviewservice.ShopReviewDetailService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberProfileService memberProfileService;
	private final ProfileImgUploadService profileImgUploadService;
	private final WithdrawalService withdrawalService;
	private final LoginMemberService loginMemberService;
	private final MemberUpdateService memberUpdateService;
	private final MypageDataService mypageDataService;
	private ShopServiceinter shopServiceinter;
	private final ShopIDao iDao;
	

	@PostMapping("/resetPw")
	public String resetPw(@Valid @ModelAttribute ResetPwDto resetPwDto,BindingResult bindingResult, HttpSession session,Model model) {
				
		if (bindingResult.hasErrors()) {
	        return "member/resetPw_view";
	    }
	
		boolean success = memberProfileService.resetPw(resetPwDto, session, model);
		
		if (!success) {
			return "member/resetPw_view";
		}		
		return "redirect:/login_view";
	}
	
	@GetMapping("/mypage/myProfile")
	public String memberProfile(Model model,MemberDto memberDto) {
		
		memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember", memberDto);
		shopServiceinter = new ShopReviewDetailService(iDao);
		shopServiceinter.execute(model);
		
		return "member/mypage/myProfile";
	}
	
	@GetMapping("/mypage/myOrderList")
	public String memberOrderList(HttpServletRequest request, Model model,MemberDto memberDto) {
		
		memberDto=loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember",memberDto);
		model.addAttribute("request", request);
		ShopOrderDetailService service = new ShopOrderDetailService(iDao);
		service.execute(model);
		
		return "member/mypage/myOrderList";
	}
	
	@GetMapping("/mypage/myScrapbook")
	public String memberScrapbook(HttpServletRequest request,Model model) {
		
		shopServiceinter = new ShopProductLikeDetailService(iDao);
		shopServiceinter.execute(model);
		
		return "member/mypage/myScrapbook";
	}
	
	@GetMapping("/mypage/myInquiry")
	public String myInquiry(Model model) {
		
		shopServiceinter = new ShopProductInquiryDetailService(iDao);
		shopServiceinter.execute(model);
		
		return "member/mypage/myInquiry";
	}
	
	@GetMapping("/mypage/myReview")
	public String myReview(Model model) {
		
		shopServiceinter = new ShopReviewDetailService(iDao);
		shopServiceinter.execute(model);
		
		return "member/mypage/myReview";
	}
	
	@GetMapping("/mypage/myComment")
	public String myComment(Model model,MemberDto memberDto,SearchVO searchVO) {	
		if (searchVO == null) {
            searchVO = new SearchVO();
        }
		model.addAttribute("searchVO", searchVO);
		PostSearchForAdminDTO dto = new PostSearchForAdminDTO();
	    model.addAttribute("postSearchForAdminDTO", dto);
		memberDto=loginMemberService.getCurrentLoginMemberDto();
		mypageDataService.excute(model, memberDto);
		
		return "member/mypage/myComment";
	}
	
	@GetMapping("/mypage/editProfile_view")
	public String editProfile_view(MemberDto memberDto, Model model) {
		
		memberDto = loginMemberService.getCurrentLoginMemberDto();
		model.addAttribute("loginMember", memberDto);
		
		return "member/mypage/editProfile_view";
	}
	
	@PostMapping("/editProfile")
	public String editProfile(@ModelAttribute MemberEditDto memberEditDto, Model model) {
		
		MemberDto memberDto = loginMemberService.getCurrentLoginMemberDto();
		memberEditDto.combineAddress(); // 폼에 입력된 값 하나로 dto에 주입
		
		boolean success = memberProfileService.updateProfile(memberDto, memberEditDto, model); //db업데이트
		
		if (!success) {
			model.addAttribute("loginMember", memberDto);
			return "member/mypage/editProfile_view";
		}
		//세션 갱신
		memberUpdateService.refreshAuthentication(memberDto.getLogin_id());
		
		return "redirect:/mypage/editProfile_view";
	}
	
	@PostMapping("/profileImgUpload")
	public String profileImgUpload(@RequestParam("profileImg") MultipartFile file, MemberDto memberDto, Model model) throws IllegalStateException, IOException {
		
		memberDto = loginMemberService.getCurrentLoginMemberDto();
		
		profileImgUploadService.changeProfileImg(memberDto, file);
		
		//세션 최신화
		memberUpdateService.refreshAuthentication(memberDto.getLogin_id());
		
		model.addAttribute("loginMember", memberDto);
		
		return "redirect:/mypage/editProfile_view";
	}
	
	@GetMapping("/mypage/editPassword")
	public String editPassword() {
		return "member/mypage/editPassword";
	}
	
	@GetMapping("/mypage/customerCenter")
	public String customerCenter() {
		return "member/mypage/customerCenter";
	}
	@GetMapping("/mypage/withdrawal_view")
	public String withdrawal_view() {
		return "member/mypage/withdrawal_view";
	}
	@PostMapping("/mypage/withdrawal")
	public String withdrawal(@RequestParam("agree") String agree,@RequestParam(value = "reason",defaultValue = "4") int reason,HttpSession session) {
		
		MemberDto memberDto = loginMemberService.getCurrentLoginMemberDto();
		withdrawalService.deletedMember(agree, reason, memberDto);
		
		//스프링 시큐리티 로그아웃(인증정보 삭제)
		SecurityContextHolder.clearContext();
		//세션 무효화
		session.invalidate();
		
		return "member/withdrawalSuccess_view";
	}
	@GetMapping("/member/withdrawalSuccess")
	public String withdrawalSuccess() {
		return "redirect:/";
	}
	
}
