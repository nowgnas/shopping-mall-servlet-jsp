package app.service.likes;

import app.dto.comp.ProductAndMemberCompositeKey;
import app.dto.product.ProductListItemOfLike;
import java.util.List;

public interface LikesService {
  List<ProductListItemOfLike> getMemberLikes(Long memberId) throws Exception;
  boolean getMemberProductLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) throws Exception;
  int addLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) throws Exception;
  int removeLikes(ProductAndMemberCompositeKey productAndMemberCompositeKey) throws Exception;
  int removeSomeLikes(List<ProductAndMemberCompositeKey> keyList) throws Exception;
}
