package com.busanit.petgathering.controller;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.busanit.petgathering.dto.DogFormDto;
import com.busanit.petgathering.entity.Dog;
import com.busanit.petgathering.repository.DogImgRepository;
import com.busanit.petgathering.repository.DogRepository;
import com.busanit.petgathering.service.DogService;

@RequestMapping("/Dboard")
@Controller
public class DogBoardController {
	
	@Autowired
	DogService dogService;
	@Autowired
	DogRepository dogRepository;
	@Autowired
	DogImgRepository dogImgRepository;
	
	

		
	@GetMapping("/new")
	public String dogForm(Model model) {
		model.addAttribute("dogFormDto", new DogFormDto());
		
		return "dogBoard/dogBoardForm";
	}
	
	@PostMapping("/new")
	public String dogNew(@Valid DogFormDto dogFormDto, BindingResult bindingResult, Model model, @RequestParam("dogImgFile") List<MultipartFile> dogImgFileList){
	
		if(bindingResult.hasErrors()) {
			return "dogBoard/dogBoardForm";
		}
		
		
		if(dogImgFileList.get(0).isEmpty() && dogFormDto.getId() == null) {
			model.addAttribute("errorMessage" , "첫번째 이미지는 필수 입력값입니다.");
			return "dogBoard/dogBoardForm";
		}
		
		try {
			dogService.saveDog(dogFormDto, dogImgFileList);
			
		}catch(Exception e) {
			model.addAttribute("errorMessage", "이미지 등록중 에러가 발생했습니다.");
			return "dogBoard/dogBoardForm";
		}
		return "redirect:/Dboard/list";
	}
	
	
	@GetMapping("/list")
	public String dogList(Model model){
		
		List<Dog> list = new ArrayList<>();
		list = dogRepository.selectDogList();
	
		
		model.addAttribute("list",list);
		
		return "dogBoard/dogBoardList";
	}
	
	@GetMapping("/list/view/{dogId}")
	public String dogView(Model model, @PathVariable("dogId") Long dogId) {
		DogFormDto dogFormDto = dogService.getDogDtl(dogId);
		model.addAttribute("dog",dogFormDto);
		
		return "dogBoard/dogBoardView";
	}
	
	@PostMapping("/list/view/{dogId}")
	public String dogList(Model model, @PathVariable("dogId") Long dogId) {
		DogFormDto dogFormDto = dogService.getDogDtl(dogId);
		model.addAttribute("dog",dogFormDto);
		
		return "dogBoard/dogBoardView";
	}
	
	
	@GetMapping("/modify/{dogId}")
	public String dogModifyView(Model model, @PathVariable("dogId") Long dogId) {
		
		try {
			DogFormDto dogFormDto = dogService.getDogDtl(dogId);
			model.addAttribute("dogFormDto", dogFormDto);
		}catch(EntityNotFoundException e) {
			model.addAttribute("errorMessage", "존재하지 않는 강아지 입니다");
			return "dogBoard/dogBoardForm";
		}
		return "dogBoard/dogBoardForm";
		
	}
	@PostMapping("/modify/{dogId}")
	public String dogUpdate(@Valid DogFormDto dogFormDto, BindingResult bindingResult, @RequestParam("dogImgFile")List<MultipartFile> dogImgFileList, Model model) {
		if(bindingResult.hasErrors()) {
			return "dogBoard/dogBoardForm";
		}
		if(dogImgFileList.get(0).isEmpty() && dogFormDto.getId() == null ) {
			model.addAttribute("errorMessage", "첫번째 이미지는 필수입력값입니다");
			return "dogBoard/dogBoardForm";
		}
		try {
			dogService.updateDog(dogFormDto, dogImgFileList);
		}catch(Exception e) {
			model.addAttribute("errorMessage", "수정중 에러 발생");
			return "dogBoard/dogBoardForm";
		}
		return "redirect:/Dboard/list/view/{dogId}";
	}
	
	

	
	
}
