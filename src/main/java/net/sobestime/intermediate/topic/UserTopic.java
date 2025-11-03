package net.sobestime.intermediate.topic;

import lombok.RequiredArgsConstructor;
import net.sobestime.intermediate.dto.UserRegisterEvent;
import net.sobestime.interview.service.InterviewUserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTopic {

    private final InterviewUserService interviewUserService;

    public void sendUserRegisterEvent(UserRegisterEvent event) {
        interviewUserService.saveUser(event);
    }
}
