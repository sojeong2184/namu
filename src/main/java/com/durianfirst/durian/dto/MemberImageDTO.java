package com.durianfirst.durian.dto;


import com.durianfirst.durian.entity.Member;
import com.durianfirst.durian.entity.MemberImg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberImageDTO {

    private String mimid;
    private String mimname;
    private String mimemail;
    private MultipartFile orimgname;
    private List<String> mimUrl;

    public MemberImageDTO(Member member){
        this.mimid = member.getMid();
        this.mimname = member.getMname();
        this.mimemail = member.getMemail();
        this.mimUrl = member.getMemberImgs().stream().map(MemberImg :: getMimgurl).collect(Collectors.toList());

    }

}
