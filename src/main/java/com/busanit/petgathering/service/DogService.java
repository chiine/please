package com.busanit.petgathering.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.busanit.petgathering.dto.DogFormDto;
import com.busanit.petgathering.dto.DogImgDto;
import com.busanit.petgathering.entity.Dog;
import com.busanit.petgathering.entity.DogImg;
import com.busanit.petgathering.repository.DogImgRepository;
import com.busanit.petgathering.repository.DogRepository;

@Service
@Transactional
public class DogService {

	@Autowired
	DogRepository dogRepository;
	
	@Autowired
	DogImgService dogImgService;
	
	@Autowired
	DogImgRepository dogImgRepository;
	
	public Dog saveDog(Dog dog) {
		
		return dogRepository.save(dog);
	}
	
	 @Transactional(readOnly = true)
	    public DogFormDto getDogDtl(Long dogId){

	        List<DogImg> dogImgList = dogImgRepository.findByDogIdOrderByIdAsc(dogId);
	        List<DogImgDto> dogImgDtoList = new ArrayList<>();

	        for (DogImg dogImg : dogImgList) {
	        	DogImgDto dogImgDto = DogImgDto.of(dogImg);
	            dogImgDtoList.add(dogImgDto); // entity -> dto
	        }

	        Dog dog = dogRepository.findById(dogId)
	                .orElseThrow(EntityNotFoundException::new);

	        DogFormDto dogFormDto = DogFormDto.of(dog);
	        dogFormDto.setDogImgDtoList(dogImgDtoList);
	        return dogFormDto; 
	    }

	
	
	public Long saveDog(DogFormDto DogFormDto, List<MultipartFile> dogImgFileList) throws Exception{
			Dog dog = DogFormDto.createDog();
			dogRepository.save(dog);
			
			for(int i = 0; i<dogImgFileList.size(); i++) {
				DogImg dogImg = new DogImg();
				dogImg.setDog(dog);
				if(i==0) {
					dogImg.setRepimgYn("Y");
				}else {
					dogImg.setRepimgYn("N");
				}
				dogImgService.saveDogImg(dogImg, dogImgFileList.get(i));
			}
			return dog.getId();
	}
	
	public Long updateDog(DogFormDto dogFormDto, List<MultipartFile> dogImgFileList) throws Exception{
		Dog dog = dogRepository.findById(dogFormDto.getId())
				.orElseThrow(EntityNotFoundException::new);
		dog.updateDog(dogFormDto);
		
		List<Long> dogImgIds = dogFormDto.getDogImgIds();
		
		for(int i=0; i<dogImgFileList.size();i++) {
			dogImgService.updateDogImg(dogImgIds.get(i), dogImgFileList.get(i));
		}
		return dog.getId();
	}
	
	
}
