package app.service.checker;

import app.dao.DaoFrame;
import app.dao.member.MemberDaoFrame;
import app.entity.Member;
import app.exception.ErrorCode;
import app.exception.member.MemberNotFoundException;
import app.utils.GetSessionFactory;
import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;


@AllArgsConstructor
public class MemberExistCheckerService implements
    EntityExistCheckerService<Long, Member> {

  private MemberDaoFrame<Long, Member> memberDao;


  @Override
  public Member isExisted(DaoFrame<Long, Member> daoFrame, Long id, SqlSession session)
      throws Exception {
    session = GetSessionFactory.getInstance().openSession();
    return memberDao.selectById(id, session).orElseThrow(() -> new MemberNotFoundException(
        ErrorCode.MEMBER_NOT_FOUND));
  }
}
