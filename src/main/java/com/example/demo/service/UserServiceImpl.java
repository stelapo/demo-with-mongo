package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        user.setUserId(-1l);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        return userRepository.findById(userId)
                .map(
                        (userToUpdate) -> {
                            user.setUserId(userToUpdate.getUserId());
                            return userRepository.save(user);
                        }
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found with id " + userId)
                )
                ;
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found with id " + userId)
                )
                ;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .map(
                        user -> {
                            userRepository.delete(user);
                            return "OK";
                        }
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found with id " + userId)
                )
        ;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findByNameAndSurname(String name, String surname, Pageable pageable) {
        /*UserSpecification specName =
                new UserSpecification(new SearchCriteria("name", ":", name));
        UserSpecification surName =
                new UserSpecification(new SearchCriteria("surname", ":", surname));

        return userRepository.findAll(Specification.where(specName).and(surName), pageable);*/

        return userRepository.findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(name==null?"":name, surname==null?"":surname, pageable);

    }

    @Override
    public Page<User> findBySearchString(String searchString, Pageable pageable) {
        /*UserSpecificationsBuilder builder = new UserSpecificationsBuilder();
        Pattern pattern = Pattern.compile(SearchCriteria.searchStringPattern);
        Matcher matcher = pattern.matcher(searchString);
        while (matcher.find()) {
            builder.addSearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<User> spec = builder.build();
        return userRepository.findAll(spec, pageable);*/

        return userRepository.findAll(pageable);
    }
}
