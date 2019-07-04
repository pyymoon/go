package com.start.go.reposity;

import com.start.go.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserReposity extends CrudRepository<User,Integer> {
}
