package com.busanit.petgathering.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import com.busanit.petgathering.constant.Role;
import com.busanit.petgathering.entity.Member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class MemberFormDto {

	private Long num;
    
    @NotEmpty(message = "아이디는 필수 입력 값입니다.")
    @Length(min=4, max=12, message = "아이디는 4자 이상, 12자 이하로 입력해주세요")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{4,12}",
            message = "아이디는 영문과 숫자 조합으로 4 ~ 12자리까지 가능합니다.")
    private String id;
    
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Pattern(regexp = "^[가-힣]*$", message = "한글만 입력해주세요.")
    private String name;

    @NotEmpty(message = "비밀번호은 필수 입력 값입니다.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}",
            message = "비밀번호는 영문과 숫자 조합으로 8 ~ 16자리까지 가능합니다.")
    private String password;

    @NotEmpty(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;

    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String streetAdr;

    private String detailAdr;

    @Pattern(regexp = "^(01[1|6|7|8|9|0])-(\\d{3,4})-(\\d{4})$",message = "010-xxxx-xxxx 양식으로 입력하여주세요")
    private String phone;
    
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    private String email;


    private Role role;

    private List<MemberFormDto> memberFormDtoList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public static MemberFormDto of(Member member){
        return modelMapper.map(member, MemberFormDto.class);
    }
}
