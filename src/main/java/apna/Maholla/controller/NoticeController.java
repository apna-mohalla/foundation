package apna.Maholla.controller;

import apna.Maholla.RequestModels.UserApartmentRequestMapper;
import apna.Maholla.ResponceModel.NoticeResponceModel;
import apna.Maholla.exception.ResourceFoundNotFound;
import apna.Maholla.exception.ResourceNotFoundException;
import apna.Maholla.exception.ResourceSavesSuccess;
import apna.Maholla.mappers.GetNoticeRequestMapper;
import apna.Maholla.model.Apartment;
import apna.Maholla.model.Notice;
import apna.Maholla.model.Users;
import apna.Maholla.repository.ApartmentRepository;
import apna.Maholla.repository.LoginRepository;
import apna.Maholla.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoticeController {
    private NoticeRepository noticeRepository;
    private LoginRepository loginRepository;
    private ApartmentRepository apartmentRepository;

    @Autowired
    public NoticeController(NoticeRepository noticeRepository, LoginRepository loginRepository, ApartmentRepository apartmentRepository) {
        this.noticeRepository = noticeRepository;
        this.loginRepository = loginRepository;
        this.apartmentRepository = apartmentRepository;
    }

    @PostMapping(path = "/addNotice", produces = { MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResourceFoundNotFound addNotice(@RequestBody Notice notice) throws Exception {
        Users user = loginRepository.findByEmailid(notice.userid);
        if(user == null){
            return new ResourceNotFoundException("User", "UserId", notice.userid, "Do not exit", "User with this userid is not registered");
        }
        notice.setApartmentkey(user.apartmentkey);
        noticeRepository.save(notice);
        return new ResourceSavesSuccess("Notice", "Notice", notice.subject, "sucess", "Notice added");
    }

    @PostMapping(path = "/getAllNotice", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<NoticeResponceModel> getAllNotice(@RequestBody UserApartmentRequestMapper noticeRequestMapper) throws Exception {
        List<Notice> notices;
        if (noticeRequestMapper.userid != null) {
            Users user = loginRepository.findByEmailid(noticeRequestMapper.userid);
            notices = noticeRepository.findAllByApartmentkey(user.apartmentkey);
        } else {
            Apartment apartment = apartmentRepository.findByApartmentuniqueid(noticeRequestMapper.apartmentKey);
            notices = noticeRepository.findAllByApartmentkey(apartment.getId());
        }
        GetNoticeRequestMapper getNoticeRequestMapper = new GetNoticeRequestMapper();
        for (Notice notice : notices) {
            Users user = loginRepository.findByEmailid(notice.userid);
            getNoticeRequestMapper.setNoticeResponceModels(notice, user);
        }
        return getNoticeRequestMapper.noticeResponceModels;
    }

}
