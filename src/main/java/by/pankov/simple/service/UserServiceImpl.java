package by.pankov.simple.service;

import by.pankov.simple.dto.CreateUserRq;
import by.pankov.simple.dto.Identification;
import by.pankov.simple.model.User;
import by.pankov.simple.model.response.ServiceResponse;
import by.pankov.simple.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public ServiceResponse createUser(CreateUserRq userRq) {
        User savedUser = userRepository.save(User.builder()
                .firstName(getParameter(userRq.getFirstName()))
                .lastName(getParameter(userRq.getLastName()))
                .age(userRq.getAge())
                .email(getParameter(userRq.getEmail()))
                .password(getParameter(userRq.getPassword()))
                .build());
        return new ServiceResponse("Success!", savedUser);
    }

    @Override
    public ServiceResponse identifyUser(Identification identification) {
        String email = getParameter(identification.getEmail());
        String password = getParameter(identification.getPassword());
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);
        if (!user.isPresent()){
            return new ServiceResponse("User not found!", null);
        }
        return new ServiceResponse("Token", tokenService.generateToken(email), LocalDateTime.now());
    }

    @Override
    public ServiceResponse findAllUserNames() {
        List<String> names = getFirstNames(userRepository.findAll());
        return new ServiceResponse("All usernames", names);
    }

    @Override
    public ServiceResponse findAllUserNamesByAge(Integer minAge) {
        List<String> names = getFirstNames(userRepository.findAllByAgeAfter(minAge));
        return new ServiceResponse("All usernames", names);
    }

    private String getParameter(String parameter) {
        return Optional.ofNullable(parameter).filter(StringUtils::isNotEmpty)
                .orElseThrow(() -> new IllegalArgumentException("Ad illegal argument value"));
    }

    private List<String> getFirstNames(List<User> users){
        return users.stream()
                .filter(Objects::nonNull)
                .map(User::getFirstName)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList());
    }
}