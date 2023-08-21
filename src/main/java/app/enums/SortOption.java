package app.enums;

import lombok.Getter;

@Getter
public enum SortOption {
  PRICE_DESC("price", "desc"),
  PRICE_ASC("price", "asc"),
  DATE_DESC("created_at", "desc");
  private String type;
  private String desc;

  SortOption(String type, String desc) {
    this.type = type;
    this.desc = desc;
  }
}
