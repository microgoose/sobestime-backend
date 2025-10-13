package net.microgoose.mocknet.intermediate.topic;

import lombok.RequiredArgsConstructor;
import net.microgoose.mocknet.intermediate.dto.UserRegisterEvent;
import net.microgoose.mocknet.interview.service.InterviewUserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTopic {

    private final InterviewUserService interviewUserService;

    public void onUserRegister(UserRegisterEvent event) {
        interviewUserService.saveUser(event);
    }
}
