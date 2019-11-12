package by.pankov.simple.controller;

import by.pankov.simple.dto.AgeRq;
import by.pankov.simple.dto.CreateUserRq;
import by.pankov.simple.dto.Identification;
import by.pankov.simple.model.response.ServiceResponse;
import by.pankov.simple.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceResponse> createUser(@RequestBody CreateUserRq userRq) {
        ServiceResponse serviceResponse = userService.createUser(userRq);
        return ResponseEntity.ok(serviceResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ServiceResponse> authenticate(@ModelAttribute Identification identification) {
        ServiceResponse identifyUser = userService.identifyUser(identification);
        return ResponseEntity.ok(identifyUser);
    }


    @ApiImplicitParam(name = "Authorization", value = "Authorization token",
            required = true, dataType = "string", paramType = "header")
    @GetMapping("/statistic/names")
    public ResponseEntity<ServiceResponse> showUserNamesPage(@ModelAttribute AgeRq age) {
        ServiceResponse userNames = userService.findAllUserNamesByAge(age.getAge());
        return ResponseEntity.ok(userNames);
    }
}