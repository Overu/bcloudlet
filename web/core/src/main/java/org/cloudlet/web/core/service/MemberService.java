package org.cloudlet.web.core.service;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.Member;
import org.cloudlet.web.core.server.MemberServiceImpl;

@ImplementedBy(MemberServiceImpl.class)
public interface MemberService extends EntryService<Member> {
}
