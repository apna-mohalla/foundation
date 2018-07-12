package apna.Maholla.mappers;

import apna.Maholla.ResponceModel.NoticeResponceModel;
import apna.Maholla.model.Notice;
import apna.Maholla.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetNoticeRequestMapper {
    public List<NoticeResponceModel> noticeResponceModels = new ArrayList<>();

    @Autowired
    public GetNoticeRequestMapper() {
    }

    public void setNoticeResponceModels(Notice notice, Users user) {
        NoticeResponceModel noticeResponceModel = new NoticeResponceModel();
        if (user != null) {
            noticeResponceModel.username = user.name;
            noticeResponceModel.emailId = user.emailid;
        }
        noticeResponceModel.date = notice.date;
        noticeResponceModel.discription = notice.description;
        noticeResponceModel.title = notice.subject;
        this.noticeResponceModels.add(noticeResponceModel);
    }
}
