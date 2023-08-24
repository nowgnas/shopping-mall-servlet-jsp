package app.service.likes;

import app.entity.ProductAndMemberCompositeKey;
import java.util.List;

public interface LikesService {
//  List<MemberLikesResponseDto> getMemberLikes(Long memberId) throws Exception;
  boolean getMemberProductLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey);
  int addLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey);
  int removeLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey);
  int removeSomeLikes(List<ProductAndMemberCompositeKey> keyList);
}
