package com.busanit.petgathering.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.busanit.petgathering.constant.Role;
import com.busanit.petgathering.dto.MemberFormDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Entity
@Table(name="member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member extends BaseEntity{

	@Id
	@Column(name="member_num", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    private String name;
    
    @Column(name="member_id")
    private String id;

    private String password;

    private String zipcode;

    private String streetAdr;

    private String detailAdr;

    private String phone;
    
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    
    private String provider;
	private String providerId;

	public String getRoleKey(){
        return role.getKey();
    }
	
    public void updateMember(MemberFormDto memberFormDto) {
        //Member 엔티티를 수정하는 메소드
        this.name = memberFormDto.getName();
        this.id = memberFormDto.getId();
        this.zipcode = memberFormDto.getZipcode();
        this.phone = memberFormDto.getPhone();
        this.email = memberFormDto.getEmail();
        this.streetAdr = memberFormDto.getStreetAdr();
        this.detailAdr = memberFormDto.getDetailAdr();
        this.role = memberFormDto.getRole();
    }

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        //MemberFormDto와 PasswordEncoder를 이용하여 Member 엔티티를 생성하는 메소드를 작성
        Member member =  new Member();
        member.setName(memberFormDto.getName());
        member.setId(memberFormDto.getId());
        member.setZipcode(memberFormDto.getZipcode());
        member.setStreetAdr(memberFormDto.getStreetAdr());
        member.setDetailAdr(memberFormDto.getDetailAdr());
        member.setPhone(memberFormDto.getPhone());
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);

        return member;
    }

}