package ru.nixson.sprint.service;

import ru.nixson.sprint.dto.BurnDto;

import java.security.Principal;

public interface BurnService {
    BurnDto getBurnDownByPrincipal(Principal principal);
}
