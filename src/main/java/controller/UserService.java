package controller;

import modle.User;

public interface UserService {
    boolean addTask(User user);
    boolean deleteTask(String id);
}
