package br.com.tdd.api.services.impl;

import br.com.tdd.api.domain.User;
import br.com.tdd.api.domain.dto.UserDTO;
import br.com.tdd.api.repositories.UserRepository;
import br.com.tdd.api.services.exceptions.DataIntegrityViolationException;
import br.com.tdd.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID      = 1;
    public static final String NAME     = "Tiago";
    public static final String EMAIL    = "tiago@email.com";
    public static final String PASSWORD = "123";

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private UserDTO userDTO;
    private Optional<User> userOptional;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(userOptional);

        User response = service.findById(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenFindByIdThenReturnObjectNotFoundException() {
        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenThrow(new ObjectNotFoundException("User not found!"));
        try {
            service.findById(ID);
        } catch (Exception ex) {
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals("User not found!", ex.getMessage());
        }
    }

    @Test
    void whenFindAllReturnAListOfUsers() {
        Mockito.when(service.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(User.class, response.get(0).getClass());
        Assertions.assertEquals(ID, response.get(0).getId());
        Assertions.assertEquals(NAME, response.get(0).getName());
        Assertions.assertEquals(EMAIL, response.get(0).getEmail());


    }

    @Test
    void whenCreatingAnUserThenReturnSuccess() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        User response = service.create(userDTO);

        Assertions.assertNotNull(response);

        Assertions.assertEquals(User.class, user.getClass());

        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenCreatingAnUserThenReturnDataIntegrityViolationException() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(userOptional);

        try {
            userOptional.get().setId(2);
            service.create(userDTO);
        } catch (Exception ex) {
            Assertions.assertEquals(DataIntegrityViolationException.class, ex.getClass());
            Assertions.assertEquals("e-mail already registered!", ex.getMessage());
        }

    }

    @Test
    void whenUpdatingAnUserThenReturnSuccess() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        User response = service.update(userDTO);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, user.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }

    @Test
    void whenUpdatingAnUserThenReturnDataIntegrityViolationException() {
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(userOptional);

        try {
            userOptional.get().setId(2);
            service.update(userDTO);
        } catch (Exception ex) {
            Assertions.assertEquals(DataIntegrityViolationException.class, ex.getClass());
            Assertions.assertEquals("e-mail already registered!", ex.getMessage());
        }

    }

    @Test
    void whenDeletingReturnSuccess() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(userOptional);
        Mockito.doNothing().when(repository).deleteById(Mockito.anyInt());

        service.delete(ID);
        Mockito.verify(repository, Mockito.times(1))
                .deleteById(Mockito.anyInt());
    }

    @Test
    void whenDeletingReturnObjectNotFoundException() {
        Mockito.when(repository.findById(Mockito.anyInt()))
                .thenThrow(new ObjectNotFoundException("User not found!"));
        try {
            service.delete(ID);
        } catch (Exception ex) {
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals("User not found!", ex.getMessage());
        }
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        userOptional = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }

}