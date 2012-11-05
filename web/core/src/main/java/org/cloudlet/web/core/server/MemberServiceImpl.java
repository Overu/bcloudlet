package org.cloudlet.web.core.server;

import com.google.inject.Singleton;

import org.cloudlet.web.core.Member;
import org.cloudlet.web.core.service.MemberService;

@Singleton
public class MemberServiceImpl extends EntryServiceImpl<Member> implements MemberService {

}
