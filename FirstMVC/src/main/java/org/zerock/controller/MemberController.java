package org.zerock.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.zerock.member.model.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
//	@RequestMapping(value="/memberJoin",method = RequestMethod.POST)
	@GetMapping("/memberJoin")	// Get, Post, Put, Delete... 메서드 맵핑 어노테이션이 있음
	public String memJoin() {
		System.out.println("memJoin에 접근...");
		return "member/memJoin";
	}
	
	@RequestMapping(value = "/memLogin", method = RequestMethod.GET)	// 
	public String memLogin(Model model) {		
		return "member/memLogin";
	}
	
	@RequestMapping(value = "/memLogin", method = RequestMethod.POST)
	public String memLogin(
			Model model,
			/* request 객체를 이용하는 방법 */
//			HttpServletRequest request
			/* 어노테이션을 이용하는 방법 */
			// @RequestParam의 추가 속성
			// @RequestParam(value = "파라미터 값", required = false, defaultValue = "기본값")
			// 하나의 인자를 사용하는 경우 라마티어 값
			// 1. required : 해당 파라미터가 필수가 아닌 경우 지정, 기본 : true, false면 값이 없어도 에러 x
			// 2. defaultValue : required 지정시 기본값 지정
			
//			@RequestParam("memId") String memId,
//			@RequestParam("memPw") String memPw,
//			@RequestParam(value = "memName", required = false, defaultValue = "tester") String name
			
			// 3. 커멘드 객체를 이요하는  HTTP 정보 얻기
			// vo객체에 동일한 setter가 있으면 자동으로 저장
			MemberVO mem
			) {
		
//		// 1.전통적 방법
//		String memId = request.getParameter("memId");
//		String memPw = request.getParameter("memPw");
		
//		System.out.println(memId);
//		System.out.println(memPw);
//		System.out.println(name);
		System.out.println(mem.getMemId());
		System.out.println(mem.getMemPw());
		System.out.println(mem.getName());
		
		// 4. Model
		// 컨트롤러에서 뷰에 데이터를 전달하기 위해서 사용되는 객체로 Model과 ModelAndView가 있다.
		// 1. Model은 데이터만 전달
		// 2. ModelAndView 데이터와 뷰의 이름을 함께 전달
		
		// Model : addAttribute("전달이름", 값);
		model.addAttribute("memId", mem.getMemId());
		model.addAttribute("memPw", mem.getMemPw());
		
		model.addAttribute("mem", mem);
		
		return "member/memLogin_ok";
	}
	
	// ModelAndView 방식
	@RequestMapping(value ="/memLoginModelAndView", method = RequestMethod.POST)
	public ModelAndView memPut(Model model, MemberVO memVO) {
		
		// 객체 생성
		ModelAndView mav = new ModelAndView();
		
		// ModelAndView에 값을 추가
		mav.addObject("memId", memVO.getMemId());
		mav.addObject("mem", memVO);
		
		// View 설정
		mav.setViewName("member/memLogin_ok");
		
		return mav;
	}
	
	@RequestMapping("/req_ex01")
	public void req_ex01() {	// void는 접속 경로의 파일 이름을 사용
		// 접속 경로(uri 중 context경로 뺸)는? => member/req_ex01
		System.out.println("req_ex01로 접속했습니다.");
	}
	
	@RequestMapping("/req_ex02")
	public String req_ex02() {
		return "member/req_ex02";
	}
	
	@RequestMapping("/req_ex03")
	public String req_ex03() {
		return "redirect:/member/req_ex01";
		
	}
	
	@ResponseBody	// 메서드에 리턴 값을 view리저블로 전달 X, 해당 메서드를 호출한 곳으로 결과를 반환  // 레스트 컨트롤?
	@RequestMapping("/test")
	public String testResponseBody() {
		return "<h2>testResponseBody<h2>";
	}
}


