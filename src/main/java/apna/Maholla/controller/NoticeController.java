package apna.Maholla.controller;

import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.model.Notice;
import apna.Maholla.model.Users;
import apna.Maholla.repository.LoginRepository;
import apna.Maholla.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NoticeController {
    private NoticeRepository noticeRepository;
    private LoginRepository loginRepository;

    @Autowired
    public NoticeController(NoticeRepository noticeRepository, LoginRepository loginRepository) {
        this.noticeRepository = noticeRepository;
        this.loginRepository = loginRepository;
    }

    @PostMapping(path = "/addNotice", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound addApartment(@RequestBody Notice notice) throws Exception {
        Users user = loginRepository.findByEmailid(notice.userid);
        if(user == null){
            return new ResourceNotFoundException("User", "UserId", notice.userid, "Do not exit", "User with this userid is not registered");
        }
        notice.setApartmentkey(user.apartmentkey);
        noticeRepository.save(notice);
        return new ResourceSavesSuccess("Notice", "Notice", notice.subject, "Sucess", "Notice added");
    }
}
