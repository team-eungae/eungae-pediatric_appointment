package com.playdata.eungae.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.playdata.eungae.member.dto.ChildrenDto;
import com.playdata.eungae.member.service.ChildrenService;

@Controller
@RequestMapping("/children")
public class ChildrenController {

	private final ChildrenService childrenService;

	@Autowired
	public ChildrenController(ChildrenService childrenService) {
		this.childrenService = childrenService;
	}

	@GetMapping("/list")
	public String getAllChildren(Model model) {
		List<ChildrenDto> childrenList = childrenService.getAllChildren();
		model.addAttribute("childrenList", childrenList);
		return "contents/member/my-children";
	}

	@GetMapping("/add")
	public String addChildrenForm(Model model) {
		model.addAttribute("childrenDto", new ChildrenDto());
		return "contents/member/my-children-add";
	}

	@PostMapping("/add")
	public String createChild(@ModelAttribute ChildrenDto childrenDto, @RequestParam("photo") MultipartFile photoFile) {
		childrenService.createChild(childrenDto, photoFile);
		return "redirect:/children/list";
	}

	@PostMapping("/{id}")
	public String deleteChild(@PathVariable Long id, @RequestParam(value = "_method", required = false) String method) {
		if ("delete".equals(method)) {
			childrenService.deleteChild(id);
		}
		return "redirect:/children/list";
	}

}