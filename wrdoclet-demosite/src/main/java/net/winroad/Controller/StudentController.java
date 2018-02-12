package net.winroad.Controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.winroad.Models.Gender;
import net.winroad.Models.LoginAuthType;
import net.winroad.Models.LoginAuthority;
import net.winroad.Models.Student;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.Resource;

@Controller
@RequestMapping("/student")
@SessionAttributes("student")
public class StudentController {
	/**
	 * @param name the name of student.
	 * @api GET v1/name.{json|html}
	 * @return the student object.
	 */
	@Deprecated
	@RequestMapping(value = "name.{json|html}", method = RequestMethod.GET)
	public @ResponseBody
	Student getStudentInJSON(@PathVariable("json|html") String name) {
		Student s = new Student();
		//s.setSno(sno);
		s.setName(name);
		s.setAge(11);
		return s;
	}

	/**
	 * 添加一个学生
	 * @tag 学生管理
	 * @param student 要添加的学生
	 * 
	 * @author Adams 
	 * @version 0.0.1 
	 * @memo 添加接口
	 * @author Bob 
	 * @version 0.0.2 
	 * @memo fix bug
	 * 
	 * @return 被添加的学生
	 * @returnCode 400 404 503
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody
	Object addStudent(@Validated @RequestBody Student student) {
		student.setAge(student.getAge() + 10);
		student.setName(student.saySomething() + student.getTEast());
		return student;
	}

	@RequestMapping(value = "/delete/{name}", method = RequestMethod.POST)
	public @ResponseBody
	String delStudent(@RequestBody(required=false) Student student,
			@PathVariable("name") String username) {
		return "delete name:" + username + "/" + student.getName();
	}

	// TODO: how to handle Object as response?
	@RequestMapping(value = "/update", method = RequestMethod.POST, params="action")
	public @ResponseBody
	Object updateStudent(@RequestHeader("Accept-Encoding") Student student, 
			@RequestParam(value = "action", required = false) String action) {
		student.setAge(student.getAge() + 100);
		return student;
	}

	@RequestMapping(value = "/getgender/{name}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
	public @ResponseBody
	Gender getStudentGender(@PathVariable("name") String username) {
		return Gender.MALE;
	}
	
	/**
	 * @wr.param username || String || user's name to login || Y || adams | init add | 0.1 | bob | update | 0.2
	 * @wr.param password || String || user's password to login 
	 * @wr.param isAdmin || boolean || login as admin || N || adams | init add | 0.1 
	 * @wr.return login cookie || http cookie
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, Model model) {
		String username = request.getParameter("username").trim();
		return username;
	}
	
	@RequestMapping(value = "/{userId}", headers="content-type=text/*")
	public ModelAndView showDetail(@PathVariable("userId") String userId,
			@CookieValue("JSESSIONID") String sessionId, 
			@RequestHeader("Accept-Language") String acceptLanguage) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("user/showDetail");
		mav.addObject("user", new Student());
		return mav;
	}
	
	@RequestMapping("/new")
	@LoginAuthority(authType = LoginAuthType.REQUEST_COOKIE_AUTH)
	public String newStudent(Student student) {
		return "success" + student.getName();
	}
	
	@RequestMapping("/getName")
	public String getMyName(HttpSession session) {
		return (String)session.getAttribute("name");
	}
	
	@RequestMapping("/handle")
	public String handleStudent(WebRequest request) {
		request.getParameter("userName");
		return "success";
	}
	
	@RequestMapping("/getLogo")
	@LoginAuthority(authType = LoginAuthType.NO_AUTH)
	public void getLogo(OutputStream os) throws IOException {
		ClassPathResource res = new ClassPathResource("/image.jpg");
		FileCopyUtils.copy(res.getInputStream(), os);
	}
	
	@RequestMapping("/handleFooBar")
	public ResponseEntity<Student> handleFooBar(HttpEntity<String> httpEntity) {
		System.out.println(httpEntity.getBody());
		ResponseEntity<Student> re = new ResponseEntity<Student> (HttpStatus.OK);
		return re;
	}
	
	@RequestMapping("/handlev2")
	public String handleStudentV2(@ModelAttribute("student") Student student) {
		student.setAge(18);
		return "/student/createSuccess";
	}
	
	@ModelAttribute("student")
	public Student getStudent() {
		return new Student();
	}
	
	@RequestMapping("/showme")
	public String showMe(ModelMap modelMap, SessionStatus sessionStatus) {
		modelMap.addAttribute("test", "value1");
		Student student = (Student) modelMap.get("student");
		sessionStatus.setComplete();
		student.setxIndex(0);
		return "/student/show";
	}
	
    @RequestMapping("/upload")
    public String upload(@RequestPart("picture") byte[] picture) {
        /* ... */
    	return null;
    }	
    
    @RequestMapping("/upload2")
    public String upload2(@RequestPart(name="picture", required=false) MultipartFile picture) {
        /* ... */
    	return null;
    }    
}