package org.cloudlet.web.core.shared;

import com.google.inject.ImplementedBy;

import org.cloudlet.web.core.server.MemberServiceImpl;

@ImplementedBy(MemberServiceImpl.class)
public interface MemberService extends ResourceService<Member> {
}
