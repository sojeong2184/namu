package com.durianfirst.durian.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberJoinDTO {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    @Length(min = 4, max =15 ,message ="아이디는 4자 이상, 15자 이하로 입력해주세요" )
    private String mid;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min = 8, max = 16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요")
    private String mpw;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String memail;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String mname;

    @NotBlank(message = "생일은 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9]*$", message = "생일은 숫자만 입력 가능합니다.")
    private String mbirthday;
    private String maddress;

    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9]*$", message = "핸드폰 번호는 숫자만 입력 가능합니다.")
    private String mphone;

    private boolean mdel;

    private boolean msocial;

    // 새로 추가된 부분
    private List<String> roles;
    public MemberJoinDTO(String mid, String mpw,String mname, String memail,
                         String mbirthday,String maddress,String mphone,
                         Boolean mdel,Boolean msocial, List<String> roles) {
        this.mid = mid;
        this.mpw = mpw;
        this.memail = memail;
        this.mname = mname;
        this.mbirthday = mbirthday;
        this.maddress = maddress;
        this.mphone = mphone;
        this.mdel = mdel;
        this.msocial = msocial;
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

}
