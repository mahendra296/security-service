package com.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.security.dto.CurrentLoggedInUser;
import com.security.exceptions.InvalidRequestException;
import com.security.model.User;
import com.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Component
@Service
public class CommonUtils {

    @Autowired
    private static UserRepository userRepository;

    public CommonUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static CurrentLoggedInUser getCurrentLoggedInUser(Authentication authentication) throws JsonProcessingException {

        Object principal = authentication.getPrincipal();
        UserDetails UserDetails = ((UserDetails) principal);
        User user = userRepository.findByEmail(UserDetails.getUsername());
        Boolean isAdmin = UserDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return CurrentLoggedInUser.build(user, isAdmin);
    }

    public static void validateUser(CurrentLoggedInUser user, Long userId) {
        if (!user.getId().equals(userId)) {
            throw new InvalidRequestException("User is not valid.");
        }
    }

    public static boolean isEmailIsValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
