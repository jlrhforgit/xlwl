package com.jlrh.heagle.server.service;

import com.jlrh.heagle.server.domain.UUser;

public interface UUserService {

	UUser findUserByEmail(String email);

	UUser insert(UUser entity);

}
