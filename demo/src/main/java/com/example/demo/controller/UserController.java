package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.models.CleanOrganisationDTO;
import com.example.demo.dto.models.CleanVolunteerDTO;
import com.example.demo.dto.models.UserResponseDTO;
import com.example.demo.model.Organisation;
import com.example.demo.model.Volunteer;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.model.User;
import com.example.demo.service.UserService;


@RestController
//@CrossOrigin(origins = "http://localhost:3000",maxAge = 3600, allowedHeaders = "*", methods = "*")
@RequestMapping("/api/v1/user")
public class UserController {
    
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/register-volunteer")
//    public ResponseEntity<MessageResponse> createVolunteer(@RequestBody RegisterVolunteerDTO v) {
//        User u = new Volunteer(v.getFullName(), v.getGender(), v.getAge(), v.getEmail(), v.getContactNo(), v.getPassword(), v.getRole());
//        User newUser = userService.createUser(u);
//        MessageResponse res = null;
//        if (newUser == null) {
//            res = new MessageResponse("registration failed", 400);
//        } else {
//            res = new MessageResponse("registration success", 200);
//        }
//        return new ResponseEntity<>(res, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/register-organisation")
//    public ResponseEntity<User> createOrganisation(@RequestBody RegisterOrganisationDTO o) {
//        System.out.println(o.getEmail());
//        System.out.println(o.getFullName());
//        System.out.println(o.getPassword());
//        User u = new Organisation(o.getEmail(), o.getFullName(), o.getPassword(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription());
//        User newUser = userService.createUser(u);
//        MessageResponse res = null;
//        if (newUser == null) {
//            res = new MessageResponse("registration failed", 400);
//        } else {
//            res = new MessageResponse("registration success", 200);
//        }
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<MessageResponse> loginUser(@RequestBody AuthenticationDTO authenticationDTO) {
////        System.out.println(payload);
//        User user = userService.authenticateUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
//        MessageResponse response = null;
//        if (user != null) {
//            char userType = 'A';
//            if (user instanceof Organisation) {
//                userType = 'O';
//            } else if (user instanceof Volunteer) {
//                userType = 'V';
//            }
//            response = new UserResponse("login success", 200, user, userType);
//        } else {
//            response = new MessageResponse("login failure", 400);
//        }
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("/{userid}")
    public ResponseEntity<Object> getUser(@PathVariable String userid) {
        User user = userService.getUser(userid);
        Object res = null;
        if (user instanceof Volunteer) {
            Volunteer v = (Volunteer) user;
            res = new CleanVolunteerDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(), v.getGender(), v.getDob(), v.getHours(), v.getPoints(), v.getPfp().getFilepath());
        } else if (user instanceof Organisation) {
            Organisation o = (Organisation) user;
            res = new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), o.getPfp().getFilepath());

        }
//        System.out.println(user.getEventsPart());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResponseDTO> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseDTO res = new ResponseDTO("user not found", 400);
        System.out.println(user);
        if (user != null) {
            if (user instanceof Volunteer) {
                res = new UserResponseDTO("user found", 200, user.getEmail(), user.getFullName(), user.getComplainCount(), user.getContactNo(), ((Volunteer) user).getGender(), ((Volunteer) user).getDob(), ((Volunteer) user).getHours(), ((Volunteer) user).getPoints(), 'V');
            } else if (user instanceof Organisation) {
                res = new UserResponseDTO("user found", 200, user.getEmail(), user.getFullName(), user.getComplainCount(), user.getContactNo(), ((Organisation) user).getLocation(), ((Organisation) user).getWebsite(), ((Organisation) user).getDescription(), 'O');
            } else {
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        User user = userService.updatePassword(changePasswordDTO.getEmail(), changePasswordDTO.getOldPassword(), changePasswordDTO.getNewPassword());
        Object res = null;
        if (user instanceof Volunteer) {
            Volunteer v = (Volunteer) user;
            res = new CleanVolunteerDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(), v.getGender(), v.getDob(), v.getHours(), v.getPoints(), v.getPfp().getFilepath());
        } else if (user instanceof Organisation) {
            Organisation o = (Organisation) user;
            res = new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), o.getPfp().getFilepath());

        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('VOLUNTEER', 'ORGANISATION')")
    public ResponseEntity<Object> deleteUser(@RequestBody AuthenticationDTO authenticationDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuth = (User) authentication.getPrincipal();
        User user = userService.deleteUser(authenticationDTO.getEmail(), authenticationDTO.getPassword());
        if (userAuth == null || user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Object res = null;
        if (user instanceof Volunteer) {
            Volunteer v = (Volunteer) user;
            res = new CleanVolunteerDTO(v.getEmail(), v.getFullName(), v.getComplainCount(), v.getContactNo(), v.getGender(), v.getDob(), v.getHours(), v.getPoints(), v.getPfp().getFilepath());
        } else if (user instanceof Organisation) {
            Organisation o = (Organisation) user;
            res = new CleanOrganisationDTO(o.getEmail(), o.getFullName(), o.getComplainCount(), o.getContactNo(), o.getLocation(), o.getWebsite(), o.getDescription(), o.getPfp().getFilepath());

        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    // @PostMapping("/{eventId}/register-event")
    // public ResponseEntity<ResponseDTO> registerEvent(@PathVariable String eventId, @RequestBody JwtDTO jwtDTO) {
    //     boolean registerSuccess = userService.registerEvent(eventId, jwtDTO.getToken());
    //     if (registerSuccess) {
    //         return new ResponseEntity<>(new ResponseDTO("register success", 200), HttpStatus.OK);
    //     }
    //     return new ResponseEntity<>(new ResponseDTO("register failure", 400), HttpStatus.OK);
    // }


//    @GetMapping("/find/{email}")
//    public ResponseEntity<User> getUser(@PathVariable String email) {
//        User user = userService.getUser(email);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }



//    @PutMapping("/updateTest")
//    public ResponseEntity<User> updateUser(@RequestBody Map<String, String> payload) {
//        User user = userService.updateHours(payload.get("email"), Double.parseDouble(payload.get("hours")));
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }


    // @PostMapping("/register-event")
    // @PreAuthorize("hasRole('VOLUNTEER')")
    // public ResponseEntity<ResponseDTO> registerEvent(@RequestBody RegisterForEventDTO registerForEventDTO) {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     User user = (User) authentication.getPrincipal();
    //     ResponseDTO res = new ResponseDTO("event registration unsucessful", 400);
    //     if (user instanceof Volunteer) {
    //         //update volunteer's event list
    //         userService.updateEventsParticipated(registerForEventDTO.getEventId(), registerForEventDTO.getUserId());
    //         res = new ResponseDTO("event registration sucessful", 200);
    //     }

    //     return new ResponseEntity<>(res, HttpStatus.CREATED);
    // }

    // @PostMapping("/register/event/{eventId}")
    // @PreAuthorize("hasRole('VOLUNTEER')")
    // public ResponseEntity<ResponseDTO> registerEvent(@PathVariable String eventId, @RequestBody String userId) {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     User user = (User) authentication.getPrincipal();
    //     ResponseDTO res = new ResponseDTO("event registration unsucessful", 400);
    //     if (user instanceof Volunteer) {
    //         //update volunteer's event list
    //         userService.updateEventsParticipated(eventId, userId);
    //         res = new ResponseDTO("event registration sucessful", 200);
    //     }

    //     return new ResponseEntity<>(res, HttpStatus.CREATED);
    // }

}
