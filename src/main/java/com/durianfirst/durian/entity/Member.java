package com.durianfirst.durian.entity;

import com.durianfirst.durian.constant.MemberRole;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="member")
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"roleSet", "question", "answers"})
public class Member extends BaseEntity {

    /*@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long mno;*/

    @Id
    @Column(nullable = false, unique = true)
    private String mid; //아이디

    @Column(nullable = false)
    private String mpw; //비밀번호

    @Column(nullable = false)
    private String mname; //이름

    @Column(nullable = false)
    private String memail; //이메일

    @Column(nullable = false)
    private String mbirthday; //생년월일

    @Column(nullable = false)
    private String mphone; //전화번호

    private String maddress; //주소

    private boolean msocial; //소셜로그인 여부

    private boolean mdel; //회원탈퇴 여부

    /*private boolean mnational; //내,외국인 구분
    private String mrecommend; //추천인 아이디*/

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<MemberImg> memberImgs;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "member_role", joinColumns = @JoinColumn(name = "mid"))
    @Column(name = "role")
    private List<String> roles;

    /* 회원 정보 수정 */
    public void changeName(String mname) { this.mname = mname; }
    public void changePassword(String mpw){
        this.mpw = mpw;
    }

    public void changeEmail(String memail){
        this.memail = memail;
    }

    public void changePhone(String mphone) { this.mphone = mphone; }

    public void changeAddress(String maddress) { this.maddress = maddress; }

    public void changeDel(boolean mdel){
        this.mdel = mdel;
    }

    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    public void clearRoles(){
        this.roleSet.clear();
    }

    public void changeSocial(boolean msocial){
        this.msocial = msocial;
    }

}