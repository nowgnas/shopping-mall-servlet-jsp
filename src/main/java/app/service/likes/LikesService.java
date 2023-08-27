package app.service.likes;

import app.dto.likes.response.LikesListWithPagination;
import app.entity.ProductAndMemberCompositeKey;
import java.util.List;

public interface LikesService {

  LikesListWithPagination getMemberLikes(Long memberId, Integer curPage) throws Exception;

  boolean getMemberProductLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) throws Exception;
  int addLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey);
  int removeLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey);
  int removeSomeLikes(List<ProductAndMemberCompositeKey> keyList);
}
