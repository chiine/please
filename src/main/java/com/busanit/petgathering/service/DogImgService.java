package com.busanit.petgathering.service;

import javax.persistence.EntityNotFoundException;

import org.apache.groovy.parser.antlr4.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.busanit.petgathering.entity.DogImg;
import com.busanit.petgathering.repository.DogImgRepository;

@Service
@Transactional
public class DogImgService {

	@Value("${dogImgLocation}")
	private String dogImgLocation;
	
	@Autowired
	DogImgRepository dogImgRepositroy;
	
	@Autowired
	FileService fileService;
	
	public void saveDogImg(DogImg dogImg, MultipartFile dogImgFile) throws Exception{
		String oriImgName = dogImgFile.getOriginalFilename();
		String imgName="";
		String imgUrl="";
		
		if(!StringUtils.isEmpty(oriImgName)) {
			imgName= fileService.uploadFile(dogImgLocation, oriImgName , dogImgFile.getBytes());
			imgUrl="/images/dog/"+imgName;
		}
		
		dogImg.updateDogImg(oriImgName, imgName, imgUrl);
		dogImgRepositroy.save(dogImg);
	}
	
	public void updateDogImg(Long dogImgId, MultipartFile dogImgFile) throws Exception{
		if(!dogImgFile.isEmpty()) {
			DogImg savedDogImg = dogImgRepositroy.findById(dogImgId)
								.orElseThrow(EntityNotFoundException::new);
			if(!StringUtils.isEmpty(savedDogImg.getImgName())) {
				fileService.deleteFile(dogImgLocation+"/"+savedDogImg.getImgName());
			}
			
			String oriImgName = dogImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(dogImgLocation,
								oriImgName, dogImgFile.getBytes());
			String imgUrl = "/images/dog/" + imgName;
			savedDogImg.updateDogImg(oriImgName, imgName, imgUrl);
			
		}
		
	}
}
