package com.yoyo.member.adapter.out.persistence;

import com.yoyo.member.adapter.out.persistence.entity.MemberJpaEntity;
import com.yoyo.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member mapToDomainEntity(MemberJpaEntity memberJpaEntity) {
        return Member.generateMember(
                new Member.MemberId(memberJpaEntity.getMemberId()),
                new Member.MemberName(memberJpaEntity.getName()),
                new Member.MemberPhoneNumber(memberJpaEntity.getPhoneNumber()),
                new Member.MemberPassword(memberJpaEntity.getPassword()),
                new Member.MemberBirthDay(memberJpaEntity.getBirthDay()),
                new Member.MemberIsValid(memberJpaEntity.isValid()),
                new Member.MemberRefreshToken(memberJpaEntity.getRefreshToken())
        );
    }
}
