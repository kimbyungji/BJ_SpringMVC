package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.member.model.SampleDTO;

@Controller
@RequestMapping("/sample/*")	// 매핑할 내용을 지정하는 어노테이션...
public class SampleController {
	
	// 경로 sample로 기본 경로
	@RequestMapping("")
	public void basic() {
		System.out.println("basic....");
	}
	
	// 경로 sample/basic인 경우
	@RequestMapping("/basic")
	public void basicGet() {
		System.out.println("basic get....");
	}
	
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		System.out.println("basic only get....");
	}
	
	// SampleDTO[mame, age]를 데이터로 가지는 클래스
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		System.out.println(dto);
		
		return "ex01";
	}
	
	// sample/ex02에 개별적 파라미터 처리 어노테이션
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") String age) {
		System.out.println("이름 :"+name);
		System.out.println("나이 :"+age);
		
		return "ex02";
		
	}
	
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		System.out.println("ids : "+ids);
		return "ex02List";
	}
	
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		System.out.println("ids : "+Arrays.toString(ids));
		return "ex02Array";
	}
	
	// sampleDTO 리스트는 sampleDTOList 클래스를 고민해야 한다.
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTO list) {
		System.out.println("list dtos : "+list);
		return "ex02Bean";
	}
	
//	@InitBinder
//	public void inintBinder(WebDataBinder binder) {
//		SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd");
//		binder.registerCustomEditor("java.util.Data.class", new CustomDateEditor(dataformat, false));
//	}
	
	/*
	 * @ModelAttribute 어노테이션 : 강제로 전달 받은 파라미터를 담아서 전달하도록 할 때 필요한 어노테이션입니다.
	 * 아래의 함수에서 page는 그냥 전달하면 넘어가지 않음...
	 * 전달하기 위해서 강제로 저장해서 보낼 필요가 있음
	 * @modelAttribute를 사용하여 강제로 저장하여 전달
	 */
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		System.out.println("dto :"+dto);
		System.out.println("page :"+page);
		
		return "sample/ex04";
	}
	
	/*
	 * RedirectAttribute : Model 타입과 더불어 스프링 MVC가 자동으로 전달해 주는 타입 중 RedirectAttribute가 존재함
	 * JSP에서 response.sendRedirect("/home?name=aaa&page=10");과 유사한 경우
	 * 
	 * Spring에서는 
	 * rttr.addFlashAttribute("name","aaa");
	 * rttr.addFlashAttribute("age",10);
	 * 
	 * return "redirect:/home";
	 * **리다이렉트 시 값을 전달하지만 일회성 데이터 전달함...
	 */
	@GetMapping("/home2")
	public String home2(RedirectAttributes rttr) {
		rttr.addFlashAttribute("name", "AAA");
		rttr.addFlashAttribute("age", 10);
		return "redirect:/";
	}
	
	/* <!-- jackson-databind : json 타입 데이터 처리 -->
	<dependency>
		<groupId>com.fasterxml.jackson.core</groupId>
		<artifactId>jackson-databind</artifactId>
		<version>2.9.4</version>
	</dependency>
	를 pon.xml에 추가하여 라이브러리를 추가 설정하면 접속이 허용됨
	*/
	@GetMapping("/ex05")
	@ResponseBody	// 데이터로 처리할 때 사용. ViewResolv를 거치지 않고 처리됨
	public SampleDTO ex05() {
		System.out.println("/ex05... VO 또는 DTO와 같은 복합데이터가 있는 객체 타입");
		SampleDTO dto = new SampleDTO();
		dto.setName("홍길동");
		dto.setAge(200);
		
		return dto;
	}
	
	@GetMapping("/ex051")
	@ResponseBody	// 데이터로 처리할 때 사용. ViewResolv를 거치지 않고 처리됨
	public List<SampleDTO> ex051() {
		System.out.println("/ex05... VO 또는 DTO와 같은 복합데이터가 있는 객체 타입");
		List<SampleDTO> list = new ArrayList<>();
		SampleDTO dto = new SampleDTO();
		dto.setName("홍길동");
		dto.setAge(200);
		list.add(dto);
		
		SampleDTO dto2 = new SampleDTO();
		dto2.setName("홍길자");
		dto2.setAge(100);
		list.add(dto2);
		
		return list;
	}
	
	// ResponseEntity 타입 : 웹 프로그램을 다룰 때에 HTTP프로토콜 헤더를 다루는 경우에 사용
	/*
	 * 스프링은 기본적으로 HttpServleRequest나 HttpServletResponse를 직접 핸들링하지 않아요
	 * 이때에 사용하는 것이 ResponseEntity입니다.
	 */
	@GetMapping("ex06")
	public ResponseEntity<String> ex06(){
		System.out.println("ex06...동작입니다.");
		
		// {"name":"홍길동"}
		String msg = "{\"name\":\"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<String>(msg, header, HttpStatus.OK);
	}
	
	// 파일 업로드 정리
	/*
	 * servlet이 2.5인 경우에는 commons의 파일 업로드를 사용하던지 cos.jar파일을 불러와서 사용했음
	 * servlet 3.0버전 이후에는 Tomcat 자체에서 지원하고 있기 때문에 별도의 라이브러리를 불러올 필요 없음
	 * 현재는 2.5가 기본으로 생성되기 때문에 테스트용으로 확인
	 * 
	 * - pom.xml에서 commons-fileupload 라이브러리를 추가
	 * - servlet-context.xml에서 일부를 추가 지정
	 */
	
	@GetMapping("/exUpload")
	public void exUpload() {
		System.out.println("/exUpload.....");
	}
	
	@PostMapping("/sample/exUploadPost")
	public void exUploadPost(ArrayList<MultipartFile> files) {
		files.forEach(file -> {
			System.out.println("--------------------------------");
			System.out.println("name : "+file.getOriginalFilename());
			System.out.println("size : "+file.getSize());
		});
	}
}
