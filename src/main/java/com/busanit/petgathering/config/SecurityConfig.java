package com.busanit.petgathering.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.busanit.petgathering.service.MemberService;
import com.busanit.petgathering.service.PrincipalOauth2UserService;

@Configuration // 설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션
@EnableWebSecurity // 보안 설정을 커스터마이징 할 수 있는 애노테이션. SpringSecurityFilterChane 이 자동으로 포함됨
public class SecurityConfig extends WebSecurityConfigurerAdapter { // WebSecurityConfigurerAdapter 를 상속받아서 보안 설정

    @Autowired
    MemberService memberService;
    
    @Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

    @Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
    

    // http 요청에 대한 페이지 설정 (권한, 로그인 페이지, 로그아웃 메소드 등등)
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/members/login") // 로그인 페이지 url
                .defaultSuccessUrl("/") // 로그인 성공 시 url
                .usernameParameter("id") // 로그인 시 사용할 파라미터 이름 지정 (username 에 들어갈 변수?)
                .failureUrl("/members/login/error") // 로그인 실패 시 url
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 페이지 url
                .logoutSuccessUrl("/") // 로그아웃 성공 시 url
		        .and()
				.oauth2Login()
				.loginPage("/login")
				.userInfoEndpoint()
				.userService(principalOauth2UserService);
        http.authorizeRequests()
		        .mvcMatchers("/","/admin/**","/members/**","/item/**","/images/**","/store/**","/Dboard/**","/board/**", "/boardImg/**","/member/**","/**","/chat/**").permitAll() // 여기는 모두가 다 접근
                .mvcMatchers("/admin/**", "/member/**").hasRole("ADMIN").anyRequest().authenticated();
        		

        http.exceptionHandling() // 인증 안된 사용자는 ㄴㄴ
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

    }

    // static 하위 파일들은 인증 무시!
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**","/js/**","/img/**");
    }

    // http 요청에 대한 보안 설정 (권한, 로그인 페이지, 로그아웃 메소드 등등)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }
            // AuthenticationManager 로 인증함
            // AuthenticationManager 는 ~Builder 로 만듦
            // auth 에 이거를 다 담아서 인증을 하는거지
                // memberService 를 저장한다.(로그인 로그아웃 + 중복가입방지 + 중복 아니면 member save)
                // 비번 암호화까지 해버림


    // 비번 암호화
    @Bean // BCryptPasswordEncoder 를 Bean 으로 등록해서 사용
    public PasswordEncoder passwordEncoder(){
        // 해킹 시 회원 정보 노출 방지 (비번같은거 털릴 때 대비)_ BCryptPasswordEncoder 의 해시 함수를 이용해서 비번 암호화
        return new BCryptPasswordEncoder();
    }


}
