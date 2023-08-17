package app.service.likes;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.dto.likes.response.MemberLikesResponseDto;
import java.util.List;

public class ProductLikesService implements LikesService {

  @Override
  public List<MemberLikesResponseDto> getMemberLikes(Long memberId) {
    return null;
  }

  @Override
  public boolean getMemberProductLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) {
    return false;
  }

  @Override
  public int addLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) {
    return 0;
  }

  @Override
  public int removeLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) {
    return 0;
  }
}
