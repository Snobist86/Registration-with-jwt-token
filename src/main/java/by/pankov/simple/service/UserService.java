package by.pankov.simple.service;

import by.pankov.simple.dto.CreateUserRq;
import by.pankov.simple.dto.Identification;
import by.pankov.simple.model.response.ServiceResponse;

public interface UserService {

   ServiceResponse createUser(CreateUserRq userRq);

   ServiceResponse identifyUser(Identification identification);

   ServiceResponse findAllUserNames();

   ServiceResponse findAllUserNamesByAge(Integer minAge);
}
