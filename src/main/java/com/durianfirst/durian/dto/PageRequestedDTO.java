package com.durianfirst.durian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestedDTO {//페이징관련정보 지정
    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private String type; // 검색의 종류 t,c,w,tc,tw,twc

    private String keyword;

    public String[] getTypes() { //검색조건 question리포지토리에서 String[]으로처리함. 때문에 type이라는 문자열을 배열로 반환해주는 기능필요
        if (type == null || type.isEmpty()) {
            return null;
        }
        return type.split("");

    }

    public Pageable getPageable(String... props) { //페이징처리를 위해 사용하는 pageable타입을 반환하는 기능구현
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    private String link;

    public String getLink () { //검색조건과 페이징조건 등을 문자열로 구성하는 getLink()

        if(link == null) {
            StringBuilder builder = new StringBuilder();

            builder.append("page=" + this.page);

            builder.append("&size=" + this.size);

            if (type != null && type.length() > 0) {
                builder.append("&type=" + type);
            }
            if (keyword != null) {
                try {
                    builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                }
            }
            link = builder.toString();
        }

        return link;

    }
}


