package app.service.likes;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.dto.product.ProductListItemOfLike;
import java.util.List;

public interface LikesService {
  List<ProductListItemOfLike> getMemberLikes(Long memberId) throws Exception;
  boolean getMemberProductLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey);
  int addLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey);
  int removeLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey);
  int removeSomeLikes(List<ProductAndMemberCompositeKey> keyList);
}
