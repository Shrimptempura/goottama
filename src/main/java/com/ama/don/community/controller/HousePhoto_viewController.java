package com.ama.don.community.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ama.don.common.dao.FileDao;
import com.ama.don.common.dto.FileDto;
import com.ama.don.common.enums.TargetType;
import com.ama.don.common.utils.CommunityPageVO;
import com.ama.don.community.dao.CommunityPostDao;
import com.ama.don.community.dto.HousePhoto.HousePhotoPostDto; // DTO 경로 맞게 변경

@Controller
@RequestMapping("/community")
public class HousePhoto_viewController {

	@Autowired
	private CommunityPostDao communityPostDao;

	@Autowired
	private FileDao fileDao;

	// 글쓰기 페이지 이동
	@GetMapping("/write_housephoto")
	public String writeView() {
		return "community/write_view"; // JSP 경로에 맞게 변경
	}

	// 하우스포토 목록
	@GetMapping("/housephoto_view")
	public String housePhotoList(@RequestParam(defaultValue = "1") int page, Model model) {
		CommunityPageVO pageVO = new CommunityPageVO();
		pageVO.setPage(page);

		String targetType = TargetType.COMMUNITY_HOUSEPHOTO.name();

		int totalCount = communityPostDao.countTargetType(targetType);
		pageVO.pageCalculate(totalCount);

		List<HousePhotoPostDto> list = communityPostDao.findHousePhotoTargetType(targetType, pageVO.getRowStart(),
				pageVO.getDisplayRowCount());

		for (HousePhotoPostDto photo : list) {
			List<FileDto> fileList = fileDao.findByTargetId(TargetType.COMMUNITY_HOUSEPHOTO, photo.getPost_id());
			photo.setFileList(fileList);
		}

		model.addAttribute("housePhotoList", list);
		model.addAttribute("pageVO", pageVO);

		return "community/house_photo_view";
	}

	// 조회수 와 좋아요 수 조회
	@GetMapping("/housephoto_live_counts")
	@ResponseBody
	public List<Map<String, Object>> getLiveHousePhotoCounts() {
		return communityPostDao.findHousePhotoCounts();
	}
}
